package com.ningmeng.manage_cms.dao;

import com.ningmeng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsConfigReposity extends MongoRepository<CmsConfig,String> {
}
