package edu.iastate.cs2280.hw2;

import java.io.File;

/**
 * 
 * @author Carson Torrey
 *
 */

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if(pts == null || pts.length == 0)
			throw new IllegalArgumentException();
		points = pts;
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		this.sortingAlgorithm = algo;
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		int counter = 0;
		File file = new File(inputFileName);
		if(file == null)
			throw new FileNotFoundException();
		Scanner scnr = new Scanner(file);
		while(scnr.hasNextLine())
		{
			String nextLine = scnr.nextLine().trim();
			
			Scanner test = new Scanner(nextLine);
			while(test.hasNextInt())
			{
				int num = test.nextInt();
				numbers.add(num);
				//System.out.println(num);
			}
		}
		
		scnr.close();
		System.out.println(numbers.size() % 2);
		if(numbers.size() % 2 != 0)
		{
			System.out.println("Size: " + (numbers.size()));
			throw new InputMismatchException("Numbers file must be a **EVEN** number!");
		}
		Point[] sortedPoints = new Point[numbers.size() / 2];
		for(int i = 0; i < numbers.size(); i += 2)
		{
			Point p = new Point(numbers.get(i), numbers.get(i + 1));
			sortedPoints[i / 2] = p;
		}
		this.points = sortedPoints;
		//System.out.println(points.toString());
		
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter = null; 
		long timeEnded = 0;
		long timeStarted = 0;
		if(sortingAlgorithm == Algorithm.SelectionSort)
		{
			aSorter = new SelectionSorter(points);
		}
		else if(sortingAlgorithm == Algorithm.QuickSort)
		{
			aSorter = new QuickSorter(points);
		}
		else if(sortingAlgorithm == Algorithm.MergeSort)
		{
			aSorter = new MergeSorter(points);
		}
		else if(sortingAlgorithm == Algorithm.InsertionSort)
		{
			aSorter = new InsertionSorter(points);
		}
		
		aSorter.setComparator(1);
		timeStarted = System.nanoTime();
		aSorter.sort();
		timeEnded = System.nanoTime();
		Point MediumPoint = aSorter.getMedian();
		int y = MediumPoint.getY();
		long timeX = timeEnded - timeStarted;
		//
		aSorter.setComparator(0);
		timeStarted = System.nanoTime();
		aSorter.sort();
		timeEnded = System.nanoTime();
		Point MediumPoint2 = aSorter.getMedian();
		int x = MediumPoint2.getX();
		long timeY = timeEnded - timeStarted;
		
		scanTime = timeX + timeY;
		
		medianCoordinatePoint = new Point(x,y);
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		return sortingAlgorithm.name() +  " " + points.length + " " + scanTime; 
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		String s = "";
		for(int i = 0; i < points.length; i++)
		{
			Point point = points[i];
			s += "(" + point.getX() + "," + point.getY() + ") \n";
		}
		//System.out.println(s);
		s += stats();
		return s;
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		try 
		{
			FileWriter writer = new FileWriter("output.txt", true);
			
	        writer.write(toString() + "\n");
			writer.close();
		} catch(IOException e)
		{
			System.out.println("ERROR: " + e.getMessage());
		}
	}		
}
