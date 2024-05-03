import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
	
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    
    private int k;
    
    private Pair[] map;
    private int size;


    /*
     * You should add additional private members as needed.
     */

    public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        
        this.k = k;
        this.size = 0;
		this.map = new Pair [this.capacity];
        
        for(int i=0; i<map.length; i++)
        	map[i] = null;
    }

    @SuppressWarnings("unchecked")
	public V search(K key) {
    	int hashed = hashFunc.hash(key);
    	
    	for (int i=0; i<this.capacity; i++) {
    		if(map[(hashed + i) % this.capacity] == null)
    			return null;
			if(map[(hashed + i) % this.capacity].first() != null && map[(hashed + i) % this.capacity].first().equals(key)) {
    			return (V) map[(hashed + i) % this.capacity]; 
    		}
    	}
    	
    	return null;
    }

    @SuppressWarnings("unchecked")
	public void insert(K key, V value) {

    	Pair<K, V> p = new Pair<K, V>(key, value);
    	
        //check if a new insertion will take us over the load factos
    	if((1.0*(this.size + 1) / this.capacity) >= this.maxLoadFactor) {
    		
    		ProbingHashTable<K, V> temp = new ProbingHashTable<K, V>(hashFactory, this.k + 1, this.maxLoadFactor);
    		
    		for(int i=0; i < map.length; i++) {	
    			if(this.map[i] != null && this.map[i].first() !=  null && this.map[i].second() != null)
    				temp.insert((K)map[i].first(), (V)map[i].second());
    		}
    		
    		this.capacity = temp.capacity;
    		this.hashFunc = temp.hashFunc;
    		this.k = temp.k;
    		this.map = temp.map;
    	}
    	
    	
    	//insert the element to the hashmap
    	int hashed = hashFunc.hash(key);
    	size ++;
    	
    	for (int i=0; i < this.size; i++) {
    		if(map[(hashed + i) % this.capacity] == null || (map[(hashed + i) % this.capacity].first()==null && map[(hashed + i) % this.capacity].second()==null)) {
    			map[(hashed + i) % this.capacity] = p;
    			return;
    		}
    	}
    }

    public boolean delete(K key) {
    	//In this assignment, we exclusively refer to the variant of Linear Probing that uses deleted signs
    	
    	int hashed = hashFunc.hash(key);
    	
    	for (int i=0; i<this.size; i++) {
    		if(map[(hashed + i) % this.capacity] == null)
    			return false;
    		
			if((map[(hashed + i) % this.capacity].first() != null && map[(hashed + i) % this.capacity].first().equals(key))){
    			map[(hashed + i) % this.capacity] = new Pair(null, null);
    			size --;
    			return true;
			}
    	}
    	
    	return false;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
}
