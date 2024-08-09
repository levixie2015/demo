package com.xlw.demo.util.xml;

import java.util.LinkedHashMap;
import java.util.Map;

public class Message {
    public static final String HEAD_CONTENT_LENGTH = "ContentLength";
    public static final String HEAD_SERVICE_CODE = "ServiceCode";
    public static final String HEAD_TRANSACTION_CODE = "TransactionCode";
    public static final String HEAD_CONTENT_TAYPE = "ContentType";
    public static final String HEAD_RETURN_CODE = "ReturnCode";
    public static final String CONTENT_TYPE_XML = "XML";
    public static final String CONTENT_TYPE_GPB = "GPB";
    public static final String CONTENT_TYPE_JSON = "JSON";
    protected static final Map<String, Object> HEAD_TEMPLATE = new LinkedHashMap<String, Object>();

    static {
        HEAD_TEMPLATE.put("ContentLength", "0");
        HEAD_TEMPLATE.put("ServiceCode", "0");
        HEAD_TEMPLATE.put("TransactionCode", "0");
        HEAD_TEMPLATE.put("ContentType", "0");
        HEAD_TEMPLATE.put("ReturnCode", "0");
    }

    private Map<String, Object> head = new LinkedHashMap<String, Object>();


    private Map<String, Object> data = new LinkedHashMap<String, Object>();


    public Message() {
        this.head.putAll(HEAD_TEMPLATE);
    }

    public Map<String, Object> getHead() {
        return this.head;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setHead(String name, Object value) {
        this.head.put(name, value);
    }

    public Object getHead(String name) {
        return this.head.get(name);
    }

    public void removeHead(String name) {
        this.head.remove(name);
    }

    public void setData(String name, Object value) {
        this.data.put(name, value);
    }

    public Object getData(String name) {
        return this.data.get(name);
    }

    public void removeData(String name) {
        this.data.remove(name);
    }
}