package com.xh.model.admin.service;

import com.xh.model.admin.dao.AdminDao;
import com.xh.model.admin.pojo.AdminModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("AdminService")
public class AdminService {

    @Resource
    private AdminDao adminDao;

    public AdminModel findByAdminName(String adminName){
        return adminDao.findByUserName(adminName);
    }
}
