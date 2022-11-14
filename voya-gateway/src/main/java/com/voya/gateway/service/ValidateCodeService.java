package com.voya.gateway.service;

import java.io.IOException;
import com.voya.common.core.exception.CaptchaException;
import com.voya.common.core.web.domain.AjaxResult;

/**
 * 验证码处理
 *
 * @author voya
 */
public interface ValidateCodeService
{
    /**
     * 生成验证码
     */
    public AjaxResult createCaptcha() throws IOException, CaptchaException;

    /**
     * 校验验证码
     */
    public void checkCaptcha(String key, String value) throws CaptchaException;
}
