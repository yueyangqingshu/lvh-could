package com.voya.auth.controller;

import com.alibaba.nacos.api.common.Constants;
import com.voya.auth.form.LoginBody;
import com.voya.auth.service.SysLoginService;
import com.voya.auth.service.SysMobileLoginService;
import com.voya.common.core.web.domain.AjaxResult;
import com.voya.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 登录验证
 *
 * @author voya
 */
@RestController
@RequestMapping("/mobile")
public class H5TokenController {

    @Autowired
    private SysMobileLoginService sysMobileLoginService;

    @Autowired
    private SysLoginService sysLoginService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult mobileLogin(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        LoginUser userInfo = sysLoginService.login(loginBody.getUsername(), loginBody.getPassword());
        String token = sysMobileLoginService.createToken(userInfo);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }
}
