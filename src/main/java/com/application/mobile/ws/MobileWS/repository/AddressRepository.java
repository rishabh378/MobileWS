package com.application.mobile.ws.MobileWS.repository;

import com.application.mobile.ws.MobileWS.io.entity.AddressEntity;
import com.application.mobile.ws.MobileWS.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);

    AddressEntity findByAddressId(String addressId);
}
