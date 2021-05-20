package cn.bt.selfCustomAutowired;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 第一种方法直接注册BPP
 *
 * @author zhouqian
 * @create 2021-05-20 14:32
 */
@Component
public class Zhou_AutowiredAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor, BeanFactoryAware {

	protected final Log logger = LogFactory.getLog(getClass());

	// 该处理器支持解析的注解，仅支持自定义注解 -->
	private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(4);

	// @Zhou_Autowired(required = false)这个注解的属性值名称
	private String requiredParameterName = "required";

	// 这个值一般请不要改变（若改成false，效果required = false的作用是相反的了）
	private boolean requiredParameterValue = true;

	@Nullable
	private ConfigurableListableBeanFactory beanFactory;

	// 方法注入、字段filed注入
	// 此处InjectionMetadata这个类非常重要，到了此处@Autowired注解含义已经没有了，完全被准备成这个元数据了
	// InjectionMetadata持有targetClass、Collection<InjectedElement> injectedElements等两个重要属性
	// 其中InjectedElement这个抽象类最重要的两个实现为：AutowiredFieldElement和AutowiredMethodElement
	private final Map<String, InjectionMetadata> injectionMetadataCache = new ConcurrentHashMap<>(256);

	/**
	 * 构造方法，完成对应注解的注入
	 *
	 * Create a new {@code AutowiredAnnotationBeanPostProcessor} for Spring's
	 * standard {@link Autowired @Autowired} and {@link Value @Value} annotations.
	 * <p>Also supports JSR-330's {@link javax.inject.Inject @Inject} annotation,
	 * if available.
	 */
	@SuppressWarnings("unchecked")
	public Zhou_AutowiredAnnotationBeanPostProcessor() {
		this.autowiredAnnotationTypes.add(Zhou_Autowired.class);
	}

