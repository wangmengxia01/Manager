<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BI System</title>

    <jsp:include page="../common_meta.jsp"/>
    <script src="/js/jquery-1.11.0.min.js"></script>
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
                <i class="fa fa-star"></i> 用户权限
            </h1>
        </div>

        <div class="row"></div>

        <form action="bi?input=system_setAuth&userId=${userId}" method="post">
            <div class="panel-group" id="accordion2" role="tablist" aria-multiselectable="true">
                <c:forEach var="authGroup" items="${authGroups}">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="heading${authGroup.name}">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion2"
                                   href="#collapse${authGroup.name}" aria-expanded="true"
                                   aria-controls="collapse${authGroup.name}">
                                        ${authGroup.name}
                                </a>
                            </h4>
                        </div>
                        <div id="collapse${authGroup.name}" class="panel-collapse collapse in" role="tabpanel"
                             aria-labelledby="heading${authGroup.name}">
                            <div class="panel-body">
                                <c:forEach var="auth" items="${authGroup.content}">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" id="checkbox${auth.key}" name="authCheck"
                                                   value="${auth.key}">
                                                ${auth.value}
                                        </label>
                                        <c:forEach var="userAuth" items="${userAuths}">
                                            <c:if test="${userAuth == auth.key}">
                                                <script>
                                                    $("#checkbox${auth.key}").attr("checked", "true");
                                                </script>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <input type="submit" class="btn btn-warning" value="更新权限"/>
            <input type="button" class="btn btn-danger"
                   onclick="javascrtpt:window.location.href='/bi?input=system_listGmUsers'" value="取消"/>
        </form>


    </div>
</div>

<jsp:include page="../common_js.jsp"/>

</body>
</html>