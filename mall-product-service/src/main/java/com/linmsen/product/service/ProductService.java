package com.linmsen.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmsen.JsonData;
import com.linmsen.product.controller.vo.ProductVO;
import com.linmsen.product.model.ProductDO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
public interface ProductService {

    Map<String, Object> page(int page, int size);

    JsonData detail(Long productId);

    ProductVO findDetailById(Long productId);

    List<ProductVO> findProductsByIdBatch(List<Long> productIdList);
}
