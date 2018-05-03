<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/base.jsp"%>    
<html>
<head>
  <meta charset="utf-8">
  <title>用户主页</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="fly,layui,前端社区">
  <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
</head>
<body style="margin-top: 65px;">

<div class="fly-header layui-bg-black">
  <div class="layui-container">
    <a class="fly-logo" href="/">
      <img src="${ctx}/static/images/logo.png" alt="layui">
    </a>
    <ul class="layui-nav fly-nav layui-hide-xs">
      <li class="layui-nav-item layui-this">
        <a href="/"><i class="iconfont icon-jiaoliu"></i>交流</a>
      </li>
      <li class="layui-nav-item">
        <a href="../case/case"><i class="iconfont icon-iconmingxinganli"></i>案例</a>
      </li>
      <li class="layui-nav-item">
        <a href="http://www.layui.com/" target="_blank"><i class="iconfont icon-ui"></i>框架</a>
      </li>
    </ul>
    
    <ul class="layui-nav fly-nav-user">
           <shiro:notAuthenticated>
      <!-- 未登入的状态 -->
      <li class="layui-nav-item">
        <a class="iconfont icon-touxiang layui-hide-xs" href="../user/login"></a>
      </li>
      <li class="layui-nav-item">
        <a href="../user/login">登入</a>
      </li>
      <li class="layui-nav-item">
        <a href="../user/reg">注册</a>
      </li>
      <li class="layui-nav-item layui-hide-xs">
        <a href="#" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" title="QQ登入" class="iconfont icon-qq"></a>
      </li>
      <li class="layui-nav-item layui-hide-xs">
        <a href="#" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" title="微博登入" class="iconfont icon-weibo"></a>
      </li>
      </shiro:notAuthenticated>
      
      <shiro:authenticated>
      <!-- 登入后的状态 -->     
      <li class="layui-nav-item">
        <a class="fly-nav-avatar" href="javascript:;">
          <cite class="layui-hide-xs"><shiro:principal></shiro:principal></cite>
          <i class="iconfont icon-renzheng layui-hide-xs" title="认证信息：${sessionScope.user.info}"></i>
          <i class="layui-badge fly-badge-vip layui-hide-xs">VIP${sessionScope.user.vip}</i>
          <img src="${sessionScope.user.avatar}">
        </a>
        <dl class="layui-nav-child">
          <dd><a href="../user/set"><i class="layui-icon">&#xe620;</i>基本设置</a></dd>
          <dd><a href="../user/message"><i class="iconfont icon-tongzhi" style="top: 4px;"></i>我的消息<span class="layui-badge-dot"></span></a></dd>
          <dd><a href="../user/home"><i class="layui-icon" style="margin-left: 2px; font-size: 22px;">&#xe68e;</i>我的主页</a></dd>
          <hr style="margin: 5px 0;">
          <dd><a href="/logout" style="text-align: center;">退出</a></dd>
        </dl>
      </li>     
      </shiro:authenticated>
    </ul>
  </div>
</div>

<div class="fly-home fly-panel" style="background-image: url();">
  <img src="${user.avatar}" alt="${user.username}">
  <i class="iconfont icon-renzheng" title="Fly社区认证"></i>
  <h1>
    ${user.username}
    <c:if test="${user.sex=='男'}">
    <i class="iconfont icon-nan"></i>
    </c:if>
    <c:if test="${user.sex=='女'}">
    <i class="iconfont icon-nv"></i>
    </c:if>
    <i class="layui-badge fly-badge-vip">VIP${user.vip}</i>
    <c:forEach items="${user.roleSet}" var="r">
    <c:if test="${r.roleName=='admin'}">
     <span style="color:#c00;">（管理员）</span>
    </c:if>
    </c:forEach>
    <!--
    <span style="color:#c00;">（管理员）</span>
    <span style="color:#5FB878;">（社区之光）</span>
    <span>（该号已被封）</span>
    -->
  </h1>

  <p style="padding: 10px 0; color: #5FB878;">认证信息：${user.info}</p>

  <p class="fly-home-info">
    <i class="iconfont icon-kiss" title="飞吻"></i><span style="color: #FF7200;">${user.credit}飞吻</span>
    <i class="iconfont icon-shijian"></i><span><fmt:formatDate value="${user.createTime}"/>加入</span>
    <i class="iconfont icon-chengshi"></i><span>来自${user.address}</span>
  </p>

  <p class="fly-home-sign">${user.sign}</p>

  <div class="fly-sns" data-user="">
    <a href="javascript:;" class="layui-btn layui-btn-primary fly-imActive" data-type="addFriend">加为好友</a>
    <a href="javascript:;" class="layui-btn layui-btn-normal fly-imActive" data-type="chat">发起会话</a>
  </div>

