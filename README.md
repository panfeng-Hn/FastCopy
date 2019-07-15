# FastCopy

## 介绍
快速复制一个目录下大量小文件到其他目录

## 软件架构
复制的时候在目标目录建立一个临时文件夹。先将文件复制到临时文件夹中。当临时文件夹的文件数量大于
一定数量的时候直接移动到目标目录中。复制的时候使用多线程复制。

## 地址  

[Gitee](https://gitee.com/panfeng-Hn/FastCopy)  
[Github](https://github.com/panfeng-Hn/FastCopy)

## 安装教程

直接将jar放到本地就好

## 使用说明

```
java -jar FastCopy src  dst [可选参数]  
 (必须)src：源目录，必须是目录并可读
 (必须)dst：目标目录，必须是目录并可写
 [可选参数]：
	-record 文件复制记录的位置，默认在目标目录,用于在程序断开后重新启动
	-threadnum 同时运行最大线程数量，默认为cpu核心数
	-pageDirNum 每个分页文件夹最大文件数量，默认为5000。即每5000个文件放入一个文件夹然后再重新复制
```


