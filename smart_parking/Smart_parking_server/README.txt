 `Smart_parking_server`

    数据库：
            Smart_parking_data     用户信息
                 User_information               //用户表
                 Vehicle_information            //车辆信息表
                 Parking_lot_information        //停车场信息
                 Administrators                 //超级管理员

            Smart_parking_order    订单信息
                 Order_information                  //订单信息表



----------------------------------------------------------------------------------------------------------------





 `Smart_parking_data	用户信息库`

    User_information        //用户表
            {
            user_name：用户名
            password：密码
            user_id：身份证号
            integral：积分
            }


    Vehicle_information     //车辆信息表
            {
            user_name：用户名
            user_id：身份证号
            license_plate_number：车牌号
            vehicle_photos ：车辆照片路径
            registration：机动车登记证照片路径
            driving_permit ：车辆行驶证照片路径
            }


    Parking_lot_information            //停车场信息
            {
            pctr_id：停车场管理员账号
            pctr_password：停车场管理员密码
            parking_lot_name：停车场名
            parking_in_the_city :停车场所在城市
            parking_lot_number：停车场编号
            parking_spaces_num：车位数量
            billing_rules：计费规则  (元/小时)
            longitude：经度
            latitude：纬度
            }


   Administrators                  //超级管理员
            {
            ctr_id：管理员账号
            ctr_password：管理员密码
            }













 `Smart_parking_data	订单信息库`



        Order                  //订单信息表
        {
         order_number:订单编号
         order_information :订单信息
             {   generation_time：订单生成时间
                 user_name：用户名
                 inTime：进入时间
                 outTime：离开时间
                 parking_lot_name：停车场名
                 parking_lot_number ：停车场编号
                 license_plate_number ：车牌号
                 payment_amount ：支付金额
                 order_status :订单状态
                 }
        }



----------------------------------------------------------------------------------------------------------------






 `建库建表语句`

用户信息库


    create database Smart_parking_data;




用户表

    create table User(user_name varchar(20) primary key,
                      password varchar(50) not null,
                      user_id varchar(20) not null,
                      integral int not null);

    insert into User values("123","123","1234568789",0),("18153301670","lyq2001124","435481224685221384",0);




车辆信息表

    create table Vehicle_information(user_name varchar(20) not null,
                                     user_id varchar(20) not null,
                                     license_plate_number varchar(20) not null,
                                     vehicle_photos varchar(60) not null,
                                     registration varchar(60) not null,
                                     driving_permit varchar(60) not null)ENGINE=InnoDB DEFAULT CHARSET=utf8;




停车场信息

    create table Parking_lot_information(pctr_id varchar(20) primary key,
                                         pctr_password varchar(50) not null,
                                         parking_lot_name varchar(50) not null,
                                         parking_in_the_city varchar(50) not null,
                                         parking_lot_number varchar(20) not null,
                                         parking_spaces_num int not null,
                                         billing_rules float not null,
                                         longitude varchar(20) not null,
                                         latitude varchar(20)  not null)ENGINE=InnoDB DEFAULT CHARSET=utf8;


    insert into Parking_lot_information values("123","123","天津市xx停车场","天津","1001",10,5.0,"39.064976","117.302938"),
                                           ("456","456","北京市xx停车场","北京","1002",20,10.0,"39.821263","117.442708");




超级管理员


    create table Administrators(ctr_id varchar(20) primary key,
                                ctr_password varchar(50) not null);


    insert into Administrators values("123","123");






----------------------------------------------------------------------------------------------------------------






订单信息库
    create database Smart_parking_order;


订单信息表

         create table Order_information( order_number varchar(50)  primary key,
                                      generation_time datetime(0) not null,
                                      user_name varchar(20) not null,
                                      inTime datetime(0),
                                      outTime datetime(0),
                                      parking_lot_number varchar(20) not null,
                                      license_plate_number varchar(20) not null,
                                      payment_amount float,
                                      order_status varchar(20) not null)ENGINE=InnoDB DEFAULT CHARSET=utf8;









