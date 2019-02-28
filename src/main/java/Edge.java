import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Edge implements Comparable<Edge> {
    protected Slide u, v;
    protected long weight;
    protected int next;
    protected List<Edge> path = new ArrayList<>();

    public Edge(Slide u, Slide v) {
        this.u = u;
        this.v = v;
        this.weight = -u.minFactor(v);
        this.next = -1;


    }


    public List<Edge> getPath() {
        return path;
    }

    public void setPath(List<Edge> path) {
        this.path = path;

    }


    @Override
    public int compareTo(Edge that) {
        return Double.compare(weight, that.weight);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.weight, weight) == 0 &&
                Objects.equals(u, edge.u) &&
                Objects.equals(v, edge.v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v, weight);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "u=" + u +
                ", v=" + v +
                ", weight=" + weight +
                ", next=" + next +
                '}';
    }
}
