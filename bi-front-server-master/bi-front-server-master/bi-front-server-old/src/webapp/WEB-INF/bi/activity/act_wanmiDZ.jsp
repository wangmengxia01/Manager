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
        <input id="offsetTimeZone" name="offsetTimeZone" type="hidden"/>
        <input id="limit" name="limit" type="hidden" value="10000"/>
        <input type="button" onclick="query();" class="btn btn-warning" value="查询"/>
    </div>

    <div class="form-group col-md-2">
        <input type="button" onclick="download();" class="btn btn-success" value="导出Excel"/>
    </div>

</form>

<div class="row"></div>

<div class="table-responsive">
    <table id="mjdrRank" class="table table-striped">

    </table>
</div>


<script src="/js/bootstrap/bootstrap-datepicker.min.js"></script>
<script src="/js/bootstrap/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/js/my_datetime.js"></script>

<iframe id="iframe1" src="" width="0" height="0" frameborder="0" scrolling="auto" style="z-index: -2;"></iframe>

<script>
    $('.form_date').datepicker({format: "yyyy-mm-dd", language: "zh-CN", autoclose: true});
    today();

    function query() {
        $.blockUI();
        $.post("bi?input=act_queryWanMiDZ", {
            timeBegin: $('#timeBegin').val(),
            timeEnd: $('#timeEnd').val(),
            offsetTimeZone: $('#offsetTimeZone').val(),
            timeInterval: "DAY",
            limit: $('#limit').val()
        }, function (res) {
            $('#mjdrRank').html(res);
            $.unblockUI();
        });
    }

    function download() {
        $('#iframe1').attr("src", "bi?input=act_downloadWanMiDZ"
                + "&timeBegin=" + $('#timeBegin').val()
                + "&timeEnd=" + $('#timeEnd').val()
                + "&offsetTimeZone=" + $('#offsetTimeZone').val()
                + "&timeInterval=" + "DAY"
                + "&limit=" + $('#limit').val()
                + "&fileName=" + "moping.csv");
    }

</script>
<jsp:include page="../err.jsp"/>
