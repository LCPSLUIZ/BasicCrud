package com.AB3.API.AB3.API.Controller;

import com.AB3.API.AB3.API.DTO.UserDTO;
import com.AB3.API.AB3.API.Model.UserInfo;
import com.AB3.API.AB3.API.Service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/")
@CrossOrigin(origins = "*")
@Log4j2
public class UserController {

    @Autowired
    UserService UserService;

    @PostMapping
    public ResponseEntity<Object> saveUser (@RequestBody @Valid UserDTO userDTO) {
        if (UserService.existsByeMail(userDTO.getEMail())) {
            log.info("[POST][CONFLICT] The email {} is already in use", userDTO.getEMail() );
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The Email " + userDTO.getEMail() + " Already exists.");
        }
        if (UserService.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            log.info("[POST][CONFLICT] The Phone Number {} is already in use", userDTO.getPhoneNumber() );
            return ResponseEntity.status(HttpStatus.CONFLICT).body("the phone number "+ userDTO.getPhoneNumber() + " Already exists.");
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userDTO, userInfo);
        userInfo.setDateTimeOfRequest(LocalDateTime.now(ZoneId.of("UTC")));
        log.info("[POST][CREATED] New user was created, with id: {}", userInfo.getId() );
        return ResponseEntity.status(HttpStatus.CREATED).body(UserService.save(userInfo));
    }

    @GetMapping
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        return ResponseEntity.status((HttpStatus.OK)).body(UserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {
        Optional<UserInfo> userInfoOptional = UserService.FindById(id);
        if(userInfoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userInfoOptional.get());
        }
        log.info("[GET][NOT_FOUND] Someone have tried to get some info from database, but the id doesn't exist:  {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found: This ID don`t exists at Database.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserInfo(@PathVariable(value = "id") UUID id) {
        Optional<UserInfo> userInfoOptional = UserService.FindById(id);
        if(userInfoOptional.isPresent()) {
            UserService.delete(userInfoOptional.get());
            log.info("[DELETE][OK] the user with the id {} was deleted", id);
            return ResponseEntity.status(HttpStatus.OK).body("User Information is deleted with success.");
        }
        log.info("[DELETE][NOT_FOUND] someone have tried to delete some user but the id {} doesn't exists", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This User don't exists to be deleted.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putUserInfo(@PathVariable(value = "id") UUID id,
                                              @RequestBody @Valid UserDTO userDTO) {
        Optional<UserInfo> userInfoOptional = UserService.FindById(id);

        if (UserService.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            log.info("[PUT][CONFLICT] The Phone Number {} is already in use", userDTO.getPhoneNumber());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The Phone Number " + userDTO.getPhoneNumber() + " Already exists.");
        }
        if (UserService.existsByeMail(userDTO.getEMail())) {
            log.info("[PUT][CONFLICT] The email {} is already in use", userDTO.getEMail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The Email " + userDTO.getEMail() + " Already exists.");
        }
        if(userInfoOptional.isPresent()) {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userInfoOptional, userInfo);
            UserService.update(userInfo, userDTO);

            log.info("[PUT][OK] The User {} was updated", userInfo.getId());
            return ResponseEntity.status(HttpStatus.OK).body("the User " + userInfo.getId() + " Was updated with success.");
        }

            log.info("[PUT][NOT_FOUND] User id {} not found", userInfoOptional);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

}
