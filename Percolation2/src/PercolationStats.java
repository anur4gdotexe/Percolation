import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private static final double CONFIDENCE_95 = 1.96;
	private double[] stats;
	private int t;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		stats = new double[trials];
		t = trials;
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(stats);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(stats);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - CONFIDENCE_95 * stddev() / (Math.sqrt(t));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + CONFIDENCE_95 * stddev() / (Math.sqrt(t));
	}

	// test client (see below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);
		
		if (n <= 0 || t <= 0) {
			throw new IllegalArgumentException();
		}
		PercolationStats pcs = new PercolationStats(n, t);

		for (int i = 0; i < t; i++) {

			Percolation pr = new Percolation(n);

			int row = 0, col = 0;
			while (!pr.percolates()) {
				row = StdRandom.uniformInt(1, n + 1);
				col = StdRandom.uniformInt(1, n + 1);
			
				if (!pr.isOpen(row, col)) {
					pr.open(row, col);
				}
			}
			pcs.stats[i] = (double) pr.numberOfOpenSites() / (double) (n*n);
		}

		StdOut.printf("mean%22s", "= ");
		StdOut.printf("%.10f\n", pcs.mean());
		StdOut.printf("stddev%20s", "= ");
		StdOut.printf("%.10f\n", pcs.stddev());
		StdOut.print("95% confidence interval" + " = " + "[" + String.valueOf(pcs.confidenceLo()) + ", "
				+ String.valueOf(pcs.confidenceHi()) + "]");
	}
}