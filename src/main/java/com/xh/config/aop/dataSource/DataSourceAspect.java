package com.xh.config.aop.dataSource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class DataSourceAspect {

    private final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution(public * com.xh.model.admin.service..*.*(..))")//切入点
    public void dataSourceAdmin() {}

    @Pointcut("execution(public * com.xh.model.user.service..*.*(..))")//切入点
    public void dataSourceUser() {}

    @Before("dataSourceAdmin()")
    public void dataSourceAdminBefore(){
        logger.info("数据源切换为dataSource_admin");
        DynamicDataSourceHolder.setDatasource("dataSource_admin");
    }
    @Before("dataSourceUser()")
    public void dataSourceUserBefore(){
        logger.info("数据源切换为dataSource_user");
        DynamicDataSourceHolder.setDatasource("dataSource_user");
    }
}
