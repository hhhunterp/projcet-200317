package com.atguigu.reader;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EsReaderBySourceBuilder {

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

        //4.构建查询语句
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //4.1 添加查询条件
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("sex", "male");
        boolQueryBuilder.filter(termQueryBuilder);

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("favo", "瑜伽球");
        boolQueryBuilder.must(matchQueryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);

        //4.2 添加聚合组
        TermsAggregationBuilder builder = AggregationBuilders.terms("groupByClass")
                .field("class_id")
                .size(10);
        searchSourceBuilder.aggregation(builder);

        //创建Search对象
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex("student")
                .addType("_doc")
                .build();

        SearchResult searchResult = jestClient.execute(search);

        //5.解析searchResult
        //5.1 获取查询总数
        Long total = searchResult.getTotal();
        System.out.println("查询命中:" + total + "条！");

        //5.2 获取查询的数据明细
        List<SearchResult.Hit<Map, Void>> hits = searchResult.getHits(Map.class);
        for (SearchResult.Hit<Map, Void> hit : hits) {
            Map source = hit.source;
            System.out.println("**************************");
            for (Object key : source.keySet()) {
                System.out.println("Key:" + key + ",Value:" + source.get(key));
            }
        }

        //5.3 获取查询的聚合组
        System.out.println("==========================");
        MetricAggregation aggregations = searchResult.getAggregations();
        TermsAggregation groupByClass = aggregations.getTermsAggregation("groupByClass");
        List<TermsAggregation.Entry> buckets = groupByClass.getBuckets();
        for (TermsAggregation.Entry bucket : buckets) {
            System.out.println("Key:" + bucket.getKey() + ",Value:" + bucket.getCount());
        }

        //6.释放资源
        jestClient.shutdownClient();

    }
}
