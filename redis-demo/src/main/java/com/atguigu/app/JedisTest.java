package com.atguigu.app;

import redis.clients.jedis.Jedis;

/**
 * @Author: HunterP
 * @Date: 2020/8/11 20:22
 * @DESC:
 */
public class JedisTest {

    public static void main(String[] args) {

        //1.获取客户端对象
        Jedis jedis = new Jedis("hadoop102",6379);

        //2.测试
        String ping = jedis.ping();
        System.out.println(ping);

        //String
        jedis.set("aa","傻狍子");
        jedis.get("aa");
        jedis.mset("bb","丽江","cc","拉链","dd","恶狼");
        jedis.mget("bb","cc","dd");

        //List
        jedis.lpush("1","111","222","333");
        jedis.rpush("1","444","555","666");
        jedis.lrange("1",0,-1);
        jedis.lpop("1");
        jedis.rpop("1");

        //Set
        jedis.sadd("22","qq","ww","ee","rr","tt");
        jedis.smembers("22");
        jedis.sismember("22","vv");




        //3.关闭连接
        jedis.close();
    }
}
