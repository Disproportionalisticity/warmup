package Structural;

// interface IPizza {
//     String getDescription();
//     double getCost();
// }

// class PlainPizza implements IPizza {
//     @Override
//     public String getDescription() {
//         return "Plain Pizza: dough, sauce, mozzarella";
//     }

//     @Override
//     public double getCost() {
//         return 10.0;
//     }
// }

// abstract class PizzaDecorator implements IPizza {
//     protected IPizza decoratedPizza;

//     public PizzaDecorator(IPizza decoratedPizza) {
//         this.decoratedPizza = decoratedPizza;
//     }
// }

// class ParmesanPizzaDecorator extends PizzaDecorator {
//     public ParmesanPizzaDecorator(IPizza decoratedPizza) {
//         super(decoratedPizza);
//     }

//     @Override
//     public String getDescription() {
//         return this.decoratedPizza.getDescription() + ", Parmesan";
//     }

//     @Override
//     public double getCost() {
//         return this.decoratedPizza.getCost() + 5.0;
//     }
// }

// class PepperoniPizzaDecorator extends PizzaDecorator {
//     public PepperoniPizzaDecorator(IPizza decoratedPizza) {
//         super(decoratedPizza);
//     }

//     @Override
//     public String getDescription() {
//         return this.decoratedPizza.getDescription() + ", Pepperoni";
//     }

//     @Override
//     public double getCost() {
//         return this.decoratedPizza.getCost() + 3.0;
//     }
// }

// class OnionPizzaDecorator extends PizzaDecorator {
//     public OnionPizzaDecorator(IPizza decoratedPizza) {
//         super(decoratedPizza);
//     }

//     @Override
//     public String getDescription() {
//         return this.decoratedPizza.getDescription() + ", Onion";
//     }

//     @Override
//     public double getCost() {
//         return this.decoratedPizza.getCost() + 2.0;
//     }
// }

// class DecoratorTest {
//     public static void main(String[] args) {
//         IPizza pizza = new PlainPizza();
//         System.out.println("Description: " + pizza.getDescription());
//         System.out.println("Cost: " + pizza.getCost());

//         IPizza parmesanPizza = new ParmesanPizzaDecorator(new PlainPizza());
//         System.out.println("Description: " + parmesanPizza.getDescription());
//         System.out.println("Cost: " + parmesanPizza.getCost());

//         IPizza peperoniOnionPizza = new PepperoniPizzaDecorator(new OnionPizzaDecorator(new PlainPizza()));
//         System.out.println("Description: " + peperoniOnionPizza.getDescription());
//         System.out.println("Cost: " + peperoniOnionPizza.getCost());

//         IPizza fullPizza = new ParmesanPizzaDecorator(new PepperoniPizzaDecorator(new OnionPizzaDecorator(new PlainPizza())));
//         System.out.println("Description: " + fullPizza.getDescription());
//         System.out.println("Cost: " + fullPizza.getCost());
//     }
// }

interface IPriceable {
    double getCost();
}

interface IDescribeable {
    String getDescription();
}

interface IPizza extends IPriceable, IDescribeable {}

class PlainPizza implements IPizza {
    @Override
    public String getDescription() {
        return "Plain Pizza: dough, sauce, mozzarella";
    }

    @Override
    public double getCost() {
        return 10.0;
    }
}

class PizzaToppingDecorator implements IPizza {
    protected IPizza decoratedPizza;
    protected String toppingName;
    protected double toppingCost;

    public PizzaToppingDecorator(IPizza decoratedPizza, String toppingName, double toppingCost) {
        this.decoratedPizza = decoratedPizza;
        this.toppingName = toppingName;
        this.toppingCost = toppingCost;
    }

    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + ", " + this.toppingName;
    }

    @Override
    public double getCost() {
        return decoratedPizza.getCost() + this.toppingCost;
    }
}

class PizzaDiscountDecorator implements IPizza {
    protected IPizza decoratedPizza;
    protected double discount;

    public PizzaDiscountDecorator(IPizza decoratedPizza, double discount) {
        this.decoratedPizza = decoratedPizza;
        this.discount = discount;
    }

    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + "; with a discount of " + this.discount ;
    }

    @Override
    public double getCost() {
        if (this.discount > 0 && this.discount < 100) {
            return this.decoratedPizza.getCost() * (100 - this.discount) / 100;
        } else {
            return this.decoratedPizza.getCost();
        }
    }
}

class DecoratorTest {
    public static void main(String[] args) {
        IPizza pizza = new PlainPizza();
        System.out.println("Description: " + pizza.getDescription());
        System.out.println("Cost: " + pizza.getCost());

        IPizza fullPizza = new PizzaDiscountDecorator(
                               new PizzaToppingDecorator(
                                   new PizzaToppingDecorator(
                                       new PizzaToppingDecorator(
                                           new PlainPizza(), "Onion", 2.0),
                                       "Pepperoni", 3.0),
                                   "Parmesan", 5.0),
                                   20.0);
        System.out.println("Description: " + fullPizza.getDescription());
        System.out.println("Cost: " + fullPizza.getCost());
    }
}
