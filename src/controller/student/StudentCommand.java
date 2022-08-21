package controller.student;

import exceptions.IllegalCommandException;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StudentCommand {

    HELP("help"),
    SHOW_MENU("show menu (\\d+) (dinner|breakfast|lunch)"),
    RETAKE("retake (\\d+) (dinner|breakfast|lunch)"),
    TRANSFER("transfer (\\d+) (dinner|breakfast|lunch) (\\d+)"),
    DEPOSIT("deposit (\\d+)"),
    RESERVE_REPORT("reserve report"),
    RESERVE("reserve (\\d+) (dinner|breakfast|lunch) (\\w+) ([a-zA-Z0-9_]+)");

    private final Pattern pattern;
    private final String regex;

    StudentCommand(String regex) {
        pattern = Pattern.compile(regex);
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, StudentCommand command) {
        return command.pattern.matcher(input.toLowerCase(Locale.ROOT));
    }

    public static StudentCommand findCommand(String input) {
        for (StudentCommand command : StudentCommand.values()) {
            if (Pattern.matches(command.regex, input))
                return command;
        }
        throw new IllegalCommandException();
    }
}