# WithMe 1.0 Alpha
&nbsp;&nbsp;这个项目是使用JavaEE开发的一个多人在线即时聊天系统。

## 新版本开发
因为这个版本是我几年前开发的，所以目前不做维护和更新。目前我正在使用微服务开发的模式，使用Netty作为websocket服务器，使用redis作为缓存等方式以提高聊天系统的可扩展性，并发性等各方面性能，同时也开发新的功能。欢迎大家关注以及共同开发～  
**WithMe3.0目前基本功能已经正常，在各方面都进行了优化，建议直接使用WithMe3.0.**
项目地址： https://github.com/IcedSoul/WithMe3.0

## 运行说明
&emsp;请按照以下方式来让此项目在你的电脑上运行。

```
1. 使用git命令行执行以下命令将项目下载到本地（需安装Git客户端）

 https://github.com/IcedSoul/WithMe
 
2. 打开WithMe/WithMe 2.0 Alpha/JavaEE(Server&Client)/WithMe,这个目录为项目的根目录（后面说的根目录都是指的这个目录）。当前目录下src/main/resources/withme.sql为建表语句。确认自己本地MySQL已经安装并且正常运行，使用navicat或者命令行在MySQL使用WithMe.sql里面语句新建对应数据库和表。

3. 修改项目根目录下src/main/resources/properties下面database.properties文件，将数据库连接地址，用户名和用户密码修改为自己使用的MySQL数据库。

4. 在命令行下进入项目根目录，执行一下命令（需要先安装Maven工具并且配置好环境变量，如未安装可以百度&Google安装方式。）

mvn clean package -DskipTests

5. 执行成功后即可在根目录下target文件夹下看到WithMe.war文件，将这个文件复制到Tomcat安装（也就是解压）目录下的webapps文件夹内，然后重启tomcat即可。（需下载并且解压Tomcat，版本最好为8.0或以上）

6. 在浏览器访问http://localhost:8080/WithMe来查看效果。

7. 测试请使用两个浏览器注册并且登录两个账号，搜索彼此添加好友来进行聊天。
```

## 运行截图
![聊天](http://img.icedsoul.cn/img/blog/withme/show.png)
## 主要功能
&nbsp;&nbsp;1.注册/登录<br/>
&nbsp;&nbsp;2.搜索用户，查看用户信息，发送好友申请，同意好友申请<br/>
&nbsp;&nbsp;3.单人即时聊天<br/>
&nbsp;&nbsp;4.建立群组，邀请好友入群，查看群组成员<br/>
&nbsp;&nbsp;5.群组聊天<br/>
## 采用框架及协议
&nbsp;&nbsp;使用JavaEE框架：Spring+SpringMVC+Hibernate<br/>
&nbsp;&nbsp;前端：Bootstrap+Layer.js插件<br/>
&nbsp;&nbsp;通信：使用JavaEE 7.0 WebSocket协议<br/>
此处请注意：WithMe1.0 Alpha 版本所使用的 WebSocket 为 JavaEE7.0（及以上）自带的 javax.websocket 类库，WebSocket版本为JSR356，无法实现与 Android 端以及桌面端通信的功能。<br/>
## 说明
&nbsp;因为使用的WebSocket协议只支持Web端，本项目只有web端。但是，我们会基于 WithMe 1.0 Alpha 继续开发 WithMe 2.0 Alpha 版本，在在2.0中，我们将采用新的WebSocket协议，实现web端、Android端以及桌面端的互相通信。<br/>
***
# WithMe 2.0 Alpha
&nbsp;&nbsp;WithMe2.0优化了WithMe1.0仅支持Web端的问题，采用了新版本的Java-WebSocket，实现了与Android端和Java桌面端的即时通信。<br/>
## 主要功能
&nbsp;&nbsp;功能同WithMe1.0，添加了Android端单聊和群聊功能。<br/>
## 采用框架及协议
&nbsp;&nbsp;框架和协议与WithMe1.0相同。<br/>
&nbsp;&nbsp;WebScoket由JavaEE7.0的javax.websocket变为Java-Websocket。<br/>
