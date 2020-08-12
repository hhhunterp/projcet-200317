package com.atguigu.writer;


import com.atguigu.bean.Movie;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

import java.io.IOException;

/**
 * @Author: HunterP
 * @Date: 2020/8/10 9:47
 * @DESC: 批量插入
 */
public class EsWriterBulk {

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

        //4.创建Bulk对象
        Movie movie1 = new Movie("1005", "霍比特人");
        Movie movie2 = new Movie("1006", "霸王别姬");
        Movie movie3 = new Movie("1007", "阳光灿烂的日子");

        Index index1 = new Index.Builder(movie1).id("1005").build();
        Index index2 = new Index.Builder(movie2).id("1006").build();
        Index index3 = new Index.Builder(movie3).id("1007").build();

        Bulk bulk = new Bulk.Builder()
                .defaultIndex("movie_test1")
                .defaultType("_doc")
                .addAction(index1)
                .addAction(index2)
                .addAction(index3)
                .build();

        //5.执行写入数据的操作
        jestClient.execute(bulk);

        //6.释放资源
        jestClient.shutdownClient();
    }
}
