import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// --------------------------------------------------------
// 1. Клас Працівник (Employee)
// --------------------------------------------------------
class Employee {
    private String firstName;
    private String lastName;
    private double salary;

    public Employee(String firstName, String lastName, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " ($" + salary + ")";
    }
}

// --------------------------------------------------------
// 2. Клас Відділ (Department)
// --------------------------------------------------------
class Department {
    private String name;
    private Employee head;
    private List<Employee> staff;

    public Department(String name, Employee head) {
        this.name = name;
        this.head = head;
        this.staff = new ArrayList<>();
    }

    public void addEmployee(Employee emp) {
        staff.add(emp);
    }

    public String getName() { return name; }
    public Employee getHead() { return head; }
    public List<Employee> getStaff() { return staff; }
}

// --------------------------------------------------------
// 3. Клас Фірма (Firm) з логікою задач
// --------------------------------------------------------
class Firm {
    private String name;
    private Employee director;
    private List<Department> departments;
    public Firm(String name, Employee director) {
        this.name = name;
        this.director = director;
        this.departments = new ArrayList<>();
    }

    public void addDepartment(Department dept) {
        departments.add(dept);
    }

    // ==================================================================
    // ЗАДАЧА 1: Знайти максимальну зарплату
    // Вимога варіанту 12: Типізований цикл «for-each» (c)
    // ==================================================================
    public double getMaxSalary() {
        double maxSal = director.getSalary();

        for (Department dept : departments) {
            if (dept.getHead().getSalary() > maxSal) {
                maxSal = dept.getHead().getSalary();
            }
            for (Employee emp : dept.getStaff()) {
                if (emp.getSalary() > maxSal) {
                    maxSal = emp.getSalary();
                }
            }
        }
        return maxSal;
    }

    // ==================================================================
    // ЗАДАЧА 2: Знайти відділ, де є працівник з зарплатою вищою за начальника
    // Вимога варіанту 12: Типізований ітератор (b)
    // ==================================================================
    public Department findDepartmentWithRichEmployee() {
        Iterator<Department> deptIter = departments.iterator();

        while (deptIter.hasNext()) {
            Department currentDept = deptIter.next();
            double headSalary = currentDept.getHead().getSalary();

            Iterator<Employee> empIter = currentDept.getStaff().iterator();
            while (empIter.hasNext()) {
                Employee emp = empIter.next();
                if (emp.getSalary() > headSalary) {
                    return currentDept;
                }
            }
        }
        return null;
    }

    // ==================================================================
    // ЗАДАЧА 3: Скласти список усіх співробітників (директор + начальники + штат)
    // Вимога варіанту 12: Нетипізований ітератор (a)
    // ==================================================================
    public List<Employee> getAllEmployeesList() {
        List<Employee> allStaff = new ArrayList<>();

        allStaff.add(director);

        Iterator deptIter = departments.iterator();

        while (deptIter.hasNext()) {
            Object deptObj = deptIter.next();
            Department d = (Department) deptObj;


            allStaff.add(d.getHead());


            Iterator empIter = d.getStaff().iterator();
            while (empIter.hasNext()) {
                Object empObj = empIter.next();
                Employee e = (Employee) empObj;
                allStaff.add(e);
            }
        }
        return allStaff;
    }
}

// --------------------------------------------------------
// Головний клас для демонстрації
// --------------------------------------------------------
public class Main {
    public static void main(String[] args) {
        // 1. Створення даних
        Employee director = new Employee("Steve", "Jobs", 10000);
        Firm apple = new Firm("Apple", director);

        // Відділ розробки (Начальник отримує більше всіх)
        Employee headDev = new Employee("Tim", "Cook", 8000);
        Department devDept = new Department("Development", headDev);
        devDept.addEmployee(new Employee("John", "Doe", 3000));
        devDept.addEmployee(new Employee("Jane", "Smith", 3500));
        apple.addDepartment(devDept);

        // Відділ продажу (Тут створимо ситуацію для задачі 2: працівник багатший за начальника)
        Employee headSales = new Employee("Bob", "Marley", 4000);
        Department salesDept = new Department("Sales", headSales);
        salesDept.addEmployee(new Employee("Alice", "Wonderland", 2000));
        salesDept.addEmployee(new Employee("Super", "Seller", 4500)); // > 4000!
        apple.addDepartment(salesDept);

        System.out.println("=== Лабораторна робота 7. Варіант 12 ===");

        // Тест задачі 1
        System.out.println("\n1) Максимальна зарплата у фірмі:");
        System.out.println("Результат: $" + apple.getMaxSalary());

        // Тест задачі 2
        System.out.println("\n2) Відділ, де працівник отримує більше за начальника:");
        Department richDept = apple.findDepartmentWithRichEmployee();
        if (richDept != null) {
            System.out.println("Знайдено відділ: " + richDept.getName());
            System.out.println("(Начальник: " + richDept.getHead().getSalary() +
                    ", але там є хтось багатший)");
        } else {
            System.out.println("Такого відділу немає.");
        }

        // Тест задачі 3
        System.out.println("\n3) Список усіх співробітників фірми:");
        List<Employee> everyone = apple.getAllEmployeesList();
        for (Employee e : everyone) {
            System.out.println(" - " + e);
        }
        System.out.println("Всього співробітників: " + everyone.size());
    }
}