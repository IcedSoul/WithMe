<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>WithMe</title>
    <link href="${cp}/img/logo.ico" rel="icon"  type="image/x-ico" /> 
    <link href="${cp}/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${cp}/css/animate.css" rel="stylesheet"/>
    <link href="${cp}/css/style.css" rel="stylesheet"/>

    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body> 
    <div class="downLoad1 animated fadeInDown">
      <a href="#" class="pcLink">
        PC版下载
      </a>
    </div>
    <div class="downLoad2 animated fadeInDown">
      <a href="#" class="appLink">
        APP下载
      </a>
    </div>
    <div class="vertical">
      <div class="box1">
        <div class="horizontal">
          <div class="box2">
            <img class="part1 animated bounceInDown" src="${cp}/img/part1.png">
            <img class="part2 animated bounceInLeft" src="${cp}/img/part2.png">
            <img class="part3 animated bounceInRight" src="${cp}/img/part3.png">
            <div class="button-style1 animated rotateInUpLeft">  
                <a href="${cp}/login" class="lk1">  
                    <div class="link1">  
                        <div class="bt1">  
                            登录  
                        </div>  
                    </div>  
                </a>      
            </div>  
            <div class="button-style2  animated rotateInUpRight">  
                <a href="${cp}/register" class="lk2">  
                    <div class="link2">  
                        <div class="bt1">  
                            注册
                        </div>  
                    </div>  
                </a>   
            </div> 
          </div>
        </div>
      </div>
      </div>
    <script src="${cp}/js/jquery.min.js"></script>
    <script src="${cp}/js/bootstrap.min.js"></script>
  </body>
</html>