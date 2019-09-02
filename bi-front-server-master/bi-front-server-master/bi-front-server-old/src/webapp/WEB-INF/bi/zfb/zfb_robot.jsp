<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap-datepicker3.min.css">

<form>

    <div class="form-group col-md-2">
        <input id="timeBegin" name="timeBegin" size="16" type="text" readonly class="form-control form_date">
    </div>

    <div class="form-group col-md-2">
        <input id="timeEnd" name="timeEnd" size="16" type="text" readonly class="form-control form_date">
    </div>

    <div class="form-group col-md-2">
        <div class="btn-group" role="group">
            <button type="button" onclick="prevDay();" class="btn btn-default">&lt;</button>
            <button type="button" onclick="today();" class="btn btn-info">今天</button>
            <button type="button" onclick="nextDay();" class="btn btn-default">&gt;</button>
        </div>
    </div>

    <div class="form-group col-md-2">
        <input type="button" id="robotIdBtn" onclick="showRobotIDs();" class="btn btn-success" value="机器人ID"/>
    </div>

    <div class="form-group col-md-2">
        <input id="offsetTimeZone" name="offsetTimeZone" type="hidden"/>
        <input type="button" onclick="query();" class="btn btn-warning" value="查询"/>
    </div>

    <div class="form-group col-md-2">
        <input type="button" onclick="download();" class="btn btn-success" value="导出Excel"/>
    </div>

    <div class="row"></div>

    <div class="form-group col-md-10" id="robotIDs" style="display: none">
        <textarea class="form-control" rows="3" id="robotIDStr"></textarea>
    </div>
</form>

<div class="row"></div>

<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 机器人总数据</h4>
</div>

<div class="row"></div>

<div class="table-responsive">
    <table id="robotSummary" class="table table-striped">

    </table>
</div>

<div class="row"></div>


<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 机器人单独数据</h4>
</div>


<div class="row"></div>

<div class="table-responsive">
    <table id="robotSingle" class="table table-striped">

    </table>
</div>


<script src="/js/bootstrap/bootstrap-datepicker.min.js"></script>
<script src="/js/bootstrap/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/js/my_datetime.js"></script>

<iframe id="iframe1" src="" width="0" height="0" frameborder="0" scrolling="auto" style="z-index: -2;"></iframe>

<script>
    $('.form_date').datepicker({format: "yyyy-mm-dd", language: "zh-CN", autoclose: true});
    today();

    function showRobotIDs() {
        $('#robotIDs').show();
        $('#robotIdBtn').attr("onclick", "hideRobotIDs();");
    }
    function hideRobotIDs() {
        $('#robotIDs').hide();
        $('#robotIdBtn').attr("onclick", "showRobotIDs();");
    }

    function query() {
        $.blockUI();
        $.post("bi?input=zfb_queryRobot", {
            timeBegin: $('#timeBegin').val(),
            timeEnd: $('#timeEnd').val(),
            offsetTimeZone: $('#offsetTimeZone').val(),
            timeInterval: "DAY",
            robotIDs: $('#robotIDStr').val()
        }, function (res) {
            if (res.indexOf("<thead>") < 0) $('#robotSummary').html(res);
            else {
                arr = res.trim().split('\n');
                $('#robotSummary').html(arr[0]);
                $('#robotSingle').html(arr[1]);
            }
            $.unblockUI();
        });
    }

    function download() {
        $('#iframe1').attr("src", "bi?input=zfb_downloadRobot"
                + "&timeBegin=" + $('#timeBegin').val()
                + "&timeEnd=" + $('#timeEnd').val()
                + "&offsetTimeZone=" + $('#offsetTimeZone').val()
                + "&timeInterval=" + "DAY"
                + "&robotIDs=" + $('#robotIDStr').val()
                + "&fileName=" + "zfb_robot.csv");
    }
</script>
<jsp:include page="../err.jsp"/>
