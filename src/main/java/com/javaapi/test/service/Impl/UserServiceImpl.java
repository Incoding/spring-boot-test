package com.javaapi.test.service.Impl;

import com.javaapi.test.dao.jpa.dao.UserDao;
import com.javaapi.test.dao.jpa.model.User;
import com.javaapi.test.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by user on 16/6/5.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;
    @Override
    public User find(Integer id) {
        return userDao.findOne(id.toString());
    }

    @Override
    public int updateUser(String id) {
        User one = userDao.findOne(id);
        one.setUsername("kk");
        User save = userDao.saveAndFlush(one);
//        if(true){
//            throw new RuntimeException("测试回滚");
//        }
        return 1;
    }


}
