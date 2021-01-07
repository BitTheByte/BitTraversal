package burp;

import scanner.Executor;
import utils.UrlUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class BurpExtender implements burp.IBurpExtender, burp.IHttpListener
{
    public static PrintWriter stdout;
    public static PrintWriter stderr;
    public static burp.IExtensionHelpers helpers;
    public static IBurpExtenderCallbacks callbacks;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks)
    {
        BurpExtender.callbacks = callbacks;
        callbacks.setExtensionName("Bit/Traversal");

        stdout  = new PrintWriter(callbacks.getStdout(), true);
        stderr  = new PrintWriter(callbacks.getStderr(),true);
        helpers = callbacks.getHelpers();
        stdout.println("0.1v - loaded");

        callbacks.registerHttpListener(this);
    }


    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, burp.IHttpRequestResponse messageInfo) throws IOException, URISyntaxException {
        if (!messageIsRequest)
            return;
        new Executor().Scan(messageInfo);
    }
}
