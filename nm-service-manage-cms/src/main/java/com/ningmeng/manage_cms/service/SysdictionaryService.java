package com.ningmeng.manage_cms.service;

import com.ningmeng.framework.domain.system.SysDictionary;
import com.ningmeng.manage_cms.dao.SysDictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SysdictionaryService
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@Service
public class SysdictionaryService {
    @Autowired
    SysDictionaryDao sysDictionaryDao;

    //根据字典分类type查询字典信息
    public SysDictionary findDictionaryByType(String type){

        SysDictionary bydType = sysDictionaryDao.findBydType(type);
        System.out.println(bydType);
        return bydType;
    }
}
