package com.AB3.API.AB3.API.Service;

import com.AB3.API.AB3.API.Model.UserInfo;
import com.AB3.API.AB3.API.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository UserRepository;

    public boolean existsByPhoneNumber(String PhoneNumber) {
        return UserRepository.existsByphoneNumber(PhoneNumber);
    }

    public boolean existsByeMail(String eMail) {
        return UserRepository.existsByeMail(eMail);
    }

    @Transactional
    public UserInfo save(UserInfo UserInfo) {
        return UserRepository.save(UserInfo);
    }

    public List<UserInfo> findAll() {
        return UserRepository.findAll();
    }

    public Optional<UserInfo> FindById(UUID id) {
        return UserRepository.findById(id);
    }

    public List<UserInfo> findByname(String firstName, String lastName) {
        return UserRepository.findByname(firstName, lastName);
    }

    public List<UserInfo> findByPhoneNumber(String phoneNumber) {
        return UserRepository.findByphoneNumber(phoneNumber);
    }

    public void deleteByPhoneNumberAndPassWord(String phoneNumber, String passWord) {
        UserRepository.deleteByphoneNumberAndPassword(phoneNumber, passWord);
    }

    public void delete(UserInfo userInfo) {
        UserRepository.delete(userInfo);
    }

}
