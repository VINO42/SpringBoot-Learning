package io.github.vino42.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.micrometer.core.instrument.util.StringUtils;
import org.redisson.api.*;
import org.redisson.api.geo.GeoSearchArgs;
import org.redisson.api.geo.OptionalGeoSearch;
import org.redisson.client.codec.StringCodec;
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
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService<V> {
    @Autowired
    @Lazy
    private RedissonClient redissonClient;

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


    public void rmapSet(String key, String k, V value) {
        redissonClient.getMap(key, StringCodec.INSTANCE).put(k, value);
    }

    public Object rmapGet(String key, String k) {
        Object orDefault = redissonClient.getMap(key, StringCodec.INSTANCE)
                .getOrDefault(k, null);
        return orDefault;
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

    public Boolean addToSet(String key, V valuie) {
        RSet<V> set = redissonClient.getSet(key);
        return set.add(valuie);
    }

    public Boolean addToSetEvic(String key, V valuie, Long ttl) {
        RSetCache<V> set = redissonClient.getSetCache(key);
        return set.add(valuie, ttl, TimeUnit.SECONDS);
    }

    public Boolean addToSortedSet(String key, V valuie, Long ttl) {
        RSortedSet<V> set = redissonClient.getSortedSet(key);
        return set.add(valuie);
    }

    public Boolean addToSortedScoreSet(String key, V valuie, int score) {
        RScoredSortedSet<Object> set = redissonClient.getScoredSortedSet(key);
        return set.add(score, valuie);
    }

    public Boolean addToLexSortedSet(String key, String value) {
        RLexSortedSet set = redissonClient.getLexSortedSet(key);
        return set.add(value);
    }

    public Boolean addToList(String key, V value) {
        RList<V> set = redissonClient.getList(key);
        return set.add(value);
    }

    public Boolean addToQueue(String key, V value) {
        RQueue<V> set = redissonClient.getQueue(key);
        return set.add(value);
    }

    public Boolean addToDQueue(String key, V value) {
        RDeque<V> set = redissonClient.getDeque(key);
        return set.add(value);
    }

    public Boolean addToBlockQueue(String key, V value) {
        RBlockingQueue<V> set = redissonClient.getBlockingQueue(key);
        return set.add(value);
    }

    public Boolean addToBoundedBlockingQueue(String key, V value) {
        RBoundedBlockingQueue<V> set = redissonClient.getBoundedBlockingQueue(key);
        return set.add(value);
    }

    public Boolean addToBlockingDeque(String key, V value) {
        RBlockingDeque<V> set = redissonClient.getBlockingDeque(key);
        return set.add(value);
    }

    public Boolean addToPriorityQueue(String key, V value) {
        RPriorityQueue<V> set = redissonClient.getPriorityQueue(key);
        return set.add(value);
    }

    public Boolean addToPriorityDeque(String key, V value) {
        RPriorityDeque<V> set = redissonClient.getPriorityDeque(key);
        return set.add(value);
    }

    public Boolean addToPriorityBlockingQueue(String key, V value) {
        RPriorityBlockingQueue<V> set = redissonClient.getPriorityBlockingQueue(key);
        return set.add(value);
    }

    public Boolean addToPriorityBlockingDeque(String key, V value) {
        RPriorityBlockingDeque<V> set = redissonClient.getPriorityBlockingDeque(key);
        return set.add(value);
    }

    public RMap<String, Object> getMap(String key) {
        return redissonClient.getMap(key);
    }

    public RMapCache<String, Object> getMapCache(String key) {
        return redissonClient.getMapCache(key);
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
     */
    public void leftPushAll(String key, List<Object> values) {
        //        redisTemplate.opsForList().leftPushAll(key,"w","x","y");
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 向集合最右边添加元素。
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

    public Boolean rGeoAdd(String geoKey, String longitude, String latitude, String member) {
        RGeo<Object> geo = redissonClient.getGeo(geoKey);
        GeoEntry geoEntry = new GeoEntry(Double.parseDouble(longitude), Double.parseDouble(latitude), member);
        long ifAdded = geo.add(geoEntry);
        return ifAdded > 0;
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

    /**
     * geo 地理信息获取
     *
     * @param geoKey  组
     * @param members 元素成员数组
     * @return
     */
    public Map<Object, GeoPosition> rGeoPosition(String geoKey, List<String> members) {
        RGeo<Object> geo = redissonClient.getGeo(geoKey);
        return geo.pos(members);
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
     * 返回两个给定位置之间的距离。
     *
     * @param geoKey
     * @param firstMember
     * @param secondMember
     * @param unit
     * @return
     */
    public Double rGeoDist(
            String geoKey, String firstMember, String secondMember, GeoUnit unit) {
        RGeo<Object> geo = redissonClient.getGeo(geoKey);

        return geo.dist(firstMember, secondMember, unit);
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
     * 返回一个或多个位置元素的 Geohash 表示。
     *
     * @param geoKey
     * @param members
     * @return
     */
    public Map<Object, String> rGeoHash(String geoKey, List<String> members) {
        RGeo<Object> geo = redissonClient.getGeo(geoKey);
        return geo.hash(members);
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
    public Map<Object, GeoPosition> rGeoRadiusByXY(
            String geoKey,
            Double centerLong,
            Double centerLa,
            Double distance,
            GeoUnit unit,
            GeoOrder sort,
            int limit) {

        RGeo<Object> geo = redissonClient.getGeo(geoKey);
        OptionalGeoSearch search = GeoSearchArgs.from(centerLong, centerLa)
                .radius(distance, unit)
                .order(sort)
                .count(limit);

        return geo.searchWithPosition(search);
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

    /**
     * 删除元素
     *
     * @param geoKey
     * @param member
     * @return
     */
    public Boolean rGeoDelete(String geoKey, String member) {
        RGeo<Object> geo = redissonClient.getGeo(geoKey);
        return geo.remove(member);
    }

    public void addPoint(String geoKey, Point point, String id) {

        redisTemplate.opsForGeo().add(geoKey, point, id);
    }

    public void rAddPoint(String geoKey, GeoEntry geoEntry) {
        RGeo<Object> geo = redissonClient.getGeo(geoKey);
        geo.add(geoEntry);
    }

    public void addPoint(String geoKey, double x, double y, String id) {

        redisTemplate.opsForGeo().add(geoKey, new Point(x, y), id);
    }

    public void rAddPoint(String geoKey, double x, double y, String id) {
        RGeo<Object> geo = redissonClient.getGeo(geoKey);
        GeoEntry geoEntry = new GeoEntry(x, y, id);
        geo.add(geoEntry);

    }

    /**
     * 根据给定的布隆过滤器添加值
     */
    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            if (!redisTemplate.opsForValue().getBit(key, i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * get ringbuffer
     * retrieves and removes the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    public RRingBuffer<V> getRingBuffer(String bufferKey) {
        RRingBuffer<V> ringBuffer = redissonClient.getRingBuffer(bufferKey);
        return ringBuffer;
    }

    /**
     * get 数据到redis的ringbuffer
     * retrieves and removes the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    public V getFromRingBuffer(String bufferKey) {
        RRingBuffer<V> ringBuffer = redissonClient.getRingBuffer(bufferKey);
        return ringBuffer.poll();
    }

    /**
     * set 数据到redis的ringbuffer
     * retrieves and removes the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    public boolean add2RingBuffer(String bufferKey, V value, int capacity) {
        RRingBuffer<V> buffer = redissonClient.getRingBuffer(bufferKey);
        buffer.trySetCapacity(capacity);
        return buffer.add(value);
    }

    /**
     * offer 数据到redis的ringbuffer
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions.
     * When using a capacity-restricted queue, this method is generally
     * preferable to {@link #add}, which can fail to insert an element only
     * by throwing an exception.
     *
     * @param value the element to add
     * @return {@code true} if the element was added to this queue, else
     * {@code false}
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it from being added to this queue
     * @throws NullPointerException     if the specified element is null and
     *                                  this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *                                  prevents it from being added to this queue
     */
    public boolean offer2RingBuffer(String bufferKey, V value) {
        RRingBuffer<V> buffer = redissonClient.getRingBuffer(bufferKey);
        return buffer.offer(value);
    }

    /**
     * 添加指定元素到 HyperLogLog 中。
     *
     * @param hyperLoglogKey
     * @param value
     * @return
     */
    public boolean addHyperLogLog(String hyperLoglogKey, V value) {
        RHyperLogLog<V> hyperLogLog = redissonClient.getHyperLogLog(hyperLoglogKey);
        return hyperLogLog.add(value);

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

    /**
     * BITPOS key bit start ：从start+1个字节开始查找，直到尾部
     * BITPOS key bit start end：从start+1字节开始到end+1字节之间查找
     *
     * @param hyperLoglogKey
     * @return
     */
    public RHyperLogLog<V> getHyperLogLog(String hyperLoglogKey) {
        RHyperLogLog<V> hyperLogLog = redissonClient.getHyperLogLog(hyperLoglogKey);
        return hyperLogLog;

    }

    /**
     * 添加指定元素列表到 HyperLogLog 中。
     *
     * @param hyperLoglogKey
     * @param values
     * @return
     */
    public boolean addHyperLogLogs(String hyperLoglogKey, List<V> values) {
        RHyperLogLog<V> hyperLogLog = redissonClient.getHyperLogLog(hyperLoglogKey);
        return hyperLogLog.addAll(values);
    }

    /**
     * 返回给定 HyperLogLog 的基数估算值。
     *
     * @param hyperLoglogKey
     * @return
     */
    public long countHyperLoglog(String hyperLoglogKey) {
        RHyperLogLog<V> hyperLogLog = redissonClient.getHyperLogLog(hyperLoglogKey);
        return hyperLogLog.count();
    }

    /**
     * 将多个 HyperLogLog 合并为一个 HyperLogLog
     *
     * @param destinationKey
     * @param hyperLoglogKeys
     * @return
     */
    public Boolean mergHyperLoglog(String destinationKey, String... hyperLoglogKeys) {
        RHyperLogLog<V> hyperLogLog = redissonClient.getHyperLogLog(destinationKey);
        hyperLogLog.mergeWith(hyperLoglogKeys);
        return Boolean.TRUE;
    }

    /**
     * @param rateKey      限流的key
     * @param rate         限流频率
     * @param rateInterval 限流间隔 默认位秒
     * @return
     */
    public Boolean rateLimite(String rateKey, long rate, long rateInterval) {
        RRateLimiter limiter = redissonClient.getRateLimiter(rateKey);
        return limiter.trySetRate(RateType.OVERALL, rate, rateInterval, RateIntervalUnit.SECONDS);
    }

    /**
     * @param rateKey 限流的key
     * @return
     */
    public RRateLimiter getRateLimite(String rateKey) {
        RRateLimiter limiter = redissonClient.getRateLimiter(rateKey);
        return limiter;
    }

    /**
     * 申请访问数据 rateKey
     *
     * @param rateKey
     */
    public void acquireRateLimite(String rateKey) {
        RRateLimiter limiter = redissonClient.getRateLimiter(rateKey);
        limiter.acquire();
    }

    /**
     * 初始化布隆过滤器
     *
     * @param booleanKey         过滤器集合key
     * @param expectedInsertions 预计统计元素数量
     * @param falseProbability   期望误差率
     * @return
     */
    public RBloomFilter<V> booleanFilterInit(String booleanKey, long expectedInsertions, double falseProbability) {
        RBloomFilter<V> bloomFilter = redissonClient.getBloomFilter(booleanKey);
        bloomFilter.tryInit(expectedInsertions, falseProbability);
        return bloomFilter;
    }

    /**
     * 布隆过滤器添加元素
     *
     * @param booleanKey 过滤器集合key
     * @param value      值
     * @return
     */
    public Boolean booleanFilterContains(String booleanKey, V value) {
        RBloomFilter<V> bloomFilter = redissonClient.getBloomFilter(booleanKey);
        return bloomFilter.add(value);
    }

    public RBucket<V> getObjBucket(String bucketKey) {
        RBucket<V> bucket = redissonClient.getBucket(bucketKey);
        return bucket;
    }

    public void setBucketObj(String bucketKey, V value) {
        RBucket<V> bucket = redissonClient.getBucket(bucketKey);
        bucket.set(value);
    }


    public void trySetBucketObj(String bucketKey, V value) {
        RBucket<V> bucket = redissonClient.getBucket(bucketKey);
        bucket.trySet(value);
    }

    public V getBucketObj(String bucketKey) {
        RBucket<V> bucket = redissonClient.getBucket(bucketKey);
        return bucket.get();
    }

    public boolean delBucketObj(String bucketKey) {
        RBucket<V> bucket = redissonClient.getBucket(bucketKey);
        return bucket.delete();
    }

    public RTopic getRtopic(String topicKey) {
        RTopic bucket = redissonClient.getTopic(topicKey);
        return bucket;
    }
}
