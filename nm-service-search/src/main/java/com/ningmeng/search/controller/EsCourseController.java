package com.ningmeng.search.controller;

import com.ningmeng.api.search.EsCourseControllerApi;
import com.ningmeng.framework.domain.course.CoursePub;
import com.ningmeng.framework.domain.course.TeachplanMediaPub;
import com.ningmeng.framework.domain.search.CourseSearchParam;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.QueryResult;
import com.ningmeng.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName: EsCourseController
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@RestController
@RequestMapping("/search/course")
public class EsCourseController implements EsCourseControllerApi {
    @Autowired
    EsCourseService esCourseService;

    @Override
    @GetMapping(value = "/list/{page}/{size}")
    public QueryResponseResult<CoursePub> list(@PathVariable("page") int page,@PathVariable("size") int size, CourseSearchParam courseSearchParam) {
        return esCourseService.list(page,size,courseSearchParam);
    }

    @Override
    @GetMapping("/getall/{id}")
    public Map<String,CoursePub> getall(String id) {
        return esCourseService.getall(id);
    }

    @Override
    @GetMapping("/getmedia/{id}")
    public TeachplanMediaPub getmedia(@PathVariable("id") String id) {
        //将一个id传给数组，加入service方法
        String[] teachMedia_id=new String[] {id};
        //通过service查询ES获取课程媒资信息
        QueryResponseResult<TeachplanMediaPub> getmedia = esCourseService.getmedia(teachMedia_id);
        QueryResult<TeachplanMediaPub> queryResult = getmedia.getQueryResult();
        if(queryResult!=null
                && queryResult.getList()!=null
                && queryResult.getList().size()>0){
        //返回课程计划对应课程媒资
            return queryResult.getList().get(0);
        }
        return new TeachplanMediaPub();
    }
}
