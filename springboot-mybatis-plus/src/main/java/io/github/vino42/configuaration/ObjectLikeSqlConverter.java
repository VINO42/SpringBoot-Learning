package io.github.vino42.configuaration;

import lombok.extern.slf4j.Slf4j;

/**
 * 通用参数的转换器
 *
 * @author lxw
 */
@Slf4j
public class ObjectLikeSqlConverter extends AbstractLikeSqlConverter<Object> {

    @Override
    public void transferWrapper(String field, Object parameter) {
        // 尚未发现这种情况
    }

    @Override
    public void transferSelf(String field, Object parameter) {
        // 尚未发现这种情况
    }

    @Override
    public void transferSplice(String field, Object parameter) {
        this.resolveObj(field, parameter);
    }

}