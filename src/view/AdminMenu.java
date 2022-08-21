package view;

import model.user.Admin;
import model.user.Distributor;
import model.user.Student;

public abstract class AdminMenu {

    public static void printHelp() {
        System.out.println("""
                add student [username] [password] [name] [id(numbers only)] [in dorm?(yes or no)]
                add distributor [username] [password] [name] [self name]
                add admin [username] [password] [name]
                add food [food name] [food price] [food type(breakfast or lunch or dinner)]
                set menu [food1 name] [food2 name] [day] [meal(breakfast or lunch or dinner)]
                demands report [day]
                distribute report [day]
                next meal""");
    }

    public static void printFoodDemands(int day, String type, String[] food, int food1, int food2) {
        System.out.println("Food 1: " + food[0] + " -> Demands: " + food1 + "\nFood 2: " + food[1] + " -> Demands: " + food2);
    }

    public static void printDistributeDemands(String name, String[] food, int food1, int food2) {
        System.out.println("\n" + name + ": " + "\t" + food[0] + " -> " + food1 + "\t" + food[1] + " -> " + food2);
    }

    public static void printDistributorsList(Distributor distributor) {
        System.out.println("\nname: " + distributor.name + " - self name: " + distributor.selfName);
    }

    public static void printStudentsList(Student student) {
        System.out.println("\nname: " + student.name + " - id: " + student.id);
    }

    public static void printAdminsList(Admin admin) {
        System.out.println("\nname: " + admin.name);
    }
}
