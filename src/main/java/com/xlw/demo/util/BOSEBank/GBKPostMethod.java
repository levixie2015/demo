package com.xlw.demo.util.BOSEBank;

import org.apache.commons.httpclient.methods.PostMethod;

public class GBKPostMethod extends PostMethod {
	public GBKPostMethod(String url) {
		super(url);
	}
	public String getRequestCharSet() { 
		return "GBK"; 
	} 

}
