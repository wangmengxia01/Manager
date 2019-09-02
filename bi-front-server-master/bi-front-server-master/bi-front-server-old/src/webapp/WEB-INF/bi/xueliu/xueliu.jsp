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
                <i class="fa fa-heart"></i> 血流成河
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="mjdrHuizongXueliu( 'myTabContent' );" href="#" data-toggle="tab">金融汇总</a></li>
            <li><a onclick="mjdrBasicXueliu( 'myTabContent' );" href="#" data-toggle="tab">血流基础报表</a></li>
            <li><a onclick="mjdrXueliuRank( 'myTabContent' );" href="#" data-toggle="tab">玩家输赢统计</a></li>
            <li><a onclick="mjdrLiushui( 'myTabContent' );" href="#" data-toggle="tab">流水统计</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>




    function mjdrLiushui(id) {
        $.post("bi?input=mjdr_getXueliuLiushuiPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function mjdrHuizongXueliu(id) {
        $.post("bi?input=mjdr_getHuizongXueliuPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function mjdrXueliuRank(id) {
        $.post("bi?input=mjdr_getRankXueliuPage", function (res) {
            $('#' + id).html(res);
        });
    }


    function mjdrBasicXueliu(id) {
        $.post("bi?input=mjdr_getBasicXueliuPage", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>