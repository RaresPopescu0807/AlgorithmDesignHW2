package gfg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GFG {

	static class Task {

		public static final int NMAX = 50005;
		public static final int INF = (int) 1e9;

		int n;
		int m;
		int source;
		int to;

		public class Edge {

			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Edge> adj[] = new ArrayList[NMAX];

		private void readInput() {
			try {

				BufferedReader br = new BufferedReader(new FileReader("p2.in"));
				String s;
				String[] s1;
				s = br.readLine();
				s1 = s.split(" ");
				n = Integer.parseInt(s1[0]);
				m = Integer.parseInt(s1[1]);

				s = br.readLine();
				s1 = s.split(" ");
				source = Integer.parseInt(s1[0]);
				to = Integer.parseInt(s1[1]);
				int f, t, w;
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					s = br.readLine();
					s1 = s.split(" ");
					f = Integer.parseInt(s1[0]);
					t = Integer.parseInt(s1[1]);
					w = Integer.parseInt(s1[2]);
					adj[f].add(new Edge(t, w));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						"p2.out"));
				StringBuilder sb = new StringBuilder();
				sb.append(result);

				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getResult() {

			return bellman(source, n, to);

		}

		public int bellman(int S, int V, int t) {
			int[] d = new int[V + 1];
			boolean[] inQueue = new boolean[V + 1];
			inQueue[S] = true;
			for (int i = 0; i <= V; i++) {
				d[i] = Integer.MAX_VALUE;
			}
			d[S] = 0;

			Queue<Integer> q = new LinkedList<>();
			q.add(S);

			while (!q.isEmpty()) {
				int u = q.peek();
				q.remove();
				inQueue[u] = false;
				for (int i = 0; i < adj[u].size(); i++) {

					int v = adj[u].get(i).node;
					int weight = adj[u].get(i).cost;

					if (d[v] > d[u] + weight) {
						d[v] = d[u] + weight;
						if (!inQueue[v]) {
							q.add(v);
							inQueue[v] = true;
						}
					}
				}
			}
			return d[t];
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
