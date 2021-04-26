package com.example.CodeFellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    PostRepository postRepository;

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup.html";
    }

    @PostMapping("/signup")
    public RedirectView signup(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password,
            @RequestParam(value="firstName") String firstName,
            @RequestParam(value="lastName") String lastName,
            @RequestParam(value="dateOfBirth") String dateOfBirth,
            @RequestParam(value="bio") String bio
    ){
        ApplicationUser newUser = new ApplicationUser(bCryptPasswordEncoder.encode(password),username,firstName,lastName,dateOfBirth,bio);
        newUser = applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/login");
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }




    @PostMapping("/perform_login")
    public String getUserProfilePage(Principal p, Model m){
        ApplicationUser myProfile = applicationUserRepository.findByUsername(p.getName());
        List<Post> posts =postRepository.findByOwnerId(myProfile.getId());
        m.addAttribute("user", ((UsernamePasswordAuthenticationToken)p).getPrincipal());
        m.addAttribute("posts", posts);
        return "profile.html";
    }



    @GetMapping("/error")
    public String getErrorPage()
    {
        return "error.html";
    }


    @GetMapping("/users/{id}")
    public String getIndividualUserPage(@PathVariable Integer id, Model m, Principal principal)
    {
        ApplicationUser currentUser = (ApplicationUser)((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        ApplicationUser individualUser = applicationUserRepository.findById(id).get();
        m.addAttribute("individualUser", individualUser);
        m.addAttribute("userID", currentUser.getId());
        m.addAttribute("username", currentUser.getUsername());
        return "profile";
    }


}
