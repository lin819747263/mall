package com.linmsen.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmsen.JsonData;
import com.linmsen.LoginUser;
import com.linmsen.content.UserContent;
import com.linmsen.product.controller.vo.ProductVO;
import com.linmsen.product.mapper.ProductMapper;
import com.linmsen.product.model.ProductDO;
import com.linmsen.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linmsen
 * @since 2021-04-10
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public Map<String, Object> page(int page, int size) {
        LoginUser loginUser = UserContent.get();

        //第1页，每页2条
        Page<ProductDO> pageInfo = new Page<>(page, size);
        IPage<ProductDO> recordDOPage = productMapper.selectPage(pageInfo, new QueryWrapper<ProductDO>().eq("user_id",loginUser.getId()).orderByDesc("create_time"));
        Map<String, Object> pageMap = new HashMap<>(3);

        pageMap.put("total_record", recordDOPage.getTotal());
        pageMap.put("total_page", recordDOPage.getPages());
        pageMap.put("current_data", recordDOPage.getRecords().stream().map(this::beanProcess).collect(Collectors.toList()));

        return pageMap;
    }

    @Override
    public JsonData detail(String productId) {
        ProductDO productDO = productMapper.selectById(productId);
        return JsonData.buildSuccess(beanProcess(productDO));
    }

    private Object beanProcess(ProductDO obj) {

        return new ProductVO();
    }
}
