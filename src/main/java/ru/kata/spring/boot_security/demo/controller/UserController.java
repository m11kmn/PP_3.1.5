package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;


import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	private final ModelMapper modelMapper;

	@Autowired
	public UserController(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}

//	@GetMapping
//	public String showUserInfo(){
//		return "user";
//	}

	@CrossOrigin
	@ResponseBody
	@GetMapping("/js")
	public UserDTO getUser(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return convertToUserDTO(user);
	}

	private UserDTO convertToUserDTO(User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		List<String> roles = user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toList());
		userDTO.setRolesId(roles);
		return userDTO;
	}
}