<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <h1>
                <i class="fa fa-server"></i> Game服务器
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
                <c:forEach var="user" items="${gameList}">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.userName}</td>
                        <td><input type="button"
                                   class="btn btn-danger"
                                   onclick="delGameServerUser( ${user.userId} )"
                                   value="删除"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
        <input id="showAddGameServerBtn" type="button"
               class="btn btn-info form-group"
               onclick="showAddGameServerUser()" value="+"/>

        <br/>
        <br/>
        <div id="addGameServerUser" style="display: none">
            <form action="bi?input=system_addGameServerUser"
                  method="post">

                <div class="row"></div>
                <div class="form-group col-md-4">
                    <input type="text"
                           class="form-control input-lg"
                           name="gameServerUserName" value=""
                           placeholder="用户名" required autofocus>
                </div>
                <div class="row"></div>
                <div class="form-group col-md-4">
                    <input type="password"
                           class="form-control input-lg"
                           name="gameServerPassWord"
                           id="gameServerPassWord" value=""
                           placeholder="密码" required>
                </div>
                <div class="row"></div>
                <div class="form-group col-md-4">
                    <input type="password"
                           class="form-control input-lg"
                           name="gameServerPassWord2"
                           id="gameServerPassWord2" value=""
                           placeholder="确认密码" required>
                </div>
                <div class="row"></div>
                <input type="submit" onclick="return checkGame();"
                       class="btn btn-success" value="确认"/> <input
                    type="button" class="btn btn-info"
                    onclick="hideAddGameServerUser()" value="取消"/>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../common_js.jsp"/>
<script>

    function delGameServerUser(id) {
        if (confirm("确定删除？")) {
            window.location.href = "/bi?input=system_delGameServerUser&userId=" + id;
        }
    }

    function showAddGameServerUser() {
        $('#addGameServerUser').show();
        $('#showAddGameServerBtn').val('-').attr("onclick", "hideAddGameServerUser();");
    }
    function hideAddGameServerUser() {
        $('#addGameServerUser').hide();
        $('#showAddGameServerBtn').val('+').attr("onclick", "showAddGameServerUser();");
    }

    function checkGame() {
        if ($("#passWordGame").val() != $("#passWordGame2").val()) {
            alert("两次密码不一致");
            return false;
        }
        return true;
    }
</script>

<jsp:include page="../err.jsp"/>
</body>
</html>