//package com.xlw.demo.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.xlw.demo.entity.redissionTest.OrderSaveBuyerReq;
//import com.xlw.demo.service.OrderService;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * 测试分布式锁
// * <pre>
// *     模拟分布式环境下,多两个JVM进程/服务(端口号分别为8080、8081)。
// *     使用Nginx反向代理.
// *
// *
// * </pre>
// */
//@RestController
//@Slf4j
//public class RedissionTestController {
//    @Autowired
//    private OrderService orderService;
//    @Resource
//    private RedissonClient redissonClient;
//
//    @PostMapping("/stock/deduct")
//    public String reduceStock(@RequestBody OrderSaveBuyerReq req) throws Exception {
//        log.debug("req:{}", JSON.toJSONString(req));
//        //获取分布式锁，只要锁的名字一样，就是同一把锁
//        RLock lock = redissonClient.getLock("lock");
//        try {
//            //加锁（阻塞等待），默认过期时间是30秒
//            lock.lock();
//            String s = orderService.reduceStock(1);
//            return s;
//        } catch (Exception e) {
//            if (e instanceof RuntimeException) {
//                throw e;
//            }
//        }finally {
//            //解锁，如果业务执行完成，就不会继续续期，即使没有手动释放锁，在30秒过后，也会释放锁
//            lock.unlock();
//        }
//        return "fail";
//    }
//}
