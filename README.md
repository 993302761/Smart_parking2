# `Smart_parking2.8`
<a href="https://www.oscs1024.com/project/oscs/993302761/Smart_parking2.0?ref=badge_small" alt="OSCS Status"><img src="https://www.oscs1024.com/platform/badge/993302761/Smart_parking2.0.svg?size=small"/></a>

### `	 Brief introduction 
	The product is based on the data, collection and analysis of the Internet of Things and artificial intelligence to solve the problems of difficult parking in modern cities, difficult to find parking spaces, and unbalanced utilization of urban parking spaces.


	The system shows the remaining parking spaces in various places in the city to users through mobile phone APP and makes corresponding route planning for users. Different preferential charging policies are formulated for parking spaces according to different durations.


	The image recognition technology is used to identify the license plate number, and the user's integrity is determined by recording the parking space and identifying the license plate number. Based on the above data, the parking of target visitors is planned to promote the balance of urban parking space utilization.

`




## `OSCS`



<a href="https://www.oscs1024.com/project/oscs/993302761/Smart_parking2.0?ref=badge_large" alt="OSCS Status"><img src="https://www.oscs1024.com/platform/badge/993302761/Smart_parking2.0.svg?size=large"/></a>


## ` Module introduction `

### `smart_parking_server	总服务模块`
	

	module_Administrators  超级管理员模块

	module_Order	           订单模块

	module_Parking_lots    停车场管理员模块

	module_User                用户模块

	module_Vehicle           车辆信息模块

### `GateWay       	网关`

	GateWay_User

	GateWay_Web

### `EurekaServer	注册中心`


	EurekaServer01

	EurekaServer02


### `ConfigServer	配置中心`

	配置信息------>>  https://gitee.com/lei-yu-qi/smart_parking_config.git
		


## ` Operating environment `

	Gradle-version:7.2.0

	Redis-version:6.2.5

	Kafka-version:2.12-2.6.0

	Zookeeper-version:3.5.6

	FastDFS:5.05
	
	RabbitMQ:3.10.7
	
	Erlang:24.3
