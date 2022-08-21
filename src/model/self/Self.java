package model.self;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Self {
    public static HashMap<String, Self> selves;
    public HashMap<Integer, HashMap<Integer, String>> breakfastStudents;
    public HashMap<Integer, HashMap<Integer, String>> lunchStudents;
    public HashMap<Integer, HashMap<Integer, String>> dinnerStudents;

    public Self(String name) {
        selves.put(name, this);
        breakfastStudents = new HashMap<>();
        lunchStudents = new HashMap<>();
        dinnerStudents = new HashMap<>();
    }

    public void reserveFood(int day, String type, String foodName, int id) {
        switch (type) {
            case "breakfast" -> {
                if (!breakfastStudents.containsKey(day))
                    breakfastStudents.put(day, new HashMap<>());
                breakfastStudents.get(day).put(id, foodName);
            }
            case "lunch" -> {
                if (!lunchStudents.containsKey(day))
                    lunchStudents.put(day, new HashMap<>());
                lunchStudents.get(day).put(id, foodName);
            }
            case "dinner" -> {
                if (!dinnerStudents.containsKey(day))
                    dinnerStudents.put(day, new HashMap<>());
                dinnerStudents.get(day).put(id, foodName);
            }
        }
    }

    public int demands(int day, String type, String foodName) {
        AtomicInteger count = new AtomicInteger();
        try {
            switch (type) {
                case "breakfast" -> {
                    breakfastStudents.get(day).forEach((key, value) -> {
                        if (value.equals(foodName))
                            count.getAndIncrement();
                    });
                }
                case "lunch" -> {
                    lunchStudents.get(day).forEach((key, value) -> {
                        if (value.equals(foodName))
                            count.getAndIncrement();
                    });
                }
                case "dinner" -> {
                    dinnerStudents.get(day).forEach((key, value) -> {
                        if (value.equals(foodName))
                            count.getAndIncrement();
                    });
                }
            }

        }catch (NullPointerException e){
            return 0;
        }

        return count.get();
    }
}