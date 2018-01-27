# WithMe 1.0 Alpha
&nbsp;&nbsp;这个项目是使用JavaEE开发的一个多人在线即时聊天系统。
## 运行截图
![聊天](http://ou7jocypf.bkt.clouddn.com/18-1-27/37562533.jpg)
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
