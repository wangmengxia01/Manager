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
        <input type="text" class="form-control" id="userId"
               placeholder="玩家ID" required>
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

<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 表1</h4>
</div>

<div class="row"></div>

<div class="table-responsive">
    <table id="table1" class="table table-striped table-bordered table-hover" data-toggle="table">

    </table>
</div>
<div class="row"></div>

<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 表2</h4>
</div>

<div class="row"></div>

<div class="table-responsive">
    <table id="table2" class="table table-striped">

    </table>
</div>
<div class="row"></div>


<script src="/js/bootstrap/bootstrap-datepicker.min.js"></script>
<script src="/js/bootstrap/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/js/my_datetime.js"></script>

<iframe id="iframe1" src="" width="0" height="0" frameborder="0" scrolling="auto" style="z-index: -2;"></iframe>

<script>
    $('.form_date').datepicker({format: "yyyy-mm-dd", language: "zh-CN", autoclose: true});
    today();

    function query() {
        if ($('#userId').val().trim() == "") {
            alert("用户ID不能为空");
            return;
        }
        $.blockUI();
        $.post("bi?input=singleUser_queryChipMove", {
            timeBegin: $('#timeBegin').val(),
            timeEnd: $('#timeEnd').val(),
            offsetTimeZone: $('#offsetTimeZone').val(),
            timeInterval: "DAY",
            userId: $('#userId').val()
        }, function (res) {
            if (res.indexOf("<thead>") < 0) $('#table1').html(res);
            else {
                arr = res.trim().split('\n');
                $('#table1').html(arr[0]);
                $('#table2').html(arr[1]);
            }
            $.unblockUI();
        });
    }

    function download() {
        if ($('#userId').val().trim() == "") {
            alert("用户ID不能为空");
            return;
        }
        $('#iframe1').attr("src", "bi?input=singleUser_downloadChipMove"
                + "&timeBegin=" + $('#timeBegin').val()
                + "&timeEnd=" + $('#timeEnd').val()
                + "&offsetTimeZone=" + $('#offsetTimeZone').val()
                + "&timeInterval=" + "DAY"
                + "&userId=" + $('#userId').val()
                + "&fileName=" + "single_user_chipmove.csv");
    }

</script>
<jsp:include page="../err.jsp"/>
