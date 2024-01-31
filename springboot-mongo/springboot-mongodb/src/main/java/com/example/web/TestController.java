package com.example.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.example.domain.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * =====================================================================================
 *
 * @Created :   2024/1/31 22:05
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email : VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RequestMapping("/test")
@RestController
public class TestController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/test1")
    public String test1() {
//        MongoCollection<Document> test = mongoTemplate.createCollection("test");
        User user = new User();
        user.setAge(10);
        user.setName("小明");
        user.setAttr1("条件一");
        user.setAtrr2("条件2");
        String jsonStr = JSONUtil.toJsonStr(user);
        mongoTemplate.insert(jsonStr, "test");
        user.setAttr1("测试1");
        user.setAtrr2("测试2");
        user.setAge(11);
        user.setName("小刚");
        mongoTemplate.insert(BeanUtil.beanToMap(user), "test");
        return "ok";
    }
}
