package Creational;

interface Shape {
    public double getPerimeter();
}

class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * this.radius;
    }
}

class Square implements Shape {
    private double length;

    public Square(double length) {
        this.length = length;
    }

    public double getPerimeter() {
        return Math.pow(this.length, 2);
    }
}

class Rectangle implements Shape {
    private double length;
    private double height;

    public Rectangle(double length, double height) {
        this.length = length;
        this.height = height;
    }

    public double getPerimeter() {
        return this.length * this.height;
    }
}

class ShapeFactory {
    public static Shape getShape(String shapeType, double... dimensions) {
        if (shapeType == null) {
            return null;
        }

        switch (shapeType.toLowerCase()) {
            case "circle":
                if (dimensions.length != 1) {
                    return null;
                }
                return new Circle(dimensions[0]);

            case "rectangle":
                if (dimensions.length != 2) {
                    return null;
                }
                return new Rectangle(dimensions[0], dimensions[1]);

            case "square": 
                if (dimensions.length != 1) {
                    return null;
                }
                return new Square(dimensions[0]);

            default:
                return null;
        }
    }
}

public class Factory {
    public static void main(String[] args) {
        Shape circle = ShapeFactory.getShape("circle", 3);
        Shape square = ShapeFactory.getShape("square", 5);
        Shape rectangle = ShapeFactory.getShape("rectangle", 5, 3);

        System.out.println(circle.getPerimeter());
        System.out.println(square.getPerimeter());
        System.out.println(rectangle.getPerimeter());
    }
}
