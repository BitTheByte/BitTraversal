package scanner;

import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.IHttpService;
import burp.IScanIssue;

import java.net.MalformedURLException;
import java.net.URL;

public class Reporter implements IScanIssue {
    private IHttpRequestResponse basePair;
    private int issueType;
    private URL url;

    private String issueName;
    private String severity;
    private String confidence;
    private String issueDetail;

    Reporter(IHttpRequestResponse basePair,
             String issueName,
             String issueDetail,
             String confidence
    ) throws MalformedURLException {
        this.basePair    = basePair;
        this.issueName   = issueName;
        this.confidence  = confidence;
        this.issueDetail = issueDetail;
        this.url         = BurpExtender.helpers.analyzeRequest(basePair).getUrl();
        this.severity    = "High";
        this.issueType   = 134217728;
    }

    @Override
    public URL getUrl() {
        return this.url;
    }

    @Override
    public String getIssueName() {
        return this.issueName;
    }

    @Override
    public int getIssueType() {
        return 134217728;
    }

    @Override
    public String getSeverity() {
        return this.severity;
    }

    @Override
    public String getConfidence() {
        return this.confidence;
    }

    @Override
    public String getIssueBackground() {
        return null;
    }

    @Override
    public String getRemediationBackground() {
        return null;
    }

    @Override
    public String getIssueDetail() {
        return this.issueDetail;
    }

    @Override
    public String getRemediationDetail() {
        return null;
    }

    @Override
    public IHttpRequestResponse[] getHttpMessages() {
        return new IHttpRequestResponse[]{this.basePair};
    }

    @Override
    public IHttpService getHttpService() {
        return this.basePair.getHttpService();
    }
}
