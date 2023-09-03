# springboot3 mybatis p6spy 集成

``P6Spy is a framework that enables database activity to be seamlessly intercepted and logged with no code changes to existing applications.``
这是官方介绍

* 打印sql

  ```
  2023-09-04 03:46:30.827 |  INFO | 4872  |  [requestId:] | [nio-8887-exec-2] | p6spy                                    |
  ============== SQL LOGGER BEGIN ==============
  SQL 执行时间       :2023-09-04 03:46:30.827
  SQL 执行毫秒       :0ms
  SQL 执行语句       :SELECT `id`, `org_id`, `user_id`, `mobile`, `password`, `avatar`, `nick_name`, `statu`, `is_del`, `create_time`, `update_time`, `create_by`, `create_name`, `update_by`, `update_name`, `created_ip`, `last_signin_time`, `last_signin_ip`, `qq`, `wechat`, `dingtalk`, `weibo`, `baidu` FROM `sys_account` LIMIT 0, 10
  ==============  SQL LOGGER END  ==============       | Closing non 
  ```