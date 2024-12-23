package org.socialmedia.dto;

import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

public class UserInfoDTO {
    private String id;
    private String username;
    // todo 23DEC add avatar id field and modify the user info dto return method as well
    private Binary avatarBinary;
    private MultipartFile avatarFile;

    public UserInfoDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Binary getAvatarBinary() {
        return avatarBinary;
    }

    public void setAvatarBinary(Binary avatarBinary) {
        this.avatarBinary = avatarBinary;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }
}
