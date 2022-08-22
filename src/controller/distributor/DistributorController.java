package controller.distributor;

import controller.Controller;
import controller.LoginController;
import controller.Time;
import controller.UserController;
import exceptions.BackException;
import exceptions.ExitException;
import exceptions.IllegalCommandException;
import model.self.Self;
import model.user.Distributor;
import view.DistributorMenu;

import java.util.regex.Matcher;

public class DistributorController extends UserController {
    Distributor distributor;
    Self self;

    public DistributorController(Distributor distributor) {
        this.distributor = distributor;
        self = Self.selves.get(distributor.selfName);
    }

    @Override
    public Controller run() {
        Controller controller = this;
        try {
            String input = getCommand("distributor command");
            DistributorCommand distributorCommand = DistributorCommand.findCommand(input);
            Matcher matcher = DistributorCommand.getMatcher(input, distributorCommand);
            if (matcher.find())
                switch (distributorCommand) {
                    case HELP -> help();
                    case CHECK_DEMAND -> checkDemand(Integer.parseInt(matcher.group(1)));
                    case GIVE_FOOD -> giveFood(Integer.parseInt(matcher.group(1)));
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
        DistributorMenu.printHelp();
    }

    private void giveFood(int id) {
        distributor.giveFood(id);
    }

    private void checkDemand(int id) {
        String food = "";
        switch (Time.currentMeal()){
            case "breakfast" -> food = self.breakfastStudents.get(Time.day).get(id);
            case "lunch" -> food = self.lunchStudents.get(Time.day).get(id);
            case "dinner" -> food = self.dinnerStudents.get(Time.day).get(id);
        }
        DistributorMenu.printDemand(food);
    }
}
