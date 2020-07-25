package com.ningmeng.filesystem.dao;

import com.ningmeng.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: CoursePicRepository
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
public interface CoursePicRepository extends JpaRepository<CoursePic,String> {
    long deleteByCourseid(String courseId);


}
