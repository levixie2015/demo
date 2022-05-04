package com.xlw.demo.persist.customized.mapper;

import com.xlw.demo.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface OrderMapper {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("insert into `order`(user_id,pid)values(#{userId},#{pid})")
    int insert(Order order);
}
