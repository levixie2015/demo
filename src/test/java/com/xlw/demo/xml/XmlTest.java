package com.xlw.demo.xml;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import org.junit.jupiter.api.Test;

public class XmlTest {

    @Test
    public void vtdXmlTest() {
        try {
            String filePath = "/Users/xieliwei/student.xml";

            VTDGen vg = new VTDGen();
            vg.parseFile(filePath, true);
            VTDNav vn = vg.getNav();

            foreach3(vn);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}