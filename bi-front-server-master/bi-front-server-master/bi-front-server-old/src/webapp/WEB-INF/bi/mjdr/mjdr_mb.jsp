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
                <i class="fa fa-heart"></i> 麻将达人手机版
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="mjdrFinance( 'myTabContent' );" href="#" data-toggle="tab">汇总金融报表</a></li>
            <li><a onclick="mjdrFunnel( 'myTabContent' );" href="#" data-toggle="tab">用户漏斗</a></li>
            <li><a onclick="mjdrOnline( 'myTabContent' );" href="#" data-toggle="tab">新增在线时长</a></li>
            <li><a onclick="mjdrBasicXueliu( 'myTabContent' );" href="#" data-toggle="tab">血流成河基础报表</a></li>
            <li><a onclick="mjdrBasicXuezhan( 'myTabContent' );" href="#" data-toggle="tab">血战到底基础报表</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>


    function mjdrFinance(id) {
        $.post("bi?input=mjdr_getFinancePageMB", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrFunnel(id) {
        $.post("bi?input=mjdr_getFunnelPageMB", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrOnline(id) {
        $.post("bi?input=mjdr_getOnlinePageMB", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrBasicXueliu(id) {
        $.post("bi?input=mjdr_getBasicXueliuPageMB", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrBasicXuezhan(id) {
        $.post("bi?input=mjdr_getBasicXuezhanPageMB", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>