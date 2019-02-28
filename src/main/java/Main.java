import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.readFile();
    }


    public void readFile() {

        try {
            AtomicInteger counter = new AtomicInteger(0);
            Set photos = Files.lines(Paths.get(getClass().getResource("a_example.txt").getPath()))
                    .skip(1)
                    .map(s -> s.split("\\s"))
                    .map(photo -> new Photo(photo[0].equals("V"), Stream.of(photo).skip(2).collect(Collectors.toSet())))
                    .collect(Collectors.toSet());
        photos.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
