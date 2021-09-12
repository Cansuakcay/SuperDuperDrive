package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.UserFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserFileMapper {

    List<UserFile> getFileByUsername(String username);

    List<UserFile> getFileByUsernameAndFileName(Map<String, Object> paraMap);
}
