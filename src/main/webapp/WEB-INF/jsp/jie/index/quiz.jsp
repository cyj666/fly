<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../common/base.jsp"%>
<html>
<head>
  <meta charset="utf-8">
  <title>Fly社区</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="renderer" content="webkit">
  <link rel="shortcut icon" href="${ctx}/static/images/myweb.ico">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="keywords" content="fly,layui,前端社区">
  <meta name="description" content="Fly社区,欢迎。">
</head>

<body>

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
        <a href="${ctx}/case/case"><i class="iconfont icon-iconmingxinganli"></i>案例</a>
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
          <img src="${user.avatar}" alt="${user.username}">
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

<div class="fly-panel fly-column">
  <div class="layui-container">
    <ul class="layui-clear">
      <li><a href="/">首页</a></li> 
      <li class="layui-hide-xs layui-this"><a href="${ctx}/jie/index/quiz?n=0">提问</a></li> 
      <li><a href="${ctx}/jie/index/share">分享<span class="layui-badge-dot"></span></a></li> 
      <li><a href="${ctx}/jie/index/discuss?n=0">讨论</a></li> 
      <li><a href="${ctx}/jie/index/suggest?n=0">建议</a></li> 
      <li><a href="${ctx}/jie/index/notice?n=0">公告</a></li> 
      <li><a href="${ctx}/jie/index/news?n=0">动态</a></li> 
      <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><span class="fly-mid"></span></li> 
      
      <shiro:authenticated>
      <!-- 用户登入后显示 -->
      <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="${ctx}/user/index">我发表的贴</a></li> 
      <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="${ctx}/user/index#collection">我收藏的贴</a></li> 
      </shiro:authenticated>
    </ul> 
    
    <div class="fly-column-right layui-hide-xs"> 
      <span class="fly-search"><i class="layui-icon"></i></span> 
      <a href="../add" class="layui-btn">发表新帖</a> 
    </div> 
    <div class="layui-hide-sm layui-show-xs-block" style="margin-top: -10px; padding-bottom: 10px; text-align: center;"> 
      <a href="../add" class="layui-btn">发表新帖</a> 
    </div> 
  </div>
</div>

