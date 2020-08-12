package com.atguigu.app;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: HunterP
 * @Date: 2020/8/11 20:32
 * @DESC:
 */
public class JedisPoolTest {

    public static void main(String[] args) {

        //1.创建连接池对象
        JedisPool jedisPool = new JedisPool("hadoop102",6379);

        //2.从池子中获取一个连接
        Jedis jedis = jedisPool.getResource();

        //3.测试
        System.out.println(jedis.ping());

        //4.关闭连接池
        jedisPool.close();
    }
}
