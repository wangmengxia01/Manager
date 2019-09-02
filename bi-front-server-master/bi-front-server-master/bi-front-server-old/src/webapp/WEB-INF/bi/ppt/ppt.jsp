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
                <i class="fa fa-heart"></i> 跑跑堂
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="pptBasic( 'myTabContent' );" href="#" data-toggle="tab">金融汇总</a></li>
            <li><a onclick="pptRank( 'myTabContent' );" href="#" data-toggle="tab">玩家输赢排行</a></li>
            <li><a onclick="sscBasic( 'myTabContent' );" href="#" data-toggle="tab">时时彩基础报表</a></li>
            <li><a onclick="pptCaichi( 'myTabContent' );" href="#" data-toggle="tab">彩池报表</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>
    function pptCaichi(id) {
        $.post("bi?input=ppt_getCaichiPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function pptBasic(id) {
        $.post("bi?input=ppt_getBasicPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function pptRank(id) {
        $.post("bi?input=ppt_getRankPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function sscBasic(id) {
        $.post("bi?input=ppt_getSscBasicPage", function (res) {
            $('#' + id).html(res);
        });
    }
</script>


</body>
</html>