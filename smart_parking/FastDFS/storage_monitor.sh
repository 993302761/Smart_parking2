#!/bin/bash

#这个就是Fastdfs trackerd(跟踪器)和storaged(存储节点)的配置文件
export FASTDFS_TSPATH=/usr/bin
#环境变量配置路径
export FASTDFS_CONFIG=/etc/fdfs


cd ${FASTDFS_TSPATH}


#查看存储节点存活
sudo ./fdfs_monitor ${FASTDFS_CONFIG}/storage.conf
