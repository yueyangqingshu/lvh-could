package com.voya.file.service;

import javax.annotation.Resource;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.voya.file.mapper.SysFileInfoMapper;
import com.voya.system.api.domain.SysFileInfo;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysFileInfoServiceImpl implements ISysFileInfoService
{
    private static final Logger log = LoggerFactory.getLogger(SysFileInfoServiceImpl.class);

    @Resource
    private SysFileInfoMapper sysFileInfoMapper;

    /**
     * 事务传播特性设置为 REQUIRES_NEW 开启新的事务 重要！！！！一定要使用REQUIRES_NEW
     */
    @DS("file")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertFile(SysFileInfo fileInfo)
    {
        log.info("=============FILE START=================");
        log.info("当前 XID: {}", RootContext.getXID());

        sysFileInfoMapper.insert(fileInfo);
//        int a=3/0;
        log.info("=============FILE END=================");
    }

}