package cn.tinet.operationplatformservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * REDIS工具类 <br>
 *
 * @param <V> Value类型
 * @Time : 2021/3/31 22:29
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : RedisUtil.java
 * @Software: IntelliJ IDEA
 **/
@Component
public class RedisUtil<V> {
    
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * redisTemplate
     */
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, V> redisTemplate;

    /**
     * valueOperations
     */
    @Resource(name = "redisTemplate")
    private ValueOperations<String, V> valueOperations;

    /**
     * hashOperations
     */
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, V> hashOperations;

    /**
     * listOperations
     */
    @Resource(name = "redisTemplate")
    private ListOperations<String, V> listOperations;

    /**
     * setOperations
     */
    @Resource(name = "redisTemplate")
    private SetOperations<String, V> setOperations;

    /****************  Key操作 Begin  ******************/

    /**
     * 删除指定键 <br>
     *
     * @param key <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个键 <br>
     *
     * @param keys <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void delete(String... keys) {
        redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 删除多个键 <br>
     *
     * @param keys <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 检查指定键是否存在 <br>
     *
     * @param key <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取所有满足匹配模式的键 <br>
     *
     * @param pattern <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除所有满足匹配模式的键 <br>
     *
     * @param pattern <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void keysDel(String pattern) {
        Set<String> keys = keys(pattern);
        delete(keys);
    }

    /**
     * 设置Key的生存时限 <br>
     *
     * @param key     <br>
     * @param timeout <br>
     * @param unit    <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Boolean expire(String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置Key的生存时限 <br>
     *
     * @param key  <br>
     * @param date <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Boolean expireAt(String key, final Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 查询Key的生存时限 <br>
     *
     * @param key <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 查询Key的生存时限 <br>
     *
     * @param key      <br>
     * @param timeUnit <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Long getExpire(String key, final TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /************ Key操作 Begin  ************************/

    /************ String类型操作 Begin  ******************/

    /**
     * 根据 key 获取对应的value 如果key不存在则返回null <br>
     *
     * @param key <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public V get(String key) {
        return valueOperations.get(key);
    }

    /**
     * 设置 key 的值为 value 如果key不存在添加key 保存值为value 如果key存在则对value进行覆盖
     *
     * @param key   <br>
     * @param value <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void set(String key, V value) {
        valueOperations.set(key, value);
    }

    /**
     * 根据提供的key集合按顺序获取对应的value值 <br>
     *
     * @param keys 集合不能为null 可以为空集合
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public List<V> mget(Collection<String> keys) {
        return valueOperations.multiGet(keys);
    }

    /**
     * 把一个map的键值对添加到REDIS中,map的key-value对应REDIS的key-value,如果key已经存在就覆盖 <br>
     *
     * @param map 不能为null,为null抛出空指针异常,可以为空集合 <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void mset(Map<? extends String, ? extends V> map) {
        valueOperations.multiSet(map);
    }

    /**
     * 如果key不存在,则设置key的值为value.存在则不设置,设置成功返回true,失败返回false <br>
     *
     * @param key   <br>
     * @param value <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Boolean setnx(String key, V value) {
        return valueOperations.setIfAbsent(key, value);
    }

    /**
     * 批量插入键值对,如果key不存在,则设置key的值为value.存在则不设置,设置成功返回true,失败返回false
     *
     * @param map
     * @return
     */
    public Boolean msetnx(Map<? extends String, ? extends V> map) {
        return valueOperations.multiSetIfAbsent(map);
    }

    /**
     * 设置key的值为value 并返回旧值。 如果key不存在返回为null <br>
     *
     * @param key   <br>
     * @param value <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public V getSet(String key, V value) {
        return valueOperations.getAndSet(key, value);
    }

    /************ String类型操作 End  ******************/

    /************ Hash类型操作 Begin  ******************/

    public Long hlen(String key) {
        return hashOperations.size(key);
    }

