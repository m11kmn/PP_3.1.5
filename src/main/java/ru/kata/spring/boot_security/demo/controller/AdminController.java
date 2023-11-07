package ru.kata.spring.boot_security.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.dto.UserDTO;

import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.utils.ConverterDTO;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final ConverterDTO converterDTO;

    @Autowired
    public AdminController(UserService userService, ConverterDTO converterDTO) {
        this.userService = userService;
        this.converterDTO = converterDTO;
    }

    @GetMapping
    public ModelAndView showUserInfo(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping("/show")
    public List<UserDTO> getUsers() {
        return userService.showListOfUsers()
                .stream().map(converterDTO::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public void createUser(@RequestBody UserDTO user) {
        userService.saveUser(converterDTO.convertToUser(user));
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody UserDTO user) {userService.updateUser(converterDTO.convertToUser(user));}

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody UserDTO user) {
        userService.deleteUser(user.getId());
    }
}
