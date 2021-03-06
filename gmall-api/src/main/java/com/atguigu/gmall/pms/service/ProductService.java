package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductParam;
import com.atguigu.gmall.vo.product.PmsProductQueryParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author Lfy
 * @since 2020-01-08
 */
public interface ProductService extends IService<Product> {

    //查询商品详情
    Product productInfo(Long id);

    /*
    * 根据复杂查询条件返回分页数据
    * */
    PageInfoVo productPageInfo(PmsProductQueryParam productQueryParam);


    /**
     * 保存商品数据
     * @param productParam
     */
    void saveProduct(PmsProductParam productParam);


    //批量上下架
    void updatePublishStatus(List<Long> ids, Integer publishStatus);

}
