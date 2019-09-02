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
                <i class="fa fa-heart"></i> 摩天轮
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="mtlFinance( 'myTabContent' );" href="#" data-toggle="tab">金融汇总</a></li>
            <%--<li><a onclick="mtlRank( 'myTabContent' );" href="#" data-toggle="tab">玩输赢排行</a></li>--%>
            <li><a onclick="mtlCaichi( 'myTabContent' );" href="#" data-toggle="tab">彩池统计</a></li>
                <li><a onclick="mtlFishRank( 'myTabContent' );" href="#" data-toggle="tab">捕鱼玩家输赢排行</a></li>
                <li><a onclick="mtlRank( 'myTabContent' );" href="#" data-toggle="tab">德州用户输赢排行</a></li>
            <li><a onclick="mtlNiubi( 'myTabContent' );" href="#" data-toggle="tab">牛币消耗统计</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>

    function mtlCaichi(id) {
        $.post("bi?input=mtl2_getCaichiPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function mtlNiubi(id) {
        $.post("bi?input=mtl2_getNiubiPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mtlFinance(id) {
        $.post("bi?input=mtl2_getFinancePage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mtlRank(id) {
        $.post("bi?input=mtl2_getRankPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function mtlFishRank(id) {
        $.post("bi?input=mtl2_getFishRankPage", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>