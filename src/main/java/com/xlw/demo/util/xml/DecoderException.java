package com.xlw.demo.util.xml;

public class DecoderException
        extends ServiceHandlerException {
    private static final long serialVersionUID = 5058640639233323039L;

    public DecoderException(String msg) {
        super(msg);
        setEcode("00000911");
    }

    public DecoderException(Throwable cause) {
        super(cause);
        setEcode("00000911");
    }

    public DecoderException(String msg, Throwable cause) {
        super(msg, cause);
        setEcode("00000911");
    }
}