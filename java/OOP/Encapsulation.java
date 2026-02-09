class Person {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public boolean validateAge() {
        return this.age > 0;
    }
}

class Encapsulation {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("Andreas");
        person.setAge(-1);

        if (!person.validateAge()) {
            System.out.println("Invalid age! Age must be a non-negative integer.");
        }

        person.setAge(17);
        System.out.println("Person's name: " + person.getName());
        System.out.println("Person's age: " + person.getAge());

        /* Output
            Invalid age! Age must be a non-negative integer.
            Person's name: Andreas
            Person's age: 17
         */
    }
}
