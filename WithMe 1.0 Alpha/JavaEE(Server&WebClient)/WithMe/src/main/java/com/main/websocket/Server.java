package com.main.websocket;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.main.entity.Message;
import com.main.entity.User;
import com.main.service.MessageService;
import com.main.service.UserService;

/*
 * 通信模块实现过程：
 * 1.进行Java Socket TCP和UDP，客户端与服务端通信的学习
 * 2.准备在已经实现登录注册添加好友功能项目的基础上使用Java Socket和ServerSocket实现通信，使用TCP
 * 3.在服务端使用多线程，可以实现响应多个客户端的基础上，已经将代码成功添加到项目中
 * 4.将代码整合到Service中，整合完成之后因为SpringMVC管理的特性出现错误，因此改回正确之后准备在Controller中直接使用Server和Client类
 * 5.测试正常，但是有以下问题：
 * 		（1）服务端无法随Tomcat服务器启动而开启
 * 		（2）服务端无法区分不同的客户端（）
 * 		（3）测试仅为固定消息，如果需要从网页获取用户输入信息需要对代码进行简单修改（简单）
 * 6.准备依次解决以上问题，网上查找博客，多次试验解决问题5.（1），第一个问题有两种解决方法
 * 		（1）自定义一个Listener，在web.xml配置之后，写一个类实现ServletContextListener，在类中开启ServerSocket
 * 		（2）自定义一个Servlet，在web.xml配置之后，继承HttpServlet，在init()方法中开启ServerSocket
 * 7.经过测试，6.（1）实现失败，转而使用6.（2），成功配置Servlet，使ServerSocket可以随Tomcat启动而开启
 * 8.6.（2）虽然成功，但是产生了新的问题，因为ServerSocket accept()方法进行监听之后会阻塞线程，导致整个Tomcat服务器挂起
 * 9.经过查找之后解决了问题8，一个解决方法为在HttpServlet的init方法中不直接开启ServerSocket监听，而是使用继承Thread（实现Runable不行）
 * 		将这个线程设置为保护线程Thread setDaemon(true)，这样线程里面阻塞不会影响Tomcat启动，而后台能够一直保持监听
 * 10.问题8解决之后又产生了新的问题，无法关闭ServerSocket监听，端口一直被占用，Tomcat服务器关闭ServerSocket仍在运行，
 * 		导致一次启动之后除非重启电脑否则端口一直被占用，无法再次正常启动Tomcat，使项目无法正常进行
 * 11.寻找问题10解决方法 失败，网上难以找到解决方法，自己思考也没有找到解决办法
 * 12.简单查看了一下问题5.（2）的解决办法，据说可以通过端口、获取的ip地址等方法区分，但是因为此方法已经无法继续，所以没有具体实现，不知道可行性。
 * 13.重新查找资料，决定采用HTML5新的协议WebSocket来实现通信功能，查看了WebSocket和Socket的区别
 * 14.WebSocket与SpringMVC结合也有两种不同的实现方法：
 * 		（1）使用websocket-api，建立WebSocketHanlder和HandShakeInterceptor以及WebSocketConfig类进行配置，并且使用sockjs在前端页面建立客户端
 * 		（2）使用JavaEE 7.0的ServerEndpoint实现WebSocket
 * 15.初步决定采用14.（1）的方法，在查找了大量资料，对第一种方法的实现框架有了基本的认识之后将其在项目中进行了实现
 * 16.最终代码工作基本完成，但是运行时一直报一个bean的错误，多方查找发现缺少一个依赖包，网上找资料发现只有maven依赖的代码，根本找不到该jar包的下载
 * 17.为此重新建立了一个简单的maven测试项目，将通信模块的代码复制过去之后能够成功运行了，但是结果竟然浏览器不支持。网上资料说明Chrome是支持的，因此判定为代码问题
 * 18.再次查找资料，修改代码之后，不会报浏览器不支持，但是新的错误出现了，一直处于未连接服务器状态。
 * 19.再次查找资料，无果。
 * 20.借此机会，对项目结构进行完全重构，使用maven进行依赖管理，具体变化见pom.xml开头注释
 * 21.决定采用14.（2）方法实现通信。
 * 22.查找资料完成代码后，14.（2）方法也出现了与14.（1）方法相同的错误，连接服务器错误，预估为配置错误
 * 23.仍不排除配置错误可能性，查找许久，有人说是新建WebSocket时路径错误，目前已初步排除此错误可能性。（路径确认无误）
 * 		已经根据浏览器开发者工具确定错误位于新建WebSocket位置即 websocket = new WebSocket(ws)处
 * 24.再次经过查看众多网友的Websocket遇到404时的解决办法，将错误归为以下几类：
 * 		（1）JavaEE版本低于7.0（其实这个很早就会报错了）
 * 		（2）新建websocket时路径错误，比如说没有加项目名称，或者是与@ServerEndpoint之后的名称不对应
 * 		（3）web.xml或者spring配置了拦截器，拒绝了访问请求
 * 		（4）Tomcat版本问题（7和8的不同，Tomcat7内部有Tomcat-WebSocket包，Maven部署时会产生jar包冲突）
 * 			需要引入本身的依赖包，Tomcat8可以正常运行
 * 	 这里的错误就是第四种错误，在网上没有直接的说Tomcat版本会导致404的说法，因为很多博客使用Tomcat7都正常运行了。
 * 	 但是留意到一篇博客下面有人说用Tomcat8正常了，但是用Tomcat7的人产生了与我一样的错误，所以由此推断出Tomcat版本
 * 	 有影响。具体原因应该就是Tomcat7或者8对websocket-api支持不同，但是详细的机理还不是很清楚，留待以后研究
 * 25.简单例子测试成功（阶段性标志），接下来开始编写代码
 * 		http://www.cnblogs.com/xdp-gacl/p/5193279.html?utm_source=tuicool&utm_medium=referral
 * 26.遇到问题：如何在websocket方法中获取httpsession，从而拿到当前用户信息。方法如下：
 * 		http://www.cnblogs.com/zhaoww/p/5119706.html?utm_source=tuicool&utm_medium=referral
 * 27.确定了使用@ClientEndpoint后台实现客户端的可行性（对于JavaSE桌面端以及Android端重要），资料如下：
 * 		http://www.cnblogs.com/akanairen/p/5616351.html
 * 28.下一步任务如下：
 * 		（1）将wesocket-client.js设置为静态资源并且正常引用
 * 		（2）修改wesocket-client.js使用户登陆之后可以向服务端发送消息
 * 		（3）修改wesocket-client.js和Server.java使用户登陆之后可以接收服务端消息
 * 		（4）进行数据json格式设计，修改Server.java使其完成转发功能
 * 		（5）前端页面设计，完成通信功能
 * 29.继续任务（date：2017/1/22）静态资源配置成功，事实证明，放在resourse文件夹下面配置不可达（可能是因为路径问题），
 * 		放在webapp下面静态资源就可以正常访问。28.（1）完成，进行28.（2）。
 * 30.当js放在外部引入时部分函数会失效，现在不必放入外部，留待以后整理。现在开始28.（2），js暂且放在内部
 * 31.已完成登陆之后向客户端发送数据功能28.（2）完成，继续28.（3）
 * 32.已完成客户端从服务端接收消息的功能，接下来进行json数据数据设计
 * 33.json数据设计完成，客户端转发功能完成，可以实现一对一聊天（又一个阶段性标志），但是存在以下问题：
 * 		（1）仅仅在双方都在线时可以互相聊天，当发消息给不在线的人的时候websocket会异常关闭
 * 		（2）群聊未实现（但是预留了实现途径，不麻烦）
 * 		（3）前端以及好友列表未实现
 * 		（想到在线还有列表有一种实现方式：每当自己上线时就给自己的好友发送一条“消息”，更新自己在好友处的在线状态。具体实现方法有待以后查找资料）
 * 34.下一步目标
 * 		（1）实现好友列表（即时在线不在线），同时实现前端
 * 		（2）不在线好友即便发送信息也不会关闭，而是在该好友上线之后发送至好友处
 * 		（3）新建聊天记录表并将聊天记录存储至数据库
 * 	在实现这些功能之前，需要先做一件事：
 * 		重新构建数据库，重构User表，重新选用主键，添加状态，修改之前先做备份。
 * 35.修改完成
 * 2017/1/22
 * 
 * 2017/1/30
 * 36.前端工作已经差不多，现在开始完善后端
 * 37.异步通信代码已完成，开始测试。
 */
