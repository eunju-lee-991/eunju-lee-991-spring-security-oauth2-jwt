package com.myprohect.springsecurityoauth2jwt.service;

import com.myprohect.springsecurityoauth2jwt.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    // 가입
    int join(User user);

    // 조회
    User findUser(int id);

    // 전체 조회
    List<User> findAllUsers();

    // 수정
    User updateUser(User user);

    // 삭제
    void deleteUser(int id);
}
