package controller.admin;

import controller.Controller;
import controller.LoginController;
import controller.Time;
import controller.UserController;
import exceptions.BackException;
import exceptions.ExitException;
import exceptions.IllegalCommandException;
import model.self.FoodHandler;
import model.self.Self;
import model.user.Admin;
import model.user.Distributor;
import model.user.Student;
import view.AdminMenu;

import java.util.Objects;
import java.util.regex.Matcher;

public class AdminController extends UserController {
    Admin admin;

    public AdminController(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Controller run() {
        Controller controller = this;
        try {
            String input = getCommand("admin command");
            AdminCommand adminCommand = AdminCommand.findCommand(input);
            Matcher matcher = AdminCommand.getMatcher(input, adminCommand);
            if (matcher.find())
                switch (adminCommand) {
                    case HELP -> help();
                    case ADD_ADMIN -> addAdmin(matcher.group(1), matcher.group(2), matcher.group(3));
                    case ADD_DISTRIBUTOR -> addDistributor(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
                    case ADD_STUDENT -> addStudent(matcher.group(1), matcher.group(2), matcher.group(3), Integer.parseInt(matcher.group(4)), matcher.group(5));
                    case DISTRIBUTE_REPORT -> distributeReport(Integer.parseInt(matcher.group(1)), matcher.group(2));
                    case DEMANDS_REPORT -> demandsReport(Integer.parseInt(matcher.group(1)), matcher.group(2));
                    case ADD_FOOD -> addFood(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3));
                    case SET_FOOD -> setMenu(matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(2)), matcher.group(3));
                    case NEXT_MEAL -> nextMeal();
                    case ADMINS_LIST -> adminsList();
                    case STUDENTS_LIST -> studentsList();
                    case DISTRIBUTORS_LIST -> distributorsList();
                }

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

    private void distributorsList() {
        for (Distributor distributor : Distributor.distributors)
            AdminMenu.printDistributorsList(distributor);

    }

    private void studentsList() {
        for (Student student : Student.students)
            AdminMenu.printStudentsList(student);
    }

    private void adminsList() {
        for (Admin admin : Admin.admins)
            AdminMenu.printAdminsList(admin);
    }

    private void help() {
        AdminMenu.printHelp();
    }

    private void nextMeal() {
        Time.nextMeal();
    }

    private void setMenu(String foodName1, String foodName2, int day, String type) {
        FoodHandler.setFood(foodName1, foodName2, day, type);
    }

    private void addFood(String name, int price, String type) {
        FoodHandler.addFood(name, price, type);
    }

    private void addAdmin(String username, String password, String name) {
        Admin admin = new Admin(username, password, name);
    }

    private void addDistributor(String username, String password, String name, String selfName) {
        Distributor distributor = new Distributor(username, password, name, selfName);
    }

    private void addStudent(String username, String password, String name, int id, String inDorm) {
        Student student = new Student(username, password, name, id, Objects.equals(inDorm, "yes"));
    }

    private void distributeReport(int day, String type) {
        String[] food = FoodHandler.getMenu(day, type);
        Self.selves.forEach((name, self) -> {
            int food1 = self.demands(day, type, food[0]);
            int food2 = self.demands(day, type, food[1]);
            AdminMenu.printDistributeDemands(name, food, food1, food2);
        });
    }

    private void demandsReport(int day, String type) {
        String[] food = FoodHandler.getMenu(day, type);
        int food1 = 0;
        int food2 = 0;
        for (Self self : Self.selves.values()) {
            food1 += self.demands(day, type, food[0]);
            food2 += self.demands(day, type, food[1]);
        }
        AdminMenu.printFoodDemands(day, type, food, food1, food2);
    }


}