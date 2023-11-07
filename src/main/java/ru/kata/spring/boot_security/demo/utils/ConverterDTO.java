package ru.kata.spring.boot_security.demo.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ConverterDTO {

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public ConverterDTO(RoleService roleService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    public User convertToUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        Set<Role> roles = userDTO.getRolesId().stream().map(x -> roleService.findByRole("ROLE_" + x)).collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }

    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        List<String> roles = user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toList());
        userDTO.setRolesId(roles);
        return userDTO;
    }
}
