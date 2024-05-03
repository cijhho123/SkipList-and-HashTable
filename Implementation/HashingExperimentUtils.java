import java.util.Collections; // can be useful
import java.util.List;

public class HashingExperimentUtils {
    final private static int k = 16;
    
    public static Pair<Double, Double> measureOperationsChained(double maxLoadFactor) {
    	HashFactory<Integer> factory = new ModularHash();
    	
    	double a = maxLoadFactor;

    	double insertTime = 0, searchTime = 0;
    	
		for(int x=1; x<=30; x++) {
			//init
			ChainedHashTable chained = new ChainedHashTable(factory, k, a);
			HashingUtils util = new HashingUtils();
			Integer [] array = util.genUniqueIntegers((int)(chained.capacity() * a * 1.5));
			//the first a  items are to be inserted, the last 0.5a items are for the unsuccesful searches
			
			double time = System.nanoTime();
			
			//insert
			for(int i=1; i < (chained.capacity() * a) - 1; i++)
				chained.insert(array[i], array[i]);
			
			time = System.nanoTime() - time;
			insertTime += (time / (chained.capacity() * a));
			
			time = System.nanoTime();
			//succesful search
			for(int i=0; i<(chained.capacity() * a * (1.0/2)); i++)
				chained.search(array[i]);
			//unsuccesful search
			for(int i=0; i<(chained.capacity() * a * (1.0/2)); i++)
				chained.search(array[(int) (i + chained.capacity() * a )]);
			

			time = System.nanoTime() - time;
			searchTime += (time / (chained.capacity() * a));
		}
		
		insertTime /= 30;
		searchTime /=30;
		
		Pair p1 = new Pair(insertTime, searchTime);
		return p1;
    }
    

	public static Pair<Double, Double> measureOperationsProbing(double maxLoadFactor) {
		HashFactory<Integer> factory = new ModularHash();
    	
    	double a = maxLoadFactor;

    	double insertTime = 0, searchTime = 0;
    	
		for(int x=1; x<=30; x++) {
			//init
			ProbingHashTable linear = new ProbingHashTable(factory, k, a);
			HashingUtils util = new HashingUtils();
			Integer [] array = util.genUniqueIntegers((int)(linear.capacity() * a * 1.5));
			//the first a  items are to be inserted, the last 0.5a items are for the unsuccesful searches
			
			double time = System.nanoTime();
			
			//insert
			for(int i=1; i < (linear.capacity() * a) - 1; i++)
				linear.insert(array[i], array[i]);
			
			time = System.nanoTime() - time;
			insertTime += (time / (linear.capacity() * a));
			
			time = System.nanoTime();
			//succesful search
			int max = (int)(linear.capacity() * a * (1.0/2)) + 1;
			for(int i=0; i < max; i++)
				linear.search(array[i]);
			//unsuccesful search
			for(int i=0; i<(linear.capacity() * a * (1.0/2)); i++)
				linear.search(array[(int) (i + linear.capacity() * a)]);
			

			time = System.nanoTime() - time;
			searchTime += (time / (linear.capacity() * a));
		}
		
		insertTime /= 30;
		searchTime /= 30;
		
		Pair p1 = new Pair(insertTime, searchTime);
		return p1;
	}

    public static Pair<Double, Double> measureLongOperations() {
    	HashFactory<Long> factory = new MultiplicativeShiftingHash();
    	
    	double a = 1;

    	double insertTime = 0, searchTime = 0;
    	
		for(int x=1; x<=10; x++) {
			//init
			ChainedHashTable longTable = new ChainedHashTable(factory, k, a);
			HashingUtils util = new HashingUtils();
			
			Long [] array = util.genUniqueLong(((int)(longTable.capacity() * a * 1.5)));
			//the first a  items are to be inserted, the last 0.5a items are for the unsuccesful searches
			
			double time = System.nanoTime();
			
			//insert
			for(int i=1; i < (longTable.capacity() * a) - 1; i++)
				longTable.insert(array[i], array[i]);
			
			time = System.nanoTime() - time;
			insertTime += (time / (longTable.capacity() * a));
			
			time = System.nanoTime();
			//succesful search
			for(int i=0; i<(longTable.capacity() * a * (1.0/2)); i++)
				longTable.search(array[i]);
			//unsuccesful search
			for(int i=0; i<(longTable.capacity() * a * (1.0/2)); i++)
				longTable.search(array[(int) (i + longTable.capacity() * a )]);
			

			time = System.nanoTime() - time;
			searchTime += (time / (longTable.capacity() * a));
		}
		
		insertTime /= 10;
		searchTime /= 10;
		
		Pair p1 = new Pair(insertTime, searchTime);
		return p1;
    }

    public static Pair<Double, Double> measureStringOperations() {
    	HashFactory<String> factory = new StringHash();
    	
    	double a = 1;

    	double insertTime = 0, searchTime = 0;
    	
		for(int x = 1; x <= 10; x ++) {
			//init
			ChainedHashTable stringTable = new ChainedHashTable(factory, k, a);
			HashingUtils util = new HashingUtils();
			
			List<String> tempList = util.genUniqueStrings((((int)(stringTable.capacity() * a * 1.5))), 10, 20);
			String[] Strings = new String [tempList.size()]; ;
			tempList.toArray(Strings);
			//the first a  items are to be inserted, the last 0.5a items are for the unsuccesful searches
			
			double time = System.nanoTime();
			
			//insert
			for(int i=1; i < (stringTable.capacity() * a) - 1; i++)
				stringTable.insert(Strings[i], Strings[i]);
			
			time = System.nanoTime() - time;
			insertTime += (time / (stringTable.capacity() * a));
			
			time = System.nanoTime();
			//succesful search
			for(int i=0; i<(stringTable.capacity() * a * (1.0/2)); i++)
				stringTable.search(Strings[i]);
			//unsuccesful search
			for(int i=0; i<(stringTable.capacity() * a * (1.0/2)); i++)
				stringTable.search(Strings[(int) (i + stringTable.capacity() * a )]);
			

			time = System.nanoTime() - time;
			searchTime += (time / (stringTable.capacity() * a));
		}
		
		insertTime /= 10;
		searchTime /= 10;
		
		Pair p1 = new Pair(insertTime, searchTime);
		return p1;
    }

    public static void main(String[] args) {
    	task3_8();
    }
    
    private static void task3_8() {
    	System.out.println("*********Chaining*********\n");
    	
    	double[] alpha = {0.5, 0.75, 1, 1.5, 2};
    	
    	for(double a : alpha) {
    		System.out.println("alpha: " + a);
    		Pair times = measureOperationsChained(a);
    		System.out.println("insertion time: "+ times.first() +"\nSearch time: " + times.second()+"\n-----------------");
    	}
    	

    	alpha = new double []{0.5, 0.75, 0.875, 0.9375};
    	
    	System.out.println("*********Linear probing*********\n");
    	for(double a : alpha) {
    		System.out.println("alpha: " + a);
    		Pair times = measureOperationsProbing(a);
    		System.out.println("insertion time: "+ times.first() +"\nSearch time: " + times.second()+"\n-----------------");
    	}
    }
    
    private static void task3_12() {
    	System.out.println("*********\ttask 3.12\t*********\n");
    	
		System.out.println("long operations");
		Pair times = measureLongOperations();
		System.out.println("insertion time: "+ times.first() +"\nSearch time: " + times.second()+"\n-----------------");
		
		System.out.println("string operations");
		times = measureStringOperations();
		System.out.println("insertion time: "+ times.first() +"\nSearch time: " + times.second()+"\n-----------------");
    	
    }
}
