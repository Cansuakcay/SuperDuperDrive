package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.models.UserForm;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private UserService userService;

    public AuthorizationService(UserService userService ) {
        this.userService = userService;
    }

    public boolean signupUser(UserForm userForm) {

        String username = userForm.getUsername();

        if(!this.userService.isUsernameAvailable(username)) {
            return false;
        }
        this.userService.createUser(userForm);
        return true;
    }

}
