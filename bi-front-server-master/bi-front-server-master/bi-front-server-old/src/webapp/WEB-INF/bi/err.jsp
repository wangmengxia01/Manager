<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<script>
    (function handleErr() {
        var msg = "${alert}";
        if (msg) {
            alert(msg);
        }

        var msg2 = "${popup}";
        if (msg2) {
            $("#showMessageText").html(msg2);
            $("#showMessageBg").show(300).delay(3000).hide(300);
        }
    })();
</script>
