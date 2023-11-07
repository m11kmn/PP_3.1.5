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
import ru.kata.spring.boot_security.demo.utils.ConverterDTO;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
public class UserController {

	private final ConverterDTO converterDTO;

	@Autowired
	public UserController(ConverterDTO converterDTO) {this.converterDTO = converterDTO;}

	@GetMapping("/show")
	public UserDTO getUser(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return converterDTO.convertToUserDTO(user);
	}
}