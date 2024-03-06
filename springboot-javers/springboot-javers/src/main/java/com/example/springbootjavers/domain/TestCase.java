package com.example.springbootjavers.domain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.StopWatch;
import org.javers.common.collections.Lists;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.custom.CustomValueComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * =====================================================================================
 *
 * @Created :   2024/3/6 22:24
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class TestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestCase.class);

    public static void main(String[] args) {
        Javers javers = JaversBuilder.javers().build();
        User old = new User("小明", 10, new BigDecimal("10.234"), 180.3);
        User newOne = new User("小明", 10, new BigDecimal("10.423"), 183.3);
        User newTwo = new User("小明", 12, new BigDecimal("10.323"), null);
        User newThree = new User();

        compare(old, newOne, javers);
        compare(newOne, newTwo, javers);
        compare(newTwo, newOne, javers);
        //比较集合
        compareList(CollUtil.newArrayList(old),CollUtil.newArrayList(newOne),javers);
        compareList(CollUtil.newArrayList(newOne),CollUtil.newArrayList(newTwo),javers);
        compareList(CollUtil.newArrayList(newTwo),CollUtil.newArrayList(newOne),javers);
        compareList(CollUtil.newArrayList(newTwo),CollUtil.newArrayList(newThree),javers);
        //自定义比较
        customCompare(CollUtil.newArrayList(newOne),CollUtil.newArrayList(newTwo));

    }

    /**
     * 对比新老对象
     *
     * @param oldObj
     * @param newObj
     * @param javers
     * @return
     * NewObject 新增
     * ObjectRemoved 删除
     * PropertyChange 修改
     * 其中： PropertyChange包含：
     *          ContainerChange 集合修改
     *          MapChange map修改
     *          ReferenceChange 实体引用修改
     *          ValueChange 普通值的修改
     *
     */
    public static boolean compare(User oldObj, User newObj, Javers javers) {
        StopWatch stopWatch = StopWatch.create(String.valueOf(System.currentTimeMillis()));
        stopWatch.start();
        Diff diff = javers.compare(oldObj, newObj);
        boolean flag = diff.hasChanges();
        Changes changes = diff.getChanges();
        LOGGER.info("diff:{}", diff.prettyPrint());
        LOGGER.info("changes:{}", changes.prettyPrint());
        for (Change change : changes) {
            if ((change instanceof NewObject)) {
                LOGGER.info("新增改动: {}", change);
            }
            if ((change instanceof ObjectRemoved)) {
                LOGGER.info("删除改动: {}", change);
            }
            if ((change instanceof ValueChange)) {
                LOGGER.info("修改改动: {}" , change.getAffectedObject());
            }
        }
        stopWatch.stop();
        LOGGER.info("[对比消耗时间：{}]", stopWatch.getTotal(TimeUnit.MILLISECONDS));
        return flag;
    }

    public static boolean compareList(List<User> oldObj, List<User> newObj, Javers javers) {
        StopWatch stopWatch = StopWatch.create(String.valueOf(System.currentTimeMillis()));
        stopWatch.start();
        Diff diff = javers.compareCollections(oldObj, newObj,User.class);
        boolean flag = diff.hasChanges();
        Changes changes = diff.getChanges();
        LOGGER.info("diff:{}", diff.prettyPrint());
        LOGGER.info("changes:{}", changes.prettyPrint());
        for (Change change : changes) {
            if ((change instanceof NewObject)) {
                LOGGER.info("新增改动: {}", change);
            }
            if ((change instanceof ObjectRemoved)) {
                LOGGER.info("删除改动: {}", change);
            }
            if ((change instanceof ValueChange)) {
                LOGGER.info("修改改动: {}" , change.getAffectedObject());
            }
        }
        stopWatch.stop();
        LOGGER.info("[对比消耗时间：{}]", stopWatch.getTotal(TimeUnit.MILLISECONDS));
        return flag;
    }
    public static boolean customCompare(List<User> oldObj, List<User> newObj) {
        Javers javers = JaversBuilder.javers()
                .registerValue(BigDecimal.class,
                        //定义小数位数长度为2位小数
                        new CustomBigDecimalComparator(2)).build();

        StopWatch stopWatch = StopWatch.create(String.valueOf(System.currentTimeMillis()));
        stopWatch.start();
        Diff diff = javers.compareCollections(oldObj, newObj,User.class);
        boolean flag = diff.hasChanges();
        Changes changes = diff.getChanges();
        LOGGER.info("diff:{}", diff.prettyPrint());
        LOGGER.info("changes:{}", changes.prettyPrint());
        for (Change change : changes) {
            if ((change instanceof NewObject)) {
                LOGGER.info("新增改动: {}", change);
            }
            if ((change instanceof ObjectRemoved)) {
                LOGGER.info("删除改动: {}", change);
            }
            if ((change instanceof ValueChange)) {
                LOGGER.info("修改改动: {}" , change.getAffectedObject());
            }
        }
        stopWatch.stop();
        LOGGER.info("[对比消耗时间：{}]", stopWatch.getTotal(TimeUnit.MILLISECONDS));
        return flag;
    }

    /**
     * 自定义一些类型的比较器
     */
    public static class CustomBigDecimalComparator implements CustomValueComparator<BigDecimal> {
        private int significantDecimalPlaces;

        public CustomBigDecimalComparator(int significantDecimalPlaces) {
            this.significantDecimalPlaces = significantDecimalPlaces;
        }

        @Override
        public boolean equals(BigDecimal a, BigDecimal b) {
            return round(a).equals(round(b));
        }

        @Override
        public String toString(BigDecimal value) {
            return round(value).toString();
        }

        private BigDecimal round(BigDecimal val) {
             return val.setScale(significantDecimalPlaces, RoundingMode.HALF_DOWN);
        }
    }

}
