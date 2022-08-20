package com.quizify.examserver.controller;

import com.quizify.examserver.dto.ResponseDTO;
import com.quizify.examserver.model.user.Role;
import com.quizify.examserver.model.user.User;
import com.quizify.examserver.model.user.UserRole;
import com.quizify.examserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Create User Method
     */
    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Set<UserRole> userRoles = new HashSet<>();

        Role role = new Role();
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setRole(role);

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        userRole.setUser(user);

        userRoles.add(userRole);
        User user1 = this.userService.createUser(user, userRoles);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    /**
     * Get User By Username
     *
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = this.userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserById(@PathVariable Long userId, @RequestBody User user) {
        User user1 = this.userService.updateUserById(userId, user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDTO> deleteUserById(@PathVariable Long userId) {
        this.userService.deleteUserById(userId);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(true);
        responseDTO.setMessage("User deleted successfully!!");
        return new ResponseEntity<> (responseDTO, HttpStatus.OK);
    }

}
