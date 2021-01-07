package scanner;


import burp.BurpExtender;
import burp.IHttpRequestResponse;
import java.net.MalformedURLException;

import static burp.BurpExtender.stdout;

public class Detector {
    private static  String[] staticRegex = {
            // ".*?",
            "root:x:\\d*:\\d*:root"
    };


    public static String staticDetection(String content){
        stdout.println(content);
        for(String match: staticRegex){
            if(content.replaceAll("\n","").replaceAll("\r","").matches(match))
                return String.format(Template.static_match_template, match);
        }
        return null;
    }

    public static String dynamicDetection(String content){
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