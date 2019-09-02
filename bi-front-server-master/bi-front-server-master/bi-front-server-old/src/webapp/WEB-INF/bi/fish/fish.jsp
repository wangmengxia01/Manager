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
                <i class="fa fa-heart"></i> 捕鱼达人
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="fishHuizong( 'myTabContent' );" href="#" data-toggle="tab">金融汇总</a></li>
            <li><a onclick="fishBasic( 'myTabContent' );" href="#" data-toggle="tab">捕鱼基础报表</a></li>
            <li><a onclick="fishRank( 'myTabContent' );" href="#" data-toggle="tab">玩家输赢统计</a></li>
            <li><a onclick="fishLuckyJack( 'myTabContent' );" href="#" data-toggle="tab">幸运彩池统计</a></li>
            <li><a onclick="fishItem( 'myTabContent' );" href="#" data-toggle="tab">道具统计</a></li>
            <li><a onclick="fishOnline( 'myTabContent' );" href="#" data-toggle="tab">玩家在线时长统计</a></li>

            <%--<li><a onclick="fishLiushui( 'myTabContent' );" href="#" data-toggle="tab">流水统计</a></li>--%>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>




    function fishLuckyJack(id) {
        $.post("bi?input=fish_getLuckyJackPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishOnline(id) {
        $.post("bi?input=fish_getFishOnlinePage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishHuizong(id) {
        $.post("bi?input=fish_getHuizongPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishRank(id) {
        $.post("bi?input=fish_getRankPage", function (res) {
            $('#' + id).html(res);
        });
    }


    function fishBasic(id) {
        $.post("bi?input=fish_getBasicPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishItem(id) {
        $.post("bi?input=fish_getItemPage", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>