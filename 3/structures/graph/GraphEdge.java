package structures.graph;

public class GraphEdge implements Comparable<GraphEdge> {
    public int from;
    public int to;
    public float weight;

    public GraphEdge(int from, int to, float weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("(%d->%d : %d)", from, to, weight);
    }

    @Override
    public int compareTo(GraphEdge o) {
        if (weight - o.weight == 0) return 0;
        else if (weight - o.weight < 0) return -1;
        else return 1;
    }
}
