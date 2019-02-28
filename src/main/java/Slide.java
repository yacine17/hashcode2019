public class Slide {
    Photo[] photos = new Photo[2];

    public Slide(Photo photo) {
        this.photos[0] = photo;
    }

    public Slide(Photo one, Photo two) {
        this.photos[0] = one;
        this.photos[1] = one;
    }

    public int minFactor() {
        return  0;
    }

}
