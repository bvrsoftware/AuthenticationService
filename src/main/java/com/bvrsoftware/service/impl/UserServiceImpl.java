package com.bvrsoftware.service.impl;

import java.io.File;




import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bvrsoftware.exception.UserAlreadyExistException;
import com.bvrsoftware.model.Role;
import com.bvrsoftware.model.User;
import com.bvrsoftware.model.UserRole;
import com.bvrsoftware.payload.PageResponse;
import com.bvrsoftware.payload.UserDTO;
import com.bvrsoftware.repository.RoleRepository;
import com.bvrsoftware.repository.UserRepository;
import com.bvrsoftware.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User local = null;
        local = this.userRepository.findByUsername(userDTO.getUsername());
        if (local != null) {
            throw new UserAlreadyExistException("User Already Exists");
        } else {
            userDTO.setProfile("signup.png");
            // encription of password

            userDTO.setPassword(this.bCryptPasswordEncoder.encode(userDTO.getPassword()));

            Set<UserRole> roles = new HashSet<>();

            Role role = new Role();
           // role.setRole_id(60L);
            //role.setRole_name("ROLE_USER");

            role.setRole_id(50L);
            role.setRole_name("ADMIN");
            User user = mapper.map(userDTO, User.class);
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);

            roles.add(userRole);
            for (UserRole r : roles) {
                roleRepository.save(r.getRole());
            }

            user.getUserRoles().addAll(roles);
            local = this.userRepository.save(user);
            this.userRepository.flush();
            local.setPassword("");
        }
        return mapper.map(local, UserDTO.class);
    }

    @Override
    public UserDTO getUser(String username) {
        User user = this.userRepository.findByUsername(username);
        user.setPassword("");
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        try {
            User user = this.userRepository.findById(id).get();
            this.userRepository.delete(user);
            this.userRepository.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = this.userRepository.findById(id).get();
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User local = this.userRepository.findByUsername(userDTO.getUsername());
        local.setFistName(userDTO.getFistName());
        local.setLastName(userDTO.getLastName());
        local.setPhone(userDTO.getPhone());
        this.userRepository.save(local);
        this.userRepository.flush();

    }

    @Override
    public String updateProfile(Long id, MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                if (file.getContentType().equals("image/jpeg")) {
                    Path root = Paths.get(
                            "/home/lt-251/Documents/Personal/Github/examportal-springboot-and-angular/Exan Portal Gui Angular/examportalfrontend/src/assets/profile"
                                    + File.separator + file.getOriginalFilename());
                    Files.copy(file.getInputStream(), root, StandardCopyOption.REPLACE_EXISTING);

                    User user = this.userRepository.findById(id).get();
                    user.setProfile(file.getOriginalFilename());
                    updateUser(mapper.map(user, UserDTO.class));
                }
            }
            return "Uploaded: " + file.getOriginalFilename();
        } catch (Exception e) {
            throw new RuntimeException("Not Uploaded");
        }
    }

    @Override
    public PageResponse<List<UserDTO>> getAllUser(Pageable pageable) {
        Page<User> pages = this.userRepository.findAllByOrderByFistNameAsc(pageable);
        List<UserDTO> userDTOS = pages.getContent().stream().map(this::toEntity).collect(Collectors.toList());
        return new PageResponse<>(userDTOS, pages.getTotalPages());
    }

    public UserDTO toEntity(User user) {
        return mapper.map(user, UserDTO.class);
    }
}
