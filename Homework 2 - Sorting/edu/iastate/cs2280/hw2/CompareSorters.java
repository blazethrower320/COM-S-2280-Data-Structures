package edu.iastate.cs2280.hw2;

import java.io.File;

/**
 *  
 * @author Carson Torrey
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import java.util.Random; 


public class CompareSorters 
{

	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		
		File file = new File("Output.txt");
		if(file.exists())
		{
			file.delete();
		}
		
		
		PointScanner[] scanners = new PointScanner[4]; 
		Random rand = new Random();
		Scanner scnr = new Scanner(System.in);
		int option = 0;
		int trialsNumber = 1;
		while(option != 3)
		{
			System.out.println("Options: [1]: Random Numbers | [2]: File Input | [3]: Exit");
			if(!scnr.hasNextInt())
			{
				System.out.println("Value does not exists! Goodbye");
				break;
			}
			option = scnr.nextInt();
			System.out.println("");
			System.out.println("Trial: " + trialsNumber + ": " + option);
			if(option == 1)
			{
				System.out.println("How many Random Points would you like: ");
				int amount = scnr.nextInt();
				var points = generateRandomPoints(amount, rand);
				System.out.println("");
				//System.out.println("Orginal Points: ");
				//System.out.println("Points: " + points.length);
				scanners[0] = new PointScanner(points, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(points, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(points, Algorithm.MergeSort);
				scanners[3] = new PointScanner(points, Algorithm.QuickSort);
				System.out.println("");
				trialsNumber++;
			}
			else if(option == 2)
			{
				System.out.println("Input File Name: ");
				String path = scnr.next();
				scanners[0] = new PointScanner(path, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(path, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(path, Algorithm.MergeSort);
				scanners[3] = new PointScanner(path, Algorithm.QuickSort);
				
				trialsNumber++;
			}
			else if(option == 3)
			{
				System.out.println("Bye I guess");	
				break;
			}
			else {
				System.out.println("Value does not exists!");
			}
			System.out.println("Algorithm size time (ns)");
			System.out.println("----------------------------------");
			for (var scan : scanners) {
			    if (scan == null) {
			        System.out.println("Scanner is null... Somehow");
			        continue;
			    }
			    scan.scan();
			    System.out.println(scan.stats());
			    scan.writeMCPToFile();
			}
		}
		
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if(numPts < 1)
		{
			throw new IllegalArgumentException("Number is < 1"); 
		}
		Point[] pts = new Point[numPts];
	    int randomNumber = rand.nextInt(101) - 50;
		for(int i = 0; i < numPts; i++)
		{
			int randomNumber1 = rand.nextInt(101) - 50;
			int randomNumber2 = rand.nextInt(101) - 50;
			pts[i] = new Point(randomNumber1, randomNumber2);
		}
		return pts;
	}
	
}
