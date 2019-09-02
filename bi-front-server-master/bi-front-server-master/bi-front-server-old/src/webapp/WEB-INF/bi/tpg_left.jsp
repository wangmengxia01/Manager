<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<aside class="sidebar show perfectScrollbar">
    <div id="solso-sidebar" style="overflow:auto;height:94%">

        <div class="panel-group" id="accordion" role="tablist"
             aria-multiselectable="true">

            <c:if test="${kUserInfo.name == 'admin'}">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="heading0">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapse0" aria-expanded="true"
                               aria-controls="collapse0">
                                <i class="fa fa-gear"></i> 管理项 <i class="pull-right fa fa-caret-down"></i>
                            </a>
                        </h4>
                    </div>

                    <div id="collapse0" class="panel-collapse collapse"
                         role="tabpanel" aria-labelledby="heading0">
                        <div>
                            <a href="bi?input=system_listGmUsers" class="list-group-item">用户管理</a>
                            <a href="bi?input=system_listBiServerUsers" class="list-group-item">BI服务器</a>
                            <a href="bi?input=system_listGameServerUsers" class="list-group-item">Game服务器</a>
                        </div>
                    </div>
                </div>
            </c:if>


            <%--<c:if test="${值 == Slist}">--%>
            <%--存在--%>
            <%--</c:if>--%>

            　　　
            　
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading1">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion"
                           href="#collapse1" aria-expanded="true"
                           aria-controls="collapse1">
                            <i class="fa fa-sitemap"></i> 汇总项 <i class="pull-right fa fa-caret-down"></i>
                        </a>
                    </h4>
                </div>

                <div id="collapse1" class="panel-collapse collapse"
                     role="tabpanel" aria-labelledby="heading1">
                    <div>
                        <c:forEach var="Slist" items="${kUserAuth}">
                            <c:if test="${Slist ==  '116' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getInstallPayHuizongPage" class="list-group-item">收入汇总</a>
                            </c:if>
                            <c:if test="${Slist ==  '100' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getInstallPayPage&team=TPG" class="list-group-item">收入基础</a>
                            </c:if>
                            <c:if test="${Slist ==  '125' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getDanTouHuiZongPage&team=TPG" class="list-group-item">弹头汇总</a>
                            </c:if>
                            <c:if test="${Slist ==  '126' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getZiChongPage&team=TPG" class="list-group-item">自充查询</a>
                            </c:if>
                            <c:if test="${Slist ==  '126' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getZiChongXiangXiPage&team=TPG" class="list-group-item">自充详细账号及金额</a>
                            </c:if>
                            <c:if test="${Slist ==  '127' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getZhouBaoPage&team=TPG" class="list-group-item">周报</a>
                            </c:if>
                            <c:if test="${Slist ==  '115' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getHuizongPage" class="list-group-item">德州金融汇总</a>
                            </c:if>
                            <c:if test="${Slist ==  '117' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getFishHuizongPage" class="list-group-item">捕鱼金融汇总</a>
                            </c:if>
                            <c:if test="${Slist ==  '110' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getFinancePage" class="list-group-item">德州基本报表</a>
                            </c:if>
                            <c:if test="${Slist ==  '120' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getFishFinancePage" class="list-group-item">捕鱼基本报表</a>
                            </c:if>
                            <c:if test="${Slist ==  '6666' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getLiushuiPage" class="list-group-item">流水汇总报表</a>
                            </c:if>
                            <c:if test="${Slist ==  '6666' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getChoushuiLvPage" class="list-group-item">抽水率报表</a>
                            </c:if>
                            <c:if test="${Slist ==  '6667' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=summary_getPeoplePage" class="list-group-item">总人数报表</a>
                            </c:if>
                        </c:forEach>

                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading2">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion"
                           href="#collapse2" aria-expanded="true"
                           aria-controls="collapse2">
                            <i class="fa fa-clone"></i> 产品项 <i
                                class="pull-right fa fa-caret-down"></i>
                        </a>
                    </h4>
                </div>

                <div id="collapse2" class="panel-collapse collapse"
                     role="tabpanel" aria-labelledby="heading2">
                    <div>
                        <c:forEach var="Slist" items="${kUserAuth}">
                            <c:if test="${Slist ==  '7001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=texaspoker_getPage" class="list-group-item">老德州</a>
                            </c:if>
                            <c:if test="${Slist ==  '7004' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=newpoker_getPage" class="list-group-item">新德州</a>
                            </c:if>
                            <c:if test="${Slist ==  '1000' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=zfb_getPage" class="list-group-item">中发白</a>
                            </c:if>
                            <c:if test="${Slist ==  '13001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=fish_getPage" class="list-group-item">无双捕鱼</a>
                            </c:if>
                            <c:if test="${Slist ==  '20043' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getGuessPokerPage" class="list-group-item">渔场猜倍活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '13002' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=fishMB_getPage" class="list-group-item">捕鱼手机版</a>
                            </c:if>
                            <c:if test="${Slist ==  '13003' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=brdz_getPage" class="list-group-item">百人德州</a>
                            </c:if>
                            <c:if test="${Slist ==  '2010' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=mtl2_getPage" class="list-group-item">摩天轮</a>
                            </c:if>
                            <c:if test="${Slist ==  '3001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=fruit_getPage" class="list-group-item">水果大满贯</a>
                            </c:if>
                            <c:if test="${Slist ==  '4001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=ppt_getPage" class="list-group-item">跑跑堂</a>
                            </c:if>
                            <c:if test="${Slist ==  '5001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=dog_getPage" class="list-group-item">汪汪运动会</a>
                            </c:if>
                            <c:if test="${Slist ==  '11001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=mjdr_getXueliuPage" class="list-group-item">血流成河</a>

                                <a href="bi?input=mjdr_getXuezhanPage" class="list-group-item">血战到底</a>

                                <a href="bi?input=mjdr_getErmaPage" class="list-group-item">二人麻将</a>

                                <a href="bi?input=mjdr_getPage" class="list-group-item">麻将合集玩法</a>

                                <a href="bi?input=mjdr_getKaifangPage" class="list-group-item">麻将开房玩法</a>
                            </c:if>
                            <c:if test="${Slist ==  '50003' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=ddz_getPage" class="list-group-item">斗地主</a>
                            </c:if>
                            <c:if test="${Slist ==  '6001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=farm_getPage" class="list-group-item">黄金矿工</a>
                            </c:if>
                            <c:if test="${Slist ==  '12001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=product_getPage" class="list-group-item">公共玩法</a>
                            </c:if>
                            <c:if test="${Slist ==  '10001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=product_getExchangePage" class="list-group-item">实物兑换</a>
                            </c:if>
                            <c:if test="${Slist ==  '40001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=props_getPropsPage" class="list-group-item">重要道具监控</a>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading3">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion"
                           href="#collapse3" aria-expanded="true"
                           aria-controls="collapse3">
                            <i class="fa fa-user"></i> 用户项 <i class="pull-right fa fa-caret-down"></i>
                        </a>
                    </h4>
                </div>

                <div id="collapse3" class="panel-collapse collapse"
                     role="tabpanel" aria-labelledby="heading3">
                    <div>
                        <c:forEach var="Slist" items="${kUserAuth}">
                            <c:if test="${Slist ==  '101' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getInstallPayDaysPage&team=TPG" class="list-group-item">新增后续付费</a>
                            </c:if>
                            <c:if test="${Slist ==  '119' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getshichangPage&team=TPG" class="list-group-item">市场部汇总表</a>
                            </c:if>
                            <c:if test="${Slist ==  '99' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getInstallRetentionPage&team=TPG" class="list-group-item">新增用户留存</a>
                            </c:if>
                            <c:if test="${Slist ==  '99' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getPayRetentionPage&team=TPG" class="list-group-item">付费用户留存</a>
                            </c:if>
                            <c:if test="${Slist ==  '99' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getFuFeiLvPage&team=TPG" class="list-group-item">付费用户持续付费率</a>
                            </c:if>
                            <c:if test="${Slist ==  '102' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=singleUser_getPage&team=TPG" class="list-group-item">单用户查询</a>
                            </c:if>
                            <c:if test="${Slist ==  '108' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getLTVPage&team=TPG" class="list-group-item">LTV查询</a>
                            </c:if>
                            <c:if test="${Slist ==  '118' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getPAYDETAILPage&team=TPG" class="list-group-item">玩家付费排行</a>
                            </c:if>
                            <c:if test="${Slist ==  '121' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getUserDantouPage&team=TPG" class="list-group-item">用户弹头使用查询</a>
                            </c:if>
                            <c:if test="${Slist ==  '122' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getUserHuiZongPage&team=TPG" class="list-group-item">用户金融</a>
                            </c:if>
                            <c:if test="${Slist ==  '40002' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getUserRMBPage&team=TPG" class="list-group-item">用户人民币统计</a>
                            </c:if>

                            <%--<c:if test="${Slist ==  '123' || kUserInfo.name == 'admin'}">--%>
                            <%--<a href="bi?input=user_getUserGameWinPage&team=TPG" class="list-group-item">用户游戏输赢统计</a>--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${Slist ==  '109' || kUserInfo.name == 'admin'}">--%>
                            <%--<a href="bi?input=user_getUserPhoneNumberPage&team=TPG" class="list-group-item">玩家手机号</a>--%>
                            <%--</c:if>--%>
                            <c:if test="${Slist ==  '113' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=user_getDAUPage&team=TPG" class="list-group-item">用户活跃度</a>
                            </c:if>
                            <%--<c:if test="${Slist ==  '114' || kUserInfo.name == 'admin'}">--%>
                            <%--<a href="bi?input=user_getUserWinLosePage&team=TPG" class="list-group-item">用户输赢统计</a>--%>
                            <%--</c:if>--%>
                        </c:forEach>

                        <%--<a href="bi?input=user_getOnlinePage&team=TPG" class="list-group-item">新增用户在线统计</a>--%>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading4">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion"
                           href="#collapse4" aria-expanded="true"
                           aria-controls="collapse4">
                            <i class="fa fa-calendar-check-o"></i> 活动项 <i class="pull-right fa fa-caret-down"></i>
                        </a>
                    </h4>
                </div>

                <div id="collapse4" class="panel-collapse collapse"
                     role="tabpanel" aria-labelledby="heading4">
                    <div>
                        <c:forEach var="Slist" items="${kUserAuth}">
                            <%--<c:if test="${Slist ==  '20001' || kUserInfo.name == 'admin'}">--%>
                            <%--<a href="bi?input=act_getGoldEggPage" class="list-group-item">幸运金蛋BI</a>--%>
                            <%--</c:if>--%>
                            <c:if test="${Slist ==  '20044' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getKaiXinZhuanPanPage" class="list-group-item">开心转盘活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20040' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getQingCaiShenPage" class="list-group-item">手机财神拼图活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20039' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getHaiDiDuoBaoPage" class="list-group-item">手机海底夺宝活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20037' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getShengDanFishPage" class="list-group-item">手机圣诞节活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20035' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getQiQiuPage" class="list-group-item">手机气球活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20033' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getZaDanPage" class="list-group-item">手机捕鱼砸蛋活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20032' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getDaFuWengPage" class="list-group-item">手机捕鱼大富翁活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20021' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getChouQianPage" class="list-group-item">手机捕鱼夺宝活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20030' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getNanGuaPage" class="list-group-item">手机万圣节活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20001' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getGoldEggPage" class="list-group-item">幸运金蛋BI</a>
                            </c:if>
                            <c:if test="${Slist ==  '20002' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getDuoBaoPage" class="list-group-item">一元夺宝</a>
                            </c:if>
                            <c:if test="${Slist ==  '20000' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getKuangChanZongPage" class="list-group-item">挖矿铲活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20005' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getJuBaoPageZong" class="list-group-item">聚宝盆</a>
                            </c:if>
                            <c:if test="${Slist ==  '20006' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getZhuanpanPage" class="list-group-item">押宝大转盘</a>
                            </c:if>
                            <c:if test="${Slist ==  '20007' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getFanpaiPage" class="list-group-item">翻牌活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20008' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getNianshouPage" class="list-group-item">年兽活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20009' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getShengdanPage" class="list-group-item">圣诞活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20011' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getYuandanPage" class="list-group-item">古墓活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20012' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getPintuPage" class="list-group-item">拼图活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20013' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getDengLongPage" class="list-group-item">灯笼转盘活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20014' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getHongBaoPage" class="list-group-item">抢红包活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20015' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getTieRenPage" class="list-group-item">铁人六项活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20016' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getLuckyChouPage" class="list-group-item">幸运抽奖活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20017' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getTaoNiuPage" class="list-group-item">套牛活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20018' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getNewZhuanPanPage" class="list-group-item">新转盘活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20019' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getSheJiPage" class="list-group-item">射击活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20020' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getQiFuPage" class="list-group-item">祈福活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20022' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getMoPingPage" class="list-group-item">魔瓶活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20023' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getChuoPage" class="list-group-item">戳戳活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20024' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getBaoZiPage" class="list-group-item">吃包子活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20025' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getGuanZiPage" class="list-group-item">罐子活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20026' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getWangPaiPage" class="list-group-item">王牌活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20027' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getWanMiPage" class="list-group-item">万米拉力赛活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20028' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getPaiShenPage" class="list-group-item">怼牌神活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20029' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getYueBingPage" class="list-group-item">中秋月饼活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20021' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getWanShengPage" class="list-group-item">德州万圣节活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20034' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getGanEnPage" class="list-group-item">德州感恩节活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20038' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getWaZiPage" class="list-group-item">德州圣诞袜活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20041' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getZaJinDanPage" class="list-group-item">德州砸金蛋活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20042' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getYYLPage" class="list-group-item">德州摇摇乐活动</a>
                            </c:if>
                            <c:if test="${Slist ==  '20010' || kUserInfo.name == 'admin'}">
                                <a href="bi?input=act_getDownloadPage" class="list-group-item">推荐页下载详情</a>
                            </c:if>
                        </c:forEach>

                    </div>
                </div>
            </div>

        </div>
    </div>
</aside>

