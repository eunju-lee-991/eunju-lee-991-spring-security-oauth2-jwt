package com.myprohect.springsecurityoauth2jwt.repository;

import com.myprohect.springsecurityoauth2jwt.model.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Integer> {
    Optional<User> findById(Integer id);
    User findByProviderAndProviderId(String provider, String providerId);

    List<User> findAll();

    User save(User user);

    void delete(User user);
}
