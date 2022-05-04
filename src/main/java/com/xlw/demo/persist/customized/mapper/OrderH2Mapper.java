package com.xlw.demo.persist.customized.mapper;

import com.xlw.demo.entity.OrderH2;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderH2Mapper {
    @Insert("insert into `order`(id,user_id,pid)values(#{id},#{userId},#{pid})")
    int insert(OrderH2 order);
}
