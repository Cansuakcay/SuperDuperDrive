package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserCredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.UserCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    private Logger logger = LoggerFactory.getLogger(CredentialService.class);
    private EncryptionService encryptionService;
    private CredentialMapper credentialMapper;
    private UserCredentialMapper userCredentialMapper;

    public CredentialService(
            EncryptionService encryptionService,
            CredentialMapper credentialMapper,
            UserCredentialMapper userCredentialMapper
    ) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
        this.userCredentialMapper = userCredentialMapper;
    }

    public List<UserCredential> getCredentialsByUsername(String username){

        List<UserCredential> userCredentialList =
                this.userCredentialMapper.getCredentialsByUsername(username);

        return userCredentialList.stream().map(userCredential -> {

            String encodedKey = userCredential.getKey();
            String encryptedPassword = userCredential.getPassword();
            String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
            userCredential.setDecryptedPassword(decryptedPassword);
            return userCredential;
        }).collect(Collectors.toList());
    }

    public Boolean insertOrUpdateCredential(UserCredential userCredential, String username) {
        String password = userCredential.getPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        Integer credentialId = userCredential.getCredentialId();

        if (credentialId == null) {
            this.userCredentialMapper.insertCredentialByUsername(
                    userCredential.getUrl(),
                    userCredential.getUsername(),
                    encodedKey,
                    encryptedPassword,
                    username);
        } else {
            this.credentialMapper.update(
                    userCredential.getUrl(),
                    userCredential.getUsername(),
                    encodedKey,
                    encryptedPassword,
                    credentialId
                    );
        }

        return true;
    }

    public Boolean deleteCredential(Integer credentialId) {
        this.credentialMapper.delete(credentialId);
        return true;
    }

}
