package com.voya.system.api.factory;

import com.voya.system.api.domain.SysFileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.voya.common.core.domain.R;
import com.voya.system.api.RemoteFileService;
import com.voya.system.api.domain.SysFile;

/**
 * 文件服务降级处理
 *
 * @author voya
 */
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteFileFallbackFactory.class);

    @Override
    public RemoteFileService create(Throwable throwable) {
        log.error("文件服务调用失败:{}", throwable.getMessage());
        return new RemoteFileService() {
            @Override
            public R<SysFile> upload(MultipartFile file) {
                return R.fail("上传文件失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> saveFile(SysFileInfo sysFileInfo) {
                return R.fail("文件入库失败:" + throwable.getMessage());
            }
        };
    }
}