</div>

<div class="layui-container">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md6 fly-home-jie" >
      <div class="fly-panel">
        <h3 class="fly-panel-title">${user.username} 最近的提问</h3>
        <ul class="jie-row">  
        <c:if test="${topicPage>0}">     
        <c:forEach items="${user.topics}" var="t" begin="${(topicPage-1)*10}" end="${topicPage*10-1}">
        <li>
            <span class="fly-jing">精</span>
            <a href="../topic/${t.id}" class="jie-title">${t.title}</a>
            <i><fmt:formatDate value="${t.createTime}" type="both"/></i>
            <em class="layui-hide-xs">${t.viewNo}阅/${t.replies.size()}答</em>
          </li>
        </c:forEach>
        </c:if>
        <c:if test="${user.topics.size()==0}">
        <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><i style="font-size:14px;">没有发表任何求解</i></div>
        </c:if>
          <div id="test1" style="text-align: center;"></div>
        </ul>
      </div>
    </div>
    
    <div class="layui-col-md6 fly-home-da">
      <div class="fly-panel">
        <h3 class="fly-panel-title">${user.username} 最近的回答</h3>
        <ul class="home-jieda">
        <c:if test="${replyPage>0}">
        <c:forEach items="${user.replies}" var="r" begin="${(replyPage-1)*10}" end="${replyPage*10-1}">
         <li>
          <p>
          <span><fmt:formatDate value="${r.createTime}" type="both"/></span>
          在<a href="../topic/${r.topic.id}" target="_blank">${r.topic.title}</a>中回答：
          </p>
          <div class="home-dacontent">${r.content}</div>
        </li>
        </c:forEach> 
        </c:if>
        <c:if test="${user.replies.size()==0}">
        <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><span>没有回答任何问题</span></div> 
        </c:if>  
        <div id="test2" style="text-align: center;"></div>              
        </ul>
      </div>
    </div>
  </div>
</div>

<div class="fly-footer">
  <p><a href="http://fly.layui.com/" target="_blank">Fly社区</a> 2017 &copy; <a href="http://www.layui.com/" target="_blank">layui.com 出品</a></p>
  <p>
    <a href="http://fly.layui.com/jie/3147/" target="_blank">付费计划</a>
    <a href="http://www.layui.com/template/fly/" target="_blank">获取Fly社区模版</a>
    <a href="http://fly.layui.com/jie/2461/" target="_blank">微信公众号</a>
  </p>
</div>
<script>
layui.use('laypage', function(){
	  var laypage = layui.laypage;	  
	  //执行一个laypage实例
	  laypage.render({
	    elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
	    ,count: ${user.topics.size()} //数据总数，从服务端得到
	    ,limit:10
	    ,curr: ${topicPage} //获取起始页
	    ,layout:['prev', 'page', 'next','count','skip']
	  	,jump: function(obj, first){	  	    	  	 
	  	    //首次不执行
	  	    if(!first){
	  	      //do something
	  	    	// window.location.href="/user/home?topicPage="+obj.curr
	  	    	window.location.search = "?uid="+${user.userId}+"&topicPage="+obj.curr
	  	    }
	  	  }
	  });
	  laypage.render({
		    elem: 'test2' //注意，这里的 test1 是 ID，不用加 # 号
		    ,count: ${user.replies.size()} //数据总数，从服务端得到
		    ,limit:10
		    ,curr: ${replyPage} //获取起始页
		    ,layout:['prev', 'page', 'next','count','skip']
		  	,jump: function(obj, first){	  	    	  	 
		  	    //首次不执行
		  	    if(!first){
		  	      //do something
		  	    	// window.location.href="/user/home?replyPage="+obj.curr
		  	    	window.location.search = "?uid="+${user.userId}+"&replyPage="+obj.curr
		  	    }
		  	  }
		  });
	});
layui.cache.page = 'user';
layui.cache.user = {
  username: '游客'
  ,uid: -1
  ,avatar: '${ctx}/static/images/avatar/00.jpg'
  ,experience: 83
  ,sex: '男'
};
layui.config({
  version: "3.0.0"
  ,base: '${ctx}/static/mods/'
}).extend({
  fly: 'index'
}).use(['fly', 'face'], function(){
	  var $ = layui.$
	  ,fly = layui.fly;
	  //如果你是采用模版自带的编辑器，你需要开启以下语句来解析。
	  
	  $('.home-dacontent').each(function(){
	    var othis = $(this), html = othis.html();
	    othis.html(fly.content(html));
	  });
	  
});
</script>


</body>
</html>