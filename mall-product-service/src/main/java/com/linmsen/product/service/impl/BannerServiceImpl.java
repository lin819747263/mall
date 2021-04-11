package com.linmsen.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmsen.product.controller.vo.BannerVO;
import com.linmsen.product.mapper.BannerMapper;
import com.linmsen.product.model.BannerDO;
import com.linmsen.product.service.BannerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class BannerServiceImpl implements BannerService {


    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<BannerVO> list() {
        List<BannerDO> list = bannerMapper.selectList(new QueryWrapper<BannerDO>().
                orderByAsc("weight"));

        List<BannerVO> bannerVOS  = list.stream().map(obj -> {
                    BannerVO vo = new BannerVO();
                    BeanUtils.copyProperties(obj, vo);
                    return vo;
                }
        ).collect(Collectors.toList());

        return bannerVOS;
    }

}
