package com.quizify.examserver.service.impl;

import com.quizify.examserver.exception.CustomException;
import com.quizify.examserver.repository.RoleRepository;
import com.quizify.examserver.repository.UserRepository;
import com.quizify.examserver.exception.ResourceNotFoundException;
import com.quizify.examserver.model.user.User;
import com.quizify.examserver.model.user.UserRole;
import com.quizify.examserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Create new User
     */
    @Override
    public User createUser(User user, Set<UserRole> userRoles) {
        User checkUser = userRepository.findByUsername(user.getUsername());
        if(checkUser!=null) {
            throw new CustomException("User already exists");
        }

        userRoles.forEach(role -> {
            roleRepository.save(role.getRole());
        });

        user.getUserRoles().addAll(userRoles);
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User updateUserById(Long userId, User user) {
        User updateUser = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User does not exist!"));
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(user.getPassword());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setProfile(user.getProfile());
        this.userRepository.save(updateUser);
        return updateUser;
    }

    @Override
    public void deleteUserById(Long userId) {
        this.userRepository.deleteById(userId);
    }
}
