package dev.sandeep.Splitwise.controller;

import dev.sandeep.Splitwise.dto.UserLoginRequestDTO;
import dev.sandeep.Splitwise.dto.UserRegistrationRequestDTO;
import dev.sandeep.Splitwise.entity.User;
import dev.sandeep.Splitwise.exception.UserLoginInvalidDataException;
import dev.sandeep.Splitwise.exception.UserRegistrationInvalidDataException;
import dev.sandeep.Splitwise.mapper.EntityDTOMapper;
import dev.sandeep.Splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO){
      validateUserRegistrationRequestDTO(userRegistrationRequestDTO);
      User savedUser  = userService.signUp(userRegistrationRequestDTO.getName(),
              userRegistrationRequestDTO.getEmail(), userRegistrationRequestDTO.getPassword());
      return ResponseEntity.ok(EntityDTOMapper.toDTO(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequestDTO userLoginRequestDTO){
        validateUserLoginRequestDTO(userLoginRequestDTO);
        User savedUser = userService.
                login(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
        return ResponseEntity.ok(EntityDTOMapper.toDTO(savedUser));

    }

    private void validateUserRegistrationRequestDTO(UserRegistrationRequestDTO userRegistrationRequestDTO){
        //todo : validate if the emial is proper
        //todo : validate if password has 8 characters including a small,capital, numeric and special characters
        if(userRegistrationRequestDTO.getEmail() == null ||
        userRegistrationRequestDTO.getName() == null ||
        userRegistrationRequestDTO.getPassword() == null){
            throw  new UserRegistrationInvalidDataException
                    ("Invalid sign up data . Please fill fields it cannot be null");
        }
    }

    private void validateUserLoginRequestDTO(UserLoginRequestDTO userLoginRequestDTO){
        if(userLoginRequestDTO.getEmail() == null||
        userLoginRequestDTO.getPassword() == null){
            throw new UserLoginInvalidDataException("Invalid login data. Please enter the correct credentials.");
        }
    }
}
