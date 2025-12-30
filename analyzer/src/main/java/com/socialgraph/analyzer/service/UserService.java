package com.socialgraph.analyzer.service;

import com.socialgraph.analyzer.entity.User;
import com.socialgraph.analyzer.exception.UserNotFoundException;
import com.socialgraph.analyzer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    // Injecting the dependency of the userRepository;
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(User user)
    {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    public void removeUserById(Long id)
    {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUserById(Long id, User user)
    {
        User userFound = userRepository.findById(id).
            orElseThrow(() -> new UserNotFoundException("User for id: " + id + " Not found",
            HttpStatus.BAD_GATEWAY));

        userFound.setUpdatedAt(LocalDateTime.now());
        userFound.setUserName(user.getUserName());
        userFound.setEmail(user.getEmail());
        userFound.setProfilePictureUrl(user.getProfilePictureUrl());
        userFound.setBio(user.getBio());
        userFound.setPassword(user.getPassword());
        userFound.setIsActive(user.getIsActive());
        userRepository.save(userFound);
    }

    public User getUserById(Long id)
    {
        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User for id: " + id + " Not found",
                HttpStatus.BAD_GATEWAY));
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
}
