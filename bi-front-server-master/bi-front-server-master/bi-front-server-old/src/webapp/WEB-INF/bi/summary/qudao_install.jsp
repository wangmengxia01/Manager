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

<div class="body">
    <%--<jsp:include page="../tpg_left.jsp"/>--%>

    <div class="container-fluid left-border">
        <div class="row"></div>
        <div class="col-md-5 col-lg-5">
            <h1><i class="fa fa-money"></i> </h1>
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

            <div class="form-group col-md-2">
                <input id="offsetTimeZone" name="offsetTimeZone" type="hidden"/>
                <input type="button" onclick="query();" class="btn btn-warning" value="查询"/>
            </div>

            <div class="row"></div>

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
        $.post("bi?input=summary_queryInstall", {
            timeBegin: $('#timeBegin').val(),
            timeEnd: $('#timeEnd').val(),
            offsetTimeZone: $('#offsetTimeZone').val(),
            timeInterval: "DAY",
            notAddDay: 1
        }, function (res) {
            $('#installPayTable').html(res);
            $.unblockUI();
        });
    }
</script>


</body>
</html>