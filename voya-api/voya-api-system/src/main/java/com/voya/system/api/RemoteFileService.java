package com.voya.system.api;

import com.voya.system.api.domain.SysFileInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.voya.common.core.constant.ServiceNameConstants;
import com.voya.common.core.domain.R;
import com.voya.system.api.domain.SysFile;
import com.voya.system.api.factory.RemoteFileFallbackFactory;

/**
 * 文件服务
 * 
 * @author voya
 */
@FeignClient(contextId = "remoteFileService", value = ServiceNameConstants.FILE_SERVICE, fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService
{
    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<SysFile> upload(@RequestPart(value = "file") MultipartFile file);

    /**
     * 保存系统文件
     *
     * @param sysFileInfo 系统文件
     * @return 结果
     */
    @PostMapping("/insertFile")
    R<Boolean> saveFile(@RequestBody SysFileInfo sysFileInfo);
}