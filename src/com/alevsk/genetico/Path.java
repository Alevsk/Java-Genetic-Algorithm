package com.alevsk.genetico;

import java.util.ArrayList;
import java.util.List;

public class Path implements Cloneable{
	public List<City> path;
	private double totalDistance;
	
	Path()
	{
		path = new ArrayList<City>();
		totalDistance = 0;
	}
	
	City getCityFromPath(int index)
	{
		return path.get(index);
	}
	
	public Object clone() 
	{ 
		Object clone = null; 
		try { 
			clone = super.clone(); 
		} catch(CloneNotSupportedException e) {
			
		} 
		return clone; 		
	}
		
	void CalculateEuclideanDistance()
	{
		double EuclideanDistance = 0;
		for(int i = 0; i < path.size(); i++)
		{
			if(!(i == path.size() - 1))
			{
				EuclideanDistance += Math.sqrt(Math.pow(((path.get(i+1).getCityPosition().x - 
						path.get(i).getCityPosition().x)),2) + 
						Math.pow(((path.get(i+1).getCityPosition().y - 
						path.get(i).getCityPosition().y)),2));
			}
			else
			{
				EuclideanDistance += Math.sqrt(Math.pow(((path.get(0).getCityPosition().x - 
						path.get(i).getCityPosition().x)),2) + 
						Math.pow(((path.get(0).getCityPosition().y - 
						path.get(i).getCityPosition().y)),2));
			}
			totalDistance = EuclideanDistance;
		}
	}
	
	double getEuclideanDistance()
	{
		return totalDistance;
	}
	
	void addCity(City name)
	{
		path.add(name);
	}
	
	void printPath()
	{
		for(int i = 0; i < path.size(); i++)
		{
			System.out.print(""+path.get(i).getCityName()+"\t");
		}
		//System.out.print(totalDistance+"\n");
		System.out.print(totalDistance);
	}
	void printPath2()
	{
		for(int i = 0; i < path.size(); i++)
		{
			System.out.print(""+path.get(i).getCityName()+"\t");
		}
		System.out.print(totalDistance+"\n");
	}
}
