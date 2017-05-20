<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>登录</title>
    <link href="${cp}/img/logo.ico" rel="icon"  type="image/x-ico" /> 
    <link href="${cp}/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${cp}/css/animate.css" rel="stylesheet"/>
    <link href="${cp}/css/style.css" rel="stylesheet"/>
    <link href="${cp}/css/login.css" rel="stylesheet"/>
    
	<script src="${cp}/js/jquery.js" type="text/javascript"></script>
    <script src="${cp}/js/jquery.min.js" type="text/javascript"></script>
    <script src="${cp}/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${cp}/js/layer.js" type="text/javascript"></script>
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <div class="jump animated fadeInDown">
      <a href="${cp}/register" class="jumpLink">
        注册
      </a>
    </div>
    <div class="wrapper animated flipInY">
      <div class="container offsetUps">
        <img class="s-logo" src="${cp}/img/s-logo.png" alt="With Me">
        
        <div class="form">
          <input id="userName" type="text" placeholder="用户名">
          <input id="userPassword" type="password" placeholder="密码">
          <button onclick="checkLogin()">登录</button>
        </div>
      </div>
    </div>
    <script type="text/javascript">
    	function checkLogin(){
    		var user = {}; 
    		user.userName = document.getElementById("userName").value;
    		user.userPassword = document.getElementById("userPassword").value;
    		if(user.userName == ''){
    			layer.msg('用户名不能为空',{icon:2});
    			return;
    		}
    		else if(user.userName.length >= 12){
    			layer.msg('用户名长度不能超过12个字符',{icon:2});
    			return;
    		}
    		else if(user.userPassword == ''){
    			layer.msg('密码不能为空',{icon:2});
    			return;
    		}
    		var loginResult = null;
    		$.ajax({
				async : false, //设置同步
				type : 'POST',
				url : '${cp}/doLogin',
				data : user,
				dataType : 'json',
				success : function(result) {
					loginResult = result.result;
				},
				error : function(result) {
					layer.alert('查询用户错误');
			}
			});
			if(loginResult == 'success'){
				layer.msg('登录成功',{icon:1});
				window.location.href="${cp}/main";
			}
			else if(loginResult == 'unexist'){
				layer.msg('是不是用户名记错了？',{icon:2});
			}
			else if(loginResult == 'wrong'){
				layer.msg('密码不对哦，再想想~',{icon:2});
			}
			else if(loginResult == 'fail'){
				layer.msg('服务器异常',{icon:2});
			}
    	}
    </script>
  </body>
</html>