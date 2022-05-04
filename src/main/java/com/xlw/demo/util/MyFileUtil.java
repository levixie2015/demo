package com.xlw.demo.util;

import com.xlw.demo.persist.customized.entity.BalanceEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MyFileUtil {

    public static final String CHARSET = "GB2312";

    public static List<String> getfile(String filepath) {
        try {
            String temp = null;
            File f = new File(filepath);
            //指定读取编码用于读取中文
            InputStreamReader read = new InputStreamReader(new FileInputStream(f), CHARSET);
            List<String> readList = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(read);
            //bufReader = new BufferedReader(new FileReader(filepath));
            while ((temp = reader.readLine()) != null && !"".equals(temp)) {
                readList.add(temp);
            }
            read.close();
            return readList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<BalanceEntity> getBalanceList(String filepath) {
        List<BalanceEntity> balanceList = new ArrayList<BalanceEntity>();
        try {
            String temp = null;
            File f = new File(filepath);
            //指定读取编码用于读取中文
            InputStreamReader read = new InputStreamReader(new FileInputStream(f), CHARSET);
            BufferedReader reader = new BufferedReader(read);
            //bufReader = new BufferedReader(new FileReader(filepath));
            while ((temp = reader.readLine()) != null && !"".equals(temp)) {
                String[] txtList = temp.split("&");
                if (!txtList[1].contains("_")) {
                    continue;
                }

                BalanceEntity entity = new BalanceEntity();
                for (int i = 0; i < txtList.length - 1; i++) {
                    entity.setSort(txtList[0]);
                    entity.setTranNetCode(txtList[1]);
                    entity.setSubAcctNo(txtList[2]);
                    entity.setAmount(new BigDecimal(txtList[3]));
                    entity.setCompanyId(txtList[1].split("_")[1]);
                }
                balanceList.add(entity);
            }
            read.close();
            return balanceList;

        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<BalanceEntity> balanceList = getBalanceList("D:\\YE2022030136551.txt");
        System.out.println(balanceList.size());
    }
}
