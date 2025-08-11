package com.example.demo.service;

import com.example.demo.dto.Response.UserResponse;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUser() {
        return userMapper.findAll();
    }

    public User getUserById(Long id) {
        return userMapper.findById(id);
    }

    public User getUserByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public boolean createUser(User user) {
        return userMapper.insert(user) > 0;
    }

    public boolean updateUser(User user) {
        return userMapper.update(user) > 0;
    }

    public boolean deleteUserById(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    public User searchUserByName(String username) {
        return userMapper.findByName(username);
    }

    public UserResponse searchUser(String username){
        User user = userMapper.findByName(username);
        if(user == null){
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
