# -java&arcSoft- 

- windows 

- 1.拷贝arcsoft_lib至D盘 
- 2.1移至https://www.arcsoft.com.cn/ 获取 appId、sdkKey 
- 2.2 将arcsoft下载下来的sdk中的 libarcsoft_face.dll、libarcsoft_face_engine.dll、libarcsoft_face_engine_jni.dll 拷贝至D:/arcsoft_lib 文件夹下
- 3.代码相关配置  app...yml  
- 4.执行(1)文件夹中的 f_face、f_user .sql 
- 5.Run  访问ip:port/index

- #运行完成 拍照获取报错无法找到相关依赖时  安装arcsoft_lib 文件夹中的vcredist_x64.exe（依赖） 


- 流程
- 1.输入姓名 点击开始拍照
- 2.注册
- 3.输入姓名和拍照进行登录 或 直接拍照登录不输入姓名,通过识别人脸比对数据库中的人脸获取用户名

