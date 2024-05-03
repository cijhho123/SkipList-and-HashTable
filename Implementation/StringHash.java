import java.util.Random;


public class StringHash implements HashFactory<String> {

    public StringHash() {
        
    }

    @Override
    public HashFunctor<String> pickHash(int k) {
    	return new Functor(k);
    }

    public class Functor implements HashFunctor<String> {
    	HashingUtils util = new HashingUtils();
        final private HashFunctor<Integer> carterWegmanHash;
        final private int c;
        final private int q;
        
        public Functor(int k) {
        	q = findPrime(util);
        	c = (int) util.genLong(2, q);
        	carterWegmanHash = new ModularHash().pickHash(k);
        }
        
        private int findPrime(HashingUtils util) {
			int number = (int) util.genLong(Integer.MAX_VALUE / 2 + 1, Integer.MAX_VALUE);
			
			while(! util.runMillerRabinTest(number, 50)) {
				number = (int) util.genLong(Integer.MAX_VALUE / 2 + 1, Integer.MAX_VALUE - 1);
			}
			
			return number;
		}

        @SuppressWarnings("static-access")
		@Override
        public int hash(String key) {
        	int sum = 0;
        	
            for(int i = 1; i <= key.length(); i++) {
            	sum += util.mod(((int)key.charAt(i - 1) * (util.modPow(c(), key.length() - i, q))), q);
            }
            
            sum = util.mod(sum, q);
            
            return carterWegmanHash.hash(sum);
        }

        public int c() {
            return c;
        }

        public int q() {
            return q;
        }

        public HashFunctor carterWegmanHash() {
            return carterWegmanHash;
        }
    }
}
