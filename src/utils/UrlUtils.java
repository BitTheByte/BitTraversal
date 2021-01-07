package utils;

import burp.IHttpRequestResponse;
import burp.IHttpService;

import java.net.*;
import java.util.Iterator;

public class UrlUtils {
    public static String CRLF = "\r\n";

    public static String dumpHeaders(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = conn.getHeaderFields().keySet().iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            if(name != null){
                sb.append(name);
                sb.append(": ");
            }
            sb.append(conn.getHeaderField(name));
            sb.append(CRLF);
        }
        sb.append(CRLF);
        return sb.toString();
    }


    public static  URL clearQueryParameters(URL url) throws URISyntaxException, MalformedURLException {
        URI uri = url.toURI();
        return new URI(uri.getScheme(),
                uri.getAuthority(),
                uri.getPath(),
                null,
                null).toURL();

    }

    public static  URL clearSemiColon(URL url) throws MalformedURLException {
        return new URL( url.toString().split(";")[0] );
    }


    public static IHttpRequestResponse createRequestResponsePair(IHttpRequestResponse basePair, byte[] request, byte[] response){
        return  new IHttpRequestResponse() {
            @Override
            public byte[] getRequest() {
                return request;
            }

            @Override
            public void setRequest(byte[] message) {

            }

            @Override
            public byte[] getResponse() {
                return response;
            }

            @Override
            public void setResponse(byte[] message) {

            }

            @Override
            public String getComment() {
                return "";
            }

            @Override
            public void setComment(String comment) {

            }

            @Override
            public String getHighlight() {
                return "";
            }

            @Override
            public void setHighlight(String color) {

            }

            @Override
            public IHttpService getHttpService() {
                return basePair.getHttpService();
            }

            @Override
            public void setHttpService(IHttpService httpService) {

            }
        };
    }
}
