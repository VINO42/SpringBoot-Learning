package io.github.vino42.domain;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/3/11 21:27
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class TreeUtilTests {
    public static void main(String[] args) {
        List<TreeVo<Integer>> data = Lists.newArrayList();
        TreeVo<Integer> p1 = new TreeVo();
        p1.setAge(1);
        p1.setParentId(null);
        p1.setId(1);
        p1.setName("父级");
        data.add(p1);

        TreeVo p2 = new TreeVo();
        p2.setAge(2);
        p2.setParentId(1);
        p2.setId(2);
        p2.setName("儿级");

        data.add(p2);
        TreeVo p22 = new TreeVo();
        p22.setAge(2);
        p22.setParentId(1);
        p22.setId(22);
        p22.setName("儿级2");
        data.add(p22);
        TreeVo p223 = new TreeVo();
        p223.setAge(2);
        p223.setParentId(1);
        p223.setId(223);
        p223.setName("儿级23");
        data.add(p223);
        TreeVo p3 = new TreeVo();
        p3.setAge(3);
        p3.setParentId(2);
        p3.setId(3);
        p3.setName("孙级");
        data.add(p3);


        TreeVo p333 = new TreeVo();
        p333.setAge(3);
        p333.setParentId(2);
        p333.setId(333);
        p333.setName("孙级");
        data.add(p333);
        /*---*/
//        TreeVo p4 = new TreeVo();
//        p4.setAge(11);
//        p4.setParentId(null);
//        p4.setId(11);
//        p4.setName("父级1");
//        data.add(p4);
//
//        TreeVo p5 = new TreeVo();
//        p5.setAge(21);
//        p5.setParentId(11);
//        p5.setId(21);
//        p5.setName("儿级1");
//        TreeVo p51 = new TreeVo();
//        p51.setAge(21);
//        p51.setParentId(11);
//        p51.setId(211);
//        p51.setName("儿级11");
//        data.add(p51);
//        TreeVo p6 = new TreeVo();
//        p6.setAge(31);
//        p6.setParentId(21);
//        p6.setId(31);
//        p6.setName("孙级1");
//        data.add(p6);
        TreeNodeConfig config = new TreeNodeConfig();
        config.setParentIdKey("pId");
        config.setIdKey("oId");

//        List<TreeVo<Integer>> treeNodes = TreeUtil.build(data, null, config, (object, tree) -> {
//
//            tree.setId(Convert.toInt(object.getId()));//必填属性
//            tree.setParentId(Convert.toInt(object.getParentId()));//必填属性
//            tree.setName(object.getName());
//            tree.putExtra("count",0);
//
//        });
//        countSum(treeNodes,0);
//
//        System.out.println(JSONUtil.toJsonStr(treeNodes));
    }

    private static void countSum(List<Tree<Integer>> treeNodes, Integer total) {

        for (Tree<Integer> treeNode : treeNodes) {
            if (treeNode.getChildren() != null && treeNode.getChildren().size() > 0) {
                Integer count = (Integer) treeNode.getOrDefault("count", 0);
                count += treeNode.getChildren().size();
                treeNode.putExtra("count", count);
                total += count;
                if (treeNode.getParent().getParentId() == null) {
                    treeNode.getParent().putExtra("count", total);
                }
                countSum(treeNode.getChildren(), total);
            }
        }
    }
}
