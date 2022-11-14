package com.voya.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.voya.auth.form.LoginBody;
import com.voya.auth.form.RegisterBody;
import com.voya.auth.service.SysLoginService;
import com.voya.common.core.domain.R;
import com.voya.common.core.utils.JwtUtils;
import com.voya.common.core.utils.StringUtils;
import com.voya.common.security.auth.AuthUtil;
import com.voya.common.security.service.TokenService;
import com.voya.common.security.utils.SecurityUtils;
import com.voya.system.api.model.LoginUser;

/**
 * token 控制
 *
 * @author voya
 */
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;
    private Logger log = LoggerFactory.getLogger(TokenController.class);

    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form) {
        log.info("执行了登陆");
        log.error("执行了登陆报错");
        log.debug("执行了登陆报---逻辑代码");
        log.trace("执行了登陆报---逻辑代码--跟踪");
        // 用户登录
        LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody) {
        // 用户注册
        sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        return R.ok();
    }
}
