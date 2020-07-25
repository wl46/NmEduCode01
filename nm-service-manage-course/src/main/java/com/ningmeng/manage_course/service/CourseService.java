package com.ningmeng.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.response.CmsPageResult;
import com.ningmeng.framework.domain.cms.response.CmsPostPageResult;
import com.ningmeng.framework.domain.course.*;
import com.ningmeng.framework.domain.course.ext.CourseInfo;
import com.ningmeng.framework.domain.course.ext.CourseView;
import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import com.ningmeng.framework.domain.course.request.CourseListRequest;
import com.ningmeng.framework.domain.course.response.AddCourseResult;
import com.ningmeng.framework.domain.course.response.CourseCode;
import com.ningmeng.framework.domain.course.response.CoursePublishResult;
import com.ningmeng.framework.exception.ExceptionCast;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.QueryResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_course.client.CmsPageClient;
import com.ningmeng.manage_course.dao.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: CourseService
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@Service
public class CourseService {
    final
    TeachplanMapper teachplanMapper;
    final
    CourseMapper courseMapper;
    final
    CoursePubRepository coursePubRepository;
    final
    CmsPageClient cmsPageClient;
    final
    CourseMarketRepository courseMarketRepository;
    final
    CourseBaseRepository courseBaseRepository;
    final
    TeachplanRepository teachplanRepository;
    final
    CoursePicRepository coursePicRepository;
    final
    TeachplanMediaRepository teachplanMediaRepository;
    final
    TeachplanMediaPubRepository teachplanMediaPubRepository;
    public CourseService(CourseBaseRepository courseBaseRepository, TeachplanRepository teachplanRepository, CourseMarketRepository courseMarketRepository, CoursePicRepository coursePicRepository, CmsPageClient cmsPageClient, CoursePubRepository coursePubRepository, TeachplanMapper teachplanMapper, CourseMapper courseMapper, TeachplanMediaRepository teachplanMediaRepository, TeachplanMediaPubRepository teachplanMediaPubRepository) {
        this.courseBaseRepository = courseBaseRepository;
        this.teachplanRepository = teachplanRepository;
        this.courseMarketRepository = courseMarketRepository;
        this.coursePicRepository = coursePicRepository;
        this.cmsPageClient = cmsPageClient;
        this.coursePubRepository = coursePubRepository;
        this.teachplanMapper = teachplanMapper;
        this.courseMapper = courseMapper;
        this.teachplanMediaRepository = teachplanMediaRepository;
        this.teachplanMediaPubRepository = teachplanMediaPubRepository;
    }


    //查询课程计划
    public TeachplanNode findTeachplanList(String courseId){
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;
    }

