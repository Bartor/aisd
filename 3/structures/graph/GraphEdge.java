package structures.graph;

public class GraphEdge {
    public int from;
    public int to;
    public int weight;

    public GraphEdge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("(%d->%d : %d)", from, to, weight);
    }
}
