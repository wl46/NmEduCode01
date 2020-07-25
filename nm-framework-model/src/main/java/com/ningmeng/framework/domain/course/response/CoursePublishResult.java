package com.ningmeng.framework.domain.course.response;

import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: CoursePublishResult
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@Data
@NoArgsConstructor
@ToString
public class CoursePublishResult extends ResponseResult {
    String previewUrl;//页面预览的url，必须得到页面id才可以拼装
    public CoursePublishResult(ResultCode resultCode,String previewUrl){
        super(resultCode);
        this.previewUrl=previewUrl;
    }
}
