package com.ningmeng.filesystem.controller;

import com.ningmeng.api.filesystem.FileSystemControllerApi;
import com.ningmeng.filesystem.service.FileSystemService;
import com.ningmeng.framework.domain.course.CoursePic;
import com.ningmeng.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: FileSystemController
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@RestController
@RequestMapping("/filesystem")
public class FileSystemController implements FileSystemControllerApi {
    @Autowired
    FileSystemService fileSystemService;
   /* @Override
    @PostMapping("/upload")
    public Result upload(MultipartFile multipartFile) throws Exception {
       try {
           AliyunOSSClientUtil aliyunOSSClientUtil = new AliyunOSSClientUtil();
           String s = aliyunOSSClientUtil.uploadImg2Oss(multipartFile);
           String imgUrl = aliyunOSSClientUtil.getImgUrl(s);
           return new Result(true,imgUrl);
       }catch (Exception e){
           e.printStackTrace();
           return new Result(false,"上传失败");
       }
    }*/

    /**
     * 保存图片
     * @param coutseId
     * @param pic
     * @return
     */
    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String coutseId,@RequestParam("pic") String pic) {
        //保存图片
        return fileSystemService.addCoursePic(coutseId,pic);
    }

    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePic findCoursePic(@PathVariable("courseId") String courseId) {
        return fileSystemService.findCoursePic(courseId);
    }
    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return fileSystemService.deleteCoursePic(courseId);
    }

}
