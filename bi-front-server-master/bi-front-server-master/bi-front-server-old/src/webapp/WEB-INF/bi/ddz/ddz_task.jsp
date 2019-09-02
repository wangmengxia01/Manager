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
        <input type="button" onclick="query();" class="btn btn-warning" value="查询"/>
    </div>

    <div class="form-group col-md-2">
        <input type="button" onclick="download();" class="btn btn-success" value="导出Excel"/>
    </div>

</form>
<div class="row"></div>

<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 任务基础信息</h4>
</div>

<div class="row"></div>

<div class="table-responsive">
    <table id="ddzjichu" class="table table-striped">

    </table>
</div>
<div class="row"></div>

<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 初级任务基础信息</h4>
</div>

<div class="row"></div>

<div class="table-responsive">
    <table id="ddzchuji" class="table table-striped">

    </table>
</div>
<div class="row"></div>

<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 中级任务基础信息</h4>
</div>

<div class="row"></div>

<div class="table-responsive">
    <table id="ddzzhongji" class="table table-striped">

    </table>
</div>
<div class="row"></div>

<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 高级任务基础信息</h4>
</div>

<div class="row"></div>

<div class="table-responsive">
    <table id="ddzgaoji" class="table table-striped">

    </table>
</div>
<div class="row"></div>

<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 精英任务基础信息</h4>
</div>

<div class="row"></div>

<div class="table-responsive">
    <table id="ddzjingying" class="table table-striped">

    </table>
</div>
<div class="row"></div>

<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 大师任务基础信息</h4>
</div>

<div class="row"></div>

<div class="table-responsive">
    <table id="ddzdashi" class="table table-striped">

    </table>
</div>
<div class="row"></div>

<div class="col-md-5 col-lg-5">
    <h4><i class="fa fa-money"></i> 任务完成信息</h4>
</div>
<div class="row"></div>

<div class="table-responsive">
    <table id="ddzrenwu" class="table table-striped">

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
        $.post("bi?input=ddz_queryTask", {
            timeBegin: $('#timeBegin').val(),
            timeEnd: $('#timeEnd').val(),
            offsetTimeZone: $('#offsetTimeZone').val(),
            timeInterval: "DAY"
        }, function (res) {
            if( res.indexOf( "<thead>" ) < 0 ) $('#ddzjichu').html(res);
            else {
                arr = res.trim().split('\n');
                $('#ddzjichu').html(arr[0]);
                $('#ddzchuji').html(arr[1]);
                $('#ddzzhongji').html(arr[2]);
                $('#ddzgaoji').html(arr[3]);
                $('#ddzjingying').html(arr[4]);
                $('#ddzdashi').html(arr[5]);
                $('#ddzrenwu').html(arr[6]);
            }
            $.unblockUI();
        });
    }

    function download() {
        $('#iframe1').attr("src", "bi?input=ddz_downloadTask"
                + "&timeBegin=" + $('#timeBegin').val()
                + "&timeEnd=" + $('#timeEnd').val()
                + "&offsetTimeZone=" + $('#offsetTimeZone').val()
                + "&timeInterval=" + "DAY"
                + "&fileName=" + "ddz_jichu.csv");
    }

</script>
<jsp:include page="../err.jsp"/>
