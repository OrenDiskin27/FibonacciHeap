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
		System.out.println(fib.totalCuts());

		fib.print_roots();
		fib.deleteMin();
		
//		try {
//			while (true) {
//				fib.deleteMin();
//			}
//		}
//		catch (Exception e) {
//			
//		}
		for (int k = 0; k<lst.size(); k++) {
			fib.print_roots();
			System.out.println("total cuts: " + fib.totalCuts());
			fib.decreaseKey(lst.get(k), m+1);

		}
		fib.print_roots();
		
		// from here real tester
	//	List<Integer> listK = new ArrayList{10,15,20,25};
		
	}	
}