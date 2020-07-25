package com.ningmeng.manage_cms.dao;

import com.ningmeng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ClassName: CmsTemplateRepository
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
}
