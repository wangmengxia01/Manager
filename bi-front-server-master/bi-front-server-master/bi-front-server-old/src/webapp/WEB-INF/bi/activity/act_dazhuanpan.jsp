<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BI System</title>

    <jsp:include page="../common_meta.jsp"/>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap-datepicker3.min.css">

</head>

<body>

<!-- common head and left -->
<jsp:include page="../tpg_head.jsp"/>

<div class="body slide">
    <jsp:include page="../tpg_left.jsp"/>

    <div class="container-fluid left-border">

        <div class="row"></div>

        <div class="col-md-5 col-lg-5">
            <h1><i class="fa fa-money"></i> 押宝大转盘</h1>
        </div>

        <div class="row"></div>

        <form>

            <div class="form-group col-md-2">
                <input id="timeBegin" name="timeBegin" size="16" type="text" readonly
                       class="form-control form_date">
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
                <input id="offsetTimeZone" name="offsetTimeZone" type="hidden"/>
                <input type="button" onclick="query();" class="btn btn-warning" value="查询"/>
            </div>

            <div class="form-group col-md-2">
                <input type="button" onclick="download();" class="btn btn-success" value="导出Excel"/>
            </div>

        </form>

        <div class="row"></div>

        <div class="table-responsive">
            <table id="wakuangchan" class="table table-striped">

            </table>
        </div>


    </div>
</div>


<jsp:include page="../common_js.jsp"/>
<script src="/js/bootstrap/bootstrap-datepicker.min.js"></script>
<script src="/js/bootstrap/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/js/my_datetime.js"></script>

<iframe id="iframe1" src="" width="0" height="0" frameborder="0" scrolling="auto" style="z-index: -2;"></iframe>

<script>
    $('.form_date').datepicker({format: "yyyy-mm-dd", language: "zh-CN", autoclose: true});
    today();

    function query() {
        $.blockUI();
        $.post("bi?input=act_queryZhuanpan", {
            timeBegin: $('#timeBegin').val(),
            timeEnd: $('#timeEnd').val(),
            offsetTimeZone: $('#offsetTimeZone').val(),
            timeInterval: "DAY"
        }, function (res) {
            $('#wakuangchan').html(res);
            $.unblockUI();
        });
    }

    //
    function download() {
        $('#iframe1').attr("src", "bi?input=act_downloadZhuanpan"
                + "&timeBegin=" + $('#timeBegin').val()
                + "&timeEnd=" + $('#timeEnd').val()
                + "&offsetTimeZone=" + $('#offsetTimeZone').val()
                + "&timeInterval=" + "DAY"
                + "&fileName=" + "act_zhuanpan.csv");
    }
</script>


</body>
</html>