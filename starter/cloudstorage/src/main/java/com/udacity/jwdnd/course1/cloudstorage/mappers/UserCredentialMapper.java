package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.UserCredential;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserCredentialMapper {

    List<UserCredential> getCredentialsByUsername(String username);

    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredentialByUsername(
            String url,
            String usernameC,
            String key,
            String password,
            String username
    );

}
