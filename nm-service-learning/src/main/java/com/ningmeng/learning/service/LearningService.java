package com.ningmeng.learning.service;

import com.ningmeng.framework.domain.course.TeachplanMediaPub;
import com.ningmeng.framework.domain.learning.respones.GetMediaResult;
import com.ningmeng.framework.domain.learning.respones.LearningCode;
import com.ningmeng.framework.exception.ExceptionCast;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.learning.config.CourseSeachClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: LearningService
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@Service
public class LearningService {
    @Autowired
    CourseSeachClient courseSeachClient;
    //获取课程学习地址（播放学习地址）
    public GetMediaResult getmedia(String courseId, String teachplanId) {
        TeachplanMediaPub getmedia = courseSeachClient.getmedia(teachplanId);
        if(getmedia==null || StringUtils.isEmpty(getmedia.getMediaUrl())){
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        return new GetMediaResult(CommonCode.SUCCESS,getmedia.getMediaUrl());
    }
}
