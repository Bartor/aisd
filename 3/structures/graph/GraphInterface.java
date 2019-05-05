package structures.graph;

import java.util.List;

public interface GraphInterface {
    public void addEdge(GraphEdge edge);
    public List<GraphEdge> shortestPath(int from);
}
