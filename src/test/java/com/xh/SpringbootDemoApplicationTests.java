package com.xh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.model.admin.pojo.AdminModel;
import com.xh.model.admin.service.AdminService;
import com.xh.model.user.dao.UserDao;
import com.xh.model.user.pojo.UserModel;
import com.xh.model.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
@MapperScan({"com.xh.model.user.dao","com.xh.model.admin.dao"})
class SpringbootDemoApplicationTests {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Resource
    UserService userService;

    @Resource
    AdminService adminService;

    @Test
    void contextLoads() {
//        redisTemplate.opsForValue().set("name","123");
        UserModel user = userService.findByUserName("admin");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uname","admin");
        UserModel one = userService.getOne(queryWrapper);
        System.out.println(user);
        System.out.println(one);
        AdminModel admin = adminService.findByAdminName("root");
        System.out.println(admin);
    }

    @Test
    void test(){
        new Thread(()->{
            for (int i =0;i<100;i++){
                contextHolder.set("线程1加的"+i);
                System.out.println(contextHolder.get());
            }

        }).start();
        new Thread(()->{
            for (int i =0;i<100;i++){
                contextHolder.set("线程2加的"+i+1);
                System.out.println(contextHolder.get());
            }
        }).start();
    }

    public static void main(String[] args) {
        int j = 0;
        new Thread(()->{
            for (int i =0;i<100;i++){
//                contextHolder.set("线程1加的"+i);
                System.out.println("线程1加的"+i);

                System.out.println("现在j的值为:"+j);
//                System.out.println("---1"+contextHolder.get());
            }

        }).start();
        new Thread(()->{
            for (int i =0;i<100;i++){
//                contextHolder.set("线程2加的"+i+1);
//                System.out.println("线程2加的"+i+1);
//                System.out.println("---2"+contextHolder.get());
            }
        }).start();
    }
}
