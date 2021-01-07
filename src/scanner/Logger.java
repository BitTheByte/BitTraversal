package scanner;

import burp.BurpExtender;

import java.util.List;

class Logger {
    public static void info(List<String> msg) {
        for (String line : msg) {
            BurpExtender.stdout.println(line);
        }
    }

    public static void info(String[] msg) {
        for (String line : msg) {
            BurpExtender.stdout.println(line);
        }
    }

    public static void info(String msg) {
        BurpExtender.stdout.println(msg);
    }
}
