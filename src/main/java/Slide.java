import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Slide {
    Photo one;
    Photo two;
    Set<String> tags;

    public Slide(Photo photo) {
        one = photo;
        tags = photo.tags;
    }

    public Slide(Photo one, Photo two) {
        this.one = one;
        this.two = two;
        tags = new HashSet<>(one.tags);
        tags.addAll(two.tags);
    }

    public long minFactor(Slide s) {
        long i = intersect(s);
        long j = minus(s);
        long k = s.minus(this);
        return Math.min(Math.min(i, j), k);
    }

    public long intersect(Slide s) {
        return tags.stream()
                .filter(tag -> s.tags.contains(tag))
                .count();
    }

    public long minus(Slide s) {
        return tags.stream()
                .filter(tag -> !s.tags.contains(tag))
                .count();
    }

    @Override
    public String toString() {
        return two == null ? String.valueOf(one.id) : two.id + " " + one.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slide)) return false;

        Slide slide = (Slide) o;

        if (one.id != slide.one.id) return false;
        return two != null ? two.id == slide.two.id : slide.two == null;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(one.id);
        result = 31 * result + (two != null ? Objects.hash(two.id) : 0);
        return result;
    }
}



