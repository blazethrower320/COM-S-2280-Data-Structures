package edu.iastate.cs2280.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * @author Carson Torrey
 */

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  @Override
  /**
   * @return returns the size of the list
   */
  public int size()
  {
    return this.size;
  }
  /**
   * 
   * @param item
   * @return true or false if the node is found
   */
  public boolean contains(E item) {
		if(size < 1)
		{
			return false;
		}
		
		Node tempNode = head.next;
		while(tempNode != tail) {
			for(int i=0; i < tempNode.count; i++) {
				if(tempNode.data[i].equals(item))
					return true;
				tempNode = tempNode.next;
			}
		}
		return false;
	}
  
  @Override
  /**
   * Adds E item to the Node List
   * @return true or false if it added successfully
   */
  public boolean add(E item)
  {
	if(item == null)
	{
		throw new NullPointerException();
	}
	
	if(contains(item))
	{
		return false;
	}
	
	if(size == 0)
	{
		Node newNode = new Node();
		newNode.addItem(item);
		head.next = newNode;
		newNode.previous = head;
		newNode.next = tail;
		tail.previous = newNode;
		//System.out.println("Size == 0");
		size++;
		return true;
	}
	Node previousNode = tail.previous;
	if(previousNode.count < nodeSize)
	{
		//System.out.println("IN HERE");
		tail.previous.addItem(item);
		size++;
		return true;
	}
	else
	{
		//System.out.println("Else Statement");
		Node newNode = new Node();
		newNode.addItem(item);
		Node tempNode = tail.previous;
		tempNode.next = newNode;
		newNode.previous = tempNode;
		newNode.next = tail;
		tail.previous = newNode;
	}
	size++;
	return true;
  }

  @Override
  /**
   * Adds a item to the List at a specific position
   * 
   */
  public void add(int pos, E item) {
	    if (pos < 0 || pos > size) {
	        throw new IndexOutOfBoundsException();
	    }
	    // If the list is already emptty add the E node into here
	    if (head.next == tail) 
	    {
	        add(item);
	        size++;
	        return;
	    }

	    NodeInfo node = find(pos);
	    Node temp = node.node;
	    int offset = node.offset;
	    
	    if (offset == 0 && temp.previous.count < nodeSize && temp.previous != head) 
	    {
	        temp.previous.addItem(item);
	        size++;
	        //System.out.println("test 1");
	        return;
	    }
	    if (temp == tail && offset == 0) 
	    {
	        add(item);
	        size++;
	      //System.out.println("test 2");
	        return;
	    }

	    if (temp.count < nodeSize) 
	    {
	        temp.addItem(offset, item);
	        size++;
	      //System.out.println("test 3");
	        return;
	    }

	    Node newNode = new Node();
	    int half = nodeSize / 2;

	    for (int i = half; i < nodeSize; i++) 
	    {
	        newNode.addItem(temp.data[half]);
	        temp.removeItem(half);
	      //System.out.println("test 4");
	    }

	    Node oldNode = temp.next;
	    temp.next = newNode;
	    newNode.previous = temp;
	    newNode.next = oldNode;
	    oldNode.previous = newNode;

	    if (offset <= half) 
	    {
	        temp.addItem(offset, item);
	    }
	    else 
	    {
	        newNode.addItem(offset - half, item);
	    }


	   
	    size++;
	}

  @Override
  /**
   * Removes an item from the list at a certain position
   * @return Returns E object removed
   */
  public E remove(int pos) {

		if (pos < 0 || pos > size)
		{
			throw new IndexOutOfBoundsException();
		}

		NodeInfo nodeInfo = find(pos);
		Node temp = nodeInfo.node;
		int offset = nodeInfo.offset;
		E nodeReturned = temp.data[offset];

		if (temp.next == tail && temp.count == 1) 
		{
			//System.out.println("test remove 1");
			Node pre = temp.previous;
			pre.next = temp.next;
			temp.next.previous = pre;
			temp = null;
			size--;
			return nodeReturned;
		}
		else if (temp.next == tail || temp.count > nodeSize / 2) 
		{
			//System.out.println("test remove 2");
			temp.removeItem(offset);
			size--;
			return nodeReturned;
		}
		
		temp.removeItem(offset);
		Node succesor = temp.next;
		
		//System.out.println("test remove 3");
		if (succesor.count > nodeSize / 2) 
		{
			temp.addItem(succesor.data[0]);
			succesor.removeItem(0);
		}
		else if (succesor.count <= nodeSize / 2)
		{
			for (int i = 0; i < succesor.count; i++) 
			{
				temp.addItem(succesor.data[i]);
			}
			
			
			
			temp.next = succesor.next;
			succesor.next.previous = temp;
			succesor = null;
			
		}
		//System.out.println("test remove 4");
		size--;
		
		return nodeReturned;
	}

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort() {
		// TODO
		E[] sortedNodes = (E[]) new Comparable[size];

		int index = 0;
		Node tempNode = head.next;
		while (tempNode != tail) 
		{
			for (int i = 0; i < tempNode.count; i++) 
			{
				sortedNodes[index] = tempNode.data[i];
				index++;
			}
			tempNode = tempNode.next;
		}
		head.next = tail;
		tail.previous = head;
		insertionSort(sortedNodes, Comparator.naturalOrder());
		size = 0;
		for (int i = 0; i < sortedNodes.length; i++) {
			add(sortedNodes[i]);
		}

	}

  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() {
		// TODO
		E[] sortedNodes = (E[]) new Comparable[size];

		int index = 0;
		Node tempNode = head.next;
		
		while (tempNode != tail) 
		{
			for (int i = 0; i < tempNode.count; i++) 
			{
				sortedNodes[index] = tempNode.data[i];
				index++;
			}
			tempNode = tempNode.next;
		}

		head.next = tail;
		tail.previous = head;

		bubbleSort(sortedNodes);
		size = 0;
		
		for (int i = 0; i < sortedNodes.length; i++) {
			add(sortedNodes[i]);
		}
	}

  
  /**
   * Calls StoutListIterator
   */
  @Override
  public Iterator<E> iterator()
  {
    return new StoutListIterator();
  }

  @Override
  /**
   * Calls StoutListIterator
   */
  public ListIterator<E> listIterator()
  {
	  return new StoutListIterator();
  }
  /**
   * Calls StoutListIterator
   */
  @Override
  public ListIterator<E> listIterator(int index)
  {
	  if(index < 0)
	  {
		  throw new IndexOutOfBoundsException();
	  }
	  return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }
  private class NodeInfo
  	{
	  public Node node;
	  public int offset;
	  public NodeInfo(Node node, int offset)
	  	{
		  	this.node = node;
		  	this.offset = offset;
	  	}
  }
  
  /**
   * Returnst he node at the position
   * @param pos
   * @return Type NodeInfo
   */
  
  private NodeInfo find(int pos) {
	    if (pos < 0 || pos > size) 
	    { 
	        throw new IndexOutOfBoundsException("Position went oout of bounds: " + pos);
	    }

	    Node currentNode = head.next;
	    int currPos = 0;

	    while (currentNode != tail) 
	    {
	        if (currPos + currentNode.count > pos) 
	        {
	            return new NodeInfo(currentNode, pos - currPos);
	        }
	        currPos += currentNode.count;
	        currentNode = currentNode.next;
	    }

	    return new NodeInfo(tail.previous, tail.previous.count);
	}
  
  
  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
 
  private class StoutListIterator implements ListIterator<E>
  {
	  
	// constants you possibly use ...   
	  
	// instance variables ... 
	  /**
	   * Holds the position of the iterator
	   */
	private int position;  
	/**
	   * Indicates if you can Remove and or Set from the next position 
	   */
	private boolean canRemoveNext;
	/**
	   * Indicates if you can remove and or set from the previous position
	   */
	private boolean canRemovePrev;
	/**
	   * Holds the Iterator data as type E
	   */
	private E[] IteratorData;
    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
    	// TODO 
    	position = 0;
    	canRemoveNext = false;
    	canRemovePrev = false;
    	formList();
    }



    /**
     * Generates the List with the updates values
     */
    private void formList() {
    	IteratorData = (E[]) new Comparable[size];

		int tempIndex = 0;
		Node tempNode = head.next;
		while (tempNode != tail) {
			for (int i = 0; i < tempNode.count; i++) 
			{
				IteratorData[tempIndex] = tempNode.data[i];
				tempIndex++;
			}
			
			tempNode = tempNode.next;
		}
	}

    
    
    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
    	if (pos > size) 
    	{  
            position = size;  
        } 
    	else 
        {
            position = pos;
        }
    	position = pos;
    	canRemoveNext = false;
    	canRemovePrev = false;
    	formList();
    }

    @Override
    /**
     * Returns true or false if there is a next value
     * @return true or false
     */
    public boolean hasNext()
    {
    	if(position >= size)
    		return false;
    	return true;
    }
    @Override
    /**
     * Returns true or false if there is a previous value
     * @return true of false
     */
    public boolean hasPrevious()
    {
    	return position > 0;
    	/*
    	if(position <= size)
    		return false;
    	return true;
    	*/
    	
    }
    @Override
    /**
     * returns the Node of type E if there is a next value
     * @return Node E
     */
    public E next()
    {
    	if(!hasNext())
    	{
    		throw new NoSuchElementException("There are no more elements in the iterator");
    	}
    	canRemoveNext = true;
    	E dataResult = IteratorData[position];
    	
    	position++;
    	return dataResult;
    }

    
    @Override
    /**
     * returns the Node of type E if there is a previous value
     * @return Node E
     */
    public E previous()
    {
    	if(!hasPrevious())
    	{
    		throw new NoSuchElementException("There are no more elements in the iterator");
    	}
    	canRemovePrev = true;
    	position--;
    	return IteratorData[position];
    }


    @Override
    /**
     * Removes a value if canRemoveNext or canRemovePrev is true
     */
    public void remove()
    {
    	// TODO 
    	if(canRemoveNext)
    	{
    		StoutList.this.remove(position-1);
    		formList();
    		position =- 1;
    		canRemoveNext = false;
    		if(position < 0)
    		{
    			position = 0;
    		}
    		// Removing the element ahead posiition
    	}
    	else if(canRemovePrev)
    	{
    		StoutList.this.remove(position);
    		formList();
    		position -= 1;
    		canRemovePrev = false;
    	}
    	else
    	{
    		throw new IllegalStateException();
    	}
    }

	@Override
	/**
     * @return Next Index Value (int)
     */
	public int nextIndex() {
		return position;
	}

	@Override
	/**
     * @return Previous Index Value (int)
     */
	public int previousIndex() {
		return position-1;
	}


	@Override
	/**
     * Sets the node to a new Node if canRemoveNext or canRemovePrev is true
     */
	public void set(E e) {		
		// TODO Auto-generated method stub
		if(e == null)
		{
			throw new NullPointerException();
		}
		if(canRemoveNext)
		{
			NodeInfo nodeInfo = find(position - 1);
			nodeInfo.node.data[nodeInfo.offset] = e;
			IteratorData[position - 1] = e;
		}
		else if(canRemovePrev)
		{
			NodeInfo nodeInfo = find(position);
			nodeInfo.node.data[nodeInfo.offset] = e;
			IteratorData[position] = e;
		}
	}

	@Override
	/**
     * Adds a node to the Iterator List
     */
	public void add(E e) {
		if(e == null)
		{
			throw new NullPointerException();
		}
		StoutList.this.add(position, e);
		position++;
		formList();
		
	}

    
    // Other methods you may want to add or override that could possibly facilitate 
    // other operations, for instance, addition, access to the previous element, etc.
    // 
    // ...
    // 
  }
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp) {
		for (int i = 1; i < arr.length; i++) 
		{
			E key = arr[i];
			
			int j = i - 1;

			while (j >= 0 && comp.compare(arr[j], key) > 0) 
			{
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = key;
		}
	}
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr) {
		int n = arr.length;
		for (int i = 0; i < n - 1; i++) 
		{
			for (int j = 0; j < n - i - 1; j++) 
			{
				if (arr[j].compareTo(arr[j + 1]) < 0) 
				{
					//
					E temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
					
				}
			}
		}

	}
 

}