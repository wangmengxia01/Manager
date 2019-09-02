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
                <i class="fa fa-heart"></i> 斗地主
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="ddzFinance( 'myTabContent' );" href="#" data-toggle="tab">汇总金融报表</a></li>
            <%--<li><a onclick="ddzFunnel( 'myTabContent' );" href="#" data-toggle="tab">用户漏斗</a></li>--%>
            <li><a onclick="ddzJichu( 'myTabContent' );" href="#" data-toggle="tab">基础信息</a></li>
                <li><a onclick="ddzRank( 'myTabContent' );" href="#" data-toggle="tab">玩家输赢排行</a></li>
                <li><a onclick="ddzTask( 'myTabContent' );" href="#" data-toggle="tab">随机任务信息</a></li>
                <li><a onclick="ddzChoujiang( 'myTabContent' );" href="#" data-toggle="tab">斗地主抽奖信息</a></li>
            <li><a onclick="ddzLiushui( 'myTabContent' );" href="#" data-toggle="tab">斗地主流水</a></li>
            <%--<li><a onclick="ddzMission( 'myTabContent' );" href="#" data-toggle="tab">斗地主大厅任务支出</a></li>--%>
            <%--<li><a onclick="ddzMult( 'myTabContent' );" href="#" data-toggle="tab">斗地主倍数信息</a></li>--%>
            <%--<li><a onclick="ddzLuckyWheel( 'myTabContent' );" href="#" data-toggle="tab">斗地主奖品出现信息</a></li>--%>
            <%--<li><a onclick="ddzPossession( 'myTabContent' );" href="#" data-toggle="tab">元宝总量监控</a></li>--%>
            <%--<li><a onclick="ddzPayItem( 'myTabContent' );" href="#" data-toggle="tab">商城购买道具监控</a></li>--%>
            <%--<li><a onclick="ddzOnline( 'myTabContent' );" href="#" data-toggle="tab">新增在线时长</a></li>--%>
            <%--<li><a onclick="ddzPhoneCardRetention( 'myTabContent' );" href="#" data-toggle="tab">刷话费留存</a></li>--%>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>

    function ddzLiushui(id) {
        $.post("bi?input=ddz_getLiushuiPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function ddzOnline(id) {
        $.post("bi?input=ddz_getOnlinePage", function (res) {
            $('#' + id).html(res);
        });
    }
    function ddzRank(id) {
        $.post("bi?input=ddz_getRankPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function ddzPossession(id) {
        $.post("bi?input=ddz_getPossessionPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function ddzPayItem(id) {
        $.post("bi?input=ddz_getPayItemPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function ddzFunnel(id) {
        $.post("bi?input=ddz_getFunnelPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function ddzMission(id) {
        $.post("bi?input=ddz_getMissionPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function ddzFinance(id) {
        $.post("bi?input=ddz_getFinancePage", function (res) {
            $('#' + id).html(res);
        });
    }

    function ddzPhoneCardRetention(id) {
        $.post("bi?input=ddz_getPhoneCardRetentionPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function ddzChoujiang(id) {
        $.post("bi?input=ddz_getddzChoujiangPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function ddzLuckyWheel(id) {
        $.post("bi?input=ddz_getddzLuckyWheelPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function ddzTask(id) {
        $.post("bi?input=ddz_getddzTaskPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function ddzMult(id) {
        $.post("bi?input=ddz_getddzMultPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function ddzJichu(id) {
        $.post("bi?input=ddz_getddzJichuPage", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>