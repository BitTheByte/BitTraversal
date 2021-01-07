package scanner;

import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.IRequestInfo;
import utils.UrlUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static burp.BurpExtender.*;

public class Executor {
    public static String CRLF = "\r\n";

    private IHttpRequestResponse getHTTPResponse(IHttpRequestResponse basepair, String url, List<String> headers) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        StringBuilder request = new StringBuilder();

        for (String line : headers) {
            if (line.contains("HTTP/")) {
                request.append(String.format("%s %s HTTP/1.1%s",
                        helpers.analyzeRequest(basepair).getMethod(),
                        new URL(url).getPath(),
                        CRLF
                ));
                continue;
            }

            String[] pair = line.split(":", 2);
            connection.setRequestProperty(pair[0], pair[1]);
            request.append(line).append(CRLF);
        }
        request.append(CRLF);

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder(UrlUtils.dumpHeaders(connection));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            response.append(line).append(CRLF);
        }

        br.close();
        connection.disconnect();
        return UrlUtils.createRequestResponsePair(basepair, request.toString().getBytes(), response.toString().getBytes());
    }


    public void Scan(IHttpRequestResponse messageInfo) throws IOException, URISyntaxException {

        IRequestInfo requestInfo = BurpExtender.helpers.analyzeRequest(messageInfo);
        URL requestURL = requestInfo.getUrl();

        if (!callbacks.isInScope(requestURL))
            return;

        URL baseURL = UrlUtils.clearSemiColon(UrlUtils.clearQueryParameters(requestURL));

        for (String mutation: Mutator.mutate(baseURL)){
            try {
                IHttpRequestResponse pair = this.getHTTPResponse(messageInfo, mutation, requestInfo.getHeaders());

                String content = new String(pair.getResponse(), StandardCharsets.UTF_8);
                String staticMatch = Detector.staticDetection(content);
                //String dynamicMatch = Detector.dynamicDetection(mutation, content);

                if (staticMatch != null)
                    Detector.report(pair, staticMatch);

                //if (dynamicMatch != null)
                //    Detector.report(pair, dynamicMatch);

            }catch (Exception e){
                stderr.println(e);
            }
        }
    }
}
