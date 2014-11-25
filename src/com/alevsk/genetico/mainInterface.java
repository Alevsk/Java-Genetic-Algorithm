package com.alevsk.genetico;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class mainInterface {
	//((1,1) (2,2) (3,3) (4,5) (5,6) (8,2) (9,0) (8,4) (21,4) (23,1) (8,3) (11,34))
	static String WelcomeMessage = "Escribe las coordenadas de las ciudades con el sig. formato:\nEjemplo: ((1,1) (2,2) (3,3) (4,5))";
	static boolean error = true;
	static GeneticsAlgorithm tsp;
	
	public static void parseStringToCoordinates(String From)
	{
		if(From.length() > 2 && From.charAt(0) == '(' && From.charAt(From.length() - 1) == ')')
		{
			System.out.println("Es una cadena valida");
			From = From.substring(1,From.length() - 1);
			String tmp[] = From.split(" ");
			tsp = new GeneticsAlgorithm(10);
			
			for(int i = 0; i < tmp.length; i++)
			{
				tmp[i] = tmp[i].replace("(","").replace(")","");
				
				Pattern p = Pattern.compile("[0-9]+(,[0-9]+)");
			    Matcher m = p.matcher(tmp[i]);
			    
			    if(m.find())
			    {
			    	/*String temporal = "";
			    	temporal = tmp[i].substring(0, tmp[i].indexOf(','));
			    	temporal = tmp[i].substring(tmp[i].indexOf(',') + 1, tmp[i].length()); */
			    	
			    	Point Coordinate = new Point(Integer.parseInt(tmp[i].substring(0, tmp[i].indexOf(','))), Integer.parseInt(tmp[i].substring(tmp[i].indexOf(',') + 1, tmp[i].length())));
			    	tsp.setNew(Coordinate);
			    }	
			    else
			    {
			    	System.out.println("No es una cadena valida");
			    	error = false;
			    	return;
			    }
			}
			tsp.createPaths();
			tsp.LaunchSimulation();
			error = false;
		}
		else
		{
			System.out.println("No es una cadena valida");
			error = true;
		}
	}
	
	public static void main(String[] args)
	{
		String userInput = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
		while(error)
		{
			System.out.println(WelcomeMessage);
			
			try{ 
				 userInput = br.readLine();
			} catch (IOException ioe){ 
				 System.out.println("Error de entrada de datos");
			}
			
			parseStringToCoordinates(userInput);
		}	
	}
}