package com.ningmeng.manage_media.controller;

import com.ningmeng.api.media.MediaUploadControllerApi;
import com.ningmeng.framework.domain.media.response.CheckChunkResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_media.service.MediaUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName: MediaUploadController
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@RestController
@RequestMapping("/media/upload")
public class MediaUploadController implements MediaUploadControllerApi {

    @Autowired
    MediaUploadService mediaUploadService;

    @Override
    @PostMapping("/register")
    public ResponseResult register(@RequestParam("fileMd5") String fileMd5,@RequestParam("fileName") String fileName,
                                   @RequestParam("fileSize") Long fileSize,@RequestParam("mimetype") String mimetype, @RequestParam("fileExt")String fileExt) {
        return mediaUploadService.register(fileMd5,fileName,fileSize,mimetype,fileExt);
    }

    @Override
    @PostMapping("/checkchunk")
    public CheckChunkResult checkchunk(@RequestParam("fileMd5") String fileMd5,@RequestParam("chunk") Integer chunk,
                                       @RequestParam("chunkSize") Integer chunkSize) {
        return mediaUploadService.checkchunk(fileMd5,chunk,chunkSize);
    }

    @Override
    @PostMapping("ulpoadChunk")
    public ResponseResult ulpoadChunk(@RequestParam("file") MultipartFile file, @RequestParam("fileMd5") String fileMd5,
                                      @RequestParam("chunk") Integer chunk) throws IOException {
        return mediaUploadService.ulpoadChunk(file,fileMd5,chunk);
    }

    @Override
    @PostMapping("mergechunks")
    public ResponseResult mergechunks(@RequestParam("fileMd5") String fileMd5,@RequestParam("fileName") String fileName,
                                      @RequestParam("fileSize") Long fileSize,@RequestParam("mimetype") String mimetype, @RequestParam("fileExt")String fileExt) {
        return mediaUploadService.mergechunks(fileMd5,fileName,fileSize,mimetype,fileExt);
    }
}
