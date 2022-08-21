package model.self;

import model.user.Student;

public class Reserve {

    public int day;
    public String type;
    public String foodName;
    public boolean eaten;
    public int price;
    public String selfName;

    public Reserve(int day, String type, String foodName, int price, String selfName) {
        this.day = day;
        this.type = type;
        this.foodName = foodName;
        this.price = price;
        this.selfName = selfName;
        eaten = false;
    }

    public void setToEaten() {
        eaten = true;

    }
}