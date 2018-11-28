package model;

public class QueryObject {
    private String peer;
    private String fcn;
    private String[] args = new String[1];

    public QueryObject(String args) {
        peer = "peer0.org1.example.com";
        fcn = "query";
        this.args[0] = args;
    }

    public String getPeer() {
        return peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }

    public String getFcn() {
        return fcn;
    }

    public void setFcn(String fcn) {
        this.fcn = fcn;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
