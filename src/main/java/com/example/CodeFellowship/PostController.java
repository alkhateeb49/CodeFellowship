package com.example.CodeFellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postrepository;

    @GetMapping("/users/post")
    public RedirectView createPost(String body, Principal principal)
    {
        ApplicationUser currentUser = applicationUserRepository.findByUsername(principal.getName());
        Post post = new Post(body,currentUser);
        postrepository.save(post);
        return new RedirectView("/users/" + currentUser.getId());
    }


}
