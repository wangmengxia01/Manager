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
                <i class="fa fa-heart"></i> 麻将开房玩法
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="kaifangHuizong( 'myTabContent' );" href="#" data-toggle="tab">金融汇总</a></li>
            <li><a onclick="kaifangBasic( 'myTabContent' );" href="#" data-toggle="tab">基本报表</a></li>
            <li><a onclick="kaifangXueliu( 'myTabContent' );" href="#" data-toggle="tab">血流行为</a></li>
            <li><a onclick="kaifangXuezhan( 'myTabContent' );" href="#" data-toggle="tab">血战行为</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>




    function kaifangHuizong(id) {
        $.post("bi?input=mjdr_getKaifangHuizongPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function kaifangBasic(id) {
        $.post("bi?input=mjdr_getKaifangJichuPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function kaifangXueliu(id) {
        $.post("bi?input=mjdr_getKaifangXueliuPage", function (res) {
            $('#' + id).html(res);
        });
    }


    function kaifangXuezhan(id) {
        $.post("bi?input=mjdr_getKaifangXuezhanPage", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>