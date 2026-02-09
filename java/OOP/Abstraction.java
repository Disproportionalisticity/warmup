abstract class Human { 
    protected String name;
    protected int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void sleep() {
        System.out.println(this.name + "goes to sleep in their bedroom.");
    }

    abstract void eat();
}

interface CognitiveAbility {
    void analyze(String topic);
    void study(String subject);
    void solveProblem(String problem);
}

interface Cook {
    void cook(String dish);
    void washDishes();
}

class Child extends Human implements CognitiveAbility, Cook {
    private String favoriteToy;

    public Child(String name, int age, String favoriteToy) {
        super(name, age);
        this.favoriteToy = favoriteToy;
    }

    public void getFavoriteToy() {
        System.out.println(this.name + "'s favorite toy is " + this.favoriteToy);
    }

    @Override
    public void eat() {
        System.out.println(this.name + " eats a salad, but they want to eat pizza");
    }

    @Override
    public void analyze(String topic) {
        System.out.println(this.name + "is analyzing the following topic: " + topic);
    }

    @Override
    public void study(String subject) {
        System.out.println(this.name + "is studying the following subject: " + subject);
    }

    @Override
    public void solveProblem(String problem) {
        System.out.println(this.name + "is solving the following problm: " + problem);
    }

    @Override
    public void cook(String dish) {
        System.out.println(this.name + "is cooking the following dish: " + dish);
    }

    @Override
    public void washDishes() {
        System.out.println(this.name + "is washing the dishes");
    }
}

class Abstraction {
    public static void main(String[] args) {
        Child child = new Child("Hulio", 7, "Transformer");
        child.getFavoriteToy();
        child.eat();
        child.sleep();
        child.analyze("Toy's size and number");
        child.study("Basic arithmetics");
        child.solveProblem("Jigsaw puzzle");
        child.cook("Breakfast Cereals");
        child.washDishes();

        /* Output
            Hulio's favorite toy is Transformer
            Hulio eats a salad, but they want to eat pizza
            Huliogoes to sleep in their bedroom.
            Huliois analyzing the following topic: Toy's size and number
            Huliois studying the following subject: Basic arithmetics
            Huliois solving the following problm: Jigsaw puzzle
            Huliois cooking the following dish: Breakfast Cereals
            Huliois washing the dishes
         */
    }
}
