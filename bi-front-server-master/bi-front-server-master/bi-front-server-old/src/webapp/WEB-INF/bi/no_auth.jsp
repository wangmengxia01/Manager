<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="notifications"></div>

<script>
    $('.notifications').notify({
        message: {text: "没有权限"},
        type: "blackgloss"
    }).show();
</script>