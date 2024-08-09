package com.xlw.demo.util.xml;

public interface MessageConvert {
    void decode(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, Message paramMessage);

    byte[] encode(Message paramMessage);
}