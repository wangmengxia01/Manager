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
                <i class="fa fa-heart"></i> 功能
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">

            <li><a onclick="people( 'myTabContent' );" href="#" data-toggle="tab">人数统计</a></li>
            <li><a onclick="count( 'myTabContent' );" href="#" data-toggle="tab">次数统计</a></li>

        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>



    function people(id) {
        $.post("bi?input=product_getGongnengPeoplePage", function (res) {
            $('#' + id).html(res);
        });
    }
    function count(id) {
        $.post("bi?input=product_getGongnengCountPage", function (res) {
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