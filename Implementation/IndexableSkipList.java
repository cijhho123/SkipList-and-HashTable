public class IndexableSkipList extends AbstractSkipList {
	
    final protected double probability;
    
    public IndexableSkipList(double probability) {
        super();
        this.probability = probability;
    }

    @Override
    public Node find(int val) {
        Node p = this.head;
        for(int i = p.height(); i >= 0; i = i - 1) {
        	while(p.getNext(i) != null && p.getNext(i).key() <= val)
        		p = p.getNext(i);
        }
        
        return p;
    }

    @Override
    public int generateHeight() {
        int counter = 0; 
        
        while(Math.random() >= probability) {
        	counter += 1;
        }
        
        return counter;
    }

    public int rank(int val) {
        return 0;
    }

    public int select(int index) {
        return 0;    
        }
}
