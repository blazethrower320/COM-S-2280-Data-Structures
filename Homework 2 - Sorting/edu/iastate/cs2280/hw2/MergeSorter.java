package edu.iastate.cs2280.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Carson Torrey
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super.points = pts;
		super.algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		int pointLength = pts.length;

		if(pointLength <= 1)
		{
			return;
		}
		int middle = pointLength / 2;
		Point[] ptsLeft = new Point[middle];
		Point[] ptsRight = new Point[pointLength - middle];
		int i = 0;

		for(int j = 0; j < ptsLeft.length; j++)
		{
			ptsLeft[j] = pts[i];
			i++;
		}

		for(int j = 0; j < ptsRight.length; j++)
		{
			ptsRight[j] = pts[i];
			i++;
		}

		mergeSortRec(ptsLeft);
		mergeSortRec(ptsRight);

		merge(pts, ptsLeft, ptsRight);
	}

	
	// Other private methods if needed ...
	private void merge(Point[] pts, Point[] ptsLeft, Point[] ptsRight)
	{
		int i = 0;
		int j = 0;
		int k = 0;
		int L = ptsLeft.length;
		int R = ptsRight.length;

		while(i < L && j < R)
		{
			if(ptsLeft[i].compareTo(ptsRight[j]) < 0)
			{
				pts[k++] = ptsLeft[i++];
			}
			else
			{
				pts[k++] = ptsRight[j++];
			}
		}

		while(j < R)
		{
			pts[k++] = ptsRight[j++];
		}

		while(i < L)
		{
			pts[k++] = ptsLeft[i++];
		}
	}

}
