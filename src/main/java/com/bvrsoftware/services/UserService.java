package com.bvrsoftware.services;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.bvrsoftware.payload.PageResponse;
import com.bvrsoftware.payload.UserDTO;

public interface UserService {
    public UserDTO createUser(UserDTO userDTO);

    public UserDTO getUser(String username);

    public void deleteUser(Long id);

    public UserDTO getUserById(Long id);

    public void updateUser(UserDTO userDTO);

    public String updateProfile(Long id, MultipartFile file);

    public PageResponse<List<UserDTO>> getAllUser(Pageable pageable);
}
