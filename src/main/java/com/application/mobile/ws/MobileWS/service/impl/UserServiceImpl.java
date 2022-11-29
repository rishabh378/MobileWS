package com.application.mobile.ws.MobileWS.service.impl;

import com.application.mobile.ws.MobileWS.exceptions.UserServiceException;
import com.application.mobile.ws.MobileWS.io.entity.UserEntity;
import com.application.mobile.ws.MobileWS.repository.UserRepository;
import com.application.mobile.ws.MobileWS.service.UserService;
import com.application.mobile.ws.MobileWS.shared.dto.AddressDTO;
import com.application.mobile.ws.MobileWS.shared.dto.UserDto;
import com.application.mobile.ws.MobileWS.shared.util.Utils;
import com.application.mobile.ws.MobileWS.ui.model.response.ErrorMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {


        if(userRepository.findByEmail(user.getEmail()) != null ) throw new RuntimeException("Records already exists");

        //UserEntity userEntity = new UserEntity();
        //BeanUtils.copyProperties(user, userEntity);

        for (int index = 0; index<user.getAddresses().size(); index++)
        {
            AddressDTO address = user.getAddresses().get(index);
            address.setUserDetails(user);
            address.setAddressId(utils.generateAddressId(30));
            user.getAddresses().set(index, address);
        }
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity);

      //  UserDto returnValue = new UserDto();
       // BeanUtils.copyProperties(storedUserDetails,returnValue);
        UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);


        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {

        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UsernameNotFoundException("User with user id: "+userId+" Not Found!!");

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {

        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UserServiceException(ErrorMessage.NO_RECORD_FOUND.getErrorMessage());

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UserServiceException(ErrorMessage.NO_RECORD_FOUND.getErrorMessage());

         userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        if(page > 0) page = page-1;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
        List<UserEntity> userEntities = userPage.getContent();

        for (UserEntity userEntity : userEntities){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);


        return new User(userEntity.getEmail(), userEntity.getEncryptPassword(), new ArrayList<>());
    }
}
