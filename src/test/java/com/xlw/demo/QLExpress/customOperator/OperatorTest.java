package com.xlw.demo.QLExpress.customOperator;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

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
}
