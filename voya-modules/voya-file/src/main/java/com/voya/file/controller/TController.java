package com.voya.file.controller;

import com.voya.common.core.constant.SecurityConstants;
import com.voya.common.core.web.controller.BaseController;
import com.voya.system.api.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 *
 * @author voya
 */
@RestController
@RequestMapping("/gateway")
public class TController extends BaseController {

    @Autowired(required = false)
    private RemoteUserService remoteUserService;

    @GetMapping("/list")
    public Object list() {
        return remoteUserService.getUserInfo("admin", SecurityConstants.INNER);
    }

}

