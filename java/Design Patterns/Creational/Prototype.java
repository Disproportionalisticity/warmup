package Creational;

import java.util.HashMap;
import java.util.Map;

interface Prototype {
    Prototype clone();
}

class Color implements Prototype {
    private String color;

    public Color(String color) {
        this.color = color;
    }

    private Color(Color color) {
        this.color = color.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public Color clone() {
        return new Color(this);
    }

    @Override
    public String toString() {
        return this.color;
    }
}

abstract class ShapeP implements Prototype {
    protected int x;
    protected int y;
    protected Color color;

    public ShapeP(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color.clone();
    }

    protected ShapeP(ShapeP shape) {
        this.x = shape.x;
        this.y = shape.y;
        // this.color = shape.color; // Shallow Copy
        this.color = shape.color.clone(); // Deep Copy
    }

    public abstract ShapeP clone();
}

class CircleP extends ShapeP {
    private int radius;

    public CircleP(int x, int y, Color color, int radius) {
        super(x, y, color);
        this.radius = radius;
    }

    private CircleP(CircleP circle) {
        super(circle);
        this.radius = circle.radius;
    }

    @Override
    public CircleP clone() {
        return new CircleP(this);
    }

    @Override
    public String toString() {
        return "Circle color: " + this.color + ", radius: " + this.radius; 
    }
}

class SquareP extends ShapeP {
    private int length;

    public SquareP(int x, int y, Color color, int length) {
        super(x, y, color);
        this.length = length;
    }

    private SquareP(SquareP square) {
        super(square);
        this.length = square.length;
    }

    public int getLength() {
        return length;
    }

    @Override
    public SquareP clone() {
        return new SquareP(this);
    }

    @Override
    public String toString() {
        return "Square color:" + this.color + ", length: " + this.length;
    }
}

class ShapeRegistry {
    private static ShapeRegistry instance;
    private Map<String, ShapeP> prototypes = new HashMap<>();

    private ShapeRegistry() {}

    public static ShapeRegistry getInstance() {
        if (ShapeRegistry.instance == null) {
            ShapeRegistry.instance = new ShapeRegistry();
        }
        return ShapeRegistry.instance;
    }

    public void register(String key, ShapeP prototype) {
        prototypes.put(key, prototype);
    }

    public ShapeP createClone(String key) {
        ShapeP prototype = prototypes.get(key);
        return prototype.clone();
    }
}

class Test {
    public static void main(String[] args) {
        // Color c1 = new Color("red");
        // System.out.println(c1.toString());
        // Color c2 = c1.clone();
        // c1.setColor("blue");
        // System.out.println(c1.toString());
        // System.out.println(c2.toString());

        Color color = new Color("blue");
        SquareP square = new SquareP(2, 3, color, 4);
        CircleP circle = new CircleP(3, 2, color, 3);

        ShapeRegistry registry = ShapeRegistry.getInstance();

        registry.register("blue square", square);
        registry.register("red circle", circle);

        ShapeP square2 = registry.createClone("blue square");
        ShapeP circle2 = registry.createClone("red circle");

        square.color.setColor("red");
        circle.color.setColor("green");

        System.out.println(square.toString());
        System.out.println(square2.toString());
        System.out.println(circle.toString());
        System.out.println(circle2.toString());
        System.err.println(square.getLength());
        // System.out.println(square2.getLength());
    }
}