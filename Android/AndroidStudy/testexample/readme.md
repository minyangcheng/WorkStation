# 测试方法

## unit test（不需要android运行环境）

查看textexample module项目下的test目录中的文件

## android unit test （需要android运行环境）

查看textexample module项目下的android test目录中的文件

## android 命令行monkey测试

```bash
adb shell
//monkey -p 包名 --throttle 点击间隔毫米数 -v -v 点击数
monkey -p com.min.study --throttle 300 -v -v 100
```
