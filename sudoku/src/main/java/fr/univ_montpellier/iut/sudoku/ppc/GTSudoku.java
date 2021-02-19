package fr.univ_montpellier.iut.sudoku.ppc;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import static org.chocosolver.solver.search.strategy.Search.minDomLBSearch;
import static org.chocosolver.util.tools.ArrayUtils.append;

public class GTSudoku {

	Data data = Data.gridGT;

	private final int n = data.grid.length;
	private final int s = (int) Math.sqrt(n);
	final static int O = 1, B = 2, T = 3, L = 5, G = 7; // nombres premiers

	final static int BL = B * L;
	final static int BG = B * G;
	final static int TL = T * L;
	final static int TG = T * G;

	IntVar[][] rows, cols, squares;

	private Model model;

	public void buildModel() {
		model = new Model();

		rows = new IntVar[n][n];
		cols = new IntVar[n][n];
		squares = new IntVar[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {

				rows[i][j] = model.intVar("c_" + i + "_" + j, 1, n, false);
				cols[j][i] = rows[i][j];
			}
		}

		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				for (int k = 0; k < s; k++) {
					for (int l = 0; l < s; l++) {
						squares[j + k * s][i + (l * s)] = rows[l + k * s][i + j * s];
					}
				}
			}
		}

		for (int i = 0; i < n; i++) {
			model.allDifferent(rows[i]).post();
			model.allDifferent(cols[i]).post();
			model.allDifferent(squares[i]).post();

		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				
				if (data.grid(i, j) % B == 0)
					model.arithm(rows[i][j], "<", rows[i + 1][j]).post();

				if (data.grid(i, j) % T == 0)
					model.arithm(rows[i][j], ">", rows[i + 1][j]).post();

				if (data.grid(i, j) % L == 0)
					model.arithm(rows[i][j], "<", rows[i][j+1]).post();

				if (data.grid(i, j) % G == 0)
					model.arithm(rows[i][j], ">", rows[i][j+1]).post();

			}
		}

	}

	public void configureSearch() {
		model.getSolver().setSearch(minDomLBSearch(append(rows)));

	}

	public void solve() {
		this.buildModel();
		model.getSolver().showStatistics();
		while(model.getSolver().solve());

		StringBuilder st = new StringBuilder(String.format("Sudoku -- %s\n", data.name()));
		st.append("\t");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				st.append(rows[i][j]).append("\t\t\t");
			}
			st.append("\n\t");
		}

		System.out.println(st.toString());
	}

	public static void main(String[] args) {
		new GTSudoku().solve();
	}

	/////////////////////////////////// DATA
	/////////////////////////////////// //////////////////////////////////////////////////
	enum Data {
		gridGT(new int[][] { { BL, BG, B, TL, TL, B, TG, TL, T }, 
							 { TL, TL, T, BL, BL, B, BL, TL, T },
							 { G, L, O, G, G, O, G, L, O }, 
							 { TG, BG, B, BL, TG, T, BL, BL, T }, 
							 { BL, TG, B, TG, BG, B, TG, BG, B },
							 { L, L, O, L, G, O, L, G, O }, 
							 { BG, BL, B, BG, TG, B, TL, TL, T }, 
							 { TG, TG, T, TG, TG, B, BG, BG, B },
							 { G, L, O, G, L, O, G, G, O } }),;

		final int[][] grid;

		Data(int[][] grid) {
			this.grid = grid;
		}

		int grid(int i, int j) {
			return grid[i][j];
		}
	}
}
