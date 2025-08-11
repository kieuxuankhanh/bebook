package com.example.demo.controller;

import com.example.demo.dto.Response.UserResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        if (userService.updateUser(user))
        {
            return ResponseEntity.ok("Cập nhật thông tin thành công");
        }
        return ResponseEntity.badRequest().body("Failed to update user");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        if ((userService.deleteUserById(id))){
            return ResponseEntity.ok("Xoá người dùng thành công");
        }
        return ResponseEntity.badRequest().body("Xóa người dùng thất bại");
    }

    @GetMapping("/search")
    public ResponseEntity<User> getUserByName(@RequestParam String name){
        return ResponseEntity.ok(userService.searchUserByName(name));
    }

    @GetMapping("/public/search")
    public ResponseEntity<UserResponse> getUserByNamePublic(@RequestParam String name){
        UserResponse userResponse = userService.searchUser(name);
        if(userResponse != null){
            return ResponseEntity.ok(userResponse);
        }
        return ResponseEntity.notFound().build();
    }
}
