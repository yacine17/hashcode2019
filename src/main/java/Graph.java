import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graph {
    Edge[][] edges;
    private Map<Slide, Integer> vertices = new HashMap<>();
    private List<Slide> slides;
    private int nVertices;

    public Graph(List<Slide> slides) {
        this.slides = slides;
        nVertices = slides.size();
        edges = new Edge[nVertices][nVertices];
        for (int i = 0; i < nVertices; i++) {
            vertices.put(slides.get(i), i);
        }

        for (int i = 0; i < nVertices; i++) {
            for (int j = i+1; j < nVertices; j++) {
                edges[i][j] = new Edge(slides.get(i), slides.get(j));
                edges[i][j].next = j;
            }
        }
    }

    private Graph(Edge[][] edges, Graph g) {
        this.edges = edges;
        this.vertices = g.vertices;
        this.slides = g.slides;
        this.nVertices = g.nVertices;

    }

    public Graph getExplodedGraph(List<Edge> solution) {
        Edge[][] subE = new Edge[nVertices][nVertices];

        solution.stream()
                .parallel()
                .flatMap(edge -> edge.getPath().stream())
                .forEach(edge -> {
                    int uId = vertices.get(edge.u);
                    int vId = vertices.get(edge.v);

                    subE[uId][vId] = edges[uId][vId];
                });

        return new Graph(subE, this);
    }


    public Graph getSubGraphFromPoints(List<Point> hitPoints) {


        Edge[][] subE = new Edge[nVertices][nVertices];
        for (int i = 0; i < hitPoints.size(); i++) {
            for (int j = 0; j < hitPoints.size(); j++) {

                int uId = vertices.get(hitPoints.get(i));
                int vId = vertices.get(hitPoints.get(j));

                subE[uId][vId] = edges[uId][vId];
            }
        }
        return new Graph(subE, this);

    }

    public List<Edge> getEdges() {

        return Stream.of(edges)
                .parallel()
                .flatMap(Stream::of)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());
    }


    public List<Edge> runKruskal() {

        SubSet[] subSets = SubSet.makeSets(nVertices);

        List<Edge> sortedEdges = getEdges();

        List<Edge> solution = new ArrayList<>();
        for (Edge edge : sortedEdges) {

            if (edge.weight == Double.POSITIVE_INFINITY)
                break;
            int x = SubSet.find(subSets, vertices.get(edge.u));
            int y = SubSet.find(subSets, vertices.get(edge.v));

            if (x != y) {
                solution.add(edge);
                SubSet.union(subSets, x, y);
            }
        }

        return solution;
    }

    public void runAllPairsLongestPath() {
        for (int k = 0; k < nVertices; k++)
            for (int i = 0; i < nVertices; i++)
                for (int j = 0; j < nVertices; j++) {
                    if (edges[i][j].weight > edges[i][k].weight + edges[k][j].weight) {
                        edges[i][j].weight = edges[i][k].weight + edges[k][j].weight;
                        edges[i][j].next = edges[i][k].next;
                    }
                }


        updateEdges();
    }

    public void updateEdge(Edge edge) {
        int uId = vertices.get(edge.u);
        int vId = vertices.get(edge.v);
        System.out.println("uId+ +vId = " + uId + " " + vId);
        edge.weight = edges[uId][vId].weight;


        List<Edge> path = new ArrayList<>();

        int v;
        while (uId != vId) {
            v = edges[uId][vId].next;
            path.add(edges[uId][v]);
            uId = v;

        }
        edge.setPath(path);
    }

    private void updateEdges() {
        for (int i = 0; i < nVertices; i++) {
            for (int j = i + 1; j < nVertices; j++) {
                Edge edge = edges[i][j];
                updateEdge(edge);
            }
        }
    }

    private static class SubSet {
        int parent;
        int rank;

        public SubSet(int v, int rank) {
            this.parent = v;
            this.rank = rank;
        }

        static SubSet[] makeSets(int n) {
            SubSet[] subSets = new SubSet[n];
            for (int i = 0; i < n; ++i)
                subSets[i] = new SubSet(i, 0);

            return subSets;
        }


        static int find(SubSet[] subSets, int i) {
            // find root and make root as parent of i (path compression)
            if (subSets[i].parent != i)
                subSets[i].parent = find(subSets, subSets[i].parent);

            return subSets[i].parent;
        }


        static void union(SubSet[] subSets, int x, int y) {
            int xroot = find(subSets, x);
            int yroot = find(subSets, y);


            if (subSets[xroot].rank < subSets[yroot].rank)
                subSets[xroot].parent = yroot;
            else if (subSets[xroot].rank > subSets[yroot].rank)
                subSets[yroot].parent = xroot;
            else {
                subSets[xroot].parent = yroot;
                subSets[yroot].rank++;
            }
        }
    }
}
