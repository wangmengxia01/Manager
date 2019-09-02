<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap-datepicker3.min.css">

<div class="col-md-6" style="border-right-color:#4F4F4F; border-right-style:solid; border-right-width:2px;">
    <form>
        <div class="form-group col-md-4">
            <input id="timeBegin" name="timeBegin" size="16" type="text" readonly class="form-control form_date">
        </div>

        <div class="form-group col-md-4">
            <input id="timeEnd" name="timeEnd" size="16" type="text" readonly class="form-control form_date">
        </div>

        <div class="form-group col-md-4">
            <div class="btn-group" role="group">
                <button type="button" onclick="prevDay();" class="btn btn-default">&lt;</button>
                <button type="button" onclick="today();" class="btn btn-info">今天</button>
                <button type="button" onclick="nextDay();" class="btn btn-default">&gt;</button>
            </div>
        </div>

        <div class="row"></div>

        <div class="form-group col-md-4">
            <input type="text" class="form-control" id="limit"
                   placeholder="IP最少出现次数" required>
        </div>

        <div class="form-group col-md-4">
            <input id="offsetTimeZone" name="offsetTimeZone" type="hidden"/>
            <input type="button" onclick="queryPossible();" class="btn btn-warning" value="查询可疑IP"/>
        </div>

        <div class="form-group col-md-4">
            <input type="button" onclick="downloadPossible();" class="btn btn-success" value="导出Excel"/>
        </div>

    </form>

    <div class="row"></div>

    <div class="table-responsive">
        <table id="possibleIpTable" class="table table-striped">

        </table>
    </div>

</div>

<div class="col-md-6">
    <form>
        <div class="form-group col-md-4">
            <input type="text" class="form-control" id="userId"
                   placeholder="玩家ID" required>
        </div>

        <div class="form-group col-md-4">
            <input type="button" onclick="query();" class="btn btn-warning" value="查询"/>
        </div>
    </form>

    <div class="form-group col-md-4">
        <input type="button" onclick="download();" class="btn btn-success" value="导出Excel"/>
    </div>

    <div class="row"></div>

    <div class="table-responsive">
        <table id="userIpTable" class="table table-striped">

        </table>
    </div>
</div>

<script src="/js/bootstrap/bootstrap-datepicker.min.js"></script>
<script src="/js/bootstrap/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/js/my_datetime.js"></script>

<iframe id="iframe1" src="" width="0" height="0" frameborder="0" scrolling="auto" style="z-index: -2;"></iframe>
<iframe id="iframe2" src="" width="0" height="0" frameborder="0" scrolling="auto" style="z-index: -2;"></iframe>

<script>
    $('.form_date').datepicker({format: "yyyy-mm-dd", language: "zh-CN", autoclose: true});
    today();

    function queryPossible() {
        if ($('#limit').val().trim() == "") {
            alert("IP最少出现次数不能为空");
            return;
        }
        $.blockUI();
        $.post("bi?input=singleUser_queryPossibleIp", {
            timeBegin: $('#timeBegin').val(),
            timeEnd: $('#timeEnd').val(),
            offsetTimeZone: $('#offsetTimeZone').val(),
            timeInterval: "NONE",
            limit: $('#limit').val(),
        }, function (res) {
            $('#possibleIpTable').html(res);
            $.unblockUI();
        });
    }

    function downloadPossible() {
        if ($('#limit').val().trim() == "") {
            alert("IP最少出现次数不能为空");
            return;
        }
        $('#iframe1').attr("src", "bi?input=singleUser_downloadPossibleIp"
                + "&timeBegin=" + $('#timeBegin').val()
                + "&timeEnd=" + $('#timeEnd').val()
                + "&offsetTimeZone=" + $('#offsetTimeZone').val()
                + "&timeInterval=" + "NONE"
                + "&limit=" + $('#limit').val()
                + "&fileName=" + "single_user_possible_ip.csv");
    }

    function query() {
        if ($('#userId').val().trim() == "") {
            alert("用户ID不能为空");
            return;
        }
        $.blockUI();
        $.post("bi?input=singleUser_queryUserIp", {
            userId: $('#userId').val()
        }, function (res) {
            $('#userIpTable').html(res);
            $.unblockUI();
        });
    }

    function download() {
        if ($('#userId').val().trim() == "") {
            alert("用户ID不能为空");
            return;
        }
        $('#iframe2').attr("src", "bi?input=singleUser_downloadUserIp"
                + "&userId=" + $('#userId').val()
                + "&fileName=" + "single_user_ip.csv");
    }

</script>
<jsp:include page="../err.jsp"/>
