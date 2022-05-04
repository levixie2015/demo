package com.xlw.demo.service;

import cn.hutool.core.util.IdUtil;
import com.xlw.demo.entity.Order;
import com.xlw.demo.entity.OrderH2;
import com.xlw.demo.entity.Product;
import com.xlw.demo.persist.customized.mapper.OrderH2Mapper;
import com.xlw.demo.persist.customized.mapper.OrderMapper;
import com.xlw.demo.persist.customized.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderService {
    @Resource
    ProductMapper productMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderH2Mapper orderH2Mapper;
    @Value("${spring.profiles.active}") //读取当前环境配置名称
    private String profileName;

    @Transactional
    public String reduceStock(Integer id) {
        log.debug("profileName:{}", profileName);
        try {
            //1.获取库存
            Product product = productMapper.getProduct(id);

            if (product.getStock() > 0) {
                //2.减库存
                int i = productMapper.updateStock(id, product.getStock() - 1);
                if (i == 1) {
                    //h2数据库操作
                    if (this.profileName.equals("dev")) {
                        OrderH2 order = new OrderH2();
                        order.setUserId(IdUtil.fastSimpleUUID());
                        order.setPid(id);
                        order.setId(IdUtil.fastSimpleUUID());
                        orderH2Mapper.insert(order);
                    } else {
                        //mysql数据库操作
                        Order order = new Order();
                        order.setUserId(IdUtil.fastSimpleUUID());
                        order.setPid(id);
                        orderMapper.insert(order);
                    }

                    //模拟耗时业务处理
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                log.info("成功买入商品，库存还剩下:{}", product.getStock() - 1);
            } else {
                log.info("扣减失败，库存不足");
                return "扣减失败，库存不足";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
