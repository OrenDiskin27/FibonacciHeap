import java.util.ArrayList;
import java.util.List;


public class testing {
	public static void main(String[] args) {
		FibonacciHeap fib = new FibonacciHeap();
		List<FibonacciHeap.HeapNode> lst = new ArrayList<FibonacciHeap.HeapNode>();
		
		int m = (int) Math.pow(2, 2);
		for (int k = m-1; k>-2; k--) {
			lst.add(fib.insert(k));
		}
		fib.print_roots();
		fib.deleteMin();
		fib.print_roots();
		for (int k = m-1; k>-2; k--) {
			fib.decreaseKey(lst.get(k), m+1);
		}
		
	}	
}