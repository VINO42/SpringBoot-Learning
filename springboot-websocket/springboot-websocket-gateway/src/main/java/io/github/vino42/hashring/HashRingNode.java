package io.github.vino42.hashring;

import java.io.Serializable;
import java.util.Objects;

/**
 * =====================================================================================
 *
 * @Created :   2023/9/2 0:33
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 真实节点信息 作为hash环的value存储
 * =====================================================================================
 */
public class HashRingNode implements Node, Serializable {

    private final String ip;

    public HashRingNode(String ip) {
        this.ip = ip;
    }

    @Override
    public String getKey() {
        return ip;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashRingNode that = (HashRingNode) o;
        return Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }
}
