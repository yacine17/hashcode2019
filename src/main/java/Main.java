import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static Set<Photo> rawSet;

    public static void main(String[] args) {
        rawSet = readFile("a_example.txt");

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

    public static Set<Photo> verticalPhotos() {
        Set<Photo> verticals = rawSet.stream().filter(photo -> photo.isVertical)
                .sorted(Comparator.comparingLong(Photo::intersactionCount))
                .collect(Collectors.toSet());

        verticals.stream()
    }
}
