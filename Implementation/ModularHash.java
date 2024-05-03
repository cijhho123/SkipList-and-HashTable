import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
	
    public ModularHash() {
        
    }

    @Override
    public HashFunctor<Integer> pickHash(int k) {
        return new Functor(k) ;
    }

    public class Functor implements HashFunctor<Integer> {
    	HashingUtils util = new HashingUtils();
    	final int a ;
        final int b ;
        final long p ;
        final int m ;
        
        public Functor(int k) {
        	a = (int) util.genLong(1, Integer.MAX_VALUE - 1);
            b = (int) util.genLong(0, Integer.MAX_VALUE - 1);
            p = findPrime(util);
            m = (int) Math.pow(2, k);
        }
        
        @SuppressWarnings("static-access")
		@Override
        public int hash(Integer key) {
            return (int) util.mod(util.mod(a() * key + b(), p()), m);
        }

        private long findPrime(HashingUtils util) {
			long number = util.genLong(Integer.MAX_VALUE + 1, Long.MAX_VALUE - 1);
			
			while(! util.runMillerRabinTest(number, 50)) {
				number = util.genLong(Integer.MAX_VALUE + 1, Long.MAX_VALUE - 1);
			}
			
			return number;
		}

		public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        public long p() {
            return p;
        }

        public int m() {
            return m;
        }
    }
}
