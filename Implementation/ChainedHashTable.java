import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
	
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private int k;
    
    private ArrayList <Pair<K,V>>[] map;
    private int size;


    /*
     * You should add additional private members as needed.
     */

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.size = 0;
        this.k = k;
        
        this.map = new ArrayList[this.capacity];
        
        for(int i=0; i< map.length; i++)
        	map[i] = new  ArrayList<Pair<K,V>>();
    }

    @SuppressWarnings("unchecked")
	public V search(K key) {
    	ArrayList<Pair<K,V>> temp = map[hashFunc.hash(key)];
    	    	
    	 for (int i = 0; i < temp.size(); i++) {
             if(temp.get(i).first() == key)
            	 return (V) temp.get(i).second();
         }
    	 
    	 return null;
    }

    @SuppressWarnings("unchecked")
	public void insert(K key, V value) {
    	
    	Pair<K, V> p = new Pair<K, V>(key, value);
    	
        //check if a new insertion will take us over the load factos
    	if((1.0*(this.size + 1) / this.capacity) >= this.maxLoadFactor) {
    		
    		ChainedHashTable<K, V> temp = new ChainedHashTable<K, V>(hashFactory, this.k + 1, this.maxLoadFactor);
    		
    		for(int i=0; i<map.length; i++) {
    			for(int j=0; j< map[i].size(); j++) {
    				//temp.insert((K)map[i].get(j).first(), (V)map[i].get(j).second());	//might need to insert the original object of the pair
    				temp.map[temp.hashFunc.hash(map[i].get(j).first())].add(map[i].get(j));
    			}
    		}
    		
    		this.capacity = temp.capacity;
    		this.hashFunc = temp.hashFunc;
    		this.k = temp.k;
    		this.map = temp.map;
    	}
    	
    	
    	//insert the element to the hashmap
    	int hashed = hashFunc.hash(key);
    	map[hashed].add(p);
    	size ++;
    }

    public boolean delete(K key) {
    	Pair<K, V> p = this.findPair(key);
    	
    	if(p == null)
    		return false;
    	
    	ArrayList<Pair<K,V>> temp = map[hashFunc.hash(key)];
    	
    	temp.remove(p);
    	size--;
    	return true;
    }
    
    private Pair<K,V> findPair (K key){
    ArrayList<Pair<K,V>> temp = map[hashFunc.hash(key)];
    	
   	 for (int i = 0; i < temp.size(); i++) {
            if(temp.get(i).first() == key)
           	 return (Pair<K,V>) temp.get(i);
        }
   	 
   	 return null;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
}
