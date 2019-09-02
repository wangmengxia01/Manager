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
                <i class="fa fa-heart"></i> 麻将合集玩法
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="mjdrFinance( 'myTabContent' );" href="#" data-toggle="tab">汇总金融报表</a></li>
            <li><a onclick="mjdrFunnel( 'myTabContent' );" href="#" data-toggle="tab">用户漏斗</a></li>
            <li><a onclick="mjdrOnline( 'myTabContent' );" href="#" data-toggle="tab">新增在线时长</a></li>
            <li><a onclick="mjdrUpgrade( 'myTabContent' );" href="#" data-toggle="tab">升级奖励</a></li>
            <li><a onclick="mjdrMission( 'myTabContent' );" href="#" data-toggle="tab">任务支出</a></li>
            <li><a onclick="mjdrBasicXueliu( 'myTabContent' );" href="#" data-toggle="tab">血流成河基础报表</a></li>
            <li><a onclick="mjdrBasicXuezhan( 'myTabContent' );" href="#" data-toggle="tab">血战到底基础报表</a></li>
            <li><a onclick="mjdrBasicErRen( 'myTabContent' );" href="#" data-toggle="tab">二人麻将基础报表</a></li>
            <li><a onclick="mjdrRank( 'myTabContent' );" href="#" data-toggle="tab">用户输赢统计</a></li>
            <li><a onclick="mjdrPossession( 'myTabContent' );" href="#" data-toggle="tab">元宝总量监控</a></li>
            <li><a onclick="mjdrPayItem( 'myTabContent' );" href="#" data-toggle="tab">商城购买道具监控</a></li>
            <li><a onclick="mjdrStatXueliu( 'myTabContent' );" href="#" data-toggle="tab">血流成河数据统计</a></li>
            <li><a onclick="mjdrStatXuezhan( 'myTabContent' );" href="#" data-toggle="tab">血战到底数据统计</a></li>
            <li><a onclick="mjdrLotteryStat( 'myTabContent' );" href="#" data-toggle="tab">抽奖人数统计</a></li>
            <li><a onclick="mjdrLotteryItem( 'myTabContent' );" href="#" data-toggle="tab">抽奖道具支出</a></li>
            <li><a onclick="mjdrLotterySum( 'myTabContent' );" href="#" data-toggle="tab">抽奖汇总表</a></li>
            <li><a onclick="mjdrAiWinLose( 'myTabContent' );" href="#" data-toggle="tab">AI输赢报表</a></li>
            <li><a onclick="mjdrPhoneCardRetention( 'myTabContent' );" href="#" data-toggle="tab">刷话费留存</a></li>
            <li><a onclick="mjdrlianxi( 'myTabContent' );" href="#" data-toggle="tab">练习场</a></li>
            <li><a onclick="mjdrlianxiWin( 'myTabContent' );" href="#" data-toggle="tab">练习场连胜</a></li>
            <li><a onclick="mjdrhuafeiGift( 'myTabContent' );" href="#" data-toggle="tab">话费礼包</a></li>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>


    function mjdrhuafeiGift(id) {
        $.post("bi?input=mjdr_getHuaFeiGiftPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrlianxiWin(id) {
        $.post("bi?input=mjdr_getLianXiWinPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrlianxi(id) {
        $.post("bi?input=mjdr_getLianXiPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrFinance(id) {
        $.post("bi?input=mjdr_getFinancePage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrFunnel(id) {
        $.post("bi?input=mjdr_getFunnelPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrOnline(id) {
        $.post("bi?input=mjdr_getOnlinePage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrUpgrade(id) {
        $.post("bi?input=mjdr_getUpgradePage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrMission(id) {
        $.post("bi?input=mjdr_getMissionPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrBasicXueliu(id) {
        $.post("bi?input=mjdr_getBasicXueliuPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrBasicXuezhan(id) {
        $.post("bi?input=mjdr_getBasicXuezhanPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrBasicErRen(id) {
        $.post("bi?input=mjdr_getBasicErRenPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrRank(id) {
        $.post("bi?input=mjdr_getRankPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrPossession(id) {
        $.post("bi?input=mjdr_getPossessionPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrPayItem(id) {
        $.post("bi?input=mjdr_getPayItemPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrStatXueliu(id) {
        $.post("bi?input=mjdr_getStatXueliuPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrStatXuezhan(id) {
        $.post("bi?input=mjdr_getStatXuezhanPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrLotteryStat(id) {
        $.post("bi?input=mjdr_getLotteryStatPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrLotteryItem(id) {
        $.post("bi?input=mjdr_getLotteryItemPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrLotterySum(id) {
        $.post("bi?input=mjdr_getLotterySumPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrAiWinLose(id) {
        $.post("bi?input=mjdr_getAiWinLosePage", function (res) {
            $('#' + id).html(res);
        });
    }

    function mjdrPhoneCardRetention(id) {
        $.post("bi?input=mjdr_getPhoneCardRetentionPage", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>