<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BI System</title>

    <jsp:include page="../common_meta.jsp"/>

</head>
<body>

<!-- common head and left -->
<jsp:include page="../tpg_head.jsp"/>

<div class="body slide">
    <jsp:include page="../tpg_left.jsp"/>

    <div class="container-fluid left-border">

        <div class="row"></div>

        <div class="col-md-10 col-lg-10">
            <h1>
                <i class="fa fa-heart"></i> 单用户查询
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
                <li><a onclick="chipMove( 'myTabContent' );" href="#" data-toggle="tab">德州用户筹码流动 </a></li>
                <li><a onclick="enemy( 'myTabContent' );" href="#" data-toggle="tab">德州用户现金桌输赢筹码记录</a></li>
            <li><a onclick="FishChipMove( 'myTabContent' );" href="#" data-toggle="tab">捕鱼单用户筹码流动 </a></li>
            <li><a onclick="lookupIp( 'myTabContent' );" href="#" data-toggle="tab">单用户登录IP</a></li>
            <li><a onclick="pay( 'myTabContent' );" href="#" data-toggle="tab">单用户支付记录</a></li>
            <li><a onclick="bigR( 'myTabContent' );" href="#" data-toggle="tab">大R游戏情况</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>

    function bigR(id) {
        $.post("bi?input=user_getbigRPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function chipMove(id) {
        $.post("bi?input=singleUser_getChipMovePage", function (res) {
            $('#' + id).html(res);
        });
    }
    function FishChipMove(id) {
        $.post("bi?input=singleUser_getFishChipMovePage", function (res) {
            $('#' + id).html(res);
        });
    }

    function enemy(id) {
        $.post("bi?input=singleUser_getEnemyPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function lookupIp(id) {
        $.post("bi?input=singleUser_getIpPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function pay(id) {
        $.post("bi?input=singleUser_getPayPage", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>