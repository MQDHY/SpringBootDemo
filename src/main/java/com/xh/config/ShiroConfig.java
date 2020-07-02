package com.xh.config;

import com.xh.shiro.UserRealm;
import com.xh.shiro.token.TokenManagerDao;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 配置过滤器
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/login");
        return shiroFilterFactoryBean;
    }

    /**
     * 配置安全管理器
     * @param userRealm
     * @param defaultSessionManager
     * @return
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm,DefaultSessionManager defaultSessionManager){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        defaultWebSecurityManager.setSessionManager(defaultSessionManager);
        return defaultWebSecurityManager;
    }

    /**
     * 配置session管理
     * @param tokenManagerDao
     * @return
     */
    @Bean
    public DefaultSessionManager sessionManager(TokenManagerDao tokenManagerDao){
        DefaultSessionManager defaultSessionManager = new DefaultSessionManager();
        defaultSessionManager.setSessionDAO(tokenManagerDao);
        return defaultSessionManager;
    }
}
