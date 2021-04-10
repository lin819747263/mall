package com.linmsen.coupon.service.impl;

import com.linmsen.coupon.model.ProductDO;
import com.linmsen.coupon.mapper.ProductMapper;
import com.linmsen.coupon.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

}
