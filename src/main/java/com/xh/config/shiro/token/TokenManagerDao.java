package com.xh.config.shiro.token;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class TokenManagerDao extends CachingSessionDAO {

    private RedisTemplate<String,Object> redisTemplate;

    public TokenManagerDao(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doUpdate(Session session) {

    }

    @Override
    protected void doDelete(Session session) {

    }

    @Override
    protected Serializable doCreate(Session session) {
        redisTemplate.opsForValue().set("nhao","123456");
        return null;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return null;
    }

}
