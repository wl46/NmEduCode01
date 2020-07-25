package com.ningmeng.filesystem.service;

import com.ningmeng.filesystem.dao.CoursePicRepository;
import com.ningmeng.framework.domain.course.CoursePic;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CoursePicService {
    @Autowired
    CoursePicRepository  coursePicRepository;

    @Transactional
    public ResponseResult addCoursePic(String coutseId, String pic) {
        //查询课程图片

        /*Optional<CoursePic> optional = coursePicRepository.findById(coutseId);
        CoursePic coursePic=null;
        if(optional.isPresent()){
            coursePic=optional.get();
        }
        //没有课程图片则新建对象
        if(coursePic==null){
            coursePic=new CoursePic();
        }
        coursePic.setCourseid(coutseId);
        coursePic.setPic(pic);
        coursePicRepository.save(coursePic);*/

        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CoursePic findCoursePic(String courseId) {
        /*  CoursePic one = coursePicRepository.getOne(courseId);*/
        return null;
    }
    //删除课程图片
    @Transactional
    public ResponseResult deleteCoursePic(String courseId) {
//执行删除，返回1表示删除成功，返回0表示删除失败
        long result = coursePicRepository.deleteByCourseid(courseId);
        if(result>0){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
