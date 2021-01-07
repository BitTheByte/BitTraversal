package scanner;

import utils.UrlUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static burp.BurpExtender.helpers;
import static burp.BurpExtender.stdout;

public class Mutator {
    static List<String> staticPayloads = new ArrayList<>();

    private static void populateStaticPayloads() throws IOException {
        if (!staticPayloads.isEmpty())
            return;

        HttpURLConnection conn = (HttpURLConnection) new URL("https://pastebin.com/raw/n0ayCmxr").openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            staticPayloads.add(line);
        }
        br.close();
        conn.disconnect();
    }

    private static List<String> staticMutator(URL url) throws IOException {
        populateStaticPayloads();
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
                stdout.println(baseURL + payload);
            }
            baseURL.append(segment).append("/");
        }
        return mutations;
    }

    public static List<String> mutate(URL url) throws IOException {
        return staticMutator(url);
    }
}
