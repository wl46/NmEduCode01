package com.ningmeng.manage_course.service;

import com.ningmeng.framework.domain.course.ext.CategoryNode;
import com.ningmeng.manage_course.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: CategoryService
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    //查询分类
    public CategoryNode findList(){
        return categoryMapper.selectList();
    }
}
