package com.AB3.API.AB3.API.Controller;

import com.AB3.API.AB3.API.DTO.UserDTO;
import com.AB3.API.AB3.API.Model.UserInfo;
import com.AB3.API.AB3.API.Repository.UserRepository;
import com.AB3.API.AB3.API.Service.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/basic-api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService UserService;

    @PostMapping
    public ResponseEntity<Object> saveUser (@RequestBody @Valid UserDTO userDTO) {
        if (UserService.existsByeMail(userDTO.getEMail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The Email " + userDTO.getEMail() + " Already exists.");
        }
        if (UserService.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("the phone number "+ userDTO.getPhoneNumber() + " Already exists.");
        }
        UserInfo UserInfo = new UserInfo();
        BeanUtils.copyProperties(userDTO, UserInfo);
        UserInfo.setDateTimeOfRequest(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(UserService.save(UserInfo));
    }

    @GetMapping
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        return ResponseEntity.status((HttpStatus.OK)).body(UserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {
        Optional<UserInfo> userInfoOptional = UserService.FindById(id);
        if(userInfoOptional.isPresent()) {
            return  ResponseEntity.status(HttpStatus.OK).body(userInfoOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found: These ID don`t exists at Database.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserInfo(@PathVariable(value = "id") UUID id) {
        Optional<UserInfo> userInfoOptional = UserService.FindById(id);
        if(userInfoOptional.isPresent()) {
            UserService.delete(userInfoOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User Information is deleted with success.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This User don't exists to be deleted.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putUserInfo(@PathVariable(value = "id") UUID id,
                                              @RequestBody @Valid UserDTO userDTO) {
        Optional<UserInfo> userInfoOptional = UserService.FindById(id);

        if (UserService.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The Phone Number " + userDTO.getPhoneNumber() + " Already exists.");
        }
        if (UserService.existsByeMail(userDTO.getEMail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The Email " + userDTO.getEMail() + " Already exists.");
        }
        if(userInfoOptional.isPresent()) {
            UserInfo UserInfo = new UserInfo();
            BeanUtils.copyProperties(userInfoOptional, UserInfo);
            UserInfo.setEMail(userDTO.getEMail());
            UserInfo.setFirstName(userDTO.getFirstName());
            UserInfo.setPassWord(userDTO.getPassWord());
            UserInfo.setLastName(userDTO.getLastName());
            UserInfo.setPhoneNumber(userDTO.getPhoneNumber());

            return ResponseEntity.status(HttpStatus.OK).body("the User " + userDTO.getFirstName() + " Is updated with success.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

}
