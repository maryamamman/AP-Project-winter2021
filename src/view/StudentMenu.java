package view;

import model.self.Reserve;

public abstract class StudentMenu {

    public static void printHelp() {
        System.out.println("""
                show menu [day] [meal(breakfast or lunch or dinner)]
                retake [day] [meal(breakfast or lunch or dinner)]
                transfer [day] [meal(breakfast or lunch or dinner)] ([a-zA-Z0-9_]+) (\\d+) (\\d+)
                "deposit [amount]"
                set menu [food1 name] [food2 name] [day] [meal(breakfast or lunch or dinner)]
                reserve report
                reserve [day] [meal(breakfast or lunch or dinner)] [food name] [self name]
                """);
    }

    public static void printReservations(Reserve reserve) {
        System.out.println("\nday: " + reserve.day + " - meal: " + reserve.type + " - food: " + reserve.foodName + " - self: " + reserve.selfName + " - gotten: " + reserve.eaten);
    }

    public static void printMenu(String [] menu, int price1, int price2) {
        System.out.println("1." + menu[0] + " - price: " + price1 + "\n2." + menu[1] + " - price: " + price2);
    }
}
