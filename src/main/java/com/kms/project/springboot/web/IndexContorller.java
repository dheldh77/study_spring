package com.kms.project.springboot.web;

import com.kms.project.springboot.config.auth.LoginUser;
import com.kms.project.springboot.config.auth.dto.SessionUser;
import com.kms.project.springboot.service.posts.PostsSerivce;
import com.kms.project.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexContorller {
    private final PostsSerivce postsSerivce;
    private final HttpSession httpSession;

    // 1. @LoginUser SessionUser user
    // - 기존에 (User) httpSession.getAttribute("user")로 가져오던 세션 정보 값이 개선
    // - 이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 되었음
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsSerivce.findAllDesc());
        // 1. (SessionUser) httpSession.getAttribute("user")
        // - 앞서 작성된 CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성
        // - 즉, 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있음
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        // 2. if(user != null)
        // - 세션에 저장된 값이 있을 때만 model에 userName으로 등록
        // - 세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게 됨
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsSerivce.findById(id);
        model.addAttribute("posts", dto);

        return "posts-update";
    }

}
