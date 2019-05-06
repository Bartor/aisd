package structures.graph;

import java.util.ArrayList;
import java.util.List;

public class Kruskal {
    private int v;
    private int e;
    private List<GraphEdge> edges = new ArrayList<>();

    private class subset {
        int parent, rank;
    }

    public Kruskal(UndirectedGraph graph) {
        this.v = graph.getEdges().length;
        //creating a simpler graph representation
        for (List<GraphEdge> l : graph.getEdges()) {
            for (GraphEdge e : l) {
                this.edges.add(new GraphEdge(e.from, e.to, e.weight));
                for (GraphEdge ee : graph.getEdges()[e.to]) {
                    if (ee.to == e.from) {
                        graph.getEdges()[e.to].remove(ee);
                        break;
                    }
                }
            }
        }
    }

    public UndirectedGraph mst() {
        UndirectedGraph g = new UndirectedGraph(v);

        GraphEdge[] res = new GraphEdge[v];
        int e = 0, i;
        for (i = 0; i < v; i++) res[i] = new GraphEdge(-1, -1, -1);

        edges.sort(GraphEdge::compareTo);

        subset[] subsets = new subset[v];
        for (i = 0; i < v; i++) {
            subsets[i] = new subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        i = 0;

        while (e < v - 1) {
            GraphEdge nextEdge = edges.get(i++);
            int x = find(subsets, nextEdge.from);
            int y = find(subsets, nextEdge.to);

            if (x != y) {
                res[e++] = nextEdge;
                union(subsets, x, y);
            }
        }

        for (i = 0; i < e; ++i) {
            g.addEdge(new GraphEdge(res[i].from, res[i].to, res[i].weight));
        }

        return g;
    }

    private int find(subset[] subsets, int i) {
        if (subsets[i].parent != i) subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    private void union(subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank) subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank) subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
}
