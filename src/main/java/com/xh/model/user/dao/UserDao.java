package com.xh.model.user.dao;

import com.xh.model.user.pojo.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface UserDao {

    @Select("select uname as username,upwd as password from userinfo where uname = #{username}")
    public UserModel findByUserName(@Param("username")String username);
}
