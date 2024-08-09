package com.xlw.demo.util.xml;

public class RapidServerException extends RuntimeException {
    private static final long serialVersionUID = -8835422891477554122L;
    protected String ecode;

    public RapidServerException(String msg) {
        super(msg);
        this.ecode = "00999999";
    }

    public RapidServerException(Throwable cause) {
        super(cause);
        this.ecode = "00999999";
    }

    public RapidServerException(String msg, Throwable cause) {
        super(msg, cause);
        this.ecode = "00999999";
    }


    public String getEcode() {
        return this.ecode;
    }


    public void setEcode(String ecode) {
        this.ecode = ecode;
    }
}