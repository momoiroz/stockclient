package model;

public class InvokeBuyObject {
    private String peer;
    private String fcn;
    private String[] args;

    public InvokeBuyObject(String[] args) {
        peer = "peer0.org1.example.com";
        fcn = "setOrder";
        this.args = args;
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
