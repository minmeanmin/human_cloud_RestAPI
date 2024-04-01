package com.example.project_v2.user;

import com.example.project_v2._core.util.ApiUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    // 메인 화면
    @GetMapping("/")
    public ResponseEntity<?> index() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<?> mainPageList = userService.getMainPageByUserRole(sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(mainPageList));
    }

    // 회원 가입
    @PostMapping("/users/join")
    public ResponseEntity<?> join(UserRequest.JoinDTO reqDTO) {
        User user = userService.join(reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(user));
    }

    // 회원 가입 페이지
    @GetMapping("/users/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    // 로그인
    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO reqDTO) {
        User sessionUser = userService.login(reqDTO);
        session.setAttribute("sessionUser", sessionUser);
        // return "redirect:/";
        return ResponseEntity.ok(new ApiUtil<>(sessionUser));
    }


    // 로그인 화면
    @GetMapping("/users/login-form")
    public String login() {
        return "/user/login-form";
    }

    // 회원 정보 수정
    // Put 으로 전환 필요
    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UserRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User newSessionUser = userService.update(sessionUser.getId(), reqDTO);
        session.setAttribute("sessionUser", newSessionUser);
        return ResponseEntity.ok(new ApiUtil<>(newSessionUser));
    }

    // 회원 정보 수정 화면
    @GetMapping("/api/users/{id}/update-form")
    public String updateForm(@PathVariable Integer id) {
        return "/user/update-form";
    }


    // 로그아웃
    @GetMapping("/api/users/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 마이페이지 메인 (공고, 이력서 출력)
    @GetMapping("/api/myPages")
    public ResponseEntity<?> myPage() {
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 마이 페이지 - 지원한 공고 (공고 출력 / 이력서 신청 여부)
    @GetMapping("/api/myPages/selectList")
    public ResponseEntity<?> myPageList() {
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 회원가입(username) 중복 확인
    @GetMapping("/api/username-same-checks")
    public ApiUtil<?> usernameSameCheck(String username) {
        User user = userService.sameCheck(username);
        if (user == null) {
            return new ApiUtil<>(true);
        } else {
            return new ApiUtil<>(false);
        }
    }

    // 스크랩 여부 확인
    @GetMapping("/api/scraps/{id}")
    public ResponseEntity<?> scrapList(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}


