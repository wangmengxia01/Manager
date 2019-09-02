<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <div class="brand">
                    <a class="navbar-brand" href="javascript:void(0);"> <span>BI
              System</span>
                    </a>
                    <a class="toogle pull-right"><i
                            class="fa fa-chevron-left"></i></a>
                </div>
            </div>

            <div class="collapse navbar-collapse"
                 id="bs-example-navbar-collapse-1">
                <div class="navbar-right">
                    <p class="navbar-text">
                        用户：<span>${kUserInfo.name}</span>
                    </p>

                    <a href="bi?input=system_modifyPass"
                       class="btn btn-warning navbar-btn">修改密码</a>
                    <a href="bi?input=system_logout"
                       class="btn btn-danger navbar-btn">退出</a>
                </div>
            </div>
        </div>
    </nav>
</header>
