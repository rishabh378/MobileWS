package com.application.mobile.ws.MobileWS.repository;

import com.application.mobile.ws.MobileWS.io.entity.AddressEntity;
import com.application.mobile.ws.MobileWS.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    static boolean recordsCreated = false;

    @BeforeEach
    void setUp() throws Exception
    {
       if(!recordsCreated) createRecords();

    }

    @Test
    final void testGetVerifiedUsers(){
        Pageable pageableRequest = PageRequest.of(1, 1);
        Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
        assertNotNull(page);

        List<UserEntity> userEntities = page.getContent();
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 1);
    }


    @Test
    final void testFindUserByFirstName()
    {
        String firstName = "Rishabh";
        List<UserEntity> users = userRepository.findUserByFirstName(firstName);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getFirstName().equals(firstName));
    }

    @Test
    final void testFindUserByLastName()
    {
        String lastName = "Pandey";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().equals(lastName));
    }

    @Test
    final void testFindUsersByKeyword()
    {
        String keyword = "Pan";
        List<UserEntity> users = userRepository.findUsersByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().contains(keyword) ||
                    user.getFirstName().contains(keyword));
    }

    @Test
    final void testfindUserFirstNameAndLastNameByKeyword()
    {
        String keyword = "Pan";
        List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        Object[] user = users.get(0);

        assertTrue(user.length == 2);
        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("First name = "+userFirstName);
        System.out.println("Last name = "+userLastName);
    }

    @Test
    final void testUpdateUserEmailVerificationStatus()
    {
        boolean newEmailVerificationStatus = true;
        userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, "1a3d4c");

        UserEntity storedUserDetails = userRepository.findByUserId("1a3d4c");

        boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();

        assertTrue(storedEmailVerificationStatus == newEmailVerificationStatus);

    }
    @Test
    final void testFindUserEntityByUserId()
    {
        String userId = "1a3d4c";
        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);

        assertNotNull(userEntity);
        assertTrue(userEntity.getUserId().equals(userId));
    }

    @Test
    final void testGetUserEntityFullNameById()
    {
        String userId = "1a3d4c";
        List<Object[]> records = userRepository.getUserEntityFullNameById(userId);

        assertNotNull(records);
        assertTrue(records.size() == 1);

        Object[] userDetails = records.get(0);

        String firstName = String.valueOf(userDetails[0]);
        String lastName = String.valueOf(userDetails[1]);

        assertNotNull(firstName);
        assertNotNull(lastName);
    }
    @Test
    final void testUpdateUserEntityEmailVerificationStatus()
    {
        boolean newEmailVerificationStatus = false;
        userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus, "1a3d4c");

        UserEntity storedUserDetails = userRepository.findByUserId("1a3d4c");

        boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();

        assertTrue(storedEmailVerificationStatus == newEmailVerificationStatus);

    }

    private void createRecords()
    {
        // Prepare User ENtity
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Rishabh");
        userEntity.setLastName("Pandey");
        userEntity.setUserId("1a3d4c");
        userEntity.setEncryptPassword("xxx");
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationStatus(true);

        // Prepare User Addresses
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setType("Shipping");
        addressEntity.setAddressId("sdfjda323");
        addressEntity.setCity("Roorkee");
        addressEntity.setCountry("India");
        addressEntity.setPostalCode("JFDSL");
        addressEntity.setStreetName("Geetanjli Vihar");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);
        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);

        // Second Data record
        // Prepare User ENtity
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Rishabh");
        userEntity2.setLastName("Pandey");
        userEntity2.setUserId("1a3d4csdf");
        userEntity2.setEncryptPassword("xxx");
        userEntity2.setEmail("test54@test.com");
        userEntity2.setEmailVerificationStatus(true);

        // Prepare User Addresses
        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setType("Shipping");
        addressEntity2.setAddressId("sdfjda323");
        addressEntity2.setCity("Roorkee");
        addressEntity2.setCountry("India");
        addressEntity2.setPostalCode("JFDSL");
        addressEntity2.setStreetName("Geetanjli Vihar");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses2.add(addressEntity2);
        userEntity2.setAddresses(addresses2);

        userRepository.save(userEntity2);

        recordsCreated = true;
    }
}