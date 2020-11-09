package cn.bt.factorybean;

import cn.bt.entity.Car;
import org.springframework.beans.factory.FactoryBean;

/**
 * @Author: zhouqian
 * @Date: 2020/11/9 15:49
 */
public class CarFactoryBean implements FactoryBean<Car> {
	private String carInfo;

	public String getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}

	@Override
	public Car getObject() throws Exception {
		String[] carInfo = this.carInfo.split(",");
		Car car = new Car();
		car.setNaem(carInfo[0]);
		car.setBrand(carInfo[1]);
		car.setSpeed(Integer.valueOf(carInfo[2]));
		return car;
	}

	@Override
	public Class<?> getObjectType() {
		return Car.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
