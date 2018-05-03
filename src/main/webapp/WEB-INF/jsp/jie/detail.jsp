<!DOCTYPE html>
<%@page import="com.fly.pojo.Topic"%>
<%@page import="com.fly.pojo.User"%>
<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/base.jsp"%>
<html>
<head>
  <meta charset="utf-8">
  <title>Fly Template v3.0，基于 layui 的极简社区页面模版</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="fly,layui,前端社区">
  <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
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
          <img src="${user.avatar}">
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

<div class="layui-hide-xs">
  <div class="fly-panel fly-column">
    <div class="layui-container">
      <ul class="layui-clear">
        <li class="layui-hide-xs"><a href="/">首页</a></li> 
        <li class="layui-this"><a href="">提问</a></li> 
        <li><a href="">分享<span class="layui-badge-dot"></span></a></li> 
        <li><a href="">讨论</a></li> 
        <li><a href="">建议</a></li> 
        <li><a href="">公告</a></li> 
        <li><a href="">动态</a></li> 
        <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><span class="fly-mid"></span></li> 
        
        <!-- 用户登入后显示 -->
        <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="../user/index">我发表的贴</a></li> 
        <li class="layui-hide-xs layui-hide-sm layui-show-md-inline-block"><a href="../user/index#collection">我收藏的贴</a></li> 
      </ul> 
      
      <div class="fly-column-right layui-hide-xs"> 
        <span class="fly-search"><i class="layui-icon"></i></span> 
        <a href="add" class="layui-btn">发表新帖</a> 
      </div> 
      <div class="layui-hide-sm layui-show-xs-block" style="margin-top: -10px; padding-bottom: 10px; text-align: center;"> 
        <a href="add" class="layui-btn">发表新帖</a> 
      </div> 
    </div>
  </div>
</div>