    /**
     * 获取hash表指定域的值 <br>
     *
     * @param key   <br>
     * @param field <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public V hget(String key, String field) {
        return hashOperations.get(key, field);
    }

    /**
     * 设置指定hash表的域值对 <br>
     *
     * @param key   <br>
     * @param field <br>
     * @param value <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void hset(String key, String field, V value) {
        hashOperations.put(key, field, value);
    }

    /**
     * 如果key和filed不存在,则设置值为value.存在则不设置,设置成功返回true,失败返回false <br>
     *
     * @param key   <br>
     * @param field <br>
     * @param value <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Boolean hsetnx(String key, String field, V value) {
        return hashOperations.putIfAbsent(key, field, value);
    }

    /**
     * 设置指定hash表的多个域值对 <br>
     *
     * @param key <br>
     * @param m   <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void hmset(String key, Map<String, ? extends V> m) {
        hashOperations.putAll(key, m);
    }

    /**
     * 获取hash表多个指定域的值 <br>
     *
     * @param key      <br>
     * @param hashKeys <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public List<V> hmget(String key, Collection<String> hashKeys) {
        return hashOperations.multiGet(key, hashKeys);
    }

    /**
     * 判断key-filed是否存在 <br>
     *
     * @param key   <br>
     * @param field <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public boolean hexist(String key, String field) {
        return hashOperations.hasKey(key, field);
    }

    /**
     * 获取hash表多个指定域的值 <br>
     *
     * @param key      <br>
     * @param hashKeys <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Long hmdel(String key, Object... hashKeys) {
        return hashOperations.delete(key, hashKeys);
    }

    public Cursor<Map.Entry<String, V>> hscan(String key) {
        return hashOperations.scan(key, ScanOptions.NONE);
    }

    public Cursor<Map.Entry<String, V>> hscan(String key, ScanOptions options) {
        return hashOperations.scan(key, options);
    }

    public Cursor<Map.Entry<String, V>> hscan(String key, Long count, String pattern) {
        ScanOptions options = ScanOptions.scanOptions().count(count).match(pattern).build();
        return hashOperations.scan(key, options);
    }

    public Stream<Map.Entry<String, V>> hscanToStream(String key, ScanOptions options) {
        Cursor<Map.Entry<String, V>> cursor = hashOperations.scan(key, options);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(cursor, Spliterator.DISTINCT), false)
                .onClose(() -> {
                    try {
                        cursor.close();
                    } catch (IOException e) {
                        logger.error("close cursor error.", e);
                    }
                });
    }

    public Stream<Map.Entry<String, V>> hscanToStream(String key, Long count, String pattern) {
        ScanOptions scanOptions = ScanOptions.scanOptions().count(count).match(pattern).build();
        Cursor<Map.Entry<String, V>> cursor = hashOperations.scan(key, scanOptions);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(cursor, Spliterator.DISTINCT), false)
                .onClose(() -> {
                    try {
                        cursor.close();
                    } catch (IOException e) {
                        logger.error("close cursor error.", e);
                    }
                });
    }

    /************ Hash类型操作 End  ******************/

    /************ List类型操作 Begin  ******************/

    /**
     * 由链表左侧插入数据 <br>
     *
     * @param key   <br>
     * @param value <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void lpush(String key, V value) {
        listOperations.leftPush(key, value);
    }

    /**
     * 由链表左侧插入数据 <br>
     *
     * @param key    <br>
     * @param values <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void lpush(String key, @SuppressWarnings("unchecked") V... values) {
        listOperations.leftPushAll(key, values);
    }

    /**
     * 由链表左侧插入数据 <br>
     *
     * @param key    <br>
     * @param values <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public void lpush(String key, Collection<V> values) {
        listOperations.leftPushAll(key, values);
    }

    /**
     * 返回存储在 key 的列表里指定范围内的元素 <br>
     * list的第一个元素下标是0（list的表头），第二个元素下标是1，以此类推
     * start和end下表的元素均包含在内
     *
     * @param key   <br>
     * @param start <br>
     * @param end   <br>
     * @return <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public List<V> range(String key, long start, long end) {
        return listOperations.range(key, start, end);
    }

    /************ List类型操作 End  ******************/

    /************ Set类型操作 Begin  ******************/

    /**
     * 将一个或多个member元素加入到集合key当中，已经存在于集合的member元素将被忽略
     *
     * @param key   <br>
     * @param value <br>
     * @return Long <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Long sadd(String key, @SuppressWarnings("unchecked") V... value) {
        return setOperations.add(key, value);
    }

    /**
     * 返回集合key中的所有成员
     *
     * @param key <br>
     * @return Set<V> <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Set<V> smembers(String key) {
        return setOperations.members(key);
    }

    /**
     * 移除集合key中的一个或多个member元素，不存在的member元素会被忽略
     *
     * @param key   <br>
     * @param value <br>
     * @return Long <br>
     * @author zhao.zhuang<br>
     * @taskId <br>
     */
    public Long srem(String key, @SuppressWarnings("unchecked") V... value) {
        return setOperations.remove(key, value);
    }

    /************ Set类型操作 End  ******************/

    /**
     * KEY格式常量定义 <br>
     *
     * @author zhao.zhuang<br>
     * @version 1.0<br>
     * @taskId <br>
     * @CreateDate 2018年4月4日 <br>
     * @since V9.0<br>
     */
    public static class RedisKeyDef {

        /**
         * 登录成功 -- OP:SYSTEMUSER_LOGIN_SUCCESS:{TOKEN}
         */
        public static final String SYSTEMUSER_LOGIN_SUCCESS = "OP:SYSTEMUSER_LOGIN_SUCCESS:{0}";

        /**
         * 登录失败 -- OP:SYSTEMUSER_LOGIN_FAILED:{USER_NAME}
         */
        public static final String SYSTEMUSER_LOGIN_FAILED = "OP:SYSTEMUSER_LOGIN_FAILED:{0}";

    }

}
