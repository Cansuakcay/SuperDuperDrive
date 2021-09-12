package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.UserCredential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private Logger logger = LoggerFactory.getLogger(CredentialController.class);
    private CredentialService credentialService;
    public CredentialController(CredentialService credentialService) {this.credentialService = credentialService;}

    @PostMapping("/credential")
    public String credentialSubmit(
            @ModelAttribute("userCredential")UserCredential userCredential,
            @RequestParam(required = false, name="url") String url,
            @RequestParam(required = false, name="username") String userName,
            @RequestParam(required = false, name="password") String password,
            Authentication authentication,
            Model model) {

        String username = (String) authentication.getPrincipal();
        if (url.isEmpty()) {
            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
        } else if (userName.isEmpty()) {
            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
        } else if (password.isEmpty()) {
            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
        } else {
            Boolean isSuccess = this.credentialService.insertOrUpdateCredential(userCredential, username);
            return "redirect:/result?isSuccess=" + isSuccess;
        }
    }

    @GetMapping("/credential")
    public String credentialDeletion(
            @ModelAttribute("userCredential") UserCredential userCredential,
            @RequestParam(required = false, name ="credentialId") Integer credentialId,
            Authentication authentication,
            Model model
    ) {
        this.logger.error("CredentialId: " + credentialId.toString());
        Boolean isSuccess = this.credentialService.deleteCredential(credentialId);
        return "redirect:/result?isSuccess=" + isSuccess;
    }

}
