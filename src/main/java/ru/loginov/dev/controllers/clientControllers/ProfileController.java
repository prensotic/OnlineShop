package ru.loginov.dev.controllers.clientControllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.entities.UserPersonalData;
import ru.loginov.dev.services.UserPersonalDataService;
import ru.loginov.dev.services.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final UserPersonalDataService userPersonalDataService;

    public ProfileController(UserService userService, UserPersonalDataService userPersonalDataService) {
        this.userService = userService;
        this.userPersonalDataService = userPersonalDataService;
    }

    @GetMapping
    public String userProfilePage(Model model, Authentication authentication){
        User user = userService.findUserByUsername(authentication.getName());
        UserPersonalData userPersonalData = userPersonalDataService.findUserPersonalDataByUserId(user.getId());

        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userPersonalData", userPersonalData);

        return "client/profile";
    }

    @PostMapping("/set-user-personal-data")
    public String setUserPersonalData(Authentication authentication,
                                      @RequestParam("firstName") String firstName,
                                      @RequestParam("lastName") String lastName,
                                      @RequestParam("phoneNumber") String phoneNumber){
        User user = userService.findUserByUsername(authentication.getName());
        userPersonalDataService.saveOrUpdateUserPersonalData(user, firstName, lastName, phoneNumber);

        return "redirect:/profile";
    }

    @PatchMapping("/set-new-password")
    public String setNewPassword(Authentication authentication,
                                 @RequestParam("password") String password,
                                 @RequestParam("newPassword") String newPassword){
        User user = userService.findUserByUsername(authentication.getName());
        userService.updatePassword(user.getId(), password, newPassword);

        return "redirect:/profile";
    }
}