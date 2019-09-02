<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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
            <h1>
                <i class="fa fa-users"></i> 用户列表
            </h1>
        </div>

        <div class="row"></div>

        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>id</th>
                    <th>用户名</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.userName}</td>
                        <td><input type="button" class="btn btn-danger"
                            ${ user.userName == 'admin' ? " disabled" : "" }
                                   onclick="delGmUser( ${user.userId} )" value="删除"/> <input
                                type="button" class="btn btn-warning"
                            ${ user.userName == 'admin' ? " disabled" : "" }
                                onclick="modAuth( ${user.userId} )" value="修改权限"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
        <input id="showAddBtn" type="button"
               class="btn btn-info form-group" onclick="showAddUser()"
               value="+"/>

        <br/>
        <br/>
        <div id="addGmUser" style="display: none">
            <form onsubmit="generateMd5();"
                  action="bi?input=system_addGmUser" method="post">
                <div class="form-group col-md-4">
                    <input type="text" class="form-control input-lg"
                           name="userName" value="" placeholder="用户名" required
                           autofocus>
                </div>
                <div class="row"></div>
                <div class="form-group col-md-4">
                    <input type="password" class="form-control input-lg"
                           name="passWord" id="passWord" value="" placeholder="密码"
                           required>
                </div>
                <div class="row"></div>
                <div class="form-group col-md-4">
                    <input type="password" class="form-control input-lg"
                           name="passWord2" id="passWord2" value=""
                           placeholder="确认密码" required>
                </div>
                <div class="row"></div>
                <input type="submit" onclick="return check();"
                       class="btn btn-success" value="确认"/> <input type="button"
                                                                   class="btn btn-info" onclick="hideAddUser()"
                                                                   value="取消"/>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../common_js.jsp"/>
<script src="/js/jquery.md5.js"></script>
<script>
    function delGmUser(id) {
        if (confirm("确定删除？")) {
            window.location.href = "/bi?input=system_delGmUser&userId=" + id;
        }
    }
    function modAuth(id) {
        window.location.href = "/bi?input=system_getAuth&userId=" + id;
    }
    function showAddUser() {
        $('#addGmUser').show();
        $('#showAddBtn').val('-').attr("onclick", "hideAddUser();");
    }
    function hideAddUser() {
        $('#addGmUser').hide();
        $('#showAddBtn').val('+').attr("onclick", "showAddUser();");
    }
    function generateMd5() {
        $("#passWord").val($.md5($("#passWord").val()).toUpperCase());
    }
    function check() {
        if ($("#passWord").val() != $("#passWord2").val()) {
            alert("两次密码不一致");
            return false;
        }
        return true;
    }
</script>
<jsp:include page="../err.jsp"/>
</body>
</html>