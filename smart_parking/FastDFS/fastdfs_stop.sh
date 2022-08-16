#!/bin/bash

#这个就是Fastdfs trackerd(跟踪器)和storaged(存储节点)的配置文件
export FASTDFS_TSPATH=/usr/bin
#环境变量配置路径
export FASTDFS_CONFIG=/etc/fdfs
#nginx开启配置路径
export NGINX_PATH=/usr/local/nginx/sbin







cd ${FASTDFS_TSPATH}
#开启跟踪器
sudo ./fdfs_trackerd ${FASTDFS_CONFIG}/tracker.conf stop
echo "已关闭跟踪器..."
#开启存储节点
sudo ./fdfs_storaged ${FASTDFS_CONFIG}/storage.conf stop
echo "已关闭存储节点..."
#开启监控查看存活节点
#sudo ./fdfs_monitor ${FASTDFS_CONFIG}/storage.conf
cd ${NGINX_PATH}
sudo ./nginx -s stop
echo "已关闭nginx服务..."