<div class="layui-container">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md8"> 


      <div class="fly-panel" style="margin-bottom: 0;">
        
        <div class="fly-panel-title fly-filter">
        <c:if test="${n==1}">
          <a href="?n=0">综合</a>
          <span class="fly-mid"></span>
          <a href="?n=1" class="layui-this">未结</a>
          <span class="fly-mid"></span>
          <a href="?n=2">已结</a>
          <span class="fly-mid"></span>
          <a href="?n=10">精华</a>
        </c:if>
        <c:if test="${n==2}">
          <a href="?n=0">综合</a>
          <span class="fly-mid"></span>
          <a href="?n=1">未结</a>
          <span class="fly-mid"></span>
          <a href="?n=2" class="layui-this">已结</a>
          <span class="fly-mid"></span>
          <a href="?n=10">精华</a>
        </c:if>
        <c:if test="${n==10}">
          <a href="?n=0">综合</a>
          <span class="fly-mid"></span>
          <a href="?n=1">未结</a>
          <span class="fly-mid"></span>
          <a href="?n=2">已结</a>
          <span class="fly-mid"></span>
          <a href="?n=10" class="layui-this">精华</a>
        </c:if>        
         <c:if test="${n!=1&&n!=2&&n!=10}">
          <a href="?n=0" class="layui-this">综合</a>
          <span class="fly-mid"></span>
          <a href="?n=1">未结</a>
          <span class="fly-mid"></span>
          <a href="?n=2">已结</a>
          <span class="fly-mid"></span>
          <a href="?n=10">精华</a>
        </c:if>                  
        </div>

        <ul class="fly-list">          
        <c:forEach items="${topics}" var="a"  begin="${(page-1)*10}" end="${page*10-1}">
        	 <li>
            <a href="user/home" class="fly-avatar">
              <img alt="${a.user.username}" src="${a.user.avatar}">
            </a>
            <h2>
            <a class="layui-badge">提问</a>
              <a href="../topic/${a.id}">${a.title}</a>
            </h2>
            <div class="fly-list-info">
              <a href="user/home" link>
                <cite>${a.user.username}</cite>                
                <i class="iconfont icon-renzheng" title="认证信息：${a.user.info}"></i>
                <i class="layui-badge fly-badge-vip">VIP${a.user.vip}</i>                
              </a>
              <span><fmt:formatDate value="${a.createTime}" type="both"/>  </span>
              
              <span class="fly-list-kiss layui-hide-xs" title="悬赏飞吻"><i class="iconfont icon-kiss"></i>${a.reward}</span>
              <!--<span class="layui-badge fly-badge-accept layui-hide-xs">已结</span>-->
              <span class="fly-list-nums"> 
                <i class="iconfont icon-pinglun1" title="回答"></i> ${a.replies.size()}
              </span>
            </div>
            <div class="fly-list-badge">
           		<c:forEach items="${a.tabs}" var="t">
           			<c:if test="${t.id=='1'}">
           			<span class="layui-badge layui-bg-green">未结</span>
           			</c:if>
           			<c:if test="${t.id=='2'}">
           			<span class="layui-badge layui-bg-blue">已结</span>
           			</c:if>
           			<c:if test="${t.id=='10'}">
           			<span class="layui-badge layui-bg-blue">精贴</span>
           			</c:if>
           		</c:forEach>
            </div>
          </li>
        </c:forEach>        
        </ul>
			<div id="test1" style="text-align: center"></div>
      </div>
    </div>
    <div class="layui-col-md4">
   
      <dl class="fly-panel fly-list-one">
        <dt class="fly-panel-title">本周热议</dt>
         <c:forEach items="${hotTopic}" var="h" begin="0" end="9">
          <dd>
         <a href="../../topic/${h.id}">${h.title}</a>
          <span><i class="iconfont icon-pinglun1"></i>${h.replies.size()}</span>
        </dd>
         </c:forEach>  
          <c:if test="${hotTopic.size()==0}">
        <div class="fly-none">没有相关数据</div>
        </c:if>  	
        <!-- 无数据时 -->
        <!--
        <div class="fly-none">没有相关数据</div>
        -->
      </dl>

      <div class="fly-panel">
        <div class="fly-panel-title">
          这里可作为广告区域
        </div>
        <div class="fly-panel-main">
          <a href="http://layim.layui.com/?from=fly" target="_blank" class="fly-zanzhu" time-limit="2017.09.25-2099.01.01" style="background-color: #5FB878;">LayIM 3.0 - layui 旗舰之作</a>
        </div>
      </div>
      
      <div class="fly-panel fly-link">
        <h3 class="fly-panel-title">友情链接</h3>
        <dl class="fly-panel-main">
          <dd><a href="http://www.layui.com/" target="_blank">layui</a><dd>
          <dd><a href="http://layim.layui.com/" target="_blank">WebIM</a><dd>
          <dd><a href="http://layer.layui.com/" target="_blank">layer</a><dd>
          <dd><a href="http://www.layui.com/laydate/" target="_blank">layDate</a><dd>
          <dd><a href="mailto:xianxin@layui-inc.com?subject=%E7%94%B3%E8%AF%B7Fly%E7%A4%BE%E5%8C%BA%E5%8F%8B%E9%93%BE" class="fly-link">申请友链</a><dd>
        </dl>
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
layui.cache.page = '';
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
}).use('fly');
layui.use('laypage', function(){
	  var laypage = layui.laypage;	  
	  //执行一个laypage实例
	  laypage.render({
	    elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
	    	,count: ${topics.size()} //数据总数，从服务端得到
	    ,limit: 10 
	    //,layout:['prev', 'page', 'next','count','skip','limit']
	    ,curr: ${page} //获取起始页
	  	,jump: function(obj, first){	  	    	  	 
	  	    //首次不执行
	  	    if(!first){
	  	      //do something
	  	    	 window.location.href="../index/quiz?n="+${n}+"&page="+obj.curr+"&&rows="+obj.limit
	  	    }
	  	  }
	  });
	});
</script>
</body>
</html>