package com.voya.system.mapper;

import com.voya.system.domain.Account;

public interface AccountMapper
{
    public Account selectById(Long userId);

    public void updateById(Account account);
}