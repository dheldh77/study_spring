package com.kms.project.springboot.web;

import com.kms.project.springboot.service.posts.PostsSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexContorller {
    private final PostsSerivce postsSerivce;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsSerivce.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
