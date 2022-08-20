package com.quizify.examserver.service;

import com.quizify.examserver.model.user.User;
import com.quizify.examserver.model.user.UserRole;

import java.util.Set;

public interface UserService {
    User createUser(User user, Set<UserRole> userRoles);

    User getUserByUsername(String username);

    User updateUserById(Long userId, User user);

    void deleteUserById(Long userId);
}
