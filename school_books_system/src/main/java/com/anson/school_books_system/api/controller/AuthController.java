package com.anson.school_books_system.api.controller;


import com.anson.school_books_system.api.form.AuthForm;
import com.anson.school_books_system.api.vo.AuthVO;
import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @folder 認證管理
 * @package com.anson.school_books_system.api.controller
 * @createTime
 * @description
 */


@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authServer;

    @PostMapping("/login")
    public AuthVO auth(@Validated @RequestBody AuthForm form) {
        return authServer.auth(form);
    }

    @GetMapping("/login2")
    public void login2() {
        log.info("test");
    }

}
