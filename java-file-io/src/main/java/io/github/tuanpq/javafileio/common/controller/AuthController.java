package io.github.tuanpq.javafileio.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import io.github.tuanpq.javafileio.common.dto.UserDto;
import io.github.tuanpq.javafileio.common.entity.User;
import io.github.tuanpq.javafileio.common.helper.Converter;
import io.github.tuanpq.javafileio.common.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired
    private Converter converter;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(converter.fromUserDtoToUser(user));
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<User> users = userService.findAllUsers();
        List<UserDto> userDtoList = new ArrayList<>();
        users.forEach(user -> {
            userDtoList.add(converter.fromUserToUserDto(user));
        });
        model.addAttribute("users", userDtoList);
        return "users";
    }
    
}
