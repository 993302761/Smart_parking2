# `Smart_parking2.8`
<a href="https://www.oscs1024.com/project/oscs/993302761/Smart_parking2.0?ref=badge_small" alt="OSCS Status"><img src="https://www.oscs1024.com/platform/badge/993302761/Smart_parking2.0.svg?size=small"/></a>


##  	`   Brief introduction   `

####   	The product is based on the data, collection and analysis of the Internet of Things and artificial intelligence to solve the problems of difficult parking in modern cities, difficult to find parking spaces, and unbalanced utilization of urban parking spaces.


####   The system shows the remaining parking spaces in various places in the city to users through mobile phone APP and makes corresponding route planning for users. Different preferential charging policies are formulated for parking spaces according to different durations.


####   	The image recognition technology is used to identify the license plate number, and the user's integrity is determined by recording the parking space and identifying the license plate number. Based on the above data, the parking of target visitors is planned to promote the balance of urban parking space utilization.  




## `OSCS`


<a href="https://www.oscs1024.com/project/oscs/993302761/Smart_parking2.0?ref=badge_large" alt="OSCS Status"><img src="https://www.oscs1024.com/platform/badge/993302761/Smart_parking2.0.svg?size=large"/></a>



##  `	 Market environment analysis    `

####   		For the development of intelligent parking industry, seven departments including the National Development and Reform Commission jointly issued the Guiding Opinions on Strengthening the Construction of Urban Parking Facilities as early as 2015. Subsequently, policies related to smart parking industry were introduced intensively to actively support and promote the development of smart parking.

####   		The intelligent parking space management project under the intelligent city is to use wireless communication technology, GIS technology, GPS technology and other Internet of Things and information technologies to achieve real-time update, query and management of urban parking space resources, and achieve the tripartite optimization of convenient car owners, efficient parking lot, and convenient urban transportation and management. Based on wireless communication, GIS, GPS and other technologies, it can realize functions such as intelligent parking space recommendation, map guidance for car owners to park, real-time management to view the distribution of urban parking spaces, which saves the labor cost of the current traditional parking industry, and solves the problem of urban parking to a certain extent.

####   		This project is in response to the Several Opinions of the CPC Central Committee and the State Council on Further Strengthening the Management of Urban Planning and Construction and the Provisions on Economical and Intensive Use of Land and other documents, to achieve reasonable allocation of parking facilities, improve space utilization efficiency and promote the economical and intensive use of land; Make full use of the ground and underground space, and promote the multi-functional three-dimensional development and composite utilization of construction land; Encourage the participation of social capital, accelerate the construction of urban parking, and gradually alleviate the parking problem.


##  ` Module introduction `

###  `smart_parking_server	总服务模块`
	
 
	module_Administrators   超级管理员模块

	module_Order	           订单模块
	
	module_Integral	          积分模块

	module_Parking_lots     停车场管理员模块

	module_User                用户模块

	module_Vehicle           车辆信息模块

###  `GateWay       	网关`

	GateWay_User

	GateWay_Web

###  `EurekaServer	注册中心`


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
	

## `  Basic configuration  `

	File | Settings | Build, Execution, Deployment | Build Tools | Gradle  
	
			Build and run using   ----> IntelliJ IDEA
			Run tests using   ----> IntelliJ IDEA
