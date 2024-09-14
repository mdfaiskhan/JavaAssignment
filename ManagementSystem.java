import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ManagementSystem {
    private ArrayList<Employee> employees;
    private final String FILE_NAME = "resources/employees.dat";

    public ManagementSystem() {
        employees = loadEmployeesFromFile();
    }

    public void addEmployee(Employee employee) {
        for (Employee emp : employees) {
            if (emp.getId() == employee.getId()) {
                System.out.println("Employee with ID " + employee.getId() + " already exists.");
                return;
            }
        }
        employees.add(employee);
        System.out.println("Employee added successfully.");
    }

    public void displayAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees in the system.");
            return;
        }
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    public void displayAverageSalary() {
        if (employees.isEmpty()) {
            System.out.println("No employees to calculate salary.");
            return;
        }
        double totalSalary = 0;
        for (Employee emp : employees) {
            totalSalary += emp.getSalary();
        }
        double averageSalary = totalSalary / employees.size();
        System.out.println("Average Salary: $" + averageSalary);
    }

    public void saveEmployeesToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Employee> loadEmployeesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Employee>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading employees: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Calculate Average Salary");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice;

            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addEmployeeOption(scanner);
                        break;
                    case 2:
                        displayAllEmployees();
                        break;
                    case 3:
                        displayAverageSalary();
                        break;
                    case 4:
                        saveEmployeesToFile();
                        System.out.println("Exiting system. Data saved.");
                        return;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a number.");
                scanner.next(); // Consume invalid input
            }
        }
    }

    private void addEmployeeOption(Scanner scanner) {
        try {
            System.out.print("Enter Employee ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Position: ");
            String position = scanner.nextLine();

            System.out.print("Enter Salary: ");
            double salary = scanner.nextDouble();

            if (salary < 0) {
                System.out.println("Salary cannot be negative.");
                return;
            }

            Employee employee = new Employee(id, name, position, salary);
            addEmployee(employee);
        } catch (Exception e) {
            System.out.println("Invalid input, please try again.");
            scanner.next(); // Consume invalid input
        }
    }
}
