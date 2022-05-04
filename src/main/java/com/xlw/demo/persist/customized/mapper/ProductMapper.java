package com.xlw.demo.persist.customized.mapper;

import com.xlw.demo.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper {
    @Select("select * from product where id = #{id} ")
    Product getProduct(@Param("id") Integer id);

    @Update("update product set stock = stock - 1 where id = #{id}")
    int deductStock(@Param("id") Integer id);

    @Update("update product set stock = #{stock} where id = #{id}")
    int updateStock(@Param("id") Integer id, @Param("stock") Integer stock);
}
