package model.self;

import java.util.*;

public class FoodHandler {
    public static HashMap<String, Integer> breakfastPrice;
    public static HashMap<String, Integer> lunchPrice;
    public static HashMap<String, Integer> dinnerPrice;
    public static HashMap<Integer, String[]> breakfastTable;
    public static HashMap<Integer, String[]> lunchTable;
    public static HashMap<Integer, String[]> dinnerTable;

    static {
        breakfastPrice = new HashMap<>();
        lunchPrice = new HashMap<>();
        dinnerPrice = new HashMap<>();
        breakfastTable = new HashMap<>();
        lunchTable = new HashMap<>();
        dinnerTable = new HashMap<>();
    }

    public static void addFood(String name, int price, String type) {
        switch (type) {
            case "breakfast" -> breakfastPrice.put(name, price);
            case "lunch" -> lunchPrice.put(name, price);
            case "dinner" -> dinnerPrice.put(name, price);
        }
    }

    public static void setFood(String foodName1, String foodName2, int day, String type) {
        switch (type) {
            case "breakfast" -> breakfastTable.put(day, new String[]{foodName1, foodName2});
            case "lunch" -> lunchTable.put(day, new String[]{foodName1, foodName2});
            case "dinner" -> dinnerTable.put(day, new String[]{foodName1, foodName2});
        }

    }
    public static boolean isAvailable(String foodName, int day, String type) {
        boolean isAvailable = false;
        switch (type) {
            case "breakfast" -> isAvailable = Arrays.asList(breakfastTable.get(day)).contains(foodName);
            case "lunch" -> isAvailable = Arrays.asList(lunchTable.get(day)).contains(foodName);
            case "dinner" -> isAvailable = Arrays.asList(dinnerTable.get(day)).contains(foodName);
        }
        return isAvailable;
    }

    public static String[] getMenu(int day, String type){
        String[] food = new String[2];
        switch (type){
            case "breakfast" -> food = FoodHandler.breakfastTable.get(day);
            case "lunch" -> food = FoodHandler.lunchTable.get(day);
            case "dinner" -> food = FoodHandler.dinnerTable.get(day);
        }
        return food;
    }
}