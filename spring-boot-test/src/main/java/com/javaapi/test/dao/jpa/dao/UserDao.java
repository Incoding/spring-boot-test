package com.javaapi.test.dao.jpa.dao;


import com.javaapi.test.dao.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, String> {
    public User findByUsername(String username);

    @Query("select u from User u where u.username=:username")
    public User findUserByQueryAnnotation(@Param("username") String username);
}