public class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double newX) {
        x = newX;
    }

    public void setY(double newY) {
        y = newY;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean equals(Point other) {
        return other.getX() == x && other.getY() == y;
    }


}
