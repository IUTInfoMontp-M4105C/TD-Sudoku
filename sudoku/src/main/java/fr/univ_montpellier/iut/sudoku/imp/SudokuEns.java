package fr.univ_montpellier.iut.sudoku.imp;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

public class SudokuEns {

	int n;
	int s;
	int[][] grid;

	/*
	 * Create an instance of the problem sudoku (nxn)
	 * 
	 */

	public SudokuEns(int n) {
		this.n = n;
		this.s = (int) Math.sqrt(n);
		this.grid = new int[n][n];
	}

	/*
	 * check if this.grid is a correct sudoku solution.
	 * 
	 */

	private boolean solutionChecker() {

		// row checker
		for (int row = 0; row < n; row++)
			for (int col = 0; col < n; col++)
				for (int col2 = col + 1; col2 < n; col2++)
					if (grid[row][col] != 0 && grid[row][col] == grid[row][col2]) {
						return false;
					}

		// column checker
		for (int col = 0; col < n; col++)
			for (int row = 0; row < n; row++)
				for (int row2 = row + 1; row2 < n; row2++)
					if (grid[row][col] != 0 && grid[row][col] == grid[row2][col]) {

						return false;
					}

		// grid checker
		for (int row = 0; row < s + 1; row += s - 1)
			for (int col = 0; col < s; col += s - 1)
				for (int pos = 0; pos < s; pos++)
					for (int pos2 = pos + 1; pos2 < s + 1; pos2++)
						if (grid[row + pos % (s - 1)][col + pos / (s - 1)] != 0 && grid[row + pos % (s - 1)][col
								+ pos / (s - 1)] == grid[row + pos2 % (s - 1)][col + pos2 / (s - 1)]) {
							return false;
						}
		return true;

	}

	/*
	 * Generate a random grid solution
	 * 
	 */

	private void generateSolution() {
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (int i = 1; i <= n; i++) {
			values.add(i);
		}
		for (int i = 0; i < n; i++) {
			Collections.shuffle(values);
			for (int j = 0; j < n; j++) {
				grid[j][i] = values.get(j);
			}
		}
	}

	/*
	 * Find a solution to the sudoku problem
	 * 
	 */
	public void findSolution() {

		Instant start = Instant.now();

		this.generateSolution();
		while (!this.solutionChecker()) {
			this.generateSolution();
		}
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				System.out.print(this.grid[j][i] + "\t");
			}
			System.out.print("\n");

		}
		
		System.out.print(this.solutionChecker());
		Instant end = Instant.now();

		System.out.println(Duration.between(start, end));
	}

	public static void main(String args[]) {
		new SudokuEns(4).findSolution();

	}
}
