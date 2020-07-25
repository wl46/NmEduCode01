package com.ningmeng.api.media;

import com.ningmeng.framework.domain.media.response.CheckChunkResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName: MediaUploadControllerApi
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@Api(value = "媒资管理接口",description = "媒资管理接口，提供文件上传，处理等接口")
public interface MediaUploadControllerApi {
    //文件上传前的准备工作,校验文件是否存在
    @ApiOperation("文件上传注册")
    public ResponseResult register(String fileMd5,
                                    String fileName,
                                    Long fileSize,
                                    String mimetype,
                                    String fileExt);
    @ApiOperation("校验分块文件是否存在")
    public CheckChunkResult checkchunk(String fileMd5,
                                       Integer chunk,
                                       Integer chunkSize);
    @ApiOperation("上传分块")
    public ResponseResult ulpoadChunk(MultipartFile file,
                                      String fileMd5,
                                      Integer chunk) throws IOException;
    @ApiOperation("合并分块")
    public ResponseResult mergechunks(
            String fileMd5,
            String fileName,
            Long fileSize,
            String mimetype,
            String fileExt
    );
}
