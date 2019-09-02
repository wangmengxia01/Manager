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
                <i class="fa fa-heart"></i> 捕鱼达人手机版
            </h1>
        </div>

        <div class="row"></div>

        <ul id="myTab" class="nav nav-tabs">
            <li><a onclick="fishHuizong( 'myTabContent' );" href="#" data-toggle="tab">金融汇总</a></li>
            <li><a onclick="fishLouDou( 'myTabContent' );" href="#" data-toggle="tab">用户漏斗</a></li>
            <li><a onclick="fishGameGold( 'myTabContent' );" href="#" data-toggle="tab">系统支出汇总</a></li>
            <li><a onclick="fishBasic( 'myTabContent' );" href="#" data-toggle="tab">捕鱼基础报表</a></li>
            <li><a onclick="fishRank( 'myTabContent' );" href="#" data-toggle="tab">玩家输赢统计</a></li>
            <li><a onclick="fishTask( 'myTabContent' );" href="#" data-toggle="tab">任务统计</a></li>
            <li><a onclick="fishDuiHuan( 'myTabContent' );" href="#" data-toggle="tab">兑换统计</a></li>
            <li><a onclick="fishFanBei( 'myTabContent' );" href="#" data-toggle="tab">翻倍统计</a></li>
            <li><a onclick="fishJiangQuan( 'myTabContent' );" href="#" data-toggle="tab">奖券统计</a></li>
            <li><a onclick="fishZhuanPan( 'myTabContent' );" href="#" data-toggle="tab">气泡统计</a></li>
            <li><a onclick="fishItem( 'myTabContent' );" href="#" data-toggle="tab">道具统计</a></li>
            <li><a onclick="fishNewUser( 'myTabContent' );" href="#" data-toggle="tab">新手支出</a></li>
            <li><a onclick="fishLogin( 'myTabContent' );" href="#" data-toggle="tab">登录奖励</a></li>
            <li><a onclick="fishBroke( 'myTabContent' );" href="#" data-toggle="tab">破产补助</a></li>
            <li><a onclick="fishUP( 'myTabContent' );" href="#" data-toggle="tab">升级奖励</a></li>
            <li><a onclick="fishRed( 'myTabContent' );" href="#" data-toggle="tab">在线红包</a></li>
            <li><a onclick="fishVIP( 'myTabContent' );" href="#" data-toggle="tab">VIP支出</a></li>
            <li><a onclick="fishDanTou( 'myTabContent' );" href="#" data-toggle="tab">弹头统计</a></li>
            <li><a onclick="fishSend( 'myTabContent' );" href="#" data-toggle="tab">弹头赠送</a></li>
            <li><a onclick="fishDanTouFish( 'myTabContent' );" href="#" data-toggle="tab">弹头鱼统计</a></li>
            <%--<li><a onclick="fishChiBang( 'myTabContent' );" href="#" data-toggle="tab">翅膀产出</a></li>--%>
            <li><a onclick="fishPaoHuoYue( 'myTabContent' );" href="#" data-toggle="tab">活跃玩家炮倍数</a></li>
            <li><a onclick="fishHaveGold( 'myTabContent' );" href="#" data-toggle="tab">玩家持有金币分布</a></li>
            <li><a onclick="fishCaptain( 'myTabContent' );" href="#" data-toggle="tab">周卡月卡支出</a></li>
            <li><a onclick="fishHandle( 'myTabContent' );" href="#" data-toggle="tab">玩家操作统计</a></li>
            <li><a onclick="fishOnline( 'myTabContent' );" href="#" data-toggle="tab">玩家在线时长统计</a></li>
            <%--<li><a onclick="fishUserDanTou( 'myTabContent' );" href="#" data-toggle="tab">玩家弹头掉落、交易统计</a></li>--%>
            <li><a onclick="fishShop( 'myTabContent' );" href="#" data-toggle="tab">道具出售情况</a></li>
            <li><a onclick="fishUserItem( 'myTabContent' );" href="#" data-toggle="tab">单道具玩家持有情况</a></li>
            <li><a onclick="fishBoss( 'myTabContent' );" href="#" data-toggle="tab">boss统计</a></li>
            <li><a onclick="fishJingJi( 'myTabContent' );" href="#" data-toggle="tab">竞技场</a></li>
            <li><a onclick="fishPoker( 'myTabContent' );" href="#" data-toggle="tab">扑克场</a></li>
            <li><a onclick="fishDaShi( 'myTabContent' );" href="#" data-toggle="tab">大师场</a></li>
            <li><a onclick="bigR( 'myTabContent' );" href="#" data-toggle="tab">大R补助</a></li>
            <li><a onclick="chongzhi( 'myTabContent' );" href="#" data-toggle="tab">充值奖励</a></li>
            <li><a onclick="xiazai( 'myTabContent' );" href="#" data-toggle="tab">玩家输赢充值弹头关系</a></li>
            <%--<li><a onclick="fishPoolMove( 'myTabContent' );" href="#" data-toggle="tab">池子转换信息</a></li>--%>
            <li><a onclick="caicaicai( 'myTabContent' );" href="#" data-toggle="tab">猜猜猜玩法</a></li>
            <li><a onclick="caicaicaiRank( 'myTabContent' );" href="#" data-toggle="tab">猜猜猜排行</a></li>



        <%--<li><a onclick="fishLiushui( 'myTabContent' );" href="#" data-toggle="tab">流水统计</a></li>--%>
        </ul>

        <br>
        <div id="myTabContent">

        </div>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>


