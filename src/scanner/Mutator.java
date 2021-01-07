package scanner;

import utils.UrlUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static burp.BurpExtender.stderr;

public class Mutator {
    static List<String> staticPayloads;

    static {
        try {
            staticPayloads = Arrays.asList(UrlUtils.getHTTPContent("https://raw.githubusercontent.com/BitTheByte/BitTraversal/master/list/payloads.list").split("\n"));
            Logger.info(staticPayloads);
        } catch (IOException e) {
            stderr.println(e);
        }
    }

    private static List<String> dynamicMutator(URL url) {
        throw new RuntimeException("not implemented");
    }

    private static List<String> staticMutator(URL url) {

        List<String> mutations = new ArrayList<>();
        String[] pathSegments = url.getPath().split("/");

        StringBuilder baseURL = new StringBuilder(url.getProtocol());
        baseURL.append("://");
        baseURL.append(url.getHost());
        baseURL.append(":");
        baseURL.append(url.getPort());
        baseURL.append("/");

        for (String segment: pathSegments) {
            if (segment.isEmpty())
                continue;
            for (String payload: staticPayloads){
                mutations.add(baseURL + payload);
                Logger.info(baseURL + payload);
            }
            baseURL.append(segment);
        }
        return mutations;
    }

    public static List<String> mutate(URL url) {
        return staticMutator(url);
    }
}
