package model.user;

import controller.Time;
import model.Database;
import model.self.Reserve;

import java.util.ArrayList;

public class Distributor extends User{
    public static ArrayList<Distributor> distributors;
    static {
        distributors = new ArrayList<>();
    }
    public String selfName;
    public Distributor(String username, String password, String name, String selfName) {
        super(username, password, name);
        this.selfName = selfName;
        distributors.add(this);
    }

    public void giveFood(int id) {
        Student student = Student.getStudent(id);
        assert student != null;
        for (Reserve reserve : student.reserveList) {
            if (reserve.day == Time.day && reserve.type.equals(Time.currentMeal())) {
                reserve.setToEaten();
            }
        }
    }
}