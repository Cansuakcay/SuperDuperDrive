package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.UserNote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface UserNoteMapper {

    List<UserNote> getNotesByUsername(String username);

    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNoteByUsername(
            String username,
            String notetitle,
            String notedescription);
}
