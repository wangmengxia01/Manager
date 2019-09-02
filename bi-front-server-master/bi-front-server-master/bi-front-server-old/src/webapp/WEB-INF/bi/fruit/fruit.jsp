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
                <i class="fa fa-heart"></i> 水果大满贯
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="fruitBasic( 'myTabContent' );" href="#" data-toggle="tab">金融汇总</a></li>
            <li><a onclick="fruitRank( 'myTabContent' );" href="#" data-toggle="tab">玩家输赢排行</a></li>
            <li><a onclick="caibeiBasic( 'myTabContent' );" href="#" data-toggle="tab">比倍基础报表</a></li>
            <li><a onclick="fruitCaichi( 'myTabContent' );" href="#" data-toggle="tab">彩池统计</a></li>
            <%--<li><a onclick="caibeiItem( 'myTabContent' );" href="#" data-toggle="tab">比倍道具支出</a></li>--%>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>

    function fruitCaichi(id) {
        $.post("bi?input=fruit_getCaichiPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fruitBasic(id) {
        $.post("bi?input=fruit_getBasicPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fruitRank(id) {
        $.post("bi?input=fruit_getRankPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function caibeiBasic(id) {
        $.post("bi?input=fruit_getCaibeiBasicPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function caibeiItem(id) {
        $.post("bi?input=fruit_getCaibeiItemPage", function (res) {
            $('#' + id).html(res);
        });
    }


</script>


</body>
</html>