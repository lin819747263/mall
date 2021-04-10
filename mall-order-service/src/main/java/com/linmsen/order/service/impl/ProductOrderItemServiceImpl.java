package com.linmsen.order.service.impl;

import com.linmsen.order.model.ProductOrderItemDO;
import com.linmsen.order.mapper.ProductOrderItemMapper;
import com.linmsen.order.service.ProductOrderItemService;
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
public class ProductOrderItemServiceImpl extends ServiceImpl<ProductOrderItemMapper, ProductOrderItemDO> implements ProductOrderItemService {

}
