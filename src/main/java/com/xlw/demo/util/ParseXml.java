package com.xlw.demo.util;

import com.alibaba.fastjson.JSON;
import com.ximpleware.AutoPilot;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

import java.util.ArrayList;
import java.util.List;

public class ParseXml {
    /**
     * @param args
     */
    public static void main(String[] args) {
        parse2();
    }

    private static void parse1() {
        try {
            VTDGen gen = new VTDGen();
            //解析student.xml对象，不含有命名空间
            gen.parseFile("/Users/xieliwei/student.xml", false);
            VTDNav nav = gen.getNav();
            AutoPilot pilot = new AutoPilot();
            //将导航器绑定到pilot对像上
            //如果把VTDNav表达为车辆上导航仪器的话，那么AutoPilot就代表开车人，他能更智能化的找到XPATH表达的含义
            pilot.bind(nav);

            //设置重新设置一个xpath表达式的字符串，但是通常是在之后调用
            pilot.selectXPath("/students/student");
            //evalXPath()返回nodeset集合中的下一个节点，如果检测到为匹配的节点，则返回为-1
            System.out.println(pilot.evalXPath());
            System.out.println(pilot.evalXPathToBoolean());
            System.out.println(pilot.evalXPathToNumber());
            System.out.println(pilot.evalXPathToString());
            System.out.println(pilot.getExprString());

            //最终操作XML的，还得让VTDNav来做,可见pilot也只是起了一个导航的作用
            if (pilot.evalXPath() != -1) {
                System.out.println(nav.toString(nav.getAttrVal("name")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void parse2() {
        try {
            VTDGen gen = new VTDGen();
            // 解析student.xml对象，不含有命名空间
            gen.parseFile("/Users/xieliwei/student.xml", false);
            VTDNav nav = gen.getNav();
            AutoPilot ap = new AutoPilot();
            AutoPilot ape = new AutoPilot();
            // 将导航器绑定到ap对像上
            // 如果把VTDNav表达为车辆上导航仪器的话，那么Autoap就代表开车人，他能更智能化的找到XPATH表达的含义
            ap.bind(nav);
            ape.bind(nav);
            // 设置重新设置一个xpath表达式的字符串，但是通常是在之后调用
            ape.selectXPath("/body/records/record");


            List<String> records = new ArrayList<String>();
            String school = "";
            // evalXPath()返回nodeset集合中的下一个节点，如果检测到为匹配的节点，否则返回为-1
            while (ape.evalXPath() != -1) {
                ap.selectXPath("school");
                school = ap.evalXPathToString();
                records.add(school);
            }

            ape.resetXPath();

            for (String s : records) {
                System.out.println("===" + s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
