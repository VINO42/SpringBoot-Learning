# springboot 集成websocket
*  测试websocket网站

   ```https://websocket.jsonin.com/```

   ```http://www.easyswoole.com/wstool.html```

    * 使用postman导入以下代码,发送给所有人消息
      ```curl --location --request POST 'http://127.0.0.1:8888/ws/broadcast?message=%E8%AF%B7%E8%BF%9B%E5%85%A5%E8%A7%86%E9%A2%91%E4%BC%9A%E8%AE%AE'```
    * 打开 https://websocket.jsonin.com/  填入 下面地址 可以实现每个用户连接道德模拟,单个用户连接    
      ```ws://127.0.0.1:8888/ws/1```
      ```ws://127.0.0.1:8888/ws/2```
    * websocket是有状态的 websocket的分布式session的维护？
      * session没有实现serializer接口 无法序列化到redis
      * 一致性hash
        * 维护一个全局映射关系 —— 当前客户端的 WebSocket 、
        
          连接是和哪一个实例/节点保持的。每次通信都经过此注册中心或哈希，查询到具体的服务实例，将连接交给它
         
          问题：
          * 哈希环的实现
          * 如何解决一致性问题（单节点故障的 Session 迁移重连将代价降到最低）
          * CAP 取舍、实例的区分依据是什么（IP ? 实例 ID？）
      * 消息队列实现
      
        使用消息队列广播连接通知到每个websocket实例,由具体的websocket实例来判断是否当前客户端的状态由自己维护
    * websocket的鉴权？