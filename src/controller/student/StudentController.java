package controller.student;

import controller.Controller;
import controller.LoginController;
import controller.UserController;
import exceptions.BackException;
import exceptions.ExitException;
import exceptions.IllegalCommandException;
import model.self.FoodHandler;
import model.self.Self;
import model.self.Reserve;
import model.user.Student;
import view.StudentMenu;

import java.util.Objects;
import java.util.regex.Matcher;

public class StudentController extends UserController {
    Student student;

    public StudentController(Student student) {
        this.student = student;
    }

    @Override
    public Controller run() {
        Controller controller = this;
        try {
            String input = getCommand("student command");
            StudentCommand studentCommand = StudentCommand.findCommand(input);
            Matcher matcher = StudentCommand.getMatcher(input, studentCommand);
            if (matcher.find())
                switch (studentCommand) {
                    case HELP -> help();
                    case RESERVE -> reserve(Integer.parseInt(matcher.group(1)), matcher.group(2), matcher.group(3), matcher.group(4));
                    case SHOW_MENU -> showFoodMenu(Integer.parseInt(matcher.group(1)), matcher.group(2));
                    case DEPOSIT -> creditEnhancement(Integer.parseInt(matcher.group(1)));
                    case TRANSFER -> transfer(Integer.parseInt(matcher.group(1)), matcher.group(2), Integer.parseInt(matcher.group(3)));
                    case RETAKE -> retake(Integer.parseInt(matcher.group(1)), matcher.group(2));
                    case RESERVE_REPORT -> reserveReport();
                }

            return null;
        } catch (
                BackException e) {
            controller = new LoginController();
        } catch (
                ExitException e) {
            controller = null;
        } catch (
                IllegalCommandException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return controller;
    }

    private void help() {
        StudentMenu.printHelp();
    }

    private void reserveReport() {
        for (Reserve reserve : student.reserveList) {
            StudentMenu.printReservations(reserve);
        }
    }

    private void retake(int day, String type) {
        if (!student.hasFood(day, type))
            throw new IllegalCommandException("This food is not reserved!");
        student.retake(day, type);
        String selfName = "";
        for (Reserve reserve : student.reserveList)
            if (day == reserve.day && Objects.equals(type, reserve.type)) {
                selfName = reserve.selfName;
                break;
            }
        Self.selves.get(selfName).retakeFood(day, type, student.id);
    }

    private void transfer(int day, String type, int toId) {
        if (!student.hasFood(day, type))
            throw new IllegalCommandException("This food is not reserved!");
        for (Reserve reserve : student.reserveList) {
            if (Objects.equals(reserve.type, type) && reserve.day == day) {
                Self self = Self.selves.get(reserve.selfName);
                self.transferFood(reserve.day, reserve.type, reserve.foodName, student.id, toId);
                student.transfer(reserve, toId);
                break;
            }
        }
    }

    private void creditEnhancement(int amount) {
        student.deposit(amount);
    }


    private void showFoodMenu(int day, String type) {
        String[] menu = FoodHandler.getMenu(day, type);
        int price1 = 0;
        int price2 = 0;
        switch (type) {
            case "breakfast" -> {
                price1 = FoodHandler.breakfastPrice.get(menu[0]);
                price2 = FoodHandler.breakfastPrice.get(menu[1]);
            }
            case "lunch" -> {
                price1 = FoodHandler.lunchPrice.get(menu[0]);
                price2 = FoodHandler.lunchPrice.get(menu[1]);
            }
            case "dinner" -> {
                price1 = FoodHandler.dinnerPrice.get(menu[0]);
                price2 = FoodHandler.dinnerPrice.get(menu[1]);
            }
        }
        StudentMenu.printMenu(menu, price1, price2);
    }


    private void reserve(int day, String type, String foodName, String selfName) {
        if (student.hasFood(day, type))
            throw new IllegalCommandException("You've already reserved your food.");
        if (!FoodHandler.isAvailable(foodName, day, type))
            throw new IllegalCommandException("This food isn't available in the chosen time.");
        if (!student.canReserve(type))
            throw new IllegalCommandException("You're not in dorm.");
        int price = 0;
        switch (type) {
            case "breakfast" -> price = FoodHandler.breakfastPrice.get(foodName);
            case "lunch" -> price = FoodHandler.lunchPrice.get(foodName);
            case "dinner" -> price = FoodHandler.dinnerPrice.get(foodName);
        }
        if (student.wallet >= price) {
            Reserve reserve = new Reserve(day, type, foodName, price, selfName);
            student.reserve(reserve);
        } else throw new IllegalCommandException(
                "You don't have enough money in your account.");
        Self.selves.get(selfName).reserveFood(day, type, foodName, student.id);
    }
}