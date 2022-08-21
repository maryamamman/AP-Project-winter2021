package view;

import model.self.Reserve;

public abstract class StudentMenu {

    public static void printReservations(Reserve reserve) {
        System.out.println("\nday: " + reserve.day + " - meal: " + reserve.type + " - food: " + reserve.foodName + " - self: " + reserve.selfName + " - gotten: " + reserve.eaten);
    }

    public static void printMenu(String [] menu, int price1, int price2) {
        System.out.println("1." + menu[0] + " - price: " + price1 + "\n2." + menu[1] + " - price: " + price2);
    }
}
