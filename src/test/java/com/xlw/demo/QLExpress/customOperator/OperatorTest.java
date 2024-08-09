package com.xlw.demo.QLExpress.customOperator;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.DynamicParamsUtil;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import org.junit.jupiter.api.Test;

public class OperatorTest {

    @Test
    public void addOperatorTest() throws Exception {
        //(1)addOperator
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.addOperator("join", new JoinOperator());
        Object r = runner.execute("1 join 2 join 3", context, null, false, false);
        System.out.println(r); // 返回结果 [1, 2, 3]
    }

    @Test
    public void replaceOperatorTest() throws Exception {
        //(2)replaceOperator
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.replaceOperator("+", new JoinOperator());
        Object r = runner.execute("1 + 2 + 3", context, null, false, false);
        System.out.println(r); // 返回结果 [1, 2, 3]
    }

    @Test
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
    @Test
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
    @Test
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

    /**
     * 编译脚本，查询外部需要定义的变量和函数
     * <pre>
     *   输出结果：
     *         var : 数学
     *         var : 综合考试
     *         var : 英语
     *         var : 语文
     * </pre>
     *
     * @throws Exception
     */
    @Test
    public void varTest() throws Exception {
        String express = "int 平均分 = (语文 + 数学 + 英语 + 综合考试.科目2) / 4.0; return 平均分";
        ExpressRunner runner = new ExpressRunner(true, true);
        String[] names = runner.getOutVarNames(express);
        for (String s : names) {
            System.out.println("var : " + s);
        }
    }

    /**
     * 关于不定参数的使用
     *
     * @throws Exception
     */
    @Test
    public void testMethodReplace() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        IExpressContext<String, Object> expressContext = new DefaultContext<String, Object>();
        runner.addFunctionOfServiceMethod("getTemplate", this, "getTemplate", new Class[]{Object[].class}, null);

        //(1)默认的不定参数可以使用数组来代替
        Object r = runner.execute("getTemplate([11,'22', 33L, true])", expressContext, null, false, false);
        System.out.println(r);
        //(2)像java一样,支持函数动态参数调用,需要打开以下全局开关,否则以下调用会失败
        DynamicParamsUtil.supportDynamicParams = true;
        r = runner.execute("getTemplate(11, '22', 33L, true)", expressContext, null, false, false);
        System.out.println(r);
    }

    //等价于getTemplate(Object[] params)
    public Object getTemplate(Object... params) throws Exception {
        String result = "";
        for (Object obj : params) {
            result = result + obj + ",";
        }
        return result;
    }

    /**
     * 关于集合的快捷写法
     *
     * @throws Exception
     */
    @Test
    public void testSet() throws Exception {
        ExpressRunner runner = new ExpressRunner(false, false);
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "abc = NewMap(1:1, 2:2); return abc.get(1) + abc.get(2);";
        Object r = runner.execute(express, context, null, false, false);
        System.out.println(r);
        express = "abc = NewList(1, 2, 3); return abc.get(1) + abc.get(2)";
        r = runner.execute(express, context, null, false, false);
        System.out.println(r);
        express = "abc = [1, 2, 3]; return abc[1] + abc[2];";
        r = runner.execute(express, context, null, false, false);
        System.out.println(r);
    }
}
