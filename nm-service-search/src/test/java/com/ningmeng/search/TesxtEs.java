package com.ningmeng.search;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TesxtEs
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TesxtEs {
    @Autowired
    RestHighLevelClient client;
    @Autowired
    RestClient restClient;

    @Test
    public void testAdd() throws IOException {
        HashMap<String,Object> jsonmap = new HashMap<>();
        jsonmap.put("name","Bootstrap开发框架");
        jsonmap.put("description","Bootstrap是由Twitter推出的一个前台页面开发框架，在行业之中使用较为广泛。此开发框架包含了大量的CSS、JS程序代码，可以帮助开发者" +
                "（尤其是不擅长页面开发的程序人员）轻松的实现一个不受浏览器限制的精美界面效果。");
        jsonmap.put("studymodel","201002");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonmap.put("timestamp",format.format(new Date()));
        jsonmap.put("price",5.6f);
        //创建索引创建对象
        IndexRequest indexRequest = new IndexRequest("nm_course", "doc");
        //文档内容
        indexRequest.source(jsonmap);
        //通过client进行http响应
        IndexResponse index = client.index(indexRequest);
        String index1 = index.getIndex();
        System.out.println(index1);

    }
    //查询
    @Test
    public void testget() throws IOException {
        //查询请求对象
        GetRequest getRequest = new GetRequest("nm_course","doc","BoY24HIB4q_72otpNYAl");
        //根据client响应http
        GetResponse documentFields = client.get(getRequest);
        //查询es里的数据
        Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
        System.out.println(sourceAsMap);
    }
    //修改
    @Test
    public void testupdate() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("nm_course","doc","BoY24HIB4q_72otpNYAl");
        HashMap<String,String> map = new HashMap<>();
        map.put("name","Cloud_javaBooe");
        updateRequest.doc(map);
        UpdateResponse update = client.update(updateRequest);
        RestStatus status = update.status();
        System.out.println(status);
    }
    //删除
    @Test
    public void textdelete() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("nm_course", "doc", "BoY24HIB4q_72otpNYAl");
        DeleteResponse delete = client.delete(deleteRequest);
        DocWriteResponse.Result result = delete.getResult();
        System.out.println(result);
    }
}
