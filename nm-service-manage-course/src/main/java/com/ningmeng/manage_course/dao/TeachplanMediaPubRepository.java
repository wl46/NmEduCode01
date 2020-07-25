package com.ningmeng.manage_course.dao;

import com.ningmeng.framework.domain.course.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachplanMediaPubRepository extends JpaRepository<TeachplanMediaPub,String> {
    //根据课程id删除
    long deleteByCourseId(String courseId);


}
