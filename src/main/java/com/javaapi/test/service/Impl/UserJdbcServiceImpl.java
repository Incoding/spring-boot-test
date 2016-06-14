package com.javaapi.test.service.Impl;

import com.javaapi.test.dao.jpa.model.User;
import com.javaapi.test.service.UserJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by user on 16/6/5.
 */
@Service
@Transactional
public class UserJdbcServiceImpl implements UserJdbcService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User find(Integer id) {
        return null;
    }

    @Override
    public int updateUser(String id) {
        int update = jdbcTemplate.update("UPDATE i_user set username='kk' where id=" + id);
        if(true){
            throw new RuntimeException("测试回滚");
        }
        return update;
    }


}
