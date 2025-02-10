package org.socialmedia.controller;

import org.socialmedia.dto.UserInfoDTO;
import org.socialmedia.model.Avatar;
import org.socialmedia.model.User;
import org.socialmedia.service.AvatarService;
import org.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private UserService userService;

    @PostMapping("/avatar/{userId}")
    public ResponseEntity<Void> saveAvatar(@PathVariable String userId, MultipartFile avatarImage) throws IOException {
        try {
            User user = new User(userId);
            avatarService.saveAvatar(avatarImage, userId);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserInfoDTO> updateUser(@PathVariable String userId,
                                                  @RequestPart(value = "username",required = false ) String username,
                                                  @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile){
        UserInfoDTO dto = new UserInfoDTO();
        dto.setId(userId);
        dto.setUsername(username);
        dto.setAvatarFile(avatarFile);
        dto = userService.updateUser(dto);
        return ResponseEntity.ok(dto);
    }


}