@ServerEndpoint(value="/server",configurator=GetHttpSessionConfigurator.class)
public class Server {
	 //静态变量，用于记录当前在线人数
	 private static int onlineCount = 0;
	 //存放每个客户端对应的Server对象，可以考虑使用Map来代替，key作为用户标识
	 private static CopyOnWriteArraySet<Server> server = new CopyOnWriteArraySet<Server>();
	 //表示与某个用户的连接会话，通过它给客户端发送数据
 	 @SuppressWarnings("unused")
	 private Session session;
	 //用户id
	 private String userId;
	 //request的session，用于获取用户信息
	 private HttpSession httpSession;
	 //在线列表
	 @SuppressWarnings("rawtypes")
	 private static List list = new ArrayList<>();
	 //用户名和websocket的session绑定的路由表
	 @SuppressWarnings("rawtypes")
	 private static Map routeTable = new HashMap<>();
	 //这里无法注入service，只能通过getBean的方式来获取service
	 private MessageService messageService=(MessageService)ContextLoader.getCurrentWebApplicationContext().getBean("messageService");
	 private UserService userService=(UserService)ContextLoader.getCurrentWebApplicationContext().getBean("userService");
	 /**
	     * 连接建立成功调用的方法
	     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	     * @param config 获取HttpSession的参数
	     */
	    @SuppressWarnings("unchecked")
		@OnOpen
	    public void onOpen(Session session, EndpointConfig config){
	        this.session = session;
	        //加入server中
	        server.add(this);     	
	        //在线人数一
	        addOnlineCount();
	        //获取httpSession
	        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
	        //获取当前登录用户的id（转为String类型）
	        this.userId=String.valueOf(((User)httpSession.getAttribute("currentUser")).getUserId());
	        //将用户id加入在线列表(这个主要是给控制台看的)
	        list.add(userId);
	        //将用户的在线状态置1
	        User user = userService.getUser(Integer.valueOf(userId));
	        user.setUserIsOnline(1);
	        userService.updateUser(user);
	        //将用户名和session绑定到路由表
	        routeTable.put(userId, session);
	        //检查自己是否有未接收的消息
	        List<Message> messageList = new ArrayList<Message>();
	        messageList =messageService.getMessageUnReceive(Integer.valueOf(userId));
		    if(messageList!=null){
	        	for(int i=0;i<messageList.size();i++){
		        	Message message = (Message)messageList.get(i);
		        	String jsonMessage = getMessage(message);
		        	singleSend(jsonMessage,(Session) routeTable.get(userId));
		        	message.setIsTransport(1);
		        	messageService.updateMessage(message);
		        }
		    }
	    }

