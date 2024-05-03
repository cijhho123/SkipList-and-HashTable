	import java.util.Random;
	
	import jdk.jshell.execution.Util;
	
	public class MultiplicativeShiftingHash implements HashFactory<Long> {
		
	    public MultiplicativeShiftingHash() {
	        
	    }
	
	    @Override
	    public HashFunctor<Long> pickHash(int k) {
	    	return  new Functor(k) ;
	    }
	
	    public class Functor implements HashFunctor<Long> {
	    	HashingUtils util = new HashingUtils();
	        final public static long WORD_SIZE = 64;
	        final private long a;
	        final private long k;
	        
	        public Functor(int k1) {
	        	this.a = util.genLong(2, Integer.MAX_VALUE - 1);
	        	this.k = k1;
	        }
	
	        @Override
	        public int hash(Long key) {
	            return (int) ((a() * key) >>> 	(WORD_SIZE - k));
	        }
	
	        public long a() {
	            return a;
	        }
	
	        public long k() {
	            return k;
	        }
	    }
	}
