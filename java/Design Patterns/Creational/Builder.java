package Creational;

/*
 * Builder Design Pattern is a pattern used to create complex objects step by step. 
 * It is most powerful in cases where the object creation requires multiple steps, has different constrains, may contain optional behaviors, etc...
 */

class Car {
    // to fully understand the power of the Builder, imagine that most of these params are optional for the majority of the cases
    private String brand;
    private String model;
    private String color;
    private int seatNumber;
    private int doorNumber;
    private int horsePower;
    private double weight;

    private Car (CarBuilder carBuilder) {
        this.brand = carBuilder.brand;
        this.model = carBuilder.model;
        this.color = carBuilder.color;
        this.seatNumber = carBuilder.seatNumber;
        this.doorNumber = carBuilder.doorNumber;
        this.horsePower = carBuilder.horsePower;
        this.weight = carBuilder.weight;
    }

    public static class CarBuilder {
        private String brand;
        private String model;
        private String color;
        private int seatNumber;
        private int doorNumber;
        private int horsePower;
        private double weight;

        public CarBuilder setBrand(String brand) { this.brand = brand; return this;}
        public CarBuilder setModel(String model) { this.model = model; return this;}
        public CarBuilder setColor(String color) { this.color = color; return this;}
        public CarBuilder setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; return this;}
        public CarBuilder setDoorNumber(int doorNumber) { this.doorNumber = doorNumber; return this;}
        public CarBuilder setHorsePower(int horsePower) { this.horsePower = horsePower; return this;}
        public CarBuilder setWeight(double weight) { this.weight = weight; return this;}
        public Car build() {return new Car(this);}
    }

    @Override
    public String toString() {
        return "Car brand: " + this.brand + ", model: " + this.model + ", color: " + this.color + ", number of seats: " + 
        this.seatNumber + ", number of doors: " + this.doorNumber + ", weight: " + this.weight + ", horse power: " + horsePower; 
    }
}

interface CarBuilderInterface {
    void setBrand();
    void setModel();
    void setColor();
    void setSeatNumber();
    void setDoorNumber();
    void setHorsePower();
    void setWeight();
    Car getCar();
}

class BMWX3BlackCarBuilder implements CarBuilderInterface {
    private Car.CarBuilder builder = new Car.CarBuilder();
    public void setBrand() {builder.setBrand("BMW");}
    public void setModel() {builder.setModel("X3");}
    public void setColor() {builder.setColor("black");}
    public void setSeatNumber() {builder.setSeatNumber(5);}
    public void setDoorNumber() {builder.setDoorNumber(4);}
    public void setHorsePower() {builder.setHorsePower(255);}
    public void setWeight() {builder.setWeight(1930);}
    public Car getCar() {return builder.build();}
}

class MercedesG350CarBuilder implements CarBuilderInterface {
    private Car.CarBuilder builder = new Car.CarBuilder();
    public void setBrand() {builder.setBrand("Mercedes-Benz");}
    public void setModel() {builder.setModel("G 350");}
    public void setColor() {builder.setColor("gray");}
    public void setSeatNumber() {builder.setSeatNumber(5);}
    public void setDoorNumber() {builder.setDoorNumber(4);}
    public void setHorsePower() {builder.setHorsePower(286);}
    public void setWeight() {builder.setWeight(2560);}
    public Car getCar() {return builder.build();}
}

class CarBuilderDirector {
    public Car construct(CarBuilderInterface builder) {
        builder.setBrand();
        builder.setModel();
        builder.setColor();
        builder.setSeatNumber();
        builder.setDoorNumber();
        builder.setHorsePower();
        builder.setWeight();
        return builder.getCar();
    }
}

class Main {
    public static void main(String[] args) {
        // Builder
        Car.CarBuilder builder = new Car.CarBuilder();
        Car bmw = builder.setBrand("BMW")
                         .setModel("X3")
                         .setColor("black")
                         .setSeatNumber(5)
                         .setDoorNumber(4)
                         .setHorsePower(255)
                         .setWeight(1930)
                         .build();
        System.out.println(bmw.toString());

        // Builder + Director
        CarBuilderDirector carBuilderDirector = new CarBuilderDirector();
        bmw = carBuilderDirector.construct(new BMWX3BlackCarBuilder());
        Car merc = carBuilderDirector.construct(new MercedesG350CarBuilder());

        System.out.println(bmw.toString());
        System.out.println(merc.toString());
    }
}
