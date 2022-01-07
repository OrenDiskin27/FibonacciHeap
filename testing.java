import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class testing {
	public static void main(String[] args) {
		FibonacciHeap fibonacciHeap = new FibonacciHeap();

        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < 2049; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        for (int i = 0; i < 2049; i++) {
            fibonacciHeap.insert(numbers.get(i));
        }
        fibonacciHeap.deleteMin();

        System.out.println(Arrays.toString(fibonacciHeap.kMin(fibonacciHeap, 1000)));
        
        for (int i = 1; i < 2048; i++) {
            if (fibonacciHeap.findMin().getKey() != i) {
            	System.out.println("bug found");
                return;
            }
            fibonacciHeap.deleteMin();
        }

	}	
}