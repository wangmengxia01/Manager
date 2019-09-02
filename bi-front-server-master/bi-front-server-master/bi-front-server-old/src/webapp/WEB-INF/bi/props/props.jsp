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
                <i class="fa fa-heart"></i> 重要道具监控
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="propsZongliang( 'myTabContent' );" href="#" data-toggle="tab">持有道具总量监控</a></li>
            <li><a onclick="propsSend( 'myTabContent' );" href="#" data-toggle="tab">道具赠送监控</a></li>
            <%--<li><a onclick="propsHuafei( 'myTabContent' );" href="#" data-toggle="tab">话费卡使用监控</a></li>--%>
            <%--<li><a onclick="props1Huafei( 'myTabContent' );" href="#" data-toggle="tab">一元话费短信监控</a></li>--%>
            <%--<li><a onclick="propsHuafeiOut( 'myTabContent' );" href="#" data-toggle="tab">话费产出监控</a></li>--%>
            <%--<li><a onclick="BuyRank( 'myTabContent' );" href="#" data-toggle="tab">道具购买及拜神排行</a></li>--%>
            <%--<li><a onclick="baishen( 'myTabContent' );" href="#" data-toggle="tab">拜神道具统计</a></li>--%>
            <li><a onclick="bishang( 'myTabContent' );" href="#" data-toggle="tab">币商收售统计</a></li>
            <li><a onclick="bishangBoss( 'myTabContent' );" href="#" data-toggle="tab">收分boss统计</a></li>

        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>

    function bishangBoss(id) {
        $.post("bi?input=props_getMaiFenBossPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function bishang(id) {
        $.post("bi?input=props_getBiShangPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function baishen(id) {
        $.post("bi?input=props_getBaiShenPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function BuyRank(id) {
        $.post("bi?input=props_getBuyRankPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function propsHuafeiOut(id) {
        $.post("bi?input=props_getHuafeiOutPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function propsZongliang(id) {
        $.post("bi?input=props_getZongLiangPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function propsSend(id) {
        $.post("bi?input=props_getSendPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function propsHuafei(id) {
        $.post("bi?input=props_getHuafeiPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function props1Huafei(id) {
        $.post("bi?input=props_getHuafei1Page", function (res) {
            $('#' + id).html(res);
        });
    }



</script>


</body>
</html>