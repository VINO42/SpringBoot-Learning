package io.github.vino42.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Range;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService<V> {

    @Autowired
    @Lazy
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // - - - - - - - - - - - - - - - - - - - - -  公共方法 - - - - - - - - - - - - - - - - - - - -

    /**
     * 给一个指定的 key 值附加过期时间 时间单元是秒
     *
     * <p>key time
     */
    public boolean expire(String key, long time) {

        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public boolean expire(String key, long time, TimeUnit timeUnit) {

        return redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 根据key 获取过期时间
     *
     * <p>key
     */
    public long getTime(String key) {

        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     *
     * <p>key
     */
    public boolean hasKey(String key) {

        return redisTemplate.hasKey(key);
    }

    /**
     * 移除指定key 的过期时间
     *
     * <p>key
     */
    public boolean persist(String key) {

        return redisTemplate.boundValueOps(key).persist();
    }

    /**
     * 移除指定key 的过期时间
     *
     * <p>key
     */
    public boolean del(String key) {

        return redisTemplate.delete(key);
    }
    // - - - - - - - - - - - - - - - - - - - - -  String类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 根据key获取值
     *
     * <p>key 键 值
     */
    public Object get(String key) {

        return StringUtils.isBlank(key) ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key获取值
     *
     * <p>key 键 值
     */
    public String getString(String key) {

        return StringUtils.isBlank(key) ? null : stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 将值放入缓存
     *
     * <p>key 键 value 值 true成功 false 失败
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将str值放入缓存
     * `
     * <p>key 键 value 值 true成功 false 失败
     */
    public void setString(String key, String value) {

        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将值放入缓存并设置时间
     *
     * <p>key 键 value 值 time 时间(秒) -1为无期限 true成功 false 失败
     */
    public void set(String key, Object value, long time) {

        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 将值放入缓存并设置时间
     *
     * <p>key 键 value 值 time 时间(秒) -1为无期限 true成功 false 失败
     */
    public void setString(String key, String value, long time) {

        if (time > 0) {
            stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 将值放入缓存并设置时间
     *
     * <p>key 键 value 值 time 时间(秒) -1为无期限 true成功 false 失败
     */
    public void set(String key, Object value, long time, TimeUnit timeUnit) {

        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 将值放入缓存并设置时间
     *
     * <p>key 键 value 值 time 时间(秒) -1为无期限 true成功 false 失败
     */
    public void setString(String key, String value, long time, TimeUnit timeUnit) {

        if (time > 0) {
            stringRedisTemplate.opsForValue().set(key, value, time, timeUnit);
        } else {
            stringRedisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 批量添加 key (重复的键会覆盖)
     *
     * <p>keyAndValue
     */
    public void batchSet(Map<String, Object> keyAndValue) {

        redisTemplate.opsForValue().multiSet(keyAndValue);
    }

    /**
     * 批量添加 key-value 只有在键不存在时,才添加 map 中只要有一个key存在,则全部不添加
     *
     * <p>keyAndValue
     */
    public void batchSetIfAbsent(Map<String, Object> keyAndValue) {

        redisTemplate.opsForValue().multiSetIfAbsent(keyAndValue);
    }

    /**
     * 对一个 key-value 的值进行加减操作, 如果该 key 不存在 将创建一个key 并赋值该 number 如果 key 存在,但 value 不是长整型 ,将报错
     *
     * <p>key number
     */
    public Long increment(String key, long number) {

        return redisTemplate.opsForValue().increment(key, number);
    }

    /**
     * 对一个 key-value 的值进行加减操作, 如果该 key 不存在 将创建一个key 并赋值该 number 如果 key 存在,但 value 不是 纯数字 ,将报错
     *
     * <p>key number
     */
    public Double increment(String key, double number) {

        return redisTemplate.opsForValue().increment(key, number);
    }

    // - - - - - - - - - - - - - - - - - - - - -  set类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 将数据放入set缓存
     *
     * <p>key 键
     */
    public void setAdd(String key, Object value) {

        redisTemplate.opsForSet().add(key, value);
    }


    /**
     * 获取变量中的值
     *
     * <p>key 键
     */
    public Set<Object> members(String key) {

        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取变量中指定个数的元素
     *
     * <p>key 键 count 值
     *
     * @return
     */
    public List<Object> randomMembers(String key, long count) {

        List<Object> classes = redisTemplate.opsForSet().randomMembers(key, count);
        return classes;
    }

    /**
     * 随机获取变量中的元素
     *
     * <p>key 键
     */
    public Object randomMember(String key) {

        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 弹出变量中的元素
     *
     * <p>key 键
     */
    public Object pop(String key) {

        return redisTemplate.opsForSet().pop("setValue");
    }

    /**
     * 获取变量中值的长度
     *
     * <p>key 键
     */
    public long size(String key) {

        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * <p>key 键 value 值 true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {

        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 检查给定的元素是否在变量中。
     *
     * <p>key 键 obj 元素对象
     */
    public boolean isMember(String key, Object obj) {

        return redisTemplate.opsForSet().isMember(key, obj);
    }

    /**
     * 转移变量的元素值到目的变量。
     *
     * <p>key 键 value 元素对象 destKey 元素对象
     */
    public boolean move(String key, Object value, String destKey) {

        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 批量移除set缓存中元素
     *
     * <p>key 键 values 值
     */
    public void remove(String key, List<Object> values) {

        redisTemplate.opsForSet().remove(key, values.toArray());
    }

    /**
     * 通过给定的key求2个set变量的差值
     *
     * <p>key 键 destKey 键
     */
    public Set<Object> difference(String key, String destKey) {

        return redisTemplate.opsForSet().difference(key, destKey);
    }

    // - - - - - - - - - - - - - - - - - - - - -  hash类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 加入缓存
     *
     * <p>key 键 map 键
     */
    public void add(String key, Map<String, String> map) {

        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取 key 下的 所有 hashkey 和 value
     *
     * <p>key 键
     */
    public Map<Object, Object> getHashEntries(String key) {

        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 验证指定 key 下 有没有指定的 hashkey
     *
     * <p>key hashKey
     */
    public boolean hashKey(String key, String hashKey) {

        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取指定key的值string
     *
     * <p>key 键 key2 键
     */
    public String getMapString(String key, String key2) {

        return redisTemplate.opsForHash().get("map1", "key1").toString();
    }

    /**
     * 获取指定的值Int
     *
     * <p>key 键 key2 键
     */
    public Integer getMapInt(String key, String key2) {

        return (Integer) redisTemplate.opsForHash().get("map1", "key1");
    }

    /**
     * 弹出元素并删除
     *
     * <p>key 键
     */
    public String popValue(String key) {

        return redisTemplate.opsForSet().pop(key).toString();
    }

    /**
     * 删除指定 hash 的 HashKey
     *
     * <p>key hashKeys 删除成功的 数量
     */
    public Long delete(String key, List<String> hashKeys) {

        return redisTemplate.opsForHash().delete(key, hashKeys.toString());
    }

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     * <p>key hashKey number
     */
    public Long increment(String key, String hashKey, long number) {

        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     * <p>key hashKey number
     */
    public Double increment(String key, String hashKey, Double number) {

        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 获取 key 下的 所有 hashkey 字段
     *
     * <p>key
     */
    public Set<Object> hashKeys(String key) {

        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取指定 hash 下面的 键值对 数量
     *
     * <p>key
     */
    public Long hashSize(String key) {

        return redisTemplate.opsForHash().size(key);
    }

    // - - - - - - - - - - - - - - - - - - - - -  list类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 在变量左边添加元素值
     *
     * <p>key value
     */
    public void leftPush(String key, Object value) {

        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取集合指定位置的值。
     *
     * <p>key index
     */
    public Object index(String key, long index) {

        return redisTemplate.opsForList().index("list", 1);
    }

    /**
     * 获取指定区间的值。
     *
     * <p>key start end
     */
    public List<Object> range(String key, long start, long end) {

        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 把最后一个参数值放到指定集合的第一个出现中间参数的前面， 如果中间参数值存在的话。
     *
     * <p>key pivot value
     */
    public void leftPush(String key, Object pivot, Object value) {

        redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 向左边批量添加参数元素。
     *
     * <p>key values
     *
     * @return
     */
    public Long leftPushAll(String key, List<Object> values) {
        //        redisTemplate.opsForList().leftPushAll(key,"w","x","y");
        Long l = redisTemplate.opsForList().leftPushAll(key, values);
        return l;
    }

    /**
     * 向集合最右边添加元素。sou
     *
     * <p>key value
     */
    public void leftPushAll(String key, Object value) {

        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向左边批量添加参数元素。
     *
     * <p>key values
     */
    public void rightPushAll(String key, List<Object> values) {
        //        redisTemplate.opsForList().leftPushAll(key,"w","x","y");
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 以集合方式向右边添加元素。
     *
     * <p>key values
     */
    public void rightPushAll(String key, Collection<Object> values) {

        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 向已存在的集合中添加元素。
     */
    public void rightPushIfPresent(String key, Object value) {

        redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 向已存在的集合中添加元素。
     *
     * <p>key
     */
    public long listLength(String key) {

        return redisTemplate.opsForList().size(key);
    }

    /**
     * 移除集合中的左边第一个元素。
     *
     * <p>key
     */
    public void leftPop(String key) {

        redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
     *
     * <p>key
     */
    public void leftPop(String key, long timeout, TimeUnit unit) {

        redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除集合中右边的元素。
     *
     * <p>key
     */
    public void rightPop(String key) {

        redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
     *
     * <p>key
     */
    public void rightPop(String key, long timeout, TimeUnit unit) {

        redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * hscan扫描
     *
     * @param key     hashkey
     * @param pattern 匹配模式
     * @return
     */
    public Map<Object, Object> hscan(String key, String pattern) {

        List<Map<Object, Object>> list = Lists.newArrayList();
        ScanOptions.ScanOptionsBuilder scanOptionsBuilder = ScanOptions.scanOptions();
        ScanOptions build = scanOptionsBuilder.match(pattern).build();
        Cursor<Map.Entry<Object, Object>> scan = redisTemplate.opsForHash().scan(key, build);
        Map<Object, Object> map = Maps.newHashMap();
        while (scan.hasNext()) {
            Map.Entry<Object, Object> entry = scan.next();
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * scan
     *
     * @param key     hashkey
     * @param pattern 匹配模式
     * @return
     */
    public List<Object> scan(String key, String pattern) {

        List<Object> list = Lists.newArrayList();
        ScanOptions.ScanOptionsBuilder scanOptionsBuilder = ScanOptions.scanOptions();
        ScanOptions build = scanOptionsBuilder.match(pattern).build();
        Cursor<Object> scan = redisTemplate.opsForSet().scan(key, build);
        while (scan.hasNext()) {
            Object next = scan.next();
            list.add(next);
        }
        return list;
    }

    /**
     * scan
     *
     * @param key     hashkey
     * @param pattern 匹配模式
     * @return
     */
    public void scanDel(String key, String pattern) {

        List<Object> list = Lists.newArrayList();
        ScanOptions.ScanOptionsBuilder scanOptionsBuilder = ScanOptions.scanOptions();
        ScanOptions build = scanOptionsBuilder.match(pattern).build();
        Cursor<Object> scan = redisTemplate.opsForSet().scan(key, build);
        while (scan.hasNext()) {
            Object next = scan.next();
            redisTemplate.delete((String) next);
        }
    }

    public Boolean geoAdd(String geoKey, String longitude, String latitude, String member) {

        GeoLocation geoLocation =
                new GeoLocation(member, new Point(Double.valueOf(longitude), Double.valueOf(latitude)));
        Long add = redisTemplate.opsForGeo().add(geoKey, geoLocation);
        return add > 0;
    }

    /**
     * geo 地理信息获取
     *
     * @param geoKey  组
     * @param members 元素成员数组
     * @return
     */
    public List<Point> geoPosition(String geoKey, List<String> members) {

        List<Point> positions = redisTemplate.opsForGeo().position(geoKey, members.toArray());
        return positions;
    }

    public List<Point> geoPosition(String geoKey, String member) {

        List<Point> positions = redisTemplate.opsForGeo().position(geoKey, member);
        return positions;
    }

    /**
     * 返回两个给定位置之间的距离。
     *
     * @param geoKey
     * @param firstMember
     * @param secondMember
     * @param unit
     * @return
     */
    public Distance geoDist(
            String geoKey, String firstMember, String secondMember, DistanceUnit unit) {

        Distance distance = redisTemplate.opsForGeo().distance(geoKey, firstMember, secondMember, unit);
        return distance;
    }


    /**
     * 返回一个或多个位置元素的 Geohash 表示。
     *
     * @param geoKey
     * @param members
     * @return
     */
    public List<String> geoHash(String geoKey, List<String> members) {

        List<String> hash = redisTemplate.opsForGeo().hash(geoKey, members.toArray());
        return hash;
    }


    /**
     * 根据经纬度来找附近的元素
     *
     * @param geoKey
     * @param centerLong
     * @param centerLa
     * @param unit
     * @param sort
     * @param limit
     * @return
     */
    public GeoResults<GeoLocation<Object>> geoRadiusByXY(
            String geoKey,
            String centerLong,
            String centerLa,
            Double distance,
            DistanceUnit unit,
            String sort,
            int limit) {

        Circle circle =
                new Circle(
                        new Point(Double.parseDouble(centerLong), Double.valueOf(centerLa)),
                        new Distance(distance, unit));

        RedisGeoCommands.GeoRadiusCommandArgs args =
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();

        switch (sort) {
            case "ASC":
                args = args.sortAscending();
                break;
            case "DESC":
                args = args.sortDescending();
                break;
            default:
                args.sortAscending();
        }
        args = args.includeDistance().includeCoordinates();

        args.limit(limit);
        GeoResults<GeoLocation<Object>> radius = redisTemplate.opsForGeo().radius(geoKey, circle, args);
        return radius;
    }

    /**
     * 根据元素点 获取周边范围多少Unit单位内的元素
     *
     * @param geoKey
     * @param distance
     * @param member
     * @param unit
     * @param sort
     * @param limit
     * @return
     */
    public GeoResults<GeoLocation<Object>> geoRadiusByPoint(
            String geoKey, String distance, String member, DistanceUnit unit, String sort, int limit) {

        Distance nearBy = new Distance(Double.valueOf(distance), unit);

        RedisGeoCommands.GeoRadiusCommandArgs args =
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();

        switch (sort) {
            case "ASC":
                args = args.sortAscending();
                break;
            case "DESC":
                args = args.sortDescending();
                break;
            default:
                args.sortAscending();
        }
        args = args.includeDistance().includeCoordinates();
        args.limit(limit);
        GeoResults<GeoLocation<Object>> radius =
                redisTemplate.opsForGeo().radius(geoKey, member, nearBy, args);
        return radius;
    }

    /**
     * 删除元素
     *
     * @param geoKey
     * @param member
     * @return
     */
    public Boolean geoDelete(String geoKey, String member) {

        Long remove = redisTemplate.opsForGeo().remove(geoKey, member);
        return remove > 0;
    }


    public void addPoint(String geoKey, Point point, String id) {

        redisTemplate.opsForGeo().add(geoKey, point, id);
    }

    public void addPoint(String geoKey, double x, double y, String id) {

        redisTemplate.opsForGeo().add(geoKey, new Point(x, y), id);
    }


    /**
     * 设置位图
     */
    /**
     * @param key
     * @param offset
     * @param value
     * @description:
     * @return:
     * @author: VINO
     * @time: 2021/6/22 9:28
     */
    public boolean setBit(String key, long offset, boolean value) {
        Boolean aBoolean = redisTemplate.opsForValue().setBit(key, offset, value);
        return aBoolean;
    }
    /**
     * 设置位图
     */
    /**
     * @param key
     * @param offset
     * @param value
     * @description:
     * @return:
     * @author: VINO
     * @time: 2021/6/22 9:28
     */
    public boolean rSetBit(String key, long offset, boolean value) {
//        RBitSet bitSet = redissonClient.getBitSet(key);
//        bitSet.set()
//        return aBoolean;
        return false;
    }

    /**
     * 获取位图的个数 如
     * setBit(${userId},${postId},true) 某个人给某篇文章点赞
     * 获取某个人点了多少赞 bitcount(${userId})
     * 获取某篇文章点了多少赞 bitcount(${postId})
     *
     * @description:
     * @param:
     * @return:
     * @author: VINO
     * @time: 2021/6/22 9:53
     */
    public Long getBitCount(String key) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    /**
     * BITPOS key bit  ：从start+1个字节开始查找，直到尾部
     * 如 查询某个人的签到次数  bitpos userId true
     *
     * @description:
     * @param: null
     * @return:
     * @author: VINO
     * @time: 2021/6/22 10:15
     */
    public Long bitRangCount(String key, boolean value) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitPos(key.getBytes(), value));
    }

    /**
     * BITPOS key bit  start end ： 指定范围查询
     * 如 查询某个人的签到次数  bitpos userId true
     *
     * @description:
     * @param: null
     * @return:
     * @author: VINO
     * @time: 2021/6/22 10:15
     */
    public Long bitRangCount(String key, boolean value, long low, long high) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitPos(key.getBytes(), value, Range.of(Range.Bound.inclusive(low), Range.Bound.inclusive(high))));
    }

    /**
     * 获取连续的位图值 返回的是1或者0 需要另行计算
     * 场景：连续签到 用户连续签到了多少天
     *
     * @param key
     * @param limit
     * @param offset
     * @return
     */
    public List<Long> bitField(String key, int limit, long offset) {
        return redisTemplate.execute((RedisCallback<List<Long>>) con -> con.bitField(key.getBytes(),
                BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(limit)).valueAt(offset)));
    }

    public Long luaPipe(String script, String key, List<String> params) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), params.toArray());
        return result;
    }
}