     //获取课程根节点，如果没有则添加根节点
    public String getTeachplanRoot(String courseId){
        //校验课程id
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }
        CourseBase courseBase = optional.get();
        //取出课程计划根节点
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if(teachplanList==null){
            Teachplan teachplanRoot = new Teachplan();
            teachplanRoot.setCourseid(courseId);
            teachplanRoot.setPname(courseBase.getName());
            teachplanRoot.setParentid("0");
            teachplanRoot.setGrade("1");//1级
            teachplanRoot.setStatus("0");//未发布
            teachplanRepository.save(teachplanRoot);
            return teachplanRoot.getId();
        }
        Teachplan teachplan = teachplanList.get(0);
        return teachplan.getId();
    }
        //添加课程计划
        @Transactional
        public ResponseResult addTeachplan(Teachplan teachplan){
            //校验课程id和课程计划名称
           if(teachplan==null
                   ||StringUtils.isEmpty(teachplan.getCourseid())
                   ||StringUtils.isEmpty(teachplan.getPname())
                 ){
               ExceptionCast.cast(CommonCode.SERVER_ERROR);
           }
           //取出课程id
            String courseid = teachplan.getCourseid();
           //取出父结点id
            String parentid = teachplan.getParentid();
            if(StringUtils.isEmpty(parentid)){
                //如果父结点为空则获取根节点
                parentid=getTeachplanRoot(courseid);
            }
            //取出父结点信息
            Optional<Teachplan> teachplanOptional = teachplanRepository.findById(parentid);
            if(!teachplanOptional.isPresent()){
                ExceptionCast.cast(CommonCode.SERVER_ERROR);
            }
            //父结点级别
            Teachplan teachplan1 = teachplanOptional.get();
            //父结点级别
            String parentGrade = teachplan1.getGrade();
            teachplan.setParentid(parentid);
            teachplan.setStatus("0");//未发布
            //子结点的级别，根据父结点来判断
            if(parentGrade.equals("1")){
                teachplan.setGrade("2");
            }else if(parentGrade.equals("2")){
                teachplan.setGrade("3");
            }
            //设置课程id
            teachplan.setCourseid(teachplan1.getCourseid());
            teachplanRepository.save(teachplan);
            return new ResponseResult(CommonCode.SUCCESS);
        }

        //课程列表分页查询
      public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest){
      if(courseListRequest==null){
          courseListRequest=new CourseListRequest();
      }
      if(page<=0){
          page=0;
      }
      if(size<=0){
          size=20;
      }
      //设置分页参数
          PageHelper.startPage(page,size);
      //分页查询
          Page<CourseInfo> courseInfoPage= courseMapper.findCourseListPage(courseListRequest);
       //查询列表
          List<CourseInfo> list=courseInfoPage.getResult();
        //总记录数
          long total = courseInfoPage.getTotal();
         //查询结果集
          QueryResult<CourseInfo> courseInfoQueryResult = new QueryResult<>();
          courseInfoQueryResult.setList(list);
          courseInfoQueryResult.setTotal(total);
          return new QueryResponseResult<CourseInfo>(CommonCode.SUCCESS,courseInfoQueryResult);

      }

      //添加课程提交
     @Transactional
    public AddCourseResult addCourseBase(CourseBase courseBase){
        //课程状态默认为未发布
         courseBase.setStatus("202001");
         courseBaseRepository.save(courseBase);
         return new AddCourseResult(CommonCode.SUCCESS,courseBase.getId());
     }

     //查询基础课程信息
    public CourseBase getCoursebaseById(String courseid){
        Optional<CourseBase> courseBase = courseBaseRepository.findById(courseid);
        if(courseBase.isPresent()){
          return  courseBase.get();
        }
        return null;
    }

    @Transactional
    public ResponseResult updateCoursebase(String id,CourseBase courseBase){
        //查询出数据
        CourseBase one
                = this.getCoursebaseById(id);
        if(one==null){
            return new  ResponseResult(CommonCode.FAIL);
        }
        //修改课程信息
        one.setName(courseBase.getName());
        one.setMt(courseBase.getMt());
        one.setSt(courseBase.getSt());
        one.setGrade(courseBase.getGrade());
        one.setStudymodel(courseBase.getStudymodel());
        one.setUsers(courseBase.getUsers());
        one.setDescription(courseBase.getDescription());
        CourseBase save = courseBaseRepository.save(one);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CourseMarket getCourseMarketById(String courseid){
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseid);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Transactional
    public CourseMarket updateCourseMarket(String id,CourseMarket courseMarket){
        CourseMarket one = this.getCourseMarketById(id);
        if(one!=null){
            one.setCharge(courseMarket.getCharge());
            one.setStartTime(courseMarket.getStartTime());
            one.setEndTime(courseMarket.getEndTime());
            one.setPrice(courseMarket.getPrice());
            one.setQq(courseMarket.getQq());
            one.setValid(courseMarket.getValid());
            courseMarketRepository.save(one);
        }else{
            //添加课程营销信息
            one =new CourseMarket();
            BeanUtils.copyProperties(courseMarket,one);
            //设置课程id
            one.setId(id);
            courseMarketRepository.save(one);
        }
      return one;
    }
    //查询课程视图，包括基本信息、图片、营销、课程计划
    public CourseView getCourseView(String id) {
        CourseView courseView = new CourseView();
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(id);
        //查询课程的基本信息
        if(courseBaseOptional.isPresent()){
            CourseBase courseBase = courseBaseOptional.get();
            courseView.setCourseBase(courseBase);
        }
        //查询课程图片
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(id);

        if(coursePicOptional.isPresent()){
            CoursePic coursePic = coursePicOptional.get();
            courseView.setCoursePic(coursePic);
        }
        //课程营销信息
        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(id);
        if(marketOptional.isPresent()){
            CourseMarket courseMarket = marketOptional.get();
            courseView.setCourseMarket(courseMarket);
        }

        //课程计划信息
        TeachplanNode teachplanNodes = teachplanMapper.selectList(id);
        courseView.setTeachplanNode(teachplanNodes);

        return courseView;
    }



    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    //根据id查询课程基本信息
    public CourseBase findCourseBaseById(String courseId){
        Optional<CourseBase> baseOptional = courseBaseRepository.findById(courseId);
        if(baseOptional.isPresent()){
            CourseBase courseBase = baseOptional.get();
            return courseBase;
        }
        ExceptionCast.cast(CourseCode.COURSE_GET_NOTEXISTS);
        return null;
    }
    //课程预览
    public CoursePublishResult preview(String id) {
        CourseBase one = this.findCourseBaseById(id);
        //发布课程预览页面
        CmsPage cmsPage = new CmsPage();
        //站点
        cmsPage.setSiteId(publish_siteId);//课程预览站点
        //模板
        cmsPage.setTemplateId(publish_templateId);
        //页面名称
        cmsPage.setPageName(id+".html");
        //页面别名
        cmsPage.setPageAliase(one.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre+id);
        //远程请求cms保存页面信息
        CmsPageResult cmsPageResult = cmsPageClient.saveCmsPage(cmsPage);
        if(!cmsPageResult.isSuccess()){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }
        //页面id
        String pageId = cmsPageResult.getCmsPage().getPageId();
        //页面url
        String pageUrl=previewUrl+pageId;
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }
   //课程发布
    @Transactional
    public CoursePublishResult publish(String id) {
        //查询课程
        CourseBase one = this.findCourseBaseById(id);
        //准备信息
        CmsPage cmsPage = new CmsPage();
        //站点
        cmsPage.setSiteId(publish_siteId);//课程预览站点
        //模板
        cmsPage.setTemplateId(publish_templateId);
        //页面名称
        cmsPage.setPageName(id+".html");
        //页面别名
        cmsPage.setPageAliase(one.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre+id);
        //调用cms一建发布接口将页面详细信息发送到服务器上
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        if(!cmsPostPageResult.isSuccess()){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }
        //调用savaCoursepubStarte更新状态为已发布
        CourseBase courseBase = this.savaCoursepubStarte(id);
        if(courseBase==null){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }
        //创建coursePub对象
        CoursePub pub = this.createPub(id);
        //把CoursePub保存到mysql
        saveCoursePub(id,pub);

        String pageUrl = cmsPostPageResult.getPageUrl();
        //向teachplanMediaPub保存媒资信息
        saveTeachplanMediaPub(id);
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }
    //向teachplanMediaPub保存媒资信息
    private void saveTeachplanMediaPub(String id){
        //删除
        teachplanMediaPubRepository.deleteByCourseId(id);
        //查询媒资信息
        List<TeachplanMedia> byCourseId = teachplanMediaRepository.findByCourseId(id);
        List<TeachplanMediaPub> teachplanMediaPubs = new ArrayList<>();
        for (TeachplanMedia teachplanMedia : byCourseId) {
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            //因为属性相同所以把teachplanMedia直接拷贝到teachplanMediaPub
            BeanUtils.copyProperties(teachplanMedia,teachplanMediaPub);
            //添加时间截
            teachplanMediaPub.setTimestamp(new Date());
            teachplanMediaPubs.add(teachplanMediaPub);
        }
    }
    //添加createPub
    private CoursePub saveCoursePub(String id,CoursePub coursePub){
        CoursePub coursePubNew=null;
        Optional<CoursePub> optional = coursePubRepository.findById(id);
        if(optional.isPresent()){
            coursePubNew = optional.get();
        }else{
            coursePubNew=new CoursePub();
        }
        //将coursePubNew拷贝到coursePub
        BeanUtils.copyProperties(coursePub,coursePubNew);
        coursePubNew.setId(id);
        //时间截点 用于logstach使用
        coursePubNew.setTimestamp(new Date());
        //发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        coursePubNew.setPubTime(format);
        coursePubRepository.save(coursePubNew);
        return coursePubNew;
    }

    //创建coursepub对象
    private CoursePub createPub(String id){
        CoursePub coursePub = new CoursePub();
        //查询CourseBase数据
        Optional<CourseBase> courseBase = courseBaseRepository.findById(id);
        if(courseBase.isPresent()){
            CourseBase courseBase1 = courseBase.get();
            //courseBase1拷贝到coursePub
            BeanUtils.copyProperties(courseBase1,coursePub);
        }
        //查询CoursePic数据
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(id);
        if(coursePicOptional.isPresent()){
            CoursePic coursePic = coursePicOptional.get();
            //coursePic拷贝到coursePub
            BeanUtils.copyProperties(coursePic,coursePub);
        }
        //查询CourseMarket数据
        Optional<CourseMarket> MarketOptional = courseMarketRepository.findById(id);
        if(MarketOptional.isPresent()){
            CourseMarket courseMarket = MarketOptional.get();
            //courseMarket拷贝到coursePub
            BeanUtils.copyProperties(courseMarket,coursePub);
        }
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        String jsonString = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(jsonString);
        return coursePub;
    }
    //更改课程状态为已发布 状态号202002
    public CourseBase savaCoursepubStarte(String id){
        CourseBase courseBaseById = this.findCourseBaseById(id);
        courseBaseById.setStatus("202002");
        courseBaseRepository.save(courseBaseById);
        return courseBaseById;
    }


    public ResponseResult savemedia(TeachplanMedia teachplanMedia) {

        //课程计划id
        String teachplanId = teachplanMedia.getTeachplanId();
        //查询课程信息
        Optional<Teachplan> optional = teachplanRepository.findById(teachplanId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //查到课程计划
        Teachplan teachplan = optional.get();
        //取出级别
        String grade = teachplan.getGrade();
        if (StringUtils.isEmpty(grade)||!grade.equals("3")){
            //只允许3级的关联视频
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_GRADEERROR);
        }
         //查询
        Optional<TeachplanMedia> optional1 = teachplanMediaRepository.findById(teachplanId);
        TeachplanMedia one=null;
        if(!optional1.isPresent()){
            one = new TeachplanMedia();
        }else{
            one = optional1.get();
        }
        one.setCourseId(teachplan.getCourseid());//课程id
        one.setMediaId(teachplanMedia.getMediaId());//媒资id
        one.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());//媒资文件的原始名称
        one.setMediaUrl(teachplanMedia.getMediaUrl());//媒资url
        one.setTeachplanId(teachplanId);

        teachplanMediaRepository.save(one);//添加媒资文件和课程id
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
