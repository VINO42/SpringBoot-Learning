package io.github.vino42.configuaration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
 
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static  final  String statu="statu";
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, statu, Integer.class, 0);
//        setFieldValByName(statu,0,metaObject);
    }
 
    @Override
    public void updateFill(MetaObject metaObject) {
//        this.strictUpdateFill(metaObject, statu, Integer.class, 3);
        setFieldValByName(statu,4,metaObject);

    }
}