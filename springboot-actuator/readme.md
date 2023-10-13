# springboot3 自定义 actuator端点

* 定义自定义端点类
  * 配置添加
    
    ```
    management.endpoints.web.exposure.include=${端点id}
    ```
* 访问 `http://127.0.0.1:8088/actuator/${端点id}`