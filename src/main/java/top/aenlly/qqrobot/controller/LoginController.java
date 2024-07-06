package top.aenlly.qqrobot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.aenlly.qqrobot.service.LoginService;

/**
 * @author Aenlly||tnw
 * @create 2024/06/07 9:17
 * @since 1.0.0
 */
@RestController
@RequestMapping("/v1/robot")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public void login(Long qq) {
        loginService.login(qq);
    }
}
