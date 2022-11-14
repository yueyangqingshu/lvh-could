package com.voya.system.mapper;

import com.voya.system.domain.Product;

public interface ProductMapper
{
    public Product selectById(Long productId);

    public void updateById(Product product);
}