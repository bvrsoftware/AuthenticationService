package com.bvrsoftware.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import com.bvrsoftware.payload.PageResponse;
import com.bvrsoftware.payload.UserDTO;
import com.bvrsoftware.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    // creating user
    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            return new ResponseEntity<UserDTO>(this.userService.createUser(userDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Already Exists", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<UserDTO>(this.userService.getUser(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Username is not exists", HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        try {
            this.userService.deleteUser(id);
            return new ResponseEntity<String>("Delete user", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("User not found with given id", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all/{pageId}")
    public ResponseEntity<List<?>> getAllUser(@PathVariable("pageId") Integer pageId) {
        PageRequest pageable = PageRequest.of(pageId - 1, 2);
        PageResponse<List<UserDTO>> listUser = this.userService.getAllUser(pageable);
        List li=new ArrayList<>();
        li.add(listUser.getData());
        li.add(pageId);
        li.add(listUser.getTotalPage());
        return new ResponseEntity<>(li, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        try {
            this.userService.updateUser(userDTO);
            return new ResponseEntity<>("User Updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User is not exist with given username", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // to upload picture
    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> updateProfilePicture(@PathVariable("userId") Long id, @RequestBody MultipartFile file) {
        try {
            String message = this.userService.updateProfile(id, file);
            return new ResponseEntity<String>(message, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Not Uploaded", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
