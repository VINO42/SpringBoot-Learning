# Springboot3 +Springcache集成caffeine

* caffeine简介
  Caffeine是一个基于Java8开发的提供了近乎最佳命中率的高性能的缓存库。存和ConcurrentMap有点相似，
  但还是有所区别。最根本的区别是ConcurrentMap将会持有所有加入到缓存当中的元素，直到它们被从缓存当中手动移除。
  但是，Caffeine的缓存Cache 通常会被配置成自动驱逐缓存中元素，以限制其内存占用。在某些场景下，
  LoadingCache和AsyncLoadingCache 因为其自动加载缓存的能力将会变得非常实用。



官方文档 细品


 `https://github.com/ben-manes/caffeine/wiki/Home-zh-CN`



* @Cacheable 的逻辑是：查找缓存 - 有就返回 -没有就执行方法体 - 将结果缓存起来；


* @CachePut 的逻辑是：执行方法体 - 将结果缓存起来；


