package com.xlw.demo.util.xml;

public class EncoderException extends ServiceHandlerException {
    private static final long serialVersionUID = 158631558460921409L;

    public EncoderException(String msg) {
        super(msg);
        setEcode("00000912");
    }

    public EncoderException(Throwable cause) {
        super(cause);
        setEcode("00000912");
    }

    public EncoderException(String msg, Throwable cause) {
        super(msg, cause);
        setEcode("00000912");
    }
}