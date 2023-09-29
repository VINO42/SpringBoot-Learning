package io.github.vino42.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * =====================================================================================
 *
 * @Created :   2023/6/13 21:46
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class SingleTreeTest {
    public static void main(String[] args) {
        Dept d1 = new Dept();
        d1.setName("d1");
        d1.setParentId(null);
        d1.setDeptId(1L);
        d1.setLevel(1);
        Dept d2 = new Dept();
        d2.setName("d2");
        d2.setParentId(1L);
        d2.setDeptId(2L);
        d2.setLevel(2);

        Dept d3 = new Dept();
        d3.setName("d3");
        d3.setParentId(2L);
        d3.setDeptId(3L);
        d3.setLevel(3);

        Dept d4 = new Dept();
        d4.setName("d4");
        d4.setParentId(3L);
        d4.setDeptId(4L);
        d4.setLevel(4);

        Dept d5 = new Dept();
        d5.setName("d5");
        d5.setParentId(4L);
        d5.setDeptId(5L);
        d5.setLevel(5);

        List<Dept> depts = Lists.newArrayList();
//        depts.add(d1);
//        depts.add(d2);
        depts.add(d3);
        depts.add(d4);
//        depts.add(d5);
        DeptVo top = new DeptVo();
        top.setParentId(null);
        List<DeptVo> vos = BeanUtil.copyToList(depts, DeptVo.class);
        //非顶级节点树
        List<DeptVo> notTop = vos.stream().filter(d -> d.getParentId() != null).collect(Collectors.toList());
        //按父级id 进行分组
        Map<Long, List<DeptVo>> collect2 = notTop.stream().collect(Collectors.groupingBy(node -> node.getParentId()));

        //循环设置对应的子节点（根据id = pid）
        vos.forEach(node -> {
            List<DeptVo> deptVos = collect2.get(node.getDeptId());
            if (CollUtil.isNotEmpty(deptVos)) {
                //
                DeptVo deptVo = deptVos.get(0);
                node.setNode(deptVo);
            }
        });
        DeptVo deptVo = vos.stream().min(Comparator.comparing(DeptVo::getLevel)).get();
        //只留下顶级节点，自动成树形结构
        List<DeptVo> collect3 = vos.stream().filter(node -> node.getLevel() == deptVo.getLevel()).collect(Collectors.toList());
        if (collect3.isEmpty()) {
            System.out.println(JSONUtil.toJsonStr(collect3));

        } else {

            System.out.println(JSONUtil.toJsonStr(collect3.get(0)));
        }
    }

}
