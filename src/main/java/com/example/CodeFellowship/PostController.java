package com.example.CodeFellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postrepository;

    @PostMapping("/post")
    public String createPost(@RequestParam(value="post") String body, Principal p,Model m)
    {
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", ((UsernamePasswordAuthenticationToken)p).getPrincipal());
        Post post = new Post(body,currentUser);
        postrepository.save(post);
        List<Post> posts =postrepository.findByOwnerId(currentUser.getId());
        m.addAttribute("posts", posts);
        return "profile.html";
    }



}
