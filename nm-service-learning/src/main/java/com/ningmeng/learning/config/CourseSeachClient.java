package com.ningmeng.learning.config;

import com.ningmeng.framework.client.NmServiceList;
import com.ningmeng.framework.domain.course.TeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = NmServiceList.NM_SERVICE_SEARCH)
public interface CourseSeachClient {

    //根据课程计划id查询媒资信息
    @GetMapping("/search/course/getmedia/{id}")
    public TeachplanMediaPub getmedia(@PathVariable("id") String id);
}
