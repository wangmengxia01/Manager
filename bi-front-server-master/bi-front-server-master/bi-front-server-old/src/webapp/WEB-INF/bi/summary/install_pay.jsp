<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BI System</title>

    <jsp:include page="../common_meta.jsp"/>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap-datetimepicker.min.css">

</head>

<body>

<!-- common head and left -->
<jsp:include page="../tpg_head.jsp"/>

<div class="body slide">
    <jsp:include page="../tpg_left.jsp"/>

    <div class="container-fluid left-border">

        <div class="row"></div>

        <div class="col-md-5 col-lg-5">
            <h1><i class="fa fa-money"></i> 收入详细</h1>
        </div>

        <div class="row"></div>

        <form>

            <div class="form-group col-md-2">
                <input id="timeBegin" name="timeBegin" size="16" type="text" readonly
                       class="form-control form_datetime">
            </div>

            <div class="form-group col-md-2">
                <input id="timeEnd" name="timeEnd" size="16" type="text" readonly class="form-control form_datetime">
            </div>

            <div class="form-group col-md-2">
                <div class="btn-group" role="group">
                    <button type="button" onclick="prevDayWithTime();" class="btn btn-default">&lt;</button>
                    <button type="button" onclick="todayWithTime();" class="btn btn-info">今天</button>
                    <button type="button" onclick="nextDayWithTime();" class="btn btn-default">&gt;</button>
                </div>
            </div>

            <div class="row"></div>

            <input type="text" style="display:none;" id="team" value="${teamGroup}">

            <div class="form-group col-md-2">
                <select id="game" name="game" class="btn btn-default dropdown-toggle">
                    <option value="ALL">所有游戏</option>
                    <c:forEach var="game" items="${games}">
                        <option value="${game.key}">${game.value}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group col-md-2">
                <select id="timeInterval" name="timeInterval" class="btn btn-default dropdown-toggle">
                    <option value="DAY">按天</option>
                    <option value="WEEK">按周</option>
                    <option value="MONTH">按月</option>
                    <option value="HOUR">按小时</option>
                    <option value="NONE">汇总</option>
                </select>
            </div>

            <div class="form-group col-md-2">
                <select id="QuDao" name="QuDao" class="btn btn-default dropdown-toggle">
                    <option value="ALL">所有渠道</option>
                    <option value="ZIMAILIANG">自买量渠道</option>
                    <option value="LIANYUN">联运渠道</option>
                    <option value="YIYEHEZUO">异业合作</option>
                    <option value="IOS">IOS渠道</option>
                    <option value="ZHUBO">主播</option>
                    <option value="CESHI">测试</option>
                </select>
            </div>


            <div class="form-group col-md-2"  style="width: 140px">
                <input type="text" class="form-control" id="qudaohao"
                       placeholder="渠道ID(选填)" required>
            </div>


            <div class="form-group col-md-2">
                <input id="offsetTimeZone" name="offsetTimeZone" type="hidden"/>
                <input type="button" onclick="query();" class="btn btn-warning" value="查询"/>
            </div>

            <div class="form-group col-md-2">
                <input type="button" onclick="download();" class="btn btn-success" value="导出Excel"/>
            </div>

        </form>

        <div class="row"></div>

        <div class="table-responsive">
            <table id="installPayTable" class="table table-striped">

            </table>
        </div>


    </div>
</div>


<jsp:include page="../common_js.jsp"/>
<script src="/js/bootstrap/bootstrap-datetimepicker.min.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/js/my_datetime.js"></script>

<iframe id="iframe1" src="" width="0" height="0" frameborder="0" scrolling="auto" style="z-index: -2;"></iframe>

<script>
    $('.form_datetime').datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        language: "zh-CN",
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        fontAwesome: true
    });
    todayWithTime();

    function query() {
        $.blockUI();
        $.post("bi?input=summary_queryInstallPay", {
            timeBegin: $('#timeBegin').val(),
            timeEnd: $('#timeEnd').val(),
            offsetTimeZone: $('#offsetTimeZone').val(),
            timeInterval: "DAY",
            team: $('#team').val(),
            game: $('#game').val(),
            qudao: $('#QuDao').val(),
            qudaohao: $('#qudaohao').val(),
            notAddDay: 1
        }, function (res) {
            $('#installPayTable').html(res);
            $.unblockUI();
        });
    }

    function download() {
        $('#iframe1').attr("src", "bi?input=summary_downloadInstallPay"
                + "&timeBegin=" + $('#timeBegin').val()
                + "&timeEnd=" + $('#timeEnd').val()
                + "&offsetTimeZone=" + $('#offsetTimeZone').val()
                + "&timeInterval=" + $('#timeInterval').val()
                + "&team=" + $('#team').val()
                + "&game=" + $('#game').val()
                + "&qudao=" + $('#QuDao').val()
                + "&qudaohao=" + $('#qudaohao').val()
                + "&notAddDay=1"
                + "&fileName=" + "install_pay.csv");
    }
</script>


</body>
</html>