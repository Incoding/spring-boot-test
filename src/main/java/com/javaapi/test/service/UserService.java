package com.javaapi.test.service;

import com.javaapi.test.dao.jpa.model.User;

/**
 * Created by user on 16/6/5.
 */
public interface  UserService {

    public User find(Integer id);

    int updateUser(String id);
}
