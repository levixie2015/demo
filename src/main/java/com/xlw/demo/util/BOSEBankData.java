package com.xlw.demo.util;

import java.util.ArrayList;
import java.util.List;

public class BOSEBankData {
    public OpRep opRep;

    public static class OpRep {
        public String serialNo;
        public String retCode;
        public String errMsg;
        public List<OpResult> opResultSet = new ArrayList<>();
    }

    public static class OpResult {
        public String ACNO;
        public String HUMI;
        public String BIZH;
    }
}