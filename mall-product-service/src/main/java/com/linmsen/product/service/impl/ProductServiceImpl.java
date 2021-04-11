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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
        IPage<ProductDO> recordDOPage = productMapper.selectPage(pageInfo, new QueryWrapper<ProductDO>().orderByDesc("create_time"));
        Map<String, Object> pageMap = new HashMap<>(3);

        pageMap.put("total_record", recordDOPage.getTotal());
        pageMap.put("total_page", recordDOPage.getPages());
        pageMap.put("current_data", recordDOPage.getRecords().stream().map(this::beanProcess).collect(Collectors.toList()));

        return pageMap;
    }

    @Override
    public JsonData detail(Long productId) {
        ProductDO productDO = productMapper.selectById(productId);
        return JsonData.buildSuccess(beanProcess(productDO));
    }

    @Override
    public ProductVO findDetailById(Long productId) {
        ProductDO productDO = productMapper.selectById(productId);
        return beanProcess(productDO);
    }

    @Override
    public List<ProductVO> findProductsByIdBatch(List<Long> productIdList) {
        List<ProductDO> productDOList =  productMapper.selectList(new QueryWrapper<ProductDO>().in("id",productIdList));

        List<ProductVO> productVOList = productDOList.stream().map(obj->beanProcess(obj)).collect(Collectors.toList());

        return productVOList;
    }

    private ProductVO beanProcess(ProductDO obj) {
        ProductVO vo= new ProductVO();
        BeanUtils.copyProperties(obj, vo);
        return vo;
    }
}
