package controller.distributor;

import exceptions.IllegalCommandException;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum DistributorCommand {
    CHECK_DEMAND("check demand (\\d+)"),
    GIVE_FOOD("give food (\\d+)");

    private final Pattern pattern;
    private final String regex;

    DistributorCommand(String regex) {
        pattern = Pattern.compile(regex);
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, DistributorCommand command) {
        return command.pattern.matcher(input.toLowerCase(Locale.ROOT));
    }

    public static DistributorCommand findCommand(String input) {
        for (DistributorCommand command : DistributorCommand.values()) {
            if (Pattern.matches(command.regex, input))
                return command;
        }
        throw new IllegalCommandException();
    }
}