package com.xh.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.xh.config.aop.dataSource.DynamicDataSourceHolder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource(value = {"classpath:dbConfig.properties"})
public class DataSourceConfig {
    @Value("${dbConfig.driverClassName}")
    private String driverClassName;
    //用户库
    @Value("${dbConfig.user.url}")
    private String user_url;
    @Value("${dbConfig.user.userName}")
    private String user_userName;
    @Value("${dbConfig.user.password}")
    private String user_password;
    //管理员库
    @Value("${dbConfig.admin.url}")
    private String admin_url;
    @Value("${dbConfig.admin.userName}")
    private String admin_userName;
    @Value("${dbConfig.admin.password}")
    private String admin_password;

    @Bean("dataSource_admin")
    public DataSource dataSource_admin() {
        return getDataSource(admin_url, admin_userName, admin_password);
    }

    @Bean("dataSource_user")
    public DataSource dataSource_user() {
        return getDataSource(user_url, user_userName, user_password);
    }

    private DataSource getDataSource(String admin_url, String admin_userName, String admin_password) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(admin_url);
        druidDataSource.setUsername(admin_userName);
        druidDataSource.setPassword(admin_password);
        return druidDataSource;
    }
    //这里偷懒使用了内部类
    class DynamicDataSource extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            //这里是实现切换路由的关键。每次使用到SQLSession时都会执行这个方法来确认数据源是哪个
            //数据源是哪个跟DynamicDataSourceHolder.getDatasource()的返回值有关系，这个返回值就是下面路由里面的key
            return DynamicDataSourceHolder.getDatasource();
        }
    }
    //配置数据源路由
    @Bean
    public DynamicDataSource dynamicDataSource(DataSource dataSource_user,DataSource dataSource_admin){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //配置多个数据源 key是用来动态切换的
        Map<Object, Object> map = new HashMap<>();
        map.put("dataSource_user",dataSource_user);
        map.put("dataSource_admin",dataSource_admin);
        dynamicDataSource.setTargetDataSources(map);
        //指定默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource_user);
        return dynamicDataSource;
    }

    //将数据源路由配置到SqlSession工厂里面 数据源路由要指定默认的数据源是哪个
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DynamicDataSource dynamicDataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        return sqlSessionFactoryBean.getObject();
    }
}
