package scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Template {
    public static String static_match_template = "";
    public static String dynamic_match_template = "";

    static {
        try {
            InputStream in = Template.class.getClassLoader().getResources("templates/report.html").nextElement().openStream();
            static_match_template = new BufferedReader(new InputStreamReader(in))
                    .lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            InputStream in = Template.class.getClassLoader().getResources("templates/dynamic-report.html").nextElement().openStream();
            dynamic_match_template = new BufferedReader(new InputStreamReader(in))
                    .lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
