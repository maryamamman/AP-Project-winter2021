package view;

public abstract class DistributorMenu {

    public static void printHelp() {
        System.out.println("""
                check demand [id]
                give food [id]
                """);
    }

    public static void printDemand(String food) {
        System.out.println("food: " + food);
    }
}
