package com.xlw.demo.xml;

import com.ximpleware.*;
import org.junit.jupiter.api.Test;

import java.io.*;

public class XmlTest2 {
    private static String CRCFILE_SPACE = "";

    @Test
    public void vtdXmlTest() {
        long currentTime = System.currentTimeMillis();
        long time1 = 0L;
        try {
            String filePath = "/Users/xieliwei/student.xml";
            //编码转换，因为VTD不支持GBK
            this.convertFile(filePath, "GBK", "UTF-8");
            time1 = System.currentTimeMillis() - currentTime;

            VTDGen vg = new VTDGen();
            vg.parseFile(filePath, true);
            VTDNav vn = vg.getNav();

            foreach3(vn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("转换编码用时===========：" + time1);
        System.out.println("共用时===========：" + (System.currentTimeMillis() - currentTime));
    }

    public void convertFile(String filePath, String fromEncoding, String toEncoding) throws IOException {
        File inputFile = new File(filePath);
        FileInputStream fis = new FileInputStream(inputFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, fromEncoding));

        File outputFile = new File(filePath + ".utf8");
        FileOutputStream fos = new FileOutputStream(outputFile);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, toEncoding));

        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
        }

        writer.close();
        reader.close();
    }

    private static void foreach3(VTDNav vn) throws NavException {
        printVN(vn, "");
        if (vn.toElement(VTDNav.FIRST_CHILD)) {
            foreach3(vn);
        } else {
            printVNTxt(vn);
        }
        while (vn.toElement(VTDNav.NEXT_SIBLING)) {
            //foreach3(vn);
            printVN(vn, "");
            if (vn.toElement(VTDNav.FIRST_CHILD)) {
                foreach3(vn);
            } else {
                printVNTxt(vn);
            }
        }
        vn.toElement(VTDNav.PARENT);
        return;
    }

    private static void foreach1(VTDNav vn) throws NavException {
        //指向根节点
        vn.toElement(VTDNav.ROOT);
        printVN(vn, "+");
        //指向一级节点
        if (vn.toElement(VTDNav.FIRST_CHILD)) {
            printVN(vn, "+++");
            //指向二级节点
            findSec(vn);
            while (vn.toElement(VTDNav.NEXT_SIBLING)) {
                printVN(vn, "+++");
                //指向二级节点
                findSec(vn);
            }

        }
    }

    private static void findSec(VTDNav vn)
            throws NavException {
        if (vn.toElement(VTDNav.FIRST_CHILD)) {
            //存在二级节点并打印
            printVN(vn, "+++++");
            printVNTxt(vn);
            //打印所有的二级节点
            while (vn.toElement(VTDNav.NEXT_SIBLING)) {
                printVN(vn, "+++++");
                printVNTxt(vn);
            }
            vn.toElement(VTDNav.PARENT);
        } else {
            printVNTxt(vn);
        }
    }

    private static void printVN(VTDNav vn, String pre) throws NavException {
        System.out.println("" + pre + "节点名：" + vn.toString(vn.getCurrentIndex()));
        findAttr(vn);
    }

    private static void printVNTxt(VTDNav vn) throws NavException {
        int t = vn.getText();
        if (t != -1) {
            System.out.print(" 节点值：" + vn.toString(t));
        }
    }

    private static void findAttr(VTDNav vn) throws NavException {
        AutoPilot ap = new AutoPilot(vn);
        ap.selectAttr("*");
        int attrCount = vn.getAttrCount();
        for (int i = 0; i < attrCount; i++) {
            int a = ap.iterateAttr();
            if (a != -1) {
                String attrName = vn.toString(a);
                int attrValueIndex = vn.getAttrVal(attrName);
                if (attrValueIndex != -1) {
                    String attrValue = vn.toString(attrValueIndex);
                    System.out.print("  节点属性值：" + attrName + " : " + attrValue + ";");
                }
            }
        }
        return;
    }

    private static void foreach3(VTDNav nav, XMLModifier xmlModifier) throws NavException, ModifyException, UnsupportedEncodingException {
        findAttr(nav, xmlModifier);
        if (nav.toElement(VTDNav.FIRST_CHILD)) {
            foreach3(nav, xmlModifier);
        } else {
            printVNTxt(nav, xmlModifier);
        }
        while (nav.toElement(VTDNav.NEXT_SIBLING)) {
            findAttr(nav, xmlModifier);
            if (nav.toElement(VTDNav.FIRST_CHILD)) {
                foreach3(nav, xmlModifier);
            } else {
                printVNTxt(nav, xmlModifier);
            }
        }

        nav.toElement(VTDNav.PARENT);
    }

    private static void printVNTxt(VTDNav vn, XMLModifier xmlModifier) throws NavException, ModifyException, UnsupportedEncodingException {
        int t = vn.getText();
        if (t != -1) {
            if (vn.toString(t).contains(" ")) {
                String value = vn.toString(t).replaceAll(" ", CRCFILE_SPACE);
                xmlModifier.updateToken(t, value);
            }
        }
    }

    private static void findAttr(VTDNav vn, XMLModifier xmlModifier) throws NavException, ModifyException {
        AutoPilot ap = new AutoPilot(vn);
        ap.selectAttr("*");
        boolean isDelete = false;
        if ("IED".equals(vn.toString(vn.getCurrentIndex()))) {
            int attrCount = vn.getAttrCount();
            for (int i = 0; i < attrCount; i++) {
                int a = ap.iterateAttr();
                if (a != -1) {
                    String attrName = vn.toString(a);
                    if (!"name".equals(attrName)) {
                        xmlModifier.removeAttribute(a);
                    }
                }
            }
        } else if ("FCDA".equals(vn.toString(vn.getCurrentIndex()))) {
            int attrCount = vn.getAttrCount();
            if (!isDelete) {
                for (int i = 0; i < attrCount; i++) {
                    int a = ap.iterateAttr();
                    if (a != -1) {
                        String attrName = vn.toString(a);
                        if ("desc".equals(attrName)) {
                            xmlModifier.removeAttribute(a);
                        }
                    }
                }
            } else {
                for (int i = 0; i < attrCount; i++) {
                    int a = ap.iterateAttr();
                    if (a != -1) {
                        String attrName = vn.toString(a);
                        if (!"bType".equals(attrName)) {
                            xmlModifier.removeAttribute(a);
                        }
                    }
                }
            }
        } else if ("CRC".equals(vn.toString(vn.getCurrentIndex()))) {
            int attrCount = vn.getAttrCount();
            for (int i = 0; i < attrCount; i++) {
                int a = ap.iterateAttr();
                if (a != -1) {
                    String attrName = vn.toString(a);
                    if ("id".equals(attrName)) {

                    }
                }
            }
            xmlModifier.remove();
        } else if ("GOOSESUB".equals(vn.toString(vn.getCurrentIndex()))) {
            isDelete = true;
        } else {
            int attrCount = vn.getAttrCount();
            for (int i = 0; i < attrCount; i++) {
                int a = ap.iterateAttr();
                if (a != -1) {
                    String attrName = vn.toString(a);
                    if ("desc".equals(attrName)) {
                        xmlModifier.removeAttribute(a);
                    }
                }
            }
        }
    }
}