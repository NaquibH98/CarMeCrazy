package com.example.carmecrazy.model;

public class Car {

	private int CarID;
	private String Car_Brand;
	private String Car_Name;
	private String Car_Price;
	private String Car_PlateNo;
	private String Car_Image;

	public Car() {}

	public int getCarID() {
		return CarID;
	}

	public void setCarID(int carID) {
		CarID = carID;
	}

	public String getCar_Brand() {
		return Car_Brand;
	}

	public void setCar_Brand(String car_Brand) {
		Car_Brand = car_Brand;
	}

	public String getCar_Name() {
		return Car_Name;
	}

	public void setCar_Name(String car_Name) {
		Car_Name = car_Name;
	}

	public String getCar_Price() {
		return Car_Price;
	}

	public void setCar_Price(String car_Price) {
		Car_Price = car_Price;
	}

	public String getCar_Car_PlateNo() {
		return Car_PlateNo;
	}

	public void setCar_PlateNo(String car_PlateNo) {
		Car_PlateNo = car_PlateNo;
	}


	public String getCar_Image() {
		return Car_Image;
	}

	public void setCar_Image(String car_Image) {
		Car_Image = car_Image;
	}

	@Override
    	public String toString() {
        return "Car{" +
                "CarID=" + CarID +
                ", Car_Brand='" + Car_Brand + '\'' +
                ", Car_Name='" + Car_Name + '\'' +
                ", Car_Price=" + Car_Price +
                ", Car_PlateNo='" + Car_PlateNo + '\'' +
                ", Car_Image='" + Car_Image + '\'' +
                '}';
    	}
}
