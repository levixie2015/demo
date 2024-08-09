package com.xlw.demo.QLExpress.customOperator;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;

public class OperatorTest {

    @org.junit.jupiter.api.Test
    public void addOperatorTest() throws Exception {
        //(1)addOperator
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.addOperator("join", new JoinOperator());
        Object r = runner.execute("1 join 2 join 3", context, null, false, false);
        System.out.println(r); // 返回结果 [1, 2, 3]
    }

    @org.junit.jupiter.api.Test
    public void replaceOperatorTest() throws Exception {
        //(2)replaceOperator
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.replaceOperator("+", new JoinOperator());
        Object r = runner.execute("1 + 2 + 3", context, null, false, false);
        System.out.println(r); // 返回结果 [1, 2, 3]
    }

    @org.junit.jupiter.api.Test
    public void addFunctionTest() throws Exception {
        //(3)addFunction
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.addFunction("join", new JoinOperator());
        Object r = runner.execute("join(1, 2, 3)", context, null, false, false);
        System.out.println(r); // 返回结果 [1, 2, 3]
    }

    /**
     * 绑定java类或者对象的method
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void addFunctionOfClass() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.addFunctionOfClassMethod("取绝对值", Math.class.getName(), "abs", new String[]{"double"}, null);
        runner.addFunctionOfClassMethod("转换为大写", BeanExample.class.getName(), "upper", new String[]{"String"}, null);

        runner.addFunctionOfServiceMethod("打印", System.out, "println", new String[]{"String"}, null);
        runner.addFunctionOfServiceMethod("contains", new BeanExample(), "anyContains", new Class[]{String.class, String.class}, null);

        String express = "取绝对值(-100); 转换为大写(\"hello world\"); 打印(\"你好吗？\"); contains(\"helloworld\",\"aeiou\")";
        Object result = runner.execute(express, context, null, false, false);
        System.out.println(result);
    }

    /**
     * macro 宏定义
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.Test
    public void macroTest() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.addMacro("计算平均成绩", "(语文+数学+英语)/3.0");
        runner.addMacro("是否优秀", "计算平均成绩>90");
        IExpressContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("语文", 88);
        context.put("数学", 99);
        context.put("英语", 95);
        Object result = runner.execute("是否优秀", context, null, false, false);
        System.out.println(result);//返回结果true
    }
}
