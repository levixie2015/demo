package com.xlw.demo.util;

import com.alibaba.fastjson.JSON;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    public static void main(String[] args) throws Exception {
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<BOSEBankData>" +
                "<opRep>" +
                "<serialNo>111111</serialNo>" +
                "<retCode>返回码 0 成功 其它：失败 错误描述在errMsg 中</retCode>" +
                "<errMsg>错误描述</errMsg>" +
                "<opResultSet>" +
                "<opResult>" +
                "<ACNO>账号1</ACNO>" +
                "<HUMI>户名1</HUMI>" +
                "<BIZH>币种1</BIZH>" +
                "</opResult>" +
                "<opResult>" +
                "<ACNO>账号2</ACNO>" +
                "<HUMI>户名2</HUMI>" +
                "<BIZH>币种2</BIZH>" +
                "</opResult>" +
                "</opResultSet>" +
                "</opRep>" +
                "</BOSEBankData>";


        byte[] ws = xmlString.getBytes("UTF-8");

        // 创建VTDGen对象，用于生成VTD
        VTDGen vg = new VTDGen();
        vg.setDoc(ws, 0, ws.length);

        vg.parse(false);
        VTDNav vn = vg.getNav();

        // 创建数据结构对象
        BOSEBankData bankData = new BOSEBankData();
        BOSEBankData.OpRep opRep = new BOSEBankData.OpRep();
        bankData.opRep = opRep;

        // 遍历到opRep元素

        if (vn.matchElement("opRep")) {
            vn.toElement(VTDNav.FIRST_CHILD);

            // 解析serialNo
            if (vn.matchElement("serialNo")) {
                vn.toElement(VTDNav.FIRST_CHILD);
                opRep.serialNo = vn.toRawString(vn.getText());
            }

            // 解析retCode
            if (vn.matchElement("retCode")) {
                vn.toElement(VTDNav.FIRST_CHILD);
                opRep.retCode = vn.toRawString(vn.getText());
            }

            // 解析errMsg
            if (vn.matchElement("errMsg")) {
                vn.toElement(VTDNav.FIRST_CHILD);
                opRep.errMsg = vn.toRawString(vn.getText());
            }

            // 解析opResultSet
            if (vn.matchElement("opResultSet")) {
                vn.toElement(VTDNav.FIRST_CHILD);
                while (vn.toElement(VTDNav.NEXT_SIBLING)) {
                    if (vn.matchElement("opResult")) {
                        BOSEBankData.OpResult opResult = new BOSEBankData.OpResult();
                        opResult.ACNO = vn.toRawString(vn.getText());


                    }
                }
            }
        }

        System.out.println(JSON.toJSONString(bankData));
    }
}