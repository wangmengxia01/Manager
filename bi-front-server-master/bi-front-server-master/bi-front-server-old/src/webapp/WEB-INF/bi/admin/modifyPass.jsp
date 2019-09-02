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
            <h1><i class="fa fa-lock"></i> 修改密码</h1>
        </div>

        <form name="tykqForm" class="form" onsubmit="generateMd5();"
              action="bi?input=system_modifyPass" method="post">
            <div class="row">
                <div class="form-group col-md-4">
                    <input type="text" class="form-control" name="oldPassWord"
                           id="oldPassWord" placeholder="旧密码" required>
                </div>
                <div class="row"></div>
                <div class="form-group col-md-4">
                    <input type="text" class="form-control" name="newPassWord"
                           id="newPassWord" placeholder="新密码" required>
                </div>
                <div class="row"></div>
                <div class="form-group col-md-4">
                    <input type="text" class="form-control" id="newPassWord2"
                           placeholder="确认新密码" required>
                </div>
                <div class="row"></div>
                <div class="form-group clearfix">
                    <div class="col-md-2">
                        <input type="submit" onclick="return check();"
                               class="btn btn-primary" value="修改"/>
                    </div>
                </div>
            </div>
            <div class="table-responsive"></div>
        </form>

    </div>
</div>

<jsp:include page="../common_js.jsp"/>
<script src="/js/jquery.md5.js"></script>
<script>
    function generateMd5() {

        $("#oldPassWord").val(
                $.md5($("#oldPassWord").val()).toUpperCase());
        $("#newPassWord").val(
                $.md5($("#newPassWord").val()).toUpperCase());
        $("#newPassWord2").val(
                $.md5($("#newPassWord2").val()).toUpperCase());

    }
    function check() {
        if ($("#newPassWord").val() != $("#newPassWord2").val()) {
            alert("两次新密码不一致");
            return false;
        }
        return true;
    }
</script>
<jsp:include page="../err.jsp"/>

</body>
</html>