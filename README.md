# `Smart_parking2.0`

<a href="https://www.oscs1024.com/project/oscs/993302761/Smart_parking2.0?ref=badge_small" alt="OSCS Status"><img src="https://www.oscs1024.com/platform/badge/993302761/Smart_parking2.0.svg?size=small"/></a>

# `智慧停车2.0`

## `OSCS`

<a href="https://www.oscs1024.com/project/oscs/993302761/Smart_parking2.0?ref=badge_large" alt="OSCS Status"><img src="https://www.oscs1024.com/platform/badge/993302761/Smart_parking2.0.svg?size=large"/></a>

## `模块介绍`

### `smart_parking_server	总服务模块`
	

	module_Administrators  超级管理员模块
		port:9000

	module_Order	           订单模块
		port:9001

	module_Parking_lots    停车场管理员模块
		port:9002

	module_User                用户模块
		port:9003

	module_Vehicle           车辆信息模块
		port:9004

### `GateWay       	网关`

		port:8501


### `EurekaServer	注册中心`

双节点 保证高可用性

	EurekaServer01
		port:8651

	EurekaServer01
		port:8652


### `ConfigServer	配置中心`

		port:9999


## `运行环境`

	Gradle-version:7.2.0

	Redis-version:6.2.5

	Kafka-version:2.12-2.6.0

	Zookeeper-version:3.5.6