<script>
    function xiazai(id) {
        $.post("bi?input=fishMB_getxiazaiPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function caicaicai(id) {
        $.post("bi?input=fishMB_getcaicaicaiPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function caicaicaiRank(id) {
        $.post("bi?input=fishMB_getCCCRankPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function chongzhi(id) {
        $.post("bi?input=fishMB_getchongzhiPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishPoolMove(id) {
        $.post("bi?input=fishMB_getPoolMovePage", function (res) {
            $('#' + id).html(res);
        });
    }

    function bigR(id) {
        $.post("bi?input=fishMB_getbigRPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishDaShi(id) {
        $.post("bi?input=fishMB_getDaShiPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishPoker(id) {
        $.post("bi?input=fishMB_getPokerPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishDanTouFish(id) {
        $.post("bi?input=fishMB_getDanTouFishPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishBoss(id) {
        $.post("bi?input=fishMB_getBossPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishJingJi(id) {
        $.post("bi?input=fishMB_getJingJiPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishChiBang(id) {
        $.post("bi?input=fishMB_getChiBangPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishUserItem(id) {
        $.post("bi?input=fishMB_getUserItemPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishPaoHuoYue(id) {
        $.post("bi?input=fishMB_getPaoHuoYuePage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishSend(id) {
        $.post("bi?input=fishMB_getSendPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishShop(id) {
        $.post("bi?input=fishMB_getShopPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishHaveGold(id) {
        $.post("bi?input=fishMB_getHaveGoldPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishHandle(id) {
        $.post("bi?input=fishMB_gethandlePage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishDuiHuan(id) {
        $.post("bi?input=fishMB_getduihuanPage", function (res) {
            $('#' + id).html(res);
        });
    }

//    function fishUserDanTou(id) {
//        $.post("bi?input=fishMB_getUserDanTouPage", function (res) {
//            $('#' + id).html(res);
//        });
//    }

    function fishFanBei(id) {
        $.post("bi?input=fishMB_getfanbeiPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishJiangQuan(id) {
        $.post("bi?input=fishMB_getjiangquanPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishZhuanPan(id) {
        $.post("bi?input=fishMB_getzhuanpanPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishNewUser(id) {
        $.post("bi?input=fishMB_getNewUserPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishLouDou(id) {
        $.post("bi?input=fishMB_getLouDouPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishDanTou(id) {
        $.post("bi?input=fishMB_getDanTouPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishTask(id) {
        $.post("bi?input=fishMB_getTaskPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishGameGold(id) {
        $.post("bi?input=fishMB_getGameGoldPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishLogin(id) {
        $.post("bi?input=fishMB_getLoginPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishBroke(id) {
        $.post("bi?input=fishMB_getBrokePage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishUP(id) {
        $.post("bi?input=fishMB_getUPPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishRed(id) {
        $.post("bi?input=fishMB_getRedPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishVIP(id) {
        $.post("bi?input=fishMB_getVIPPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishCaptain(id) {
        $.post("bi?input=fishMB_getCaptainPage", function (res) {
            $('#' + id).html(res);
        });
    }

    function fishOnline(id) {
        $.post("bi?input=fishMB_getFishOnlinePage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishHuizong(id) {
        $.post("bi?input=fishMB_getHuizongPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishRank(id) {
        $.post("bi?input=fishMB_getRankPage", function (res) {
            $('#' + id).html(res);
        });
    }


    function fishBasic(id) {
        $.post("bi?input=fishMB_getBasicPage", function (res) {
            $('#' + id).html(res);
        });
    }
    function fishItem(id) {
        $.post("bi?input=fishMB_getItemPage", function (res) {
            $('#' + id).html(res);
        });
    }

</script>


</body>
</html>