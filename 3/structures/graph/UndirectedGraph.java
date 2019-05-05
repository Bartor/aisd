package structures.graph;

import java.util.ArrayList;
import java.util.List;

public class UndirectedGraph implements GraphInterface {
    private List<GraphEdge>[] edges;

    public UndirectedGraph(int verticesCount) {
        this.edges = (List<GraphEdge>[]) new List[verticesCount];
        for (int i = 0; i < verticesCount; i++) edges[i] = new ArrayList<>();
    }

    @Override
    public void addEdge(GraphEdge edge) {
        edges[edge.from].add(edge);
        edges[edge.to].add(edge);
    }

    public List<GraphEdge>[] getEdges() {
        return edges;
    }
}
