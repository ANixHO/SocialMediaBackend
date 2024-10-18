package org.socialmedia.controller;

import org.socialmedia.model.Avatar;
import org.socialmedia.model.User;
import org.socialmedia.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/avatar")
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> saveAvatar(@PathVariable Long userId, MultipartFile avatarImage) throws IOException {
        try {
            User user = new User(userId);
            avatarService.saveAvatar(avatarImage, user);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Avatar> getAvatar(@PathVariable Long userId){
        try {
            User user = new User(userId);
            Avatar avatar =  avatarService.getAvatarByUser(user);
            return ResponseEntity.ok(avatar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable Long userId){
        try {
            User user = new User(userId);
            avatarService.getAvatarByUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
