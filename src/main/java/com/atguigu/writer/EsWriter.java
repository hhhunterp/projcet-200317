package com.atguigu.writer;

import com.atguigu.bean.Movie2;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;

import java.io.IOException;

/**
 * @Author: HunterP
 * @Date: 2020/8/10 9:46
 * @DESC: 单条插入数据
 */
public class EsWriter {

    public static void main(String[] args) throws IOException {

        //1.创建客户端工厂对象
        JestClientFactory jestClientFactory = new JestClientFactory();


        //2.设置连接参数
        HttpClientConfig httpClientConfig = new HttpClientConfig
                .Builder("http://hadoop102:9200")
                .build();
        jestClientFactory.setHttpClientConfig(httpClientConfig);
        //3.获取客户端对象
        JestClient jestClient = jestClientFactory.getObject();

        //4.创建Index对象
        Movie2 movie2 = new Movie2("1004", "教父");
        Index index = new Index.Builder(movie2)
                .index("movie_test1")
                .type("_doc")
                .id("1004")
                .build();
        /*
        Index index = new Index.Builder("{\n" +
                "  \"id\":\"002\",\n" +
                "  \"movie_name\":\"姜子牙\"\n" +
                "}")
                .index("movie_test1")
                .type("_doc")
                .id("1002")
                .build();
        */

        //5.写入数据操作
        jestClient.execute(index);

        //6.关闭资源
        jestClient.shutdownClient();

/*        //1.创建客户端工厂对象
        JestClientFactory jestClientFactory = new JestClientFactory();

        //2.设置连接参数
        HttpClientConfig httpClientConfig = new HttpClientConfig
                .Builder("http://hadoop102:9200")
                .build();
        jestClientFactory.setHttpClientConfig(httpClientConfig);

        //3.获取客户端对象
        JestClient jestClient = jestClientFactory.getObject();*/
    }
}
