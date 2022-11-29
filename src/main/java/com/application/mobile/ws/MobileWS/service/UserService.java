package com.application.mobile.ws.MobileWS.service;

import com.application.mobile.ws.MobileWS.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);

    UserDto getUser(String email);

    UserDto getUserByUserId(String userId);

    UserDto updateUser(String id, UserDto user);

    void deleteUser(String userId);


    List<UserDto> getUsers(int page, int limit);
}
