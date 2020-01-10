package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.ProductCategory;
import com.atguigu.gmall.pms.mapper.ProductCategoryMapper;
import com.atguigu.gmall.pms.service.ProductCategoryService;
import com.atguigu.gmall.vo.product.PmsProductCategoryWithChildrenItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.atguigu.gmall.constant.SysCacheConstant.CATEGORY_MENU_CACHE_KEY;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2020-01-08
 */
@Log4j2
@Service  //dubbo的注解，实际上的实现
@Component
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    ProductCategoryMapper categoryMapper;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    /*
    *   分部署缓存都用redis做
    * */
    @Override
    public List<PmsProductCategoryWithChildrenItem> listCatelogWithChilder(Integer i) {
        Object cacheMenu = redisTemplate.opsForValue().get(CATEGORY_MENU_CACHE_KEY);
        List<PmsProductCategoryWithChildrenItem> items = null;
        if ( cacheMenu != null) {
            //缓存中有了
            log.debug("菜单数据命中缓存.......");
            items = (List<PmsProductCategoryWithChildrenItem>) cacheMenu;
        } else {
            //从数据库中找
            items = categoryMapper.listCatelogWithChilder(i);
            //放到缓存中， redis;
            redisTemplate.opsForValue().set(CATEGORY_MENU_CACHE_KEY, items);
        }


        return items;
    }
}
