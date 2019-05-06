package structures.graph;

import java.util.ArrayList;
import java.util.List;

public class DirectedGraph implements GraphInterface {
    private List<GraphEdge>[] edges;

    public DirectedGraph(int v) {
        this.edges = new List[v];
        for (int i = 0; i < v; i++) this.edges[i] = new ArrayList<>();
    }

    @Override
    public void addEdge(GraphEdge edge) {
        this.edges[edge.from].add(edge);
    }

    @Override
    public int getVerticesCount() {
        return edges.length;
    }

    public List<GraphEdge>[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < edges.length; i++) {
            b.append(i);
            b.append(": ");
            for (GraphEdge e : edges[i]) {
                b.append(e);
                b.append(" ");
            }
            b.append("\n");
        }
        return b.toString();
    }
}