	    /**
	     * 连接关闭调用的方法
	     */
	    @OnClose
	    public void onClose(){
	    	//从server中删除当前对象
	        server.remove(this);
	        //在线人数减1
	        subOnlineCount();
	        //从在线列表移除这个用户
	        list.remove(userId);
	        //将用户的在线状态设为0
	        User user = userService.getUser(Integer.valueOf(userId));
	        user.setUserIsOnline(0);
	        userService.updateUser(user);
	        //从路由表中删除当前用户
	        routeTable.remove(userId);
	    }

	    /**
	     * 
	     * 接收客户端的jsonMessage,存储数据库并且判断当前聊天用户是否在线，
	     * 在线则服务器直接转发并且记录已经转发，不在线则记录未转发，
	     * 然后将这条数据存储至数据库
	     * 
	     */
	    @OnMessage
	    public void onMessage(String jsonMessage) throws IOException {
	    	System.out.println("我收到了客户端的消息:"+jsonMessage);
		    Message message = new Message();
			//存储数据库
			JSONObject jsonObjectMessage = JSON.parseObject(jsonMessage);
			message.setFrom(jsonObjectMessage.getIntValue("from"));
			message.setContent(jsonObjectMessage.getString("content"));
			message.setType(jsonObjectMessage.getIntValue("type"));
			message.setTime(Timestamp.valueOf(jsonObjectMessage.getString("time")));
			JSONArray users =  jsonObjectMessage.getJSONArray("to");
			//发送给自己,不过发之前先判断一下是普通消息还是通知性消息，通知性消息不必给自己发回去了，服务器直接转发
			if(jsonObjectMessage.getIntValue("type") == 0){
				String self = String.valueOf(jsonObjectMessage.get("from"));
				singleSend(jsonMessage, (Session) routeTable.get(self));
			}
			if(jsonObjectMessage.getIntValue("type") == 1){
				message.setTo(users.getInteger(0));
				message.setType(2);
				message.setIsTransport(1);
				messageService.addMessage(message);
				message.setType(1);
			}
			for(int i = 0;i < users.size();i++){
				if(!(jsonObjectMessage.getIntValue("type") == 1 && i==0)){
				   String user = String.valueOf(users.get(i));
	//			   if(!users.get(i).equals(jsonObjectMessage.get("from"))){
				       	//分别发送给每个指定用户
					   message.setTo(Integer.valueOf(user));
					   	//先判断该用户是否在线,在线则发送并且设置发送状态为1，不在线则直接设置发送状态为0，并且添加到数据库
					   Session singleSession = (Session)routeTable.get(user);
					   if(singleSession!=null && !singleSession.equals("")){
						   singleSend(jsonMessage, singleSession);
						   message.setIsTransport(1);
						   messageService.addMessage(message);
					   }
					   else{
						   message.setIsTransport(0);
						   if(message.getType()!=3 && message.getType()!=4)
							   messageService.addMessage(message);
					   }
				}
			 }
//			}
	}

