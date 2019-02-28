import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Set<Photo> photos = readFile("a_example.txt");
        List<Slide> slides = getSlides(photos);
        System.out.println(slides);
        Graph graph = new Graph(slides);
        System.out.println("done");
        graph.runAllPairsLongestPath();

        for (Edge[] edges:graph.edges
             ) {

            System.out.println(Arrays.deepToString(edges));
        }
    }

    public static Set<Photo> readFile(String file) {
        try {
            return Files.lines(Paths.get(Main.class.getResource(file).getPath()))
                    .skip(1)
                    .map(s -> s.split("\\s"))
                    .map(photo -> new Photo(photo[0].equals("V"), Stream.of(photo).skip(2).collect(Collectors.toSet())))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Slide> processVerticals(Set<Photo> photos) {
        List<Photo> verticals = photos
                .stream()
                .filter(photo -> photo.isVertical)
                .collect(Collectors.toList());

        List<V> list = new ArrayList<>();
        for (int i = 0; i < verticals.size(); i++) {
            Photo one = verticals.get(i);
            for (int j = i + 1; j < verticals.size(); j++) {
                Photo two = verticals.get(j);
                list.add(new V(i, j, one.similarTags(two)));
            }
        }
        Collections.sort(list);
        return list.stream()
                .map(v -> new Slide(verticals.get(v.i), verticals.get(v.j)))
                .collect(Collectors.toList());
    }

    public static List<Slide> getSlides(Set<Photo> photos) {
        List<Slide> slides = processVerticals(photos);
        slides.addAll(photos.stream()
                .filter(photo -> !photo.isVertical)
                .map(Slide::new)
                .collect(Collectors.toList()));

        return slides;
    }

    static class V implements Comparable<V> {
        int i;
        int j;
        long value;

        public V(int i, int j, long value) {
            this.i = i;
            this.j = j;
            this.value = value;
        }

        @Override
        public int compareTo(V o) {
            return Long.compare(value, o.value);
        }
    }
}
