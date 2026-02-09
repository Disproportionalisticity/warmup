class BaseClass {
    public int add(int a, int b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }

    public double add (double a, double b) {
        return a + b;
    }

    public double generaticArithmeticOperation (double a, double b) {
        return a * b;
    }
}

class SubClass extends BaseClass {
    @Override
    public double generaticArithmeticOperation (double a, double b) {
        return a / b;
    }
}

class Polymorphism {
    public static void main(String[] args) {
        BaseClass base = new BaseClass();
        int a = 1;
        int b = 2;
        int c = 3;
        System.out.println("Base add with a and b: "+ String.valueOf(base.add(a, b)));
        System.out.println("Multiple params add with a, b and c: " + String.valueOf(base.add(a, b, c)));
        double d = 5.6;
        double e = 7.2;
        System.out.println("Double type add with d and e: " + String.valueOf(base.add(d, e)));
        double f = 9.3;
        double g = 3.1;
        System.out.println("GenericArithmeticOperation from base class with f and g: " + String.valueOf(base.generaticArithmeticOperation(f, g)));
        SubClass sub = new SubClass();
        System.out.println("GenericArithmeticOperation from sub class with f and g: " + String.valueOf(sub.generaticArithmeticOperation(f, g)));

        /* Output
            Base add with a and b: 3
            Multiple params add with a, b and c: 6
            Double type add with d and e: 12.8
            GenericArithmeticOperation from base class with f and g: 28.830000000000002
            GenericArithmeticOperation from sub class with f and g: 3.0
         */
    }
}