	    /**
	     * 发生错误时调用
	     * @param error
	     */
	    @OnError
	    public void onError(Throwable error){
	        error.printStackTrace();
	    }

	    /**
	     * 广播消息
	     * @param message
	     */
	    public void broadcast(String message){
	    	System.out.println("I will broadcast this message"+message);
	    }

	    /**
	     * 对特定用户发送消息
	     * @param message
	     * @param session
	     */
	    public void singleSend(String message, Session session){
	        try {
	            session.getBasicRemote().sendText(message);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * 根据Message实体组装Json格式的数据返回给前台
	     */
		public String getMessage(Message message){
			//使用JSONObject方法构建Json数据
	        JSONObject jsonObjectMessage = new JSONObject();
	        jsonObjectMessage.put("from", String.valueOf(message.getFrom()));
	        jsonObjectMessage.put("to", new String[] {String.valueOf(message.getTo())});
	        jsonObjectMessage.put("content", String.valueOf(message.getContent()));
	        jsonObjectMessage.put("type", String.valueOf(message.getType()));
	        jsonObjectMessage.put("time", message.getTime().toString());
	        return jsonObjectMessage.toString();
	    }

	    public int getOnlineCount() {
	        return onlineCount;
	    }

	    public void addOnlineCount() {
	        Server.onlineCount++;
	    }

	    public void subOnlineCount() {
	        Server.onlineCount--;
	    }

}

