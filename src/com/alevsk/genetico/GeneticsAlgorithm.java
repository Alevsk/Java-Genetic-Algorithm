package com.alevsk.genetico;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class GeneticsAlgorithm {
	private List<City> Map;
	private List<Path> paths;
	private int n;
	private int MaxGenerations;
	private int generation;
	private int populationSize;
	private String[] names = {"Airds","Albury","Armidale","Ashfield","Bradbury","Bankstown","Bateau-Bay","Batemans-Bay-Outreach","Bathurst","Bega","Bidwill","Blackett","Blacktown","Burwood","Campbelltown","Casino","Charlestown","Claymore","Claymore-Gumnut-Services","Coffs-Harbour","Cootamundra","Government-Office-Building","Corrimal","Cowra","Cranebrook","Dapto","Dee-Why","Dubbo","Fairfield","Glebe-Outreach","Gosford","Goulburn","Grafton","Griffith","Gunnedah","Hamilton-South","Hurstville","Inverell","Kempsey","Leeton-Outreach","Lismore","Lithgow","Liverpool","Macquarie-Fields","Maitland","Maroubra","Miller","Minto","Miranda","Moree","Moree-South-ITM","Mount-Druitt","Narrabri","Newcastle","Nowra","Orange","Parkes","Parramatta","Penrith","Port-Macquarie","Queanbeyan","Raymond-Terrace","Redfern","Riverwood","Ryde","Seven-Hills","Shellharbour","South-Coogee","Surry-Hills","Tamworth","Tamworth-ITM","Taree","Telopea-ITM","Toronto","Tumut-Outreach","Tweed-Heads","Wagga-Wagga","Waterloo","Willmot","Wollongong","Woolloomooloo","Wyong"};
	private List<Integer> bannedCities;
	
	GeneticsAlgorithm(int max)
	{
		Map = new ArrayList<City>();
		paths = new ArrayList<Path>();
		n = 0;
		generation = 0;
		populationSize = 0;
		bannedCities = new ArrayList<Integer>();
		MaxGenerations = max;
	}
	
	void setNew(Point coordinate)
	{
		City newCity = new City(names[n],coordinate);
		Map.add(newCity);
		n++;
	}
	
	void createPaths()
	{
		if((3 * n) % 2 == 0)
			populationSize = (3 * n);
		else
			populationSize = (3 * n) + 1;
		
		for(int i = 0; i < populationSize; i++)
		{
			paths.add(new Path());
			int j = 0;
			for(; j < Map.size(); j++)
			{
				paths.get(i).addCity(Map.get(GenerateNewCityBanning()));
			}
			paths.get(i).addCity(paths.get(i).getCityFromPath(0));
			paths.get(i).CalculateEuclideanDistance();
			
/*			if(isAValidPath(paths.get(i)))
				System.out.println("Genero un camino valido al iniciar");
			else
				System.out.println("Genero un camino invalido al iniciar")*/;
			
			bannedCities.clear();
		}
	}
	
	void LaunchSimulation()
	{
		for(int i = 0; i < MaxGenerations; i++)
		{
			generation++;
			//Comienza un nuevo ciclo
			//Seleccionar a los individuos mejor adaptados mediante Torneo (ordenamos la lista segun la distancia requerida para recorrer el path)
			Collections.sort(paths, new Comparator<Path>() {
                @Override
                public int compare(Path o1, Path o2) {
                        if (o1.getEuclideanDistance() < o2.getEuclideanDistance())
                                return -1;
                        else if (o1.getEuclideanDistance() > o2.getEuclideanDistance())
                                return 1;
                        else
                                return 0;
                }
			});
			
/*			for(int w = 0; w < paths.size(); w++)
			{
				paths.get(w).printPath2();
			}*/
						
			List<Path> NewPaths = new ArrayList<Path>();
			
		    double[] tmp;
		    tmp = new double[paths.size()];
		    for(int x = 0; x < tmp.length; x++){
		    	tmp[x] = paths.get(x).getEuclideanDistance();
		    }
			
			//Seleccion deterministica
       
				//Poblacion par: Ej. 10, tomo la mitad + 1 (6) y con ellos genero 5 nuevos individuos cruzandolos.
				//Despues elimino ese + 1 (fue necesario solo para el cruce) para mantener la cantidad de inviduos como en la generacion anterior
				//El + 1 consistira en el individuo peor adaptado de la poblacion ya que en probabilidad si solo cruzamos los mejores individuos convergeremos a un optimo local
				//Entonces ese + 1 tratara siempre de sacarnos de ese optimo local (Tambien es para no promover el elitismo)
				
				for(int j = 0; j < tmp.length; j++)
				{
					if((j < (tmp.length) / 4) || (j >= tmp.length / 2 && j <= (tmp.length - tmp.length / 4)))
					{
						
					}
					else
					{
						tmp[j] = -1.0;
					}
				}
											
				for(int j = 0; j < tmp.length; j++)
				{
					if(tmp[j] == paths.get(j).getEuclideanDistance())
					{
						NewPaths.add(paths.get(j));
					}
				}
				//Tenemos a los mas aptos guardados en NewPaths
				//Aplicando algoritmo de cruce
				//Tomamos de 2 en 2 individuos padres para cruzarlos
				int fatherSize = NewPaths.size();
				//for(int j = 0; j < NewPaths.size(); j++)
				for(int j = 0; j < (fatherSize - 1); j++)
				{
					
					if(NewPaths.get(j+1) != null)
					{
						Path f1 = NewPaths.get(j);
						Path f2 = NewPaths.get(j+1);
						Path s1 = new Path();
						Path s2 = new Path();
						
						int cutPoint1 = 0;
						int cutPoint2 = 0;
						int aux = 0;
							
						do
						{
							
							Random random = new Random();
							cutPoint1 = random.nextInt(NewPaths.get(j).path.size() - 1);
							cutPoint2 = cutPoint1;
							
							while(cutPoint2 == cutPoint1 || cutPoint2 == cutPoint1 + 2 || cutPoint2 == cutPoint1 - 2 || cutPoint2 == cutPoint1 + 1 || cutPoint2 == cutPoint1 - 1)
								cutPoint2 = random.nextInt(NewPaths.get(j).path.size() - 1);
								
							if(cutPoint1 > cutPoint2)
							{
								aux = cutPoint2;
								cutPoint2 = cutPoint1;
								cutPoint1 = aux;
							}
		
						}while(GenerateNewValidPath(f1, f2, s1, s2, cutPoint1, cutPoint2) == false);
						
						
						if(s1.getEuclideanDistance() < s2.getEuclideanDistance())
						{
							NewPaths.add(s1);
						}
						else
						{
							NewPaths.add(s2);
						}
					}
				}

			//Mutacion
			for(int a = 0; a < NewPaths.size(); a++)
			{
				Path temporal = new Path();
				Random random = new Random();
				int cutPoint1 = random.nextInt(NewPaths.get(a).path.size() - 2);
				if(cutPoint1 == 0) cutPoint1++;	
				int cutPoint2 = cutPoint1;
				while(cutPoint2 == cutPoint1 || cutPoint2 < 1)
					cutPoint2 = random.nextInt(NewPaths.get(a).path.size() - 2);
				
				for(int b = 0; b < NewPaths.get(a).path.size(); b++)
				{
					temporal.path.add(NewPaths.get(a).getCityFromPath(b));
				}
				
				//Hacemos switch de ciudades y si el resultado de distancia recorrida es menor, entonces mutam el individuo
				temporal.path.set(cutPoint1, temporal.getCityFromPath(cutPoint2));
				temporal.path.set(cutPoint2, NewPaths.get(a).getCityFromPath(cutPoint1));
				temporal.CalculateEuclideanDistance();
				
/*				System.out.println("Mutado");
				temporal.printPath2();
				System.out.println("Original");
				NewPaths.get(a).printPath2();*/
				
				if(temporal.getEuclideanDistance() < NewPaths.get(a).getEuclideanDistance())
				{
					NewPaths.set(a, temporal);
					//System.out.println("El individuo ha mutado, viva la evolucion!");
				}
					
					
			}
			
			//Igualacion
			paths = NewPaths;
/*			for(int w = 0; w < paths.size(); w++)
			{
				paths.get(w).printPath();
				if(isAValidPath(paths.get(w)))
					System.out.print(" camino valido \n");
				else
					System.out.print(" camino no valido \n");

			}*/

			
			System.out.println("\nGeneracion: "+generation);
			Collections.sort(paths, new Comparator<Path>() {
                @Override
                public int compare(Path o1, Path o2) {
                        if (o1.getEuclideanDistance() < o2.getEuclideanDistance())
                                return -1;
                        else if (o1.getEuclideanDistance() > o2.getEuclideanDistance())
                                return 1;
                        else
                                return 0;
                }
			});
			
/*			for(int w = 0; w < paths.size(); w++)
			{
				paths.get(w).printPath2();
			}*/
			
			paths.get(0).printPath();
			if(isAValidPath(paths.get(0)))
				System.out.print(" Camino valido");
			else
				System.out.print(" Camino invalido");
		}
	}
	
	boolean GenerateNewValidPath(Path f1, Path f2, Path s1, Path s2, int minCutPoint, int maxCutPoint)
	{
		//System.out.println("minCutPoint: "+minCutPoint+" maxCutPoint: "+maxCutPoint+"\n");
		minCutPoint++;
		City[] f1Extract = new City[maxCutPoint - minCutPoint];
		City[] f2Extract = new City[maxCutPoint - minCutPoint];
		int j = 0, w = 0;
		boolean stop = false;
		//Checar que el punto de corte funcione
		//Recombinaci—n en dos puntos
		
		
		//Padres
/*		System.out.println("Padres:\n");
		f1.printPath();
		f2.printPath();*/
		
		if(!(f1.getCityFromPath(minCutPoint).getCityName().equals(f2.getCityFromPath(minCutPoint + 1).getCityName())) || 
		   !(f1.getCityFromPath(maxCutPoint - 1).getCityName().equals(f2.getCityFromPath(maxCutPoint).getCityName())) ||
		   !(f2.getCityFromPath(minCutPoint).getCityName().equals(f1.getCityFromPath(minCutPoint + 1).getCityName())) || 
		   !(f2.getCityFromPath(maxCutPoint - 1).getCityName().equals(f1.getCityFromPath(maxCutPoint).getCityName())))
			{
			for(int i = 0; i < f1.path.size(); i++)
			{
				s1.path.add(f1.getCityFromPath(i));
				s2.path.add(f2.getCityFromPath(i));
			}
			//Cruzamos
			for(int i = minCutPoint; i < maxCutPoint; i++)
			{

				f1Extract[j] = f1.getCityFromPath(i);
				f2Extract[j] = f2.getCityFromPath(i);
				s1.path.set(i, f2Extract[j]);
				s2.path.set(i, f1Extract[j]);
				
				j++;
			}
			
			//Hijos Generados (aun sin verificar si son validos)
/*			System.out.println("Hijos sin verificar:\n");
			s1.printPath();
			s2.printPath();*/
			
			//Verificamos estados repetidos en el hijo 1 y en caso de existir corregimos.
			//System.out.println("\nEvolucion del hijo 1\n");
			for(int i = 0; i < f1Extract.length; i++)
			{
				for(int x = 0; x < f1.path.size(); x++)
				{
					
					if(f2Extract[i].getCityName().equals(s1.getCityFromPath(x).getCityName()) && (x < minCutPoint || x >= maxCutPoint))
					{
						//s1.printPath();
						for(int z = 0; z < f1.path.size(); z++)
						{
							boolean found = false;
							for(int y = 0; y < s1.path.size(); y++)
							{
								if(f1.getCityFromPath(z).getCityName().equals(s1.getCityFromPath(y).getCityName()))
								{
									found = true;
								}
							}
							if(!found)
							{
								//System.out.println("No encontre a "+f1.getCityFromPath(z).getCityName()+" por lo tanto lo agrego");
								s1.path.set(x, f1.getCityFromPath(z));
								if(x == 0)
								{
									s1.path.set(s1.path.size() - 1, s1.getCityFromPath(0));
								}
								break;
							}
						}
						//s1.printPath();
					}	
				}

			}
				
			stop = false;
			w = 0;
			//Verificamos estados repetidos en el hijo 1 y en caso de existir corregimos.
			//System.out.println("Evolucion del hijo 2");
			for(int i = 0; i < f2Extract.length; i++)
			{
				for(int x = 0; x < f2.path.size(); x++)
				{
					if(f1Extract[i].getCityName().equals(s2.getCityFromPath(x).getCityName()) && (x < minCutPoint || x >= maxCutPoint))
					{
						//s2.printPath();
						for(int z = 0; z < f2.path.size(); z++)
						{
							boolean found = false;
							for(int y = 0; y < s2.path.size(); y++)
							{
								if(f2.getCityFromPath(z).getCityName().equals(s2.getCityFromPath(y).getCityName()))
								{
									found = true;
								}
							}
							if(!found)
							{
								//System.out.println("No encontre a "+f2.getCityFromPath(z).getCityName()+" por lo tanto lo agrego");
								s2.path.set(x, f2.getCityFromPath(z));
								if(x == 0)
								{
									s2.path.set(s2.path.size() - 1, s2.getCityFromPath(0));
								}
								break;
							}
						}
						//s2.printPath();
					}
				}
			}
				
			//Calculamos la distancia euclidiana nueva para cada hiji
			s1.CalculateEuclideanDistance();
			s2.CalculateEuclideanDistance();
			
/*			System.out.println("Padres:");
			f1.printPath();
			f2.printPath();
			System.out.println("Hijos:");
			s1.printPath();
			s2.printPath();*/
			
/*			if(isAValidPath(s1))
				System.out.println("Hijo 1 es valido");
			else
				System.out.println("Hijo 1 es invalido");
			
			if(isAValidPath(s2))
				System.out.println("Hijo 2 es valido");
			else
				System.out.println("Hijo 2 es invalido");*/
			
			return true;
		}
		else
		{
			return false;
		}
	}
		
	int GenerateNewCityBanning()
	{
		boolean validNumber = false;
		
		while(!validNumber)
		{
			Random random = new Random();
			int testN = random.nextInt(n);
			if(!bannedCities.contains(testN))
			{
				bannedCities.add(testN);
				return testN;
			}
		}
		return -1;
	}
	
	boolean isAValidPath(Path check)
	{
		for(int i = 0; i < check.path.size() - 1; i++)
		{
			for(int j = 0; j < check.path.size() - 1; j++)
			{
				if(check.getCityFromPath(i).getCityName().equals(check.getCityFromPath(j).getCityName()) && i != j )
				{
					return false;
				}
			}
		}
		return true;
	}
}

