package com.ningmeng.api.course;

import com.ningmeng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: CategoryControllerApi
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@Api(value = "课程分类管理",description = "课程分类管理",tags = {"课程分类管理"})
public interface CategoryControllerApi {
    @ApiOperation("查询分类")
    public CategoryNode findList();

}
