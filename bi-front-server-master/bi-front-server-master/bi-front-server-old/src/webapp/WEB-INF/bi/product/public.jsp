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
                <i class="fa fa-heart"></i> 公共玩法
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">

            <li><a onclick="huiZong( 'myTabContent' );" href="#" data-toggle="tab">金融汇总</a></li>
            <li><a onclick="loginGift( 'myTabContent' );" href="#" data-toggle="tab">登录奖励</a></li>
            <li><a onclick="goodLucky( 'myTabContent' );" href="#" data-toggle="tab">好运基金</a></li>
            <li><a onclick="loginHuafei( 'myTabContent' );" href="#" data-toggle="tab">登陆送话费</a></li>
            <li><a onclick="Month( 'myTabContent' );" href="#" data-toggle="tab">月卡支出</a></li>
            <li><a onclick="task( 'myTabContent' );" href="#" data-toggle="tab">任务</a></li>

        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>



    function huiZong(id) {
        $.post("bi?input=product_getHuizongPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function loginGift(id) {
        $.post("bi?input=product_getLoginGiftPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function goodLucky(id) {
        $.post("bi?input=product_getLuckyFundPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function loginHuafei(id) {
        $.post("bi?input=product_getLoginPhoneCardPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function shiwu(id) {
        $.post("bi?input=product_getDuihuanPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function Month(id) {
        $.post("bi?input=product_getMonthGiftPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function task(id) {
        $.post("bi?input=product_getTaskPage", function (res) {
            $('#' + id).html(res);
        });
    }



</script>


</body>
</html>