	/**
	 * 处理合并的bean定义信息
	 * 1、解析@Autowired等注解然后转换
	 * 2、把注解信息转换为InjectionMetadata然后缓存到上面的injectionMetadataCache里面
	 * @param beanDefinition the merged bean definition for the bean
	 * @param beanType the actual type of the managed bean instance
	 * @param beanName the name of the bean
	 */
	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
		// 解析注解并缓存
		InjectionMetadata metadata = findAutowiringMetadata(beanName, beanType, null);
		metadata.checkConfigMembers(beanDefinition);
	}

	@Override
	public void resetBeanDefinition(String beanName) {

	}

	// bean工厂必须是ConfigurableListableBeanFactory的
	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
			throw new IllegalArgumentException(
					"AutowiredAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
		}
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	/**
	 *  方法名为查找到该bean的依赖注入元信息，内部只要查找到了就会加入到缓存内，下次没必要再重复查找了~
	 * 	它是一个模版方法，真正做事的方法是：buildAutowiringMetadata，它复杂把标注有@Autowired注解的属性转换为Metadata元数据信息，从而消除注解的定义
	 * 	此处查找包括了字段依赖注入和方法依赖注入~~~
	 * @param beanName
	 * @param clazz
	 * @param pvs
	 * @return
	 */
	private InjectionMetadata findAutowiringMetadata(String beanName, Class<?> clazz, @Nullable PropertyValues pvs) {
		// Fall back to class name as cache key, for backwards compatibility with custom callers.
		String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
		// Quick check on the concurrent map first, with minimal locking.
		// 从缓存中获取该类的信息
		InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
		// 判断是否需要刷新缓存
		if (InjectionMetadata.needsRefresh(metadata, clazz)) {
			synchronized (this.injectionMetadataCache) {
				metadata = this.injectionMetadataCache.get(cacheKey);
				if (InjectionMetadata.needsRefresh(metadata, clazz)) {
					if (metadata != null) {
						metadata.clear(pvs);
					}
					// 构建自动装配的属性和方法元数据
					metadata = buildAutowiringMetadata(clazz);
					this.injectionMetadataCache.put(cacheKey, metadata);
				}
			}
		}
		return metadata;
	}

	/**
	 * 去寻找有Autowired和Value注解的属性和方法，也包括自定义的父类的，封装成AutowiredMethodElement放入集合中
	 * @param clazz
	 * @return
	 */
	private InjectionMetadata buildAutowiringMetadata(final Class<?> clazz) {
		// 如果clazz是JDK中的类，直接忽略，因为不可能标注有这些标注
		if (!AnnotationUtils.isCandidateClass(clazz, this.autowiredAnnotationTypes)) {
			return InjectionMetadata.EMPTY;
		}

		List<InjectionMetadata.InjectedElement> elements = new ArrayList<>();
		Class<?> targetClass = clazz;

		do {
			final List<InjectionMetadata.InjectedElement> currElements = new ArrayList<>();

			// 遍历类中的每个属性，判断属性是否包含指定的属性(通过 findAutowiredAnnotation 方法)
			// 如果存在则保存，这里注意，属性保存的类型是 AutowiredFieldElement
			ReflectionUtils.doWithLocalFields(targetClass, field -> {
				MergedAnnotation<?> ann = findAutowiredAnnotation(field);
				if (ann != null) {
					//Zhou_Autowired注解不支持静态方法
					if (Modifier.isStatic(field.getModifiers())) {
						if (logger.isInfoEnabled()) {
							logger.info("Autowired annotation is not supported on static fields: " + field);
						}
						return;
					}
					//查看是否是required的
					boolean required = determineRequiredStatus(ann);
					currElements.add(new Zhou_AutowiredFieldElement(field, required));
				}
			});


//			// 遍历类中的每个方法，判断属性是否包含指定的属性(通过 findAutowiredAnnotation 方法)
//			// 如果存在则保存，这里注意，方法保存的类型是 AutowiredMethodElement
//			ReflectionUtils.doWithLocalMethods(targetClass, method -> {
//				Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
//				if (!BridgeMethodResolver.isVisibilityBridgeMethodPair(method, bridgedMethod)) {
//					return;
//				}
//				MergedAnnotation<?> ann = findAutowiredAnnotation(bridgedMethod);
//				if (ann != null && method.equals(ClassUtils.getMostSpecificMethod(method, clazz))) {
//					if (Modifier.isStatic(method.getModifiers())) {
//						if (logger.isInfoEnabled()) {
//							logger.info("Autowired annotation is not supported on static methods: " + method);
//						}
//						return;
//					}
//					// 如果方法没有入参，输出日志，不做任何处理
//					if (method.getParameterCount() == 0) {
//						if (logger.isInfoEnabled()) {
//							logger.info("Autowired annotation should only be used on methods with parameters: " +
//									method);
//						}
//					}
//					boolean required = determineRequiredStatus(ann);
//					PropertyDescriptor pd = BeanUtils.findPropertyForMethod(bridgedMethod, clazz);
//					// AutowiredMethodElement里封装了一个PropertyDescriptor（比字段多了一个参数）
//					currElements.add(new AutowiredMethodElement(method, required, pd));
//				}
//			});

			// 父类的都放在第一位，所以父类是最先完成依赖注入的
			elements.addAll(0, currElements);
			targetClass = targetClass.getSuperclass();
		}
		while (targetClass != null && targetClass != Object.class);

		// InjectionMetadata就是对clazz和elements的一个包装而已
		return InjectionMetadata.forElements(elements, clazz);
	}

	/**
	 * 只要方法/属性上但凡标注有一个注解，就立马返回了
	 * @param ao
	 * @return
	 */
	@Nullable
	private MergedAnnotation<?> findAutowiredAnnotation(AccessibleObject ao) {
		MergedAnnotations annotations = MergedAnnotations.from(ao);
		for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
			MergedAnnotation<?> annotation = annotations.get(type);
			if (annotation.isPresent()) {
				return annotation;
			}
		}
		return null;
	}

	/**
	 * Determine if the annotated field or method requires its dependency.
	 * <p>A 'required' dependency means that autowiring should fail when no beans
	 * are found. Otherwise, the autowiring process will simply bypass the field
	 * or method when no beans are found.
	 * @param ann the Autowired annotation
	 * @return whether the annotation indicates that a dependency is required
	 */
	@SuppressWarnings({"deprecation", "cast"})
	protected boolean determineRequiredStatus(MergedAnnotation<?> ann) {
		// The following (AnnotationAttributes) cast is required on JDK 9+.
		return determineRequiredStatus((AnnotationAttributes)
				ann.asMap(mergedAnnotation -> new AnnotationAttributes(mergedAnnotation.getType())));
	}

	/**
	 * Determine if the annotated field or method requires its dependency.
	 * <p>A 'required' dependency means that autowiring should fail when no beans
	 * are found. Otherwise, the autowiring process will simply bypass the field
	 * or method when no beans are found.
	 * @param ann the Autowired annotation
	 * @return whether the annotation indicates that a dependency is required
	 * @deprecated since 5.2, in favor of {@link #determineRequiredStatus(MergedAnnotation)}
	 */
	@Deprecated
	protected boolean determineRequiredStatus(AnnotationAttributes ann) {
		return (!ann.containsKey(this.requiredParameterName) ||
				this.requiredParameterValue == ann.getBoolean(this.requiredParameterName));
	}

	/**
	 * Resolve the specified cached method argument or field value.
	 */
	@Nullable
	private Object resolvedCachedArgument(@Nullable String beanName, @Nullable Object cachedArgument) {
		if (cachedArgument instanceof DependencyDescriptor) {
			DependencyDescriptor descriptor = (DependencyDescriptor) cachedArgument;
			Assert.state(this.beanFactory != null, "No BeanFactory available");
			return this.beanFactory.resolveDependency(descriptor, beanName, null, null);
		}
		else {
			return cachedArgument;
		}
	}

	/**
	 * Register the specified bean as dependent on the autowired beans.
	 */
	private void registerDependentBeans(@Nullable String beanName, Set<String> autowiredBeanNames) {
		if (beanName != null) {
			for (String autowiredBeanName : autowiredBeanNames) {
				if (this.beanFactory != null && this.beanFactory.containsBean(autowiredBeanName)) {
					this.beanFactory.registerDependentBean(autowiredBeanName, beanName);
				}
				if (logger.isTraceEnabled()) {
					logger.trace("Autowiring by type from bean name '" + beanName +
							"' to bean named '" + autowiredBeanName + "'");
				}
			}
		}
	}

	/**
	 * 完成bean中@Autowired，@Inject，@Value注解的解析并注入的功能
	 * @param pvs
	 * @param bean
	 * @param beanName
	 * @return
	 */
	@Override
	public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
		// 从缓存中取出这个bean对应的依赖注入的元信息~
		InjectionMetadata metadata = findAutowiringMetadata(beanName, bean.getClass(), pvs);
		try {
			// 进行属性注入
			metadata.inject(bean, beanName, pvs);
		}
		catch (BeanCreationException ex) {
			throw ex;
		}
		catch (Throwable ex) {
			throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
		}
		return pvs;
	}

	/**
	 * Class representing injection information about an annotated field.
	 */
	private class Zhou_AutowiredFieldElement extends InjectionMetadata.InjectedElement {

		private final boolean required;

		private volatile boolean cached = false;

		@Nullable
		private volatile Object cachedFieldValue;

		public Zhou_AutowiredFieldElement(Field field, boolean required) {
			super(field, null);
			this.required = required;
		}

		/**
		 * 完成属性的注入
		 * @param bean
		 * @param beanName
		 * @param pvs
		 * @throws Throwable
		 */
		@Override
		protected void inject(Object bean, @Nullable String beanName, @Nullable PropertyValues pvs) throws Throwable {
			Field field = (Field) this.member;
			Object value;
			// 如果缓存，从缓存中获取
			if (this.cached) {
				// 如果  cachedFieldValue instanceof DependencyDescriptor。则调用 resolveDependency 方法重新加载。
				value = resolvedCachedArgument(beanName, this.cachedFieldValue);
			}
			else {
				// 否则调用了 resolveDependency 方法。这个在前篇讲过，在 populateBean 方法中按照类型注入的时候就是通过此方法，
				// 也就是说明了 @Autowired 和 @Inject默认是 按照类型注入的
				DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
				desc.setContainingClass(bean.getClass());
				Set<String> autowiredBeanNames = new LinkedHashSet<>(1);
				Assert.state(beanFactory != null, "No BeanFactory available");
				// 转换器使用的bean工厂的转换器
				TypeConverter typeConverter = beanFactory.getTypeConverter();
				try {
					// 获取依赖的value值的工作  最终还是委托给beanFactory.resolveDependency()去完成的
					// 这个接口方法由AutowireCapableBeanFactory提供，它提供了从bean工厂里获取依赖值的能力
					value = beanFactory.resolveDependency(desc, beanName, autowiredBeanNames, typeConverter);
				}
				catch (BeansException ex) {
					throw new UnsatisfiedDependencyException(null, beanName, new InjectionPoint(field), ex);
				}
				// 把缓存值缓存起来
				synchronized (this) {
					// 如果没有缓存，则开始缓存
					if (!this.cached) {
						// 可以看到value！=null并且required=true才会进行缓存的处理
						if (value != null || this.required) {
							// 这里先缓存一下 desc，如果下面 utowiredBeanNames.size() > 1。则在上面从缓存中获取的时候会重新获取。
							this.cachedFieldValue = desc;
							// 注册依赖bean
							registerDependentBeans(beanName, autowiredBeanNames);
							// autowiredBeanNames里可能会有别名的名称,所以size可能大于1
							if (autowiredBeanNames.size() == 1) {
								// beanFactory.isTypeMatch挺重要的,因为@Autowired是按照类型注入的
								String autowiredBeanName = autowiredBeanNames.iterator().next();
								if (beanFactory.containsBean(autowiredBeanName) &&
										beanFactory.isTypeMatch(autowiredBeanName, field.getType())) {
									this.cachedFieldValue = new ShortcutDependencyDescriptor(
											desc, autowiredBeanName, field.getType());
								}
							}
						}
						else {
							this.cachedFieldValue = null;
						}
						this.cached = true;
					}
				}
			}
			if (value != null) {
				// 通过反射，给属性赋值
				ReflectionUtils.makeAccessible(field);
				field.set(bean, value);
			}
		}
	}

	/**
	 * DependencyDescriptor variant with a pre-resolved target bean name.
	 */
	@SuppressWarnings("serial")
	private static class ShortcutDependencyDescriptor extends DependencyDescriptor {

		private final String shortcut;

		private final Class<?> requiredType;

		public ShortcutDependencyDescriptor(DependencyDescriptor original, String shortcut, Class<?> requiredType) {
			super(original);
			this.shortcut = shortcut;
			this.requiredType = requiredType;
		}

		@Override
		public Object resolveShortcut(BeanFactory beanFactory) {
			return beanFactory.getBean(this.shortcut, this.requiredType);
		}
	}
}
