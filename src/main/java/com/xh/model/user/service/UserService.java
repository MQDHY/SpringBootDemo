package com.xh.model.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.model.user.dao.UserDao;
import com.xh.model.user.pojo.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
public class UserService extends ServiceImpl<UserDao,UserModel> {

    @Resource
    public UserDao userDao;

    public UserModel findByUserName(String username){
        return userDao.findByUserName(username);
    }
}
