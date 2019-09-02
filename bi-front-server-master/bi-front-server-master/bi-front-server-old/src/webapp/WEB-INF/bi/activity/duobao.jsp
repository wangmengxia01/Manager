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
                <i class="fa fa-heart"></i> 一元夺宝
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="actJichu( 'myTabContent' );" href="#" data-toggle="tab">一元夺宝基础报表</a></li>
            <li><a onclick="actDanQi( 'myTabContent' );" href="#" data-toggle="tab">单期查询</a></li>
            <li><a onclick="actDanQiDanRen( 'myTabContent' );" href="#" data-toggle="tab">单期单人查询</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>

    function actJichu(id) {
        $.post("bi?input=act_getDuoBaoJichuPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function actDanQi(id) {
        $.post("bi?input=act_getDanQiPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function actDanQiDanRen(id) {
        $.post("bi?input=act_getDanQiDanRenPage", function (res) {
            $('#' + id).html(res);
        });
    }




</script>


</body>
</html>