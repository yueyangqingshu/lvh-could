package com.voya.system.mapper;

import com.voya.system.domain.Order;

public interface OrderMapper
{
    public void insert(Order order);

    public void updateById(Order order);
}