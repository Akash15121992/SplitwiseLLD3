package dev.sandeep.Splitwise.service;

import dev.sandeep.Splitwise.entity.User;

public interface UserService {
    User signUp(String name, String email, String password);
    User login(String email , String password);
}
