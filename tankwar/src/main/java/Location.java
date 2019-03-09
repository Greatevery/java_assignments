import java.util.Objects;

public class Location implements Cloneable{
    private int x;
    private int y;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public Location clone() throws CloneNotSupportedException {
        return (Location)super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
