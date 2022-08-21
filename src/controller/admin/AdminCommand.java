package controller.admin;

import exceptions.IllegalCommandException;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AdminCommand {

    HELP("help"),
    ADD_STUDENT("add student ([a-zA-Z0-9_]+) ([a-zA-Z0-9_]+) (\\w+) (\\d+) (yes|no)"),
    ADD_DISTRIBUTOR("add distributor ([a-zA-Z0-9_]+) ([a-zA-Z0-9_]+) (\\w+) ([a-zA-Z0-9_]+)"),
    ADD_ADMIN("add admin ([a-zA-Z0-9_]+) ([a-zA-Z0-9_]+) (\\w+)"),
    ADD_FOOD("add food (\\w+) (\\d+) (dinner|breakfast|lunch)"),
    SET_FOOD("set menu (\\w+) (\\w+) (\\d+) (dinner|breakfast|lunch)"),
    DEMANDS_REPORT("demands report (\\d+) (dinner|breakfast|lunch)"),
    DISTRIBUTE_REPORT("distribute report (\\d+) (dinner|breakfast|lunch)"),
    ADMINS_LIST("admins list"),
    STUDENTS_LIST("students list"),
    DISTRIBUTORS_LIST("distributors list"),
    NEXT_MEAL("next meal");

    private final Pattern pattern;
    private final String regex;

    AdminCommand(String regex) {
        pattern = Pattern.compile(regex);
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, AdminCommand command) {
        return command.pattern.matcher(input.toLowerCase(Locale.ROOT));
    }

    public static AdminCommand findCommand(String input) {
        for (AdminCommand command : AdminCommand.values()) {
            if (Pattern.matches(command.regex, input))
                return command;
        }
        throw new IllegalCommandException();
    }
}
