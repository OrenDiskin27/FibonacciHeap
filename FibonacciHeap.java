import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
	public HeapNode min;
	public HeapNode first;
	public HeapNode last;
	public int size;
	public int numMarked;
	static int SumsLinks;
	static int SumsCuts;
	public int numTrees;

	
	public FibonacciHeap(){
		
	}
	public FibonacciHeap(HeapNode x){
		min = x;
		first = x;
		last = x;
		x.setNext(x);
		x.setPrev(x);
	}
	
   /**
    * public boolean isEmpty()
    *
    * Returns true if and only if the heap is empty.
    *   
    */
    public boolean isEmpty()
    {
    	if (size == 0) {
    		return true;
    	}
    	return false;
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * The added key is assumed not to already belong to the heap.  
    * 
    * Returns the newly created node.
    */
    public HeapNode insert(int key)
    {    
    	new HeapNode newHeap = HeapNode(key);
    	
    	last.prev.setNext(newHeap);
    	last.next.setPrev(newHeap);
    	newHeap.setNext(last.getNext);
    	newHeap.setPrev(last.getPrev);
    	last = newHeap;
    	
    	if (newHeap.getKey() < min.getKey()) {
    		min = newHeap;
    	}

    	return newHeap;
    }

   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
    	
     	return; // should be replaced by student code
     	
    }

   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin()
    {
    	if (isEmpty()) {
    		return null;
    	}
    	return min;
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Melds heap2 with the current heap.
    *
    */
    public void meld (FibonacciHeap heap2)
    {
    	  return; // should be replaced by student code   		
    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size()
    {
    	return size; 
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
    * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
    * 
    */
    public int[] countersRep()
    {
    	int[] arr = new int[size];    	
    	new HeapNode index = first;
    	
    	While (index.next != first){	// add to arr[i] +1 if rank = i
    		arr[index.getRank()] += 1;
    	}
    	
    	arr[index.getRank()] += 1;
    	
    	int targetIndex = 0;	// deletes all 0 in arr;											
    	for( int sourceIndex = 0;  sourceIndex < arr.length;  sourceIndex++ )
    	{
    	    if( arr[sourceIndex] != 0 )
    	    	arr[targetIndex++] = arr[sourceIndex];
    	}
    	int[] newArray = new int[targetIndex];
    	System.arraycopy( arr, 0, newArray, 0, targetIndex );
    	
    	return newArray;
        }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap.
	* It is assumed that x indeed belongs to the heap.
    *
    */
    public void delete(HeapNode x) 
    {    
    	return; // should be replaced by student code
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	x.setKey(x.getKey() - delta);
    	if (x.getParent() == null || x.getParent().getKey() < x.getKey()) {
    		return;
    	}
    	cut (x,x.getParent());
    	return; 
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * 
    * In words: The potential equals to the number of trees in the heap
    * plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {        	
    	return numTrees +2*numMarked; // if numTrees is hard to maintain we can do 
    								  // this function in O(n) by going on all the roots
    }
    

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the
    * run-time of the program. A link operation is the operation which gets as input two
    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
    * tree which has larger value in its root under the other tree.
    */
    public static int totalLinks()
    {    
    	return SumsLinks; // should be replaced by student code
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the
    * run-time of the program. A cut operation is the operation which disconnects a subtree
    * from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return SumsCuts; // should be replaced by student code
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
    * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
    *  
    * ###CRITICAL### : you are NOT allowed to change H. 
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {    
        int[] arr = new int[100];
        return arr; // should be replaced by student code
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in another file. 
    *  
    */
   public static void cut(HeapNode x)
   {
       
       x.setParent(null);

   }

    public static class HeapNode{

    	public int key;
    	private boolean marked; //true iff i lost a boy( or girl, it's 2022 man(or woman(or tractor)))
    	private int rank;
    	private HeapNode next;
    	private HeapNode prev;
    	private HeapNode child;
    	private HeapNode parent;

    	public HeapNode(int key) {
    		this.key = key;
    	}

    	public int getKey() {
    		return this.key;
    	}

       public HeapNode getNext() {return next;}

       public boolean isMarked() {return marked;}

    	public int getRank() {return rank;}

    	public HeapNode getChild() {return child;}

    	public HeapNode getPrev() {return prev;}

       public void setNext(HeapNode next) {this.next = next;}

       public HeapNode getParent() {return parent;}

    	public void setMarked(boolean marked) {this.marked = marked;}

    	public void setRank(int rank) {this.rank = rank;}

    	public void setChild(HeapNode child) {this.child = child;}

    	public void setKey(int key) {this.key = key;}

    	public void setPrev(HeapNode prev) {this.prev = prev;}

    	public void setParent(HeapNode parent) {this.parent = parent;}
   }
}
