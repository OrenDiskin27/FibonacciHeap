import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
	public HeapNode min;
	public HeapNode last;
	public int size;
	public int numMarked;
	public static int SumsLinks;
	public static int SumsCuts;
	public int numTrees;

	
	public FibonacciHeap(){}
	
	public FibonacciHeap(HeapNode x){
		min = x;
		last = x;
		x.setNext(x);
		x.setPrev(x);
		size = x.getRank()+1;
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
    	
    	HeapNode newHeap = new HeapNode(key);
    	
    	if (isEmpty()) {
    		min = newHeap;
    		last = newHeap;
    		newHeap.setNext(newHeap);
    		newHeap.setPrev(newHeap);
    	}
    	else {
    		last.next.setPrev(newHeap);
        	newHeap.setNext(last.getNext());
        	last.setNext(newHeap);
        	newHeap.setPrev(last);

        	if (newHeap.getKey() < min.getKey()) {
        		min = newHeap;
        	}
    	}
    	size += 1;
    	numTrees +=1;
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
    	if (!isEmpty()) {
    		delete_root(min); 
    	}
     	
    }

    public HeapNode actuallyFindMin() { // this func sets the new min and return it
    	HeapNode temp = last;	// looking for the new min, going on the roots
     	if (temp == null) {
     		min = null;
     	}
     	else {
     		min = temp;
     		temp = temp.getNext();
         	while (temp != last) {
         		if (temp.getKey() < min.getKey()) {
         			min = temp;
         		}
         		temp = temp.getNext();
         	}
     	}
     	return min;
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
    public void meld (FibonacciHeap heap2) // cheack that heap2 does not contain keys from this heap
    {
    	if (isEmpty()) {
    		if (heap2.isEmpty()) { // both are empty
    			return;
    		}
    		else { // heap 2 isnt empty
    			min = heap2.min;
    			last = heap2.last;
    			size = heap2.size();
    			numMarked = heap2.numMarked;
    			numTrees = heap2.numTrees;
    		}

    	}
    	else {
    		if (heap2.isEmpty()) { // this isnt empty and heap2 is empty
    			return;
    		}
    		else { // both are not empty;
    			size += heap2.size();
    			numMarked += heap2.numMarked;
    			numTrees += heap2.numTrees;

    			last.getNext().setPrev(heap2.last); // fix all pointers for last
    			heap2.last.getNext().setPrev(last);
    			
    			HeapNode temp = last.getNext(); // temp is first in heap1
    			
    			last.setNext(heap2.last.getNext());
    			heap2.last.setNext(temp);

    			
    	    	if (min.getKey() < heap2.min.getKey()) {
    	    		return;
    	    	}
    	    	else {
    	    		min = heap2.min;
    	    	}
    		}
    	}
    	return;
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
    	HeapNode index = last.getNext();
    	int size_of_array=last.getRank();
    	while (index != last){	// add to arr[i] +1 if rank = i
    		if (size_of_array < index.getRank()) {
    			size_of_array = index.getRank();
    		}
    		index = index.getNext();
    	}

    	int[] arr = new int[size_of_array+1];
    	HeapNode index2 = last.getNext();
    	while (index2 != last){	// add to arr[i] +1 if rank = i
    		arr[index2.getRank()] += 1;
    		index2 = index2.getNext();
    	}
    	arr[index2.getRank()] += 1;


    	return arr;
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
    	decreaseKeyThenDelete(x);
    	
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {
    	x.setKey(x.getKey() - delta); // this is not good, what 
    	if (x.getKey() < min.getKey()) {
    		min = x;
    	}
    	
    	if (x.getParent() == null || x.getParent().getKey() < x.getKey()) {
    		return;
    	}
    	else {
        	cascading_cut (x);
    	}
    	return;
    }
    
    public void decreaseKeyThenDelete(HeapNode x) // func ment for delete
    {
    	
    	x.setKey(min.getKey()-1); // now x is min
    	min = x;

    	if (x.getParent() == null || x.getParent().getKey() < x.getKey()) {
        	deleteMin();
    		return;
    	}
    	cascading_cut (x);    	
    	deleteMin();
    	
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
    	return SumsLinks; // need to fix this
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
    	return SumsCuts;  // need to fix this
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

   public void cut(HeapNode x) //our func
   {
	   // if x is root what sould happen?
	   if (x.getParent() == null) {
		   System.out.println("you tried to cut the root. shame on you, you should know that the root can not be marked as is written in 66");
		   return;
	   }
	   
	   SumsCuts += 1;
	   numTrees +=1;
	   if (x.isMarked()) {
	       x.setMarked(false);
	       numMarked -= 1;
	   }
       HeapNode temp_p = x.getParent();
       x.getParent().setRank(x.getParent().getRank()-1);
       x.setParent(null);
       HeapNode first = last.getNext();
       
       /* this code is fudementally wrong but we are too afraid to delete it.
       int maxRank = 0;
       for (HeapNode temp = x ; temp != x.getPrev(); x = x.getNext()) { // check which son has the biggest rank
    	   if (temp.getRank() > maxRank) {
    		   maxRank = temp.getRank();
    	   }
       }
       if (x.getPrev().getRank() > maxRank && x != x.getPrev()) {
		   maxRank = x.getPrev().getRank();
	   }
       temp_p.setRank(maxRank+1);
       if (maxRank == 0 ) {
           temp_p.setRank(0);
       }
       */
       
       if(x.getNext() == x){temp_p.setChild(null);}
       else
       {
           temp_p.setChild(x.getNext());
           x.getPrev().setNext(x.getNext());
           x.getNext().setPrev(x.getPrev());
       }
	   first.setPrev(x);
	   x.setNext(first);
	   x.setPrev(last);
	   last.setNext(x);
   }
   public void cascading_cut(HeapNode x) //our func
   {
       HeapNode temp_p = x.getParent();
       cut(x);
       if(temp_p != null)
       {
    	   if(temp_p.isMarked()){cascading_cut(temp_p);}
           if(!temp_p.isMarked() && temp_p.getParent()!= null){
        	   temp_p.setMarked(true);
        	   numMarked += 1;
        	   }
       }
   }
   //gets 2 nodes that represent trees in the heap, with the same degree
   public static void merge(HeapNode x, HeapNode y) // I think rank is missing here
   {
	   SumsLinks +=1;
	   
       HeapNode small;
       HeapNode big;
       if ((x.getParent() != null)||(y.getParent()!= null)){System.out.println("kick back, you just merged by 2 nodes that aint been on the surface, B-I-A-T-C-H!");}
       if (x.getKey()<y.getKey())
       {
           small = x;
           big = y;
       }
       else
       {
           small = y;
           big = x;
       }
       big.setParent(small);
       HeapNode brother_of_biggie = small.getChild();
       if(brother_of_biggie == null){small.setChild(big);}//in case small has no kids
       else if(brother_of_biggie.getNext() == brother_of_biggie)//in case  only 1 child
	   {
	   		brother_of_biggie.setNext(big);
	   		brother_of_biggie.setPrev(big);
	   		big.setNext(brother_of_biggie);
	   		big.setPrev(brother_of_biggie);
       }
       else 
       {
    	   brother_of_biggie.getPrev().setNext(big);
    	   big.setPrev(brother_of_biggie.getPrev());
    	   brother_of_biggie.setPrev(big);
    	   big.setNext(brother_of_biggie);
       }
       small.setRank(small.getRank()+1);

   }
   
   public void delete_root(HeapNode x) {
	   
	   size -= 1;

	   HeapNode temp = x.getChild();
	   
	   if (temp == null) {
		   x.getPrev().setNext(x.getNext());
		   x.getNext().setPrev(x.getPrev());
	   }
	   else {
		   while (temp.getNext()!= x.getChild()) { // delete x kids pointer to x.
				temp.setParent(null);
				temp = temp.getNext();
		   		}
		   temp.setParent(null);//this is the last son we need to detach from his papa
		   if (x.getNext() == x) {
			   last = temp;
		   }
		   else {
			   x.getPrev().setNext(x.getChild());	// make x.prev point to x child and then to x.next
			   x.getNext().setPrev(x.getChild().getPrev());
			   x.getChild().getPrev().setNext(x.getNext());
			   x.getChild().setPrev(x.getPrev());
		   }
		   
	   }   
	   if (x == last && x.getNext() != x) {
		   last = x.getPrev();
	   }
	   if (x == min) {
		   min = actuallyFindMin();
	   }
	   consolidating();
	   
	   
   }

   public void consolidating() {
	  Map<Integer,HeapNode> map = new HashMap<Integer,HeapNode>();
	  while (last.getNext() != last) {
		  HeapNode temp = last.getNext();
		  last.setNext(temp.getNext());
		  temp.getNext().setPrev(last);
		  temp.setNext(temp);
		  temp.setPrev(temp);
		  addDic(map,temp);
	
	  }
	  addDic(map,last);
	  last = null;
	  int k =0;
	  numTrees = map.size();
	  
	  while (map.size() != 0) {
		  if (map.containsKey(k)) {
			  if (last == null) {
				  last = map.remove(k);
			  }
			  else {
				  HeapNode temp = map.remove(k);
				  last.getNext().setPrev(temp);
				  temp.setNext(last.getNext());
				  last.setNext(temp);
				  temp.setPrev(last);
				  last = temp;
			  }
		  }
		   k += 1;
	  }
	  
   }
   
   public void addDic(Map<Integer, HeapNode> map, HeapNode x) { // func to help Consolidating
	   if (!map.containsKey(x.getRank())) {
		   map.put(x.getRank(), x);
	   }
	   else {
		   
		   HeapNode temp = map.remove(x.getRank());
		   if(temp.getParent()!=null) {System.out.println("Yo, this node got parents bro,don't treat it like that");}

		   if (x.getKey() < temp.getKey()) {
			   merge(x,temp);
			   addDic(map,x);
		   }
		   else {
			   merge(x,temp);
			   addDic(map,temp);
		   }
		   
	   }
   }
   
   public void print_roots() {
	   List<Integer> lst = new ArrayList<Integer>();
	   for (HeapNode temp = last.getNext(); temp != last; temp = temp.getNext()) {
		   lst.add(temp.getRank());
	   }
	   lst.add(last.getRank());
	   System.out.println(Arrays.toString(lst.toArray()));

	   
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
    		this.rank = 0;
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