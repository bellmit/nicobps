package obps.models;

public class Audittrail 
{
    private String username; 
    private String userid;
    private String actiontaken;
    private String pageurl;
    private String browser;
    private String os;
    private String ipaddress;
    private String entrydate;

    public Audittrail() {
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getActiontaken() {
        return actiontaken;
    }
    public void setActiontaken(String actiontaken) {
        this.actiontaken = actiontaken;
    }

    public String getPageurl() {
        return pageurl;
    }
    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
    }

    public String getBrowser() {
        return browser;
    }
    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }

    public String getIpaddress() {
        return ipaddress;
    }
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getEntrydate() {
        return entrydate;
    }
    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }
    
    
}

