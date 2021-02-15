package fr.univ_montpellier.iut.sudoku.imp;

import java.time.Duration;
import java.time.Instant;

public class SudokuSolver {

	int n;
	int s;
	int[][] grid;
	public boolean allSolutions;
	public static int counter, solutions;

	/*
	 * Create an instance of the problem sudoku (nxn)
	 * 
	 */

	public SudokuSolver(int n, boolean allSolutions) {
		this.n = n;
		this.s = (int) Math.sqrt(n);
		this.grid = new int[n][n];
		this.allSolutions = allSolutions;
	}

	public boolean alreadyInCol(int val, int col) {
		for (int row = 0; row < n; row++) {
			if (grid[row][col] == val)
				return true;
		}
		return false;
	}

	public boolean alreadyInRow(int val, int row) {
		for (int col = 0; col < n; col++) {
			if (grid[row][col] == val)
				return true;
		}
		return false;
	}

	public boolean alreadyInShape(int val, int row, int col) {
		int dotLeft = s * (col / s);
		int dotTop = s * (row / s);
		for (int c = dotLeft; c < dotLeft + s; c++) {
			for (int l = dotTop; l < dotTop + s; l++) {
				if (grid[l][c] == val)
					return true;
			}
		}
		return false;
	}

	public boolean isPossible(int valeur, int row, int col) {
		return !alreadyInCol(valeur, col) && !alreadyInRow(valeur, row) && !alreadyInShape(valeur, row, col);
	}

	public void print() {
		for (int l = 0; l < n; l++) {
			for (int c = 0; c < n; c++) {
				if (grid[l][c] != 0)
					System.out.print(grid[l][c] + "\t");
				else
					System.out.print(" \t");
			}
			System.out.println();
		}
		System.out.println();
	}

	public boolean findSolution(int row, int col) {

		counter++;

		int nextRow;
		int nextCol;

		if (col == n - 1) {
			nextRow = row + 1;
			nextCol = 0;
		} else {
			nextRow = row;
			nextCol = col + 1;
		}

		if (row == n) {
			print();
			//return true;
			solutions++;
			return !allSolutions;
		}

			for (int val = 1; val < n + 1; val++) {
				if (!isPossible(val, row, col))
					continue;
				grid[row][col] = val;
				boolean correct = findSolution(nextRow, nextCol);
				if (correct)
					return true;
			}
			grid[row][col] = 0;
			return false;
		
	}

	public static void main(String[] a) {
		counter = 0;
		solutions = 0 ;
		
		
		Instant start = Instant.now();
		
		boolean allSolutions = true;
		System.out.println(new SudokuSolver(4, allSolutions ).findSolution(0, 0) + "\n #recursive_calls=" + counter
				+ "\n nb_solutions: "+ solutions);
		
		Instant end = Instant.now();
		
		System.out.println(Duration.between(start, end));
		
	}
}












