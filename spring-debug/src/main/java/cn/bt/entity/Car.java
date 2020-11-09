package cn.bt.entity;

/**
 * @Author: zhouqian
 * @Date: 2020/11/9 15:28
 */
public class Car {
	private String naem;
	private String brand;
	private Integer speed;

	public String getNaem() {
		return naem;
	}

	public void setNaem(String naem) {
		this.naem = naem;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	
	@Override
	public String toString() {
		return "Car{" +
				"naem='" + naem + '\'' +
				", brand='" + brand + '\'' +
				", speed=" + speed +
				'}';
	}
}
