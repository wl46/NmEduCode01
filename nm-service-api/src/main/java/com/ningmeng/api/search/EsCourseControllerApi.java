package com.ningmeng.api.search;

import com.ningmeng.framework.domain.course.CoursePub;
import com.ningmeng.framework.domain.course.TeachplanMediaPub;
import com.ningmeng.framework.domain.search.CourseSearchParam;
import com.ningmeng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * @ClassName: EsCourseControllerApi
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@Api(value = "课程搜索接口",description = "课程搜索")
public interface EsCourseControllerApi {
    //搜索课程信息
    @ApiOperation("课程综合搜索")
    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam);
    @ApiOperation("根据课程id查看课程信息")
    public Map<String,CoursePub> getall(String id);
    @ApiOperation("根据课程计划id查看课程信息")
    public TeachplanMediaPub getmedia(String id);
}
