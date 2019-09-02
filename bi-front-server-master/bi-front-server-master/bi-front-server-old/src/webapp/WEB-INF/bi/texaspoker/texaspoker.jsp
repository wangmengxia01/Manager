<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
                <i class="fa fa-heart"></i> 胡莱德州扑克
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="tpHuizong( 'myTabContent' );" href="#" data-toggle="tab">金融汇总</a></li>
            <li><a onclick="tpJichu( 'myTabContent' );" href="#" data-toggle="tab">基本报表</a></li>
            <li><a onclick="tpRank( 'myTabContent' );" href="#" data-toggle="tab">玩家输赢排行 </a></li>
            <li><a onclick="tpZhuanpan( 'myTabContent' );" href="#" data-toggle="tab">转盘报表 </a></li>
            <li><a onclick="tpTimer( 'myTabContent' );" href="#" data-toggle="tab">定时比赛 </a></li>
            <li><a onclick="tpYaojiangji( 'myTabContent' );" href="#" data-toggle="tab">摇奖机 </a></li>
            <li><a onclick="tpYoulun( 'myTabContent' );" href="#" data-toggle="tab">游轮基础报表</a></li>
            <li><a onclick="tpPayDetail( 'myTabContent' );" href="#" data-toggle="tab">付费详情</a></li>
            <li><a onclick="liushui( 'myTabContent' );" href="#" data-toggle="tab">流水详情</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>


    function liushui(id) {
        $.post("bi?input=texaspoker_getLiushuiPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function tpHuizong(id) {
        $.post("bi?input=texaspoker_getHuizongPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function tpJichu(id) {
        $.post("bi?input=texaspoker_getJichuPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function tpZhuanpan(id) {
        $.post("bi?input=texaspoker_getZhuanpanPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function tpTimer(id) {
        $.post("bi?input=texaspoker_getTimerPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function tpYaojiangji(id) {
        $.post("bi?input=texaspoker_getYaojiangjiPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function tpRank(id) {
        $.post("bi?input=texaspoker_getRankPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function tpYoulun(id) {
        $.post("bi?input=texaspoker_getYoulunPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function tpPayDetail(id) {
        $.post("bi?input=texaspoker_getPayDetailPage", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>