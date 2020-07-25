package com.ningmeng.manage_cms;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.CmsPageParam;
import com.ningmeng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by BJDGZJD on 3/12/2019.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    //自定义条件查询测试
    @Test
    public void testFindAll(){
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
      exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //页面别名模糊查询，需要自定义字符串的匹配器实现模糊查询
//ExampleMatcher.GenericPropertyMatchers.contains() 包含
//ExampleMatcher.GenericPropertyMatchers.startsWith()//开头匹配
//条件值
        CmsPage cmsPage = new CmsPage();
//站点ID
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
//模板ID
        cmsPage.setTemplateId("5a962c16b00ffc514038fafd");
// cmsPage.setPageAliase("分类导航");
//创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Pageable pageable = new PageRequest(0, 10);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        System.out.println(all);
    }
    @Test
    public void testPage(){
        int page= 0;
        int size =10;
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all=  cmsPageRepository.findAll(pageable);
        System.out.println("==================================="+all.getTotalElements());
    }

    //添加
    @Test
    public void testInsert(){
    //定义实体类
        CmsPage cmsPage=new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams=new ArrayList<>();
        CmsPageParam cmsPageParam=new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);
    }

    //删除
    @Test
    public void testDelete(){
        cmsPageRepository.deleteById("5b17a2c511fe5e0c409e5eb3");
    }

    //修改
    @Test
    public void testUpdate(){
        Optional<CmsPage> optional= cmsPageRepository.findById("");
        if(optional.isPresent()){
            CmsPage cmsPage=optional.get();
            cmsPage.setPageName("测试页面01");
            cmsPageRepository.save(cmsPage);
        }
    }

}
