package com.ningmeng.api.media;

import com.ningmeng.framework.domain.media.MediaFile;
import com.ningmeng.framework.domain.media.request.QueryMediaFileRequest;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "媒体文件管理",description = "媒体文件管理接口",tags = {"媒体文件管理接口"})
public interface MediaFileControllerApi {
    @ApiOperation("查询文件列表")
    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);

    @ApiOperation("开始处理某个文件")
    public ResponseResult process(String id);
}
