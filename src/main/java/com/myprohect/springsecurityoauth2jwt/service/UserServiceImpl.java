package com.myprohect.springsecurityoauth2jwt.service;

import com.myprohect.springsecurityoauth2jwt.model.User;
import com.myprohect.springsecurityoauth2jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User join(User user) {
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    @Override
    public User findUser(int id) {

        return null;
    }

    @Override
    public User findUser(String provider, String providerId) {
        User user = userRepository.findByProviderAndProviderId(provider, providerId);

        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(int id) {

    }
}
