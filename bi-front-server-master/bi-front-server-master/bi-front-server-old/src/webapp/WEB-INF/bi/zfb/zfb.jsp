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
                <i class="fa fa-heart"></i> 中发白
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">

            <li><a onclick="zfbBasic( 'myTabContent' );" href="#" data-toggle="tab">中发白基础报表</a></li>
            <%--<li><a onclick="zfbRobot( 'myTabContent' );" href="#" data-toggle="tab">中发白机器人</a></li>--%>
            <li><a onclick="zfbFishRank( 'myTabContent' );" href="#" data-toggle="tab">捕鱼输赢排行</a></li>
            <li><a onclick="zfbRank( 'myTabContent' );" href="#" data-toggle="tab">德州输赢排行</a></li>
            <li><a onclick="zfbShangzhuang( 'myTabContent' );" href="#" data-toggle="tab">上庄卡、合庄统计</a></li>
            <li><a onclick="zfbRedEnvelope( 'myTabContent' );" href="#" data-toggle="tab">中发白红包玩法</a></li>
            <li><a onclick="zfbCaichi( 'myTabContent' );" href="#" data-toggle="tab">彩池统计</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>

    function zfbShangzhuang(id) {
        $.post("bi?input=zfb_getShangzhuangPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function zfbCaichi(id) {
        $.post("bi?input=zfb_getCaichiPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function zfbBasic(id) {
        $.post("bi?input=zfb_getBasicPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function zfbRobot(id) {
        $.post("bi?input=zfb_getRobotPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function zfbRank(id) {
        $.post("bi?input=zfb_getRankPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function zfbFishRank(id) {
        $.post("bi?input=zfb_getFishRankPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function zfbRedEnvelope(id) {
        $.post("bi?input=zfb_getRedEnvelopePage", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>