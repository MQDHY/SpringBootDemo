package com.xh.model.admin.dao;

import com.xh.model.admin.pojo.AdminModel;
import com.xh.model.user.pojo.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminDao {

    @Select("select user_name,password from sys_user where user_name = #{username}")
    public AdminModel findByUserName(@Param("username")String username);
}
