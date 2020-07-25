package com.ningmeng.api.filesystem;

import com.ningmeng.framework.domain.course.CoursePic;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

public interface FileSystemControllerApi {
   /* @ApiOperation("上传图片")
    public Result upload(MultipartFile multipartFile) throws Exception;*/

    @ApiOperation("添加课图片")
    public ResponseResult addCoursePic(String coutseId,String pic);

    @ApiOperation("获取课程基础信息")
    public CoursePic findCoursePic(String courseId);

    @ApiOperation("图片删除")
    public  ResponseResult deleteCoursePic(String courseId);
}
