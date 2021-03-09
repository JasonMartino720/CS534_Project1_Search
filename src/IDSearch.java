
public class IDSearch {
	
	
	public IDSearch() {}
	
	
	public Board IDS(Board root) {
        // loops through until a goal node is found
        for (int depth = 0; depth <= root.size; depth++) {
            Board found = DLS(root, depth);
            if (found != null) {
            	System.out.println("Solution depth: " + depth);
            	return found;
            }
        }
        // this will never be reached as it 
        // loops forever until goal is found
        return null;
    }	
	
	public Board DLS(Board current, int depth) {
        if (depth == 0 && current.findNumAttacks() == 0) {
            return current;
        }
        if (depth > 0) {
            for (Board child : current.adjBoards()) {
                Board found = DLS(child, depth - 1);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
