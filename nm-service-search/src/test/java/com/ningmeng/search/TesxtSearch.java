package com.ningmeng.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName: TesxtEs
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TesxtSearch {
    @Autowired
    RestHighLevelClient client;
    @Autowired
    RestClient restClient;
  //搜索全部记录
    @Test
    public void searchget() throws IOException, ParseException {
        //搜索请求对像
        SearchRequest searchRequest= new SearchRequest("nm_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //matchAllQuery()搜索全部
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //设置源字段的过滤第一个参数结果集包括那些字段第二参数表示结果集包括那些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索向Es发起http请求
        SearchResponse search = client.search(searchRequest);
        //搜索结果
        SearchHits hits = search.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            //文档的主键
            String id = documentFields.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            //获取源文档的name
            String name = (String) sourceAsMap.get("name");
            //由于前边设置了源文档字段过滤这时description是取不到的
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Double price = (Double) sourceAsMap.get("price");
            Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp")) ;
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }

    }

    //分页查询
    @Test
    public void searchfy() throws IOException, ParseException {
        //搜索请求对像
        SearchRequest searchRequest= new SearchRequest("nm_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置分页参数
        //页码
        int page=1;
        //每页记录数
        int size =1;
        //计算出记录起始下标
        int from =(page-1)*size;
        searchSourceBuilder.from(from);//起始记录下标从0开始
        searchSourceBuilder.size(size);//每页显示的记录数
        //搜索方式
        //matchAllQuery()搜索全部
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //设置源字段的过滤第一个参数结果集包括那些字段第二参数表示结果集包括那些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索向Es发起http请求
        SearchResponse search = client.search(searchRequest);
        //搜索结果
        SearchHits hits = search.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            //文档的主键
            String id = documentFields.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            //获取源文档的name
            String name = (String) sourceAsMap.get("name");
            //由于前边设置了源文档字段过滤这时description是取不到的
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Double price = (Double) sourceAsMap.get("price");
            Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp")) ;
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }

    }
    //分页精确查询
    @Test
    public void searchjq() throws IOException, ParseException {
        //搜索请求对像
        SearchRequest searchRequest= new SearchRequest("nm_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置分页参数
        //页码
        int page=1;
        //每页记录数
        int size =1;
        //计算出记录起始下标
        int from =(page-1)*size;
        searchSourceBuilder.from(from);//起始记录下标从0开始
        searchSourceBuilder.size(size);//每页显示的记录数
        //搜索方式
        //termQuery精确查询
        searchSourceBuilder.query(QueryBuilders.termQuery("name","spring"));
        //设置源字段的过滤第一个参数结果集包括那些字段第二参数表示结果集包括那些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索向Es发起http请求
        SearchResponse search = client.search(searchRequest);
        //搜索结果
        SearchHits hits = search.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            //文档的主键
            String id = documentFields.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            //获取源文档的name
            String name = (String) sourceAsMap.get("name");
            //由于前边设置了源文档字段过滤这时description是取不到的
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Double price = (Double) sourceAsMap.get("price");
            Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp")) ;
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }

    }
    //分页id查询
    @Test
    public void searchid() throws IOException, ParseException {
        //搜索请求对像
        SearchRequest searchRequest= new SearchRequest("nm_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置分页参数
        //页码
        int page=1;
        //每页记录数
        int size =1;
        //计算出记录起始下标
        int from =(page-1)*size;
        searchSourceBuilder.from(from);//起始记录下标从0开始
        searchSourceBuilder.size(size);//每页显示的记录数
        //搜索方式
        //termQueryid查询
       String[] ids= new String[]{"1","2"};
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id",ids));
        //设置源字段的过滤第一个参数结果集包括那些字段第二参数表示结果集包括那些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索向Es发起http请求
        SearchResponse search = client.search(searchRequest);
        //搜索结果
        SearchHits hits = search.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            //文档的主键
            String id = documentFields.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            //获取源文档的name
            String name = (String) sourceAsMap.get("name");
            //由于前边设置了源文档字段过滤这时description是取不到的
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Double price = (Double) sourceAsMap.get("price");
            Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp")) ;
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }

    }
    //分页分词查询
    @Test
    public void searchfenci() throws IOException, ParseException {
        //搜索请求对像
        SearchRequest searchRequest= new SearchRequest("nm_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置分页参数
        //页码
        int page=1;
        //每页记录数
        int size =1;
        //计算出记录起始下标
        int from =(page-1)*size;
        searchSourceBuilder.from(from);//起始记录下标从0开始
        searchSourceBuilder.size(size);//每页显示的记录数
        //搜索方式
        //termQueryid查询
        searchSourceBuilder.query(QueryBuilders.matchQuery("name","spring开发基础").minimumShouldMatch("80%"));
        //设置源字段的过滤第一个参数结果集包括那些字段第二参数表示结果集包括那些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索向Es发起http请求
        SearchResponse search = client.search(searchRequest);
        //搜索结果
        SearchHits hits = search.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            //文档的主键
            String id = documentFields.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            //获取源文档的name
            String name = (String) sourceAsMap.get("name");
            //由于前边设置了源文档字段过滤这时description是取不到的
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Double price = (Double) sourceAsMap.get("price");
            Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp")) ;
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }

    }
    //multiMatchQuery查询
    @Test
    public void searchmulti() throws IOException, ParseException {
        //搜索请求对像
        SearchRequest searchRequest= new SearchRequest("nm_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置分页参数
        //页码
        int page=1;
        //每页记录数
        int size =1;
        //计算出记录起始下标
        int from =(page-1)*size;
        searchSourceBuilder.from(from);//起始记录下标从0开始
        searchSourceBuilder.size(size);//每页显示的记录数
        //搜索方式
        //termQueryid查询
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("spring css","name","description")
                .minimumShouldMatch("50%")
                .field("name",10));
        //设置源字段的过滤第一个参数结果集包括那些字段第二参数表示结果集包括那些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索向Es发起http请求
        SearchResponse search = client.search(searchRequest);
        //搜索结果
        SearchHits hits = search.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            //文档的主键
            String id = documentFields.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            //获取源文档的name
            String name = (String) sourceAsMap.get("name");
            //由于前边设置了源文档字段过滤这时description是取不到的
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Double price = (Double) sourceAsMap.get("price");
            Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp")) ;
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }

    }
    //double组合查询
    @Test
    public void search() throws IOException, ParseException {
        //搜索请求对像
        SearchRequest searchRequest= new SearchRequest("nm_course");
        //指定类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置分页参数
        //页码
        int page=1;
        //每页记录数
        int size =1;
        //计算出记录起始下标
        int from =(page-1)*size;
        searchSourceBuilder.from(from);//起始记录下标从0开始
        searchSourceBuilder.size(size);//每页显示的记录数
        //搜索方式
        //termQueryid查询
        MultiMatchQueryBuilder field = QueryBuilders.multiMatchQuery("spring css", "name", "description")
                .minimumShouldMatch("50%")
                .field("name", 10);
        TermQueryBuilder studymodel1 = QueryBuilders.termQuery("studymodel", "201001");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(field);
        boolQueryBuilder.must(studymodel1);
        //设置源字段的过滤第一个参数结果集包括那些字段第二参数表示结果集包括那些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索向Es发起http请求
        SearchResponse search = client.search(searchRequest);
        //搜索结果
        SearchHits hits = search.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度高的文档
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            //文档的主键
            String id = documentFields.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            //获取源文档的name
            String name = (String) sourceAsMap.get("name");
            //由于前边设置了源文档字段过滤这时description是取不到的
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Double price = (Double) sourceAsMap.get("price");
            Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp")) ;
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }

    }
}
