package com.ningmeng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.ningmeng.framework.model.request.RequestData;

/**
 * Created by BJDGZJD on 2/12/2019.
 */
@Data
public class QueryPageRequest extends RequestData{

    //站点id
    @ApiModelProperty("站点Id")
    private String siteId;
    //页面ID
    @ApiModelProperty("页面ID")
    private String pageId;
    //页面名称
    @ApiModelProperty("页面名称")
    private String pageName;
    //别名
    @ApiModelProperty("页面别名")
    private String pageAliase;
    //模版id
    @ApiModelProperty("模版id")
    private String templateId;

}
