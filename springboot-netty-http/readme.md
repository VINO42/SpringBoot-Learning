# springboot 集成内嵌netty-http server
* 原理：自定义handler,项目启动加载所有的requestMapping,handler进行反射处理请求。