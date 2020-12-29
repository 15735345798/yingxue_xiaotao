<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
        //延迟加载
    $(function(){
        pageInit();
    });
    //创建表格
    function pageInit(){
        $("#userTable").jqGrid({
            url : "${path}/log/queryLogPage",  //接收  page=当前页   rows=每页展示条数   返回  page=当前页   rows=[User,User]数据    tolal=总页数   records=总条数
            datatype : "json", //数据格式
            rowNum : 10,  //每页展示条数
            rowList : [ 10, 20, 30 ],  //可选没夜战是条数
            pager : '#userPage',  //分页工具栏
            sortname : 'id', //排序
            type : "post",  //请求方式
            styleUI:"Bootstrap", //使用Bootstrap
            autowidth:true,  //宽度自动
            height:"auto",   //高度自动
            viewrecords : true, //是否展示总条数
            colNames : [ 'Id', '管理员', '时间', '操作', '是否成功'],
            colModel : [
                {name : 'id',width : 100},
                {name : 'adminname',width : 50},
                {name : 'optiontime',width : 70},
                {name : 'options',width : 70,},
                {name : 'issuccess',width : 100}
            ]
        });
    }

</script>

<%--创建一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <span>日志信息</span>
    </div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">日志信息</a></li>
    </ul><br>


    <%--创建表格--%>
    <table id="userTable" />

    <%--分页工具栏--%>
    <div id="userPage"/>

</div>