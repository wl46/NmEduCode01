package com.ningmeng.ucenter.controller;

import com.ningmeng.api.ucenter.UcenterControllerApi;
import com.ningmeng.framework.domain.ucenter.ext.NmUserExt;
import com.ningmeng.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: UcenterController
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {
    @Autowired
    UserService userService;
    @Override
    @GetMapping("/getuserext")
    public NmUserExt getUserext(@RequestParam("username") String username) {
        NmUserExt userExt = userService.getUserExt(username);
        return userExt;
    }
}
