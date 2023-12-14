package io.github.vino42.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import io.github.vino42.domain.ResourceDTO;
import io.github.vino42.domain.entity.Resource;
import io.github.vino42.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author vino
 * @since 2023-12-14
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    /**
     * 树节点和模糊查询到的id进行匹配
     *
     * @param keyword
     * @return
     */
    @GetMapping("/tree")
    public List getTree(@RequestParam("keyword") String keyword) {
        LambdaQueryWrapper<Resource> like = Wrappers.<Resource>lambdaQuery()
                .like(StrUtil.isNotBlank(keyword), Resource::getName, keyword);
        List<Resource> search = resourceService.list(like);


        List<Resource> list = resourceService.list();
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setParentIdKey("parentId");
        treeNodeConfig.setChildrenKey("children");
        List<Tree<Integer>> treeNodes = TreeUtil.build(list, 0, treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setName(treeNode.getName());
                });
        if (CollUtil.isNotEmpty(search)) {
            List<Tree<Integer>> nodes = Lists.newArrayList();
            for (Resource resource : search) {

                for (Tree<Integer> treeNode : treeNodes) {
                    Tree<Integer> node = treeNode.getNode(resource.getId());
                    if (node != null) {
                        nodes.add(node);
                    }
                }
            }
            return nodes;
        }
        return treeNodes;
    }

    /**
     * 先进行模糊查询 查出id后再查出所有的 这里可以不用递归去查询了 可以子查询直接内存组装树。
     *
     * @param keyword
     * @return
     */
    @GetMapping("/tree2")
    public List getTree2(@RequestParam("keyword") String keyword) {
        LambdaQueryWrapper<Resource> like = Wrappers.<Resource>lambdaQuery()
                .like(StrUtil.isNotBlank(keyword), Resource::getName, keyword);
        List<Resource> search = resourceService.list(like);
        List<Integer> ids = search.stream().map(Resource::getId).toList();

        List<Resource> list = resourceService.recursive(ids);
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setParentIdKey("parentId");
        treeNodeConfig.setChildrenKey("children");
        List<Integer> parentIds = search.stream().map(Resource::getParentId).distinct().toList();

        List<Tree<Integer>> list1 = parentIds.stream().map(id -> {

            List<Tree<Integer>> treeNodes = TreeUtil.build(list, id, treeNodeConfig,
                    (treeNode, tree) -> {
                        tree.setId(treeNode.getId());
                        tree.setParentId(treeNode.getParentId());
                        tree.setName(treeNode.getName());
                    });
            return treeNodes;
        }).filter(Objects::nonNull).flatMap(Collection::stream).toList();
        return list1;
    }

    /**
     * mybatis嵌套查询 本质是循环查询
     *
     * @param keyword
     * @return
     */
    @GetMapping("/tree3")
    public List getTree3(@RequestParam("keyword") String keyword) {
        List<ResourceDTO> list = resourceService.recursive(keyword);

        return list;
    }
}
