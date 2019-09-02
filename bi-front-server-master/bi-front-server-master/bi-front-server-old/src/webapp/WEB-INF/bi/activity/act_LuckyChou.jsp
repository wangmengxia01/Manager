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
                <i class="fa fa-heart"></i> 幸运抽奖活动
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="choujiang( 'myTabContent' );" href="#" data-toggle="tab">德州抽奖活动</a></li>
            <li><a onclick="majiang( 'myTabContent' );" href="#" data-toggle="tab">抽奖活动单人查询</a></li>
            <li><a onclick="rank( 'myTabContent' );" href="#" data-toggle="tab">玩家抢红包排行榜</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>




    function rank(id) {
        $.post("bi?input=act_getLuckyChouRankPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function majiang(id) {
        $.post("bi?input=act_getLuckyChouChaPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function choujiang(id) {
        $.post("bi?input=act_getLuckyChouDZPage", function (res) {
            $('#' + id).html(res);
        });
    }


</script>


</body>
</html>