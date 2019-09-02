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
            <li><a onclick="jichu( 'myTabContent' );" href="#" data-toggle="tab">基本报表</a></li>
            <li><a onclick="huizong( 'myTabContent' );" href="#" data-toggle="tab">金融汇总</a></li>
            <li><a onclick="liushui( 'myTabContent' );" href="#" data-toggle="tab">流水查询</a></li>
            <%--<li><a onclick="inning( 'myTabContent' );" href="#" data-toggle="tab">游戏手数分布</a></li>--%>

        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>

    function liushui(id) {
        $.post("bi?input=newpoker_getLiushuiPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function jichu(id) {
        $.post("bi?input=newpoker_getjichuPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function huizong(id) {
        $.post("bi?input=newpoker_getHuizongPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function inning(id) {
        $.post("bi?input=newpoker_getInningPage", function (res) {
            $('#' + id).html(res);
        });
    }




</script>


</body>
</html>