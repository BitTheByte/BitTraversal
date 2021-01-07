package scanner;


import burp.BurpExtender;
import burp.IHttpRequestResponse;
import utils.UrlUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static burp.BurpExtender.stderr;

public class Detector {
    private static String[] staticRegex;

    static {
        try {
            staticRegex = UrlUtils.getHTTPContent("https://raw.githubusercontent.com/BitTheByte/BitTraversal/master/list/regex.list").split("\n");
            Logger.info(staticRegex);
        } catch (IOException e) {
            stderr.println(e);
        }
    }

    public static String staticDetection(String content) {
        for (String match : staticRegex) {
            if (Pattern.compile(match).matcher(content).find())
                return String.format(Template.static_match_template, match);
        }
        return null;
    }


    private static Map<String, String> responseMap = new HashMap<>();

    public static String dynamicDetection(String url, String content) throws MalformedURLException {
        for (Map.Entry<String, String> entry : responseMap.entrySet()) {
            if (entry.getValue().equals(content) &&
                    new URL(url).getHost().equals(new URL(entry.getKey()).getHost())) {
                return String.format(Template.dynamic_match_template, url, entry.getKey());
            }
        }

        if (!responseMap.containsKey(url))
            responseMap.put(url, content);

        return null;
    }


    public static void report(IHttpRequestResponse messageInfo, String match) throws MalformedURLException {
        BurpExtender.callbacks.addScanIssue(new Reporter(
                messageInfo,
                "Path Traversal",
                match,
                "Certain"));
    }


}