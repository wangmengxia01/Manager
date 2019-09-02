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
                <i class="fa fa-server"></i> BI服务器
            </h1>
        </div>

        <div class="row"></div>

        <!--  第一个表开始  -->
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
                <c:forEach var="user" items="${biList}">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.userName}</td>
                        <td><input type="button"
                                   class="btn btn-danger"
                                   onclick="delBiServerUser( ${user.userId} )"
                                   value="删除"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
        <input id="showAddBiServerBtn" type="button"
               class="btn btn-info form-group"
               onclick="showAddBiServerUser()" value="+"/>

        <br/>
        <br/>
        <div id="addBiServerUser" style="display: none">
            <form action="bi?input=system_addBiServerUser"
                  method="post">

                <div class="row"></div>
                <div class="form-group col-md-4">
                    <input type="text"
                           class="form-control input-lg"
                           name="biServerUserName" value=""
                           placeholder="用户名" required autofocus>
                </div>
                <div class="row"></div>
                <div class="form-group col-md-4">
                    <input type="password"
                           class="form-control input-lg"
                           name="biServerPassWord"
                           id="biServerPassWord" value=""
                           placeholder="密码" required>
                </div>
                <div class="row"></div>
                <div class="form-group col-md-4">
                    <input type="password"
                           class="form-control input-lg"
                           name="biServerPassWord2"
                           id="biServerPassWord2" value=""
                           placeholder="确认密码" required>
                </div>
                <div class="row"></div>
                <input type="submit" onclick="return checkBi();"
                       class="btn btn-success" value="确认"/> <input
                    type="button" class="btn btn-info"
                    onclick="hideAddBiServerUser()" value="取消"/>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../common_js.jsp"/>
<script>
    function delBiServerUser(id) {
        if (confirm("确定删除？")) {
            window.location.href = "/bi?input=system_delBiServerUser&userId=" + id;
        }
    }

    function showAddBiServerUser() {
        $('#addBiServerUser').show();
        $('#showAddBiServerBtn').val('-').attr("onclick", "hideAddBiServerUser();");
    }
    function hideAddBiServerUser() {
        $('#addBiServerUser').hide();
        $('#showAddBiServerBtn').val('+').attr("onclick", "showAddBiServerUser();");
    }

    function checkBi() {
        if ($("#biServerPassWord").val() != $("#biServerPassWord2").val()) {
            alert("两次密码不一致");
            return false;
        }
        return true;
    }
</script>

<jsp:include page="../err.jsp"/>
</body>
</html>