package com.ningmeng.api.cms;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.request.QueryPageRequest;
import com.ningmeng.framework.domain.cms.response.CmsPageResult;
import com.ningmeng.framework.domain.cms.response.CmsPostPageResult;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * Created by BJDGZJD on 2/12/2019.
 */
@Api(value = "cms页面管理接口",description = "管理接口，提供页面的增删改查")
public interface CmsPageControllerApi {

    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="页码",required=true,paramType="path",dataType="int"),
                    @ApiImplicitParam(name="size",value="每页记录数",required=true,paramType="path",dataType="int")})
    public QueryResponseResult findList(int page,int size,QueryPageRequest queryPageRequest);
    public List findAll();
    @ApiOperation("添加页面")
    public CmsPageResult add(CmsPage cmsPage);
    @ApiOperation("通过ID查询页面")
    public CmsPage findById(String id);
    @ApiOperation("修改页面")
    public CmsPageResult edit(String id,CmsPage cmsPage);
    @ApiOperation("通过ID删除页面")
    public ResponseResult delete(String id);

    //页面发布
    @ApiOperation("页面发布")
    public ResponseResult post(String pageId);
    @ApiOperation("保存页面")
    public CmsPageResult save(CmsPage cmsPage);

    @ApiOperation("一键发布页面")
    public CmsPostPageResult postPageQuick(CmsPage cmsPage);

}
