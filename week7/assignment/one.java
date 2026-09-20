
    import java.util.*;

abstract class Shape {
    private static int counter = 1001;
    private final String shapeId;

    public Shape() {
        this.shapeId = "SHAPE-" + counter++;
    }

    public String getShapeId() {
        return shapeId;
    }

    public abstract double calculateArea();

    public void scale(double factor) {
    }

    public void scale(double xFactor, double yFactor) {
        scale(xFactor);
    }
}

class CircleShape extends Shape {
    private double radius;

    public CircleShape(double radius) {
        super();
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void scale(double factor) {
        this.radius *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        scale(xFactor);
    }
}

class SquareShape extends Shape {
    private double side;

    public SquareShape(double side) {
        super();
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public void scale(double factor) {
        this.side *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        scale(xFactor);
    }
}

public class one {
    public static void printArea(Shape s) {
        System.out.println(s.calculateArea());
    }

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        System.out.println("~" + String.format(Locale.US, "%.2f", c.calculateArea()));

        SquareShape sq = new SquareShape(4.0);
        System.out.println(sq.calculateArea());

        sq.scale(2.0);
        System.out.println(sq.calculateArea());

        printArea(c);
    }
}