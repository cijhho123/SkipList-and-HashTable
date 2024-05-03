public class SkipListExperimentUtils {
	
	
    public static double measureLevels(double p, int x) {
    	IndexableSkipList temp = new IndexableSkipList(p);
    	int sum = 0;
    	int counter = 0;
    	
    	while (counter < x) {
        	sum += temp.generateHeight();
        	counter ++;
        }
    	
    	return (1.0 * sum / x) + 1;
    }

    /*
     * The experiment should be performed according to these steps:
     * 1. Create the empty Data-Structure.
     * 2. Generate a randomly ordered list (or array) of items to insert.
     *
     * 3. Save the start time of the experiment (notice that you should not
     *    include the previous steps in the time measurement of this experiment).
     * 4. Perform the insertions according to the list/array from item 2.
     * 5. Save the end time of the experiment.
     *
     * 6. Return the DS and the difference between the times from 3 and 5.
     */
    public static Pair<AbstractSkipList, Double> measureInsertions(double p, int size) {
    	
    	
    	IndexableSkipList list = new IndexableSkipList(p);
    	int [] array = generate_random_order(size, 2);
    	
    	long time = System.nanoTime();
    	
    	for(int i=0; i<array.length; i++)
    		list.insert(array[i]);
    	
    	time = System.nanoTime() - time;
    	time = time / size;
    	
    	Pair p1 = new Pair(list, time);
    	return p1;
    }
    
    private static int[] generate_random_order(int size, int jump){
    	int[] arr = new int[size + 1];
    	
    	//fill the array
    	for(int i = 0; i <= size; i++)
    		arr[i] = jump * i;
    	
    	//scrumble the array
    	for(int i = 0; i < 2000; i++) {
    		int a = (int) (Math.random() * (size + 1));
    		int b = (int) (Math.random() * (size + 1));
    		
    		int temp = arr[a];
    		arr[a] = arr[b];
    		arr[b] = temp;
    	}
    	
    	return arr;
    }

    public static double measureSearch(AbstractSkipList skipList, int size) {
    	
    	int[] array = generate_random_order(2* size, 1);
    	
    	long time = System.nanoTime();
    	
    	for(int i = 0; i <= (2*size); i++)
    		skipList.search(array[i]);
    	
    	
    	time = System.nanoTime() - time;
    	time = time / (2 * size);
    	return time;
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
    	int[] array = generate_random_order(size, 2);
    	AbstractSkipList.Node[] nodes = new AbstractSkipList.Node[size + 1];
    	
    	for(int i = 0 ; i < array.length; i ++)
    		nodes[i] = skipList.search(2 * i);
    	
    	long time = System.nanoTime();
    	
    	for(int i = 0; i <= size; i++)
    		skipList.delete(nodes[i]);
    	
    	
    	time = System.nanoTime() - time;
    	time = time / (2 * size);
    	return time;
    }

    public static void main(String[] args) {
    	task_2_2_3();
    	
    	/*
    	int [] arr = generate_random_order(10, 2);
    	for(int i : arr)
    		System.out.println(i);
    	*/
    	
    	/*
    	Pair p = measureInsertions(0.5, 10);
    	System.out.println(p.first().toString());
    	System.out.println("------------------");
    	System.out.println(p.second().toString());
    	*/
    }
    
    public static void task_2_2_3() {
        double [] probabilities =  {0.33, 0.5, 0.75, 0.9};
        int [] lengths = {1000, 2500, 5000, 10000, 15000, 20000, 50000};
        
        for(double p : probabilities) {
        	System.out.println("probability: " + p);
        	
        	
        	for(int l : lengths) {
        		//sum1: insert, sum2: search, sum3: delete
            	double sum1 = 0, sum2 = 0, sum3 = 0;
        		
        		for(int time = 1; time <= 30; time++) {
        			Pair temp = measureInsertions(p, l);
        			sum1 += (long) temp.second();
        			
        			sum2 += (long) measureSearch((AbstractSkipList) temp.first(), l);
        			sum3 += (long) measureDeletions((AbstractSkipList) temp.first(), l);
        		}
        		sum1 = sum1 / 30;
        		sum2 = sum2 / 30;
        		sum3 = sum3 / 30;
        		
        		System.out.println("x: "+ l + "\t\tinsertion: "+ String.format("%.1f",sum1) + "\t\tsearch: " + String.format("%.1f",sum2) + "\t\tdelete: " + String.format("%.1f",sum3));
        	}
        	System.out.print("\n-----------------------------------\n");
        }
    }
    
	public static void task_2_2_2 () {
        double [] probabilities =  {0.33, 0.5, 0.75, 0.9};
        int [] lengths = {10, 100, 1000, 10000};
        
        for(double p : probabilities) {
        	System.out.print("probability: " + p);
    				
        	for(int l : lengths) {
            	double exp = 1/p;
            	double delta = 0;
        		
        		
        		System.out.print("\nlength: " + l + "\nExperiments:\t");
        		
        		for(int time = 1; time <= 5; time++) {
        			double a = measureLevels(p, l);
        			System.out.print(String.format("%.1f", a) + "\t");
        			delta += Math.abs(exp - a);           		
        		}
        		delta = delta / 5;
        		System.out.println("\nExpected: "+ String.format("%.1f", exp) +"\tdelta: "+ String.format("%.1f", delta));
        	}
        	System.out.print("\n-----------------------------------\n");
        }
	}

        
}
