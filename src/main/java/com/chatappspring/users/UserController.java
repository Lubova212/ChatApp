package com.chatappspring.users;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/register")
    public String showRegisterPage(){
        return "register";
    }

    @PostMapping("/register")
    public String handleUserRegistration(User user){
        try {
            this.userService.createUser(user);
            System.out.println(user);
            return "redirect:login?status=REGISTER_SUCCESS";
        }catch (Exception exception){
            return "redirect:register?status=REGISTER_FAILED&message="+exception.getMessage();
        }
    }

    @GetMapping("login")
    public String displayLoginPage(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "message", required = false) String message,
            Model model
    ){
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(LoginRequest loginRequest, HttpServletResponse response){

        try {

        User loggedInUser = this.userService.verifyUser(loginRequest.username, loginRequest.password);
        //save user id to cookie / session

            if (loggedInUser == null) throw new RuntimeException("User not found");

            Cookie cookie = new Cookie("loggedInUserId", loggedInUser.getId().toString());
            cookie.setMaxAge(60*60*60*24);
            response.addCookie(cookie);
            return "redirect:chat-room";

        }catch (Exception e){
            return "redirect:login?status=LOGIN_FAILED&message=" + e.getMessage();
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response,
                         @CookieValue(value = "loggedInUserId", defaultValue = "") String userId){
        try {
            Cookie cookie = new Cookie("loggedInUserId", userId);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        return "redirect:login?status=LOGOUT_SUCCESS&message=You_logged_out_successfully";

    }catch (Exception e){
        return "redirect:login?status=LOGOUT_FAILED&message=" + e.getMessage();
    }
    }
}
