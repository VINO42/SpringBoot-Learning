# SpringBoot graalvm jdk17 dockerfile方式构建
、
* mvnd clean install -DskipTests -Pdev
* docker build     -t vino/springboot-graalvm-dockerfile:1.0.0 .
* docker-compose up springboot-graalvm-dockerfile -d