<div class="layui-container">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md8 content detail">
      <div class="fly-panel detail-box">
        <h1>${topic.title}</h1>   <!-- 主题标题处 -->
        <div class="fly-detail-info">  
         <!-- 主题标签处 --> 
        	<c:forEach items="${topic.tabs}" var="tab">
		       <c:if test="${tab.id==1}">
		       <span class="layui-badge" style="background-color: #999;">未结</span>
		       </c:if>
		       <c:if test="${tab.id==11}">
		       <span class="layui-badge">审核中</span>
		       </c:if>
		       <c:if test="${tab.id==8}">
		       <span class="layui-badge layui-bg-green fly-detail-column">动态</span>
		       </c:if>
		       <c:if test="${tab.id==2}">
		       <span class="layui-badge" style="background-color: #5FB878;">已结</span>
		       </c:if>
		       <c:if test="${tab.id==9}">
		       <span class="layui-badge layui-bg-black">置顶</span>
		       </c:if>
		       <c:if test="${tab.id==10}">
		       <span class="layui-badge layui-bg-red">精帖</span>
		       </c:if>
		       <c:if test="${tab.id==6}">
		       <span class="layui-badge layui-bg-green">建议</span>
		       </c:if>
		       <c:if test="${tab.id==3}">
		       <span class="layui-badge layui-bg-green">讨论</span>
		       </c:if>
		       <c:if test="${tab.id==4}">
		       <span class="layui-badge layui-bg-green">提问</span>
		       </c:if>
		       <c:if test="${tab.id==5}">
		       <span class="layui-badge layui-bg-green">分享</span>
		       </c:if>
			</c:forEach>
			
			
          <!-- <span class="layui-badge">审核中</span> -->
           <!-- <span class="layui-badge layui-bg-green fly-detail-column">动态</span>          
          <span class="layui-badge" style="background-color: #999;">未结</span>-->
          <!-- <span class="layui-badge" style="background-color: #5FB878;">已结</span> -->          
         <!--  <span class="layui-badge layui-bg-black">置顶</span>
          <span class="layui-badge layui-bg-red">精帖</span>-->
          
          <shiro:hasRole name="admin"><!-- 只有管理员权限才能有此功能 -->
          <div class="fly-admin-box" data-id="${topic.id}">
            <span class="layui-btn layui-btn-xs jie-admin" type="del">删除</span>
            
            <span class="layui-btn layui-btn-xs jie-admin" type="set" field="stick" rank="1">置顶</span> 
            <!--<span class="layui-btn layui-btn-xs jie-admin" type="set" field="stick" rank="0" style="background-color:#ccc;">取消置顶</span> -->
            
            <span class="layui-btn layui-btn-xs jie-admin" type="set" field="status" rank="1">加精</span> 
            <!-- <span class="layui-btn layui-btn-xs jie-admin" type="set" field="status" rank="0" style="background-color:#ccc;">取消加精</span> -->         	
         	<%User user = (User)request.getAttribute("user");
         	Topic topic = (Topic)request.getAttribute("topic");        	
         	%>
         	<c:if test='<%=user.getCollections().contains(new Topic(topic.getId()))%>'>
         	<span class="layui-btn layui-btn-danger layui-btn-xs jie-admin " type="collect" data-type="remove">取消收藏</span> 
         	</c:if>
 	        <c:if test='<%=!user.getCollections().contains(new Topic(topic.getId()))%>'>
         	<span class="layui-btn layui-btn-xs jie-admin" type="collect" data-type="add">收藏</span> 
         	</c:if>
         	       	
          </div>          
          
          </shiro:hasRole>
         
          
          <span class="fly-list-nums"> 
            <a href="#comment"><i class="iconfont" title="回答">&#xe60c;</i>${topic.replies.size()}</a><!-- 主题回复数 -->
            <i class="iconfont" title="人气">&#xe60b;</i> ${topic.viewNo}  <!-- 主题查看数 -->
          </span>
        </div>
        <div class="detail-about">
          <a class="fly-avatar" href="../user?uid=${topic.user.userId}">
            <img src="${topic.user.avatar}" alt="${user.username}">
          </a>
          <div class="fly-detail-user">
            <a href="../user/home" class="fly-link">
              <cite>${topic.user.username}</cite>  <!-- 作者 -->
              <i class="iconfont icon-renzheng" title="认证信息：${topic.user.info}"></i> <!-- 认证信息 -->
              <i class="layui-badge fly-badge-vip">VIP${topic.user.vip}</i>  <!-- VIP等级 -->
            </a>
            <span><fmt:formatDate type="both" 
            value="${topic.createTime}" /></span> <!-- 创造时间 -->
            <!--<span style="color:#1E9FFF">楼主</span>
            <span style="color:#5FB878">(管理员)</span>-->
          </div>
          <div class="detail-hits" id="LAY_jieAdmin" data-id="${topic.id}">
            <span style="padding-right: 10px; color: #FF7200">悬赏：${topic.reward}飞吻</span>  <!-- 悬赏数 -->
           <c:if test="${sessionScope.get('org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY')==topic.user.username}">
      			<!-- 判断当前用户是否为作者 -->
            <!-- <span class="layui-btn layui-btn-xs jie-admin" type="edit"><a href="../delete/${topic.id}">删除</a></span>-->
            <span class="layui-btn layui-btn-xs jie-admin" type="del">删除</span>
            </c:if>
          </div>
        </div>
        <!-- 主题内容 -->
        <div class="detail-body photos">${topic.content}</div>
      </div>

      <div class="fly-panel detail-box" id="flyReply">
        <fieldset class="layui-elem-field layui-field-title" style="text-align: center;">
        <legend>回帖</legend>     
        </fieldset>

        <ul class="jieda" id="jieda">
       	<c:forEach items="${replies}" var="li">
		<li data-id="${li.id}" class="jieda-daan">
            <a name="item-1111111111"></a>
            <div class="detail-about detail-about-reply">
              <a class="fly-avatar" href="../user?uid=${li.user.userId}">
                <img src="${li.user.avatar}" alt="${li.user.username}">
              </a>
              <div class="fly-detail-user">
                <a href="" class="fly-link">
                  <cite>${li.user.username}</cite>
                  <i class="iconfont icon-renzheng" title="认证信息：${li.user.info}"></i>
                  <i class="layui-badge fly-badge-vip">VIP${li.user.vip}</i>              
                </a>
                <c:if test="${li.user.username==topic.user.username}">
                 <span>(楼主)</span>
                </c:if>
                <c:if test="${li.user.status==0}">
                   <span style="color:#FF9E3F">（社区之光）</span>
                </c:if>
                <c:if test="${li.user.status==1}">
                  <span style="color:#999">（该号已被封）</span>
                </c:if>
                <c:if test="${li.user.status==2}">
                  <span style="color:#999">（该号已过期）</span>
                </c:if>
                <c:if test="${li.user.status==4}">
                  <span style="color:#5FB878">(管理员)</span>
                </c:if>
                <!--
                <span style="color:#5FB878">(管理员)</span>
                <span style="color:#FF9E3F">（社区之光）</span>
                <span style="color:#999">（该号已被封）</span>
                -->
              </div>

              <div class="detail-hits">
                <span><fmt:formatDate type="both" 
            value="${li.createTime}" /></span>
              </div>
				<c:if test="${li.accept}">
				<i class="iconfont icon-caina" title="最佳答案"></i>
				</c:if>
            </div>
            <div class="detail-body jieda-body photos">${li.content}</div>
            <div class="jieda-reply">
            	<span>#${li.floor}</span>
            <c:if test="${li.like>0}">
              <span class="jieda-zan zanok" type="zan">             
               <i class="iconfont icon-zan"></i>
                <em>${li.like}</em>                       
              </span>
            </c:if>
             <c:if test="${li.like==0}">
              <span class="jieda-zan" type="zan">             
               <i class="iconfont icon-zan"></i>
                <em>${li.like}</em>                       
              </span>
            </c:if>
              <span type="reply">
                <i class="iconfont icon-svgmoban53"></i>
                回复
              </span>
              <div class="jieda-admin">
              	<c:if test="${sessionScope.get('org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY')==li.user.username}">
      			<shiro:lacksRole name="admin">
      			<!--<span type="edit">编辑</span>-->
      			<span type="del">删除</span>
      			</shiro:lacksRole>
      			<!-- 判断当前用户是否为回帖作者 -->
            	</c:if>
            	<c:if test="${li.accept==false&&sessionScope.get('org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY')==topic.user.username}">
      			<span class="jieda-accept" type="accept">采纳</span>
                </c:if>
            	<shiro:hasRole name="admin">
                <span type="del">删除</span>
                <!-- <span type="edit">编辑</span> -->
              	</shiro:hasRole>
              </div>
            </div>
          </li>
		</c:forEach>
		        
          <!-- 无数据时 -->
         <c:if test="${topic.replies.size()==0}">
        <li class="fly-none">消灭零回复</li> 
        </c:if>
          
        </ul>
        <c:if test="${topic.replies.size()>0}">
        <div id="test1" style="text-align: center"></div>
        </c:if>
        
        <div class="layui-form layui-form-pane">
          <form action="/jie/reply/" method="post">
            <div class="layui-form-item layui-form-text">
              <a name="comment"></a>
              <div class="layui-input-block">
                <textarea id="L_content" name="content" required lay-verify="required" placeholder="请输入内容"  class="layui-textarea fly-editor" style="height: 150px;"></textarea>
              </div>
            </div>
            <div class="layui-form-item">
              <input type="hidden" name="uid" value="${user.userId}">
              <input type="hidden" name="tid" value="${topic.id}">
              <button class="layui-btn" lay-filter="*" lay-submit>提交回复</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="layui-col-md4">
      <dl class="fly-panel fly-list-one">
        <dt class="fly-panel-title">本周热议</dt>
       <c:forEach items="${hotTopic}" var="h" begin="0" end="9"><!-- 最多显示十条 -->
          <dd>
          <a href="../topic/${h.id }">${h.title}</a>
          <span><i class="iconfont icon-pinglun1"></i>${h.replies.size()}</span>
        </dd>
		</c:forEach>
        <!-- 无数据时 -->
        <c:if test="${hotTopic.size()==0}">
        <div class="fly-none">没有相关数据</div>
        </c:if>
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

      <div class="fly-panel" style="padding: 20px 0; text-align: center;">
        <img src="${ctx}/static/images/weixin.jpg" style="max-width: 100%;" alt="layui">
        <p style="position: relative; color: #666;">微信扫码关注 layui 公众号</p>
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
layui.cache.page = 'jie';
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
  
  $('.detail-body').each(function(){
    var othis = $(this), html = othis.html();
    othis.html(fly.content(html));
  });
  
});
layui.use('laypage', function(){
	  var laypage = layui.laypage;	  
	  //执行一个laypage实例
	  laypage.render({
	    elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
	    ,count: ${total} //数据总数，从服务端得到
	    ,limit:${pages.pageSize}
	    ,layout:['prev', 'page', 'next','count','skip','limit']
	    ,curr: ${pages.pageNum} //获取起始页
	  	,jump: function(obj, first){	  	    	  	 
	  	    //首次不执行
	  	    if(!first){
	  	      //do something
	  	    	 window.location.href="/topic/${topic.id}?page="+obj.curr+"&&rows="+obj.limit
	  	    }
	  	  }
	  });
	});
</script>

</body>
</html>