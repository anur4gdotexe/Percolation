import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int size;
	private boolean[][] arr;	
	private int openCount = 0;
	private WeightedQuickUnionUF uf;
	
	// creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if (n <= 0) {
            throw new IllegalArgumentException("Size should be a positive integer.");
        }
    	size = n;
    	uf = new WeightedQuickUnionUF(size*size + 2);
    	
    	arr = new boolean[n+1][n+1];
    	for (int i = 0; i < n+1; i++) {
    		for (int j = 0; j < n+1; j++) {
    			arr[i][j] = false;
    		}
    	}
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	
    	if (validate(row, col)) {
    		arr[row][col] = true;
        	openCount++;  
        	
        	// connect with other adjacent open sites
        	int linearInd = convertInd(row, col);
        	if (row > 1 && isOpen(row-1, col)) { uf.union(linearInd, convertInd(row-1, col)); }
        	if (row < size && isOpen(row+1, col)) { uf.union(linearInd, convertInd(row+1, col)); }
        	if (col > 1 && isOpen(row, col-1)) { uf.union(linearInd, convertInd(row, col-1)); }
        	if (col < size && isOpen(row, col+1)) { uf.union(linearInd, convertInd(row, col+1)); }
        	
        	if (linearInd <= size && linearInd > 0) {
        		uf.union(0, linearInd);
        	}
        	if (linearInd <= size*size && linearInd > size*size - size) {
        		uf.union(size*size+1, linearInd);
        	}
    	}	
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	if (validate(row, col)) {
    		return arr[row][col];
    	}
    	return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	if (validate(row, col)) {
    		return (uf.find(convertInd(row, col)) == uf.find(0));
    	}
        return false;
    }
    
    // returns the number of open sites
    public int numberOfOpenSites() {
    	return openCount;
    }
    
    // does the system percolate?
    public boolean percolates() {
    	return (uf.find(0) == uf.find(size*size + 1));
    }
    
    private boolean validate(int row, int col) {
    	if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("index [" + row + "," + col + "] is not between [1,1] and [" + size + "," + size + "]");
    	}
    	return true;
    }
    
    private int convertInd(int row, int col) {
    	return (row-1)*size + col;
    }
}