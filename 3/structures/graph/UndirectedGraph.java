package structures.graph;

import java.util.ArrayList;
import java.util.List;

public class UndirectedGraph implements GraphInterface {
    private List<GraphEdge>[] edges;

    public UndirectedGraph(int verticesCount) {
        this.edges = new ArrayList[verticesCount];
        for (int i = 0; i < verticesCount; i++) edges[i] = new ArrayList<>();
    }

    @Override
    public void addEdge(GraphEdge edge) {
        edges[edge.from].add(edge);
        edges[edge.to].add(new GraphEdge(edge.to, edge.from, edge.weight));
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
