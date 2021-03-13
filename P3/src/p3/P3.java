/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class P3 {

	static class Task {

		public static final int NMAX = 10000000;
		public static final int INF = 2000000000;

		int n;
		int m;
		int e;
		int[] parent;
		//int[][] a;

		public class Edge {

			public int node;
			public double cost;

			Edge(int _node, double _cost) {
				node = _node;
				cost = _cost;
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Edge>[] adj = new ArrayList[NMAX];
		ArrayList<Edge>[] adj2 = new ArrayList[NMAX];


		public void printPath(int []parent, int v) {
			if (v < 0) {

				return;
			}

			if (v != 0) {
				printPath(parent, parent[v]);
			}
			if (v != 0) {
				System.out.print(" " + (v));
			} else {
				System.out.print(v);
			}
		}

		private void readInput() {
			try {

				BufferedReader br = new BufferedReader(new FileReader("p3.in"));
				String s;
				String[] s1;
				s = br.readLine();
				s1 = s.split(" ");
				n = Integer.parseInt(s1[0]);
				m = Integer.parseInt(s1[1]);
				e = Integer.parseInt(s1[2]);
				parent = new int[n + 1];
				Arrays.fill(parent, -1);

				int f, t, w;
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= n; i++) {
					adj2[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					s = br.readLine();
					s1 = s.split(" ");
					f = Integer.parseInt(s1[0]);
					t = Integer.parseInt(s1[1]);
					w = Integer.parseInt(s1[2]);
					adj[f].add(new Edge(t, -(Math.log10(((double)100-w)/100))));
					adj2[f].add(new Edge(t, w));

				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(double result) {
			try {

					PrintStream o = new PrintStream(new File("p3.out"));
					System.setOut(o);
					System.out.println(e*result);
					//System.out.println(rez);
					printPath(parent, n);
					System.out.println();
				

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private double getResult() {

			return bellman(1, n, n);

		}

		public double bellman(int S, int V, int t) {
			double[] d = new double[V + 1];
			double[] d2 = new double[V + 1];
			boolean[] inQueue = new boolean[V + 1];
			inQueue[S] = true;
			for (int i = 0; i <= V; i++) {
				d[i] = Integer.MAX_VALUE;
			}
			for (int i = 0; i <= V; i++) {
				d2[i] = 1;
			}
			d2[S]=1;
			d[S] = 0;

			Queue<Integer> q = new LinkedList<>();
			q.add(S);

			while (!q.isEmpty()) {
				int u = q.peek();
				q.remove();
				inQueue[u] = false;
				for (int i = 0; i < adj[u].size(); i++) {

					int v = adj[u].get(i).node;
					double weight = adj[u].get(i).cost;
					double weight2 = adj2[u].get(i).cost;

					if (d[v] > d[u] + weight) {
						d[v] = d[u] + weight;
						d2[v] = d2[u]*(100-weight2 )/100;
						if (!inQueue[v]) {
							q.add(v);
							inQueue[v] = true;
						}
						parent[v] = u;
					}
				}
			}
			return d2[t];
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}

