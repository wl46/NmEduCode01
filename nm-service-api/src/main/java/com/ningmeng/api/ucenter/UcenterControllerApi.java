package com.ningmeng.api.ucenter;

import com.ningmeng.framework.domain.ucenter.ext.NmUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户中心",description = "用户中心管理")
public interface UcenterControllerApi {
    @ApiOperation("给据用户账号查询用户信息")
    public NmUserExt getUserext(String username);
}
