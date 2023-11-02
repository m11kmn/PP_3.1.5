package ru.kata.spring.boot_security.demo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String showUserInfo(){
        return "admin";
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/js")
    public List<UserDTO> getUsers() {
        return userService.showListOfUsers()
                .stream().map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/js")
    public void createUser(@RequestBody UserDTO user) {
        userService.saveUser(convertToUser(user));
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping("/js")
    public void updateUser(@RequestBody UserDTO user) {
        userService.updateUser(convertToUser(user));

    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping("/js")
    public void deleteUser(@RequestBody UserDTO user) {
        userService.deleteUser(user.getId());
    }


    private User convertToUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        Set<Role> roles = userDTO.getRolesId().stream().map(x -> roleService.findByRole("ROLE_" + x)).collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        List<String> roles = user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toList());
        userDTO.setRolesId(roles);
        return userDTO;
    }

}
