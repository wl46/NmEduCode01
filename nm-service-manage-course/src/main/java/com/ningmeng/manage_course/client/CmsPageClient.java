package com.ningmeng.manage_course.client;

import com.ningmeng.framework.client.NmServiceList;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.response.CmsPageResult;
import com.ningmeng.framework.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = NmServiceList.NM_SERVICE_MANAGE_CMS)//指定远程调用的服务名
public interface CmsPageClient {
  //根据页面id查询页面信息远程调用cms请求数据
    @GetMapping("/cms/page/get/{id}")//用GetMapping标识远程调用的http的方法类型
    public CmsPage findCmsPageById(@PathVariable("id") String id);

    //添加页面，用于课程预览
  @PostMapping("/cms/page/save")
  public CmsPageResult saveCmsPage(@RequestBody CmsPage cmsPage);
   //一键发布
  @PostMapping("/cms/page/postPageQuick")
  public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage);
}
