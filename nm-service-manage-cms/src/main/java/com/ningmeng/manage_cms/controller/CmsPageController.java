package com.ningmeng.manage_cms.controller;

import com.ningmeng.api.cms.CmsPageControllerApi;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.request.QueryPageRequest;
import com.ningmeng.framework.domain.cms.response.CmsPageResult;
import com.ningmeng.framework.domain.cms.response.CmsPostPageResult;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_cms.dao.CmsPageRepository;
import com.ningmeng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by BJDGZJD on 2/12/2019.
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private PageService pageService;
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageRequest queryPageRequest) {
       /* QueryResult queryResult = new QueryResult();
       queryResult.setTotal(2);
        //静态数据列表
        List list=new ArrayList();
        CmsPage cmsPage=new CmsPage();
       cmsPage.setPageName("测试页面");
        list.add(cmsPage);
        queryResult.setList(list);
       QueryResponseResult queryResponseResult=new
                QueryResponseResult(CommonCode.SUCCESS,queryResult);*/

        return pageService.findList(page,size,queryPageRequest);
    }

    @Override
    @GetMapping("/findAll")
    public  List findAll() {
                return cmsPageRepository.findAll();
    }
    //添加
    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
     return pageService.add(cmsPage);
    }
    /*
     * 根据id查询页面
     * */
    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return pageService.getById(id);
    }

    @Override
    @PostMapping("/edit/{id}")//这是使用put方法，http方法中put表示更新
    public CmsPageResult edit(@PathVariable("id") String id,@RequestBody CmsPage cmsPage) {

        return pageService.edit(id,cmsPage);
    }

    @Override
    @GetMapping("/del/{id}")//使用http的delete方法完成岗位操作
    public ResponseResult delete(@PathVariable("id") String id) {
        return pageService.delete(id);
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) {
        return pageService.post(pageId);
    }

    @Override
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        return pageService.save(cmsPage);
    }

    @Override
    @PostMapping("/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage) {
        return pageService.postPageQuick(cmsPage);
    }



}
