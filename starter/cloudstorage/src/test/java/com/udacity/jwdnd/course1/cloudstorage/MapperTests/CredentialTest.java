package com.udacity.jwdnd.course1.cloudstorage.MapperTests;


import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
public class CredentialTest {

    private Logger logger = LoggerFactory.getLogger(CredentialTest.class);

    @Autowired
    private CredentialMapper credentialMapper;

    @Test
    public void insertCredential() {
        Credential newCredential = new Credential(null, "www.casoftwaretesting.co.uk",
                "casoftwaretesting", "sadasda324324", "ca1234");

        this.credentialMapper.insert(newCredential);

        Integer credentialId = newCredential.getCredentialid();
        Assertions.assertNotNull(credentialId);

        Credential credential = this.credentialMapper.getCredentialByCredentialId(credentialId);

        Assertions.assertNotNull(credential);
        Assertions.assertEquals(newCredential.getUrl(), credential.getUrl());
        Assertions.assertEquals(newCredential.getUsername(), credential.getUsername());
        Assertions.assertEquals(newCredential.getKey(), credential.getKey());
        Assertions.assertEquals(newCredential.getPassword(), credential.getPassword());
    }

    @Test
    public void updateCredential() {
        Credential newCredential = new Credential(null, "www.casoftwaretesting.co.uk",
                "casoftwaretesting", "sadasda324324", "ca1234");

        this.credentialMapper.insert(newCredential);

        Integer credentialId = newCredential.getCredentialid();
        Assertions.assertNotNull(credentialId);

        this.credentialMapper.update("www.casoftwaretesting.com", "CA", "asdasdas1", "CA6889"
                , credentialId);

        Credential credential = this.credentialMapper.getCredentialByCredentialId(credentialId);

        Assertions.assertEquals("www.casoftwaretesting.com", credential.getUrl());
        Assertions.assertEquals("CA", credential.getUsername());
        Assertions.assertEquals("asdasdas1", credential.getKey());
        Assertions.assertEquals("CA6889", credential.getPassword());
    }

    @Test
    public void deleteCredential() {
        Credential newCredential = new Credential(null, "www.casoftwaretesting.co.uk",
                "casoftwaretesting", "sadasda324324", "ca1234");

        this.credentialMapper.insert(newCredential);

        Integer credentialId = newCredential.getCredentialid();
        Assertions.assertNotNull(credentialId);

        this.credentialMapper.delete(credentialId);

        Credential credential = this.credentialMapper.getCredentialByCredentialId(credentialId);
        Assertions.assertNull(credential);

    }


}
