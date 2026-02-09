package Structural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface IEmployee {
    void printDetails();
    int calculateSalary();
}

class Developer implements IEmployee {
    private String details;
    private int salary;

    public Developer(String details, int salary) {
        this.details = details;
        this.salary = salary;
    }

    @Override
    public void printDetails() {
        System.out.println(this.details);
    }

    @Override
    public int calculateSalary() {
        return this.salary;
    }
}

class Designer implements IEmployee {
    private String details;
    private int salary;

    public Designer(String details, int salary) {
        this.details = details;
        this.salary = salary;
    }

    @Override
    public void printDetails() {
        System.out.println(this.details);
    }

    @Override
    public int calculateSalary() {
        return this.salary;
    }
}

class Manager implements IEmployee {
    private String details;
    private int salary;
    private List<IEmployee> employees;

    public Manager(String details, int salary, IEmployee... employees) {
        this.details = details;
        this.salary = salary;
        this.employees = new ArrayList<>();
        this.employees.addAll(Arrays.asList(employees));
    }

    @Override
    public void printDetails() {
        System.out.println(this.details);
        for (IEmployee employee : this.employees) {
            employee.printDetails();
        }
    }

    @Override
    public int calculateSalary() {
        int finalSalary = this.salary;
        for (IEmployee employee: this.employees) {
            finalSalary += employee.calculateSalary();
        }
        return finalSalary;
    }
}

class CompositeTest {
    public static void main(String[] args) {
        IEmployee lostManager = new Manager("managerTier0", 12500,
            new Manager("ManagerTier1", 11000, 
                    new Designer("DesignerTier2", 8000),
                    new Developer("DeveloperTier2", 9000),
                    new Manager("ManagerTier2", 5000, 
                        new Designer("DesignerTier3", 3000)
                    )
                ),
            new Designer("DesignerTier1", 9000),
            new Developer("DeveloperTier1", 11500)
        );

        lostManager.printDetails();
        System.out.println(lostManager.calculateSalary());
    }
}