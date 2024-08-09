package com.xlw.demo.QLExpress;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

import java.util.Map;

public class Test {

    @org.junit.jupiter.api.Test
    public void test1() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        String express = "a + b * c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }

    /**
     * 模拟低代码中动态if功能
     * <pre>
     *     执行过程:
     *      低代码引擎解析规则部分，转化未低成脚本语言
     *      读取从入参中读取流程变量配置，设置上下文
     *      执行运算并获取结果
     * </pre>
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void test2() throws Exception {
        String json = "{\n" +
                "\t\"rule\": {\n" +
                "        \"condition\": \"age > 18\",\n" +
                "        \"actions\": {\n" +
                "            \"allow\": \"accessGranted\",\n" +
                "            \"deny\": \"accessDenied\"\n" +
                "        }\n" +
                "    },\n" +
                "         \n" +
                "    \"parameters\": {\n" +
                "        \"age\": 20\n" +
                "    }\n" +
                "}";
        Map<String, Object> jsonMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
        Map<String, Object> rule = (Map<String, Object>) jsonMap.get("rule");
        Map<String, Object> parameters = (Map<String, Object>) jsonMap.get("parameters");

        //执行表达式
        Object result = testIf(rule, parameters);
        System.out.println(result);
    }

    /**
     * 替换 if then else 等关键字
     */
    @org.junit.jupiter.api.Test
    public void test3() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("否则", "else", null);

        String express = "如果 (语文 + 数学 + 英语 > 270) 则 {return 1;} 否则 {return 0;}";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        Object result = runner.execute(express, context, null, false, false, -1);
        System.out.println(result);
    }

    public Object testIf(Map<String, Object> rule, Map<String, Object> parameters) throws Exception {
        // 根据 Map 对象动态生成 QLExpress 表达式
        String condition = (String) rule.get("condition");
        Map<String, String> actionsMap = (Map<String, String>) rule.get("actions");
        String allowAction = actionsMap.get("allow");
        String denyAction = actionsMap.get("deny");

        // 定义 allowAccess 和 denyAccess 方法
        String qlExpress = "function accessGranted() { return \"Access granted\"; }" +
                "function accessDenied() { return \"Access denied\"; }" +
                "if (" + condition + ") { result = " + allowAction + "; } else { result = " + denyAction + "; }";

        // 执行 QLExpress 表达式
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("age", parameters.getOrDefault("age", 20)); // 设置年龄为20岁
        Object result = runner.execute(qlExpress, context, null, true, false);
        System.out.println("Result: " + result);
        return result;
    }
}
