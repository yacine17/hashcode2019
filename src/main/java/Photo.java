import java.util.Set;

public class Photo {
    static int n = 0;
    public Set<String> tags;
    public boolean isVertical;
    public int id;

    public Photo(boolean isVertical, Set<String> tags) {
        this.tags = tags;
        this.isVertical = isVertical;
        this.id = n++;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "tags=" + tags +
                ", isVertical=" + isVertical +
                ", id=" + id +
                '}';
    }



}
