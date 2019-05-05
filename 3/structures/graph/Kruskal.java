package structures.graph;

import java.util.Comparator;

public class Kruskal {
    private int[] rank;
    private Integer[] parent;
    private UndirectedGraph graph;

    public Kruskal(UndirectedGraph graph) {
        this.graph = graph;
        rank = new int[graph.getEdges().length];
        parent = new Integer[graph.getEdges().length];
    }

    public UndirectedGraph mst() {
        UndirectedGraph g = new UndirectedGraph(graph.getEdges().length);
        for (int i = 0; i < graph.getEdges().length; i++) makeSet(i);

        for (int i = 0; i < graph.getEdges().length; i++) {
            graph.getEdges()[i].sort(Comparator.comparingInt(GraphEdge::getWeight));
        }

        for (int i = 0; i < graph.getEdges().length; i++) {
            for (GraphEdge e : graph.getEdges()[i]) {
                if (findSet(e.to) != findSet(e.from)) {
                    g.addEdge(new GraphEdge(e.from, e.to, e.weight));
                    union(e.to, e.from);
                }
            }
        }

        return g;
    }

    private void link(int x, int y) {
        if (rank[x] > rank[y]) {
            parent[y] = x;
        } else {
            parent[x] = y;
            if (rank[x] == rank[y]) rank[y]++;
        }
    }

    private void makeSet(int x) {
        parent[x] = x;
        rank[x] = 0;
    }

    private int findSet(int x) {
        if (x != parent[x]) {
            parent[x] = findSet(parent[x]);
        }
        return parent[x];
    }

    private void union(int x, int y) {
        link(findSet(x), findSet(y));
    }
}
