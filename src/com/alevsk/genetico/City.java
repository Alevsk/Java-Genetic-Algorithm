package com.alevsk.genetico;

import java.awt.Point;

public class City {
	
	private Point CityPosition;
	private String cityName;
	
	City(String name, Point coordinate)
	{
		cityName = name;
		CityPosition = coordinate;
	}
	
	String getCityName()
	{
		return cityName;
	}
		
	Point getCityPosition()
	{
		return CityPosition;
	}

}
