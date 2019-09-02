<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width">

    <link href='//fonts.googleapis.com/css?family=Roboto:400,100,400italic,700italic,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/animate.min.css">

    <title>BI System</title>

</head>

<body>

<header class="site__header island">
    <div class="wrap">
        <span style="display: block;"><h1 class="site__title mega">BI System</h1></span>
    </div>
</header><!-- /.site__header -->

<main class="site__content island" role="content">
    <div class="wrap">
        <form onsubmit="generateMd5();" action="bi?input=system_login" method="post">
      <span style="display: block;">
        <input type="text" class="input input--dropdown" name="userName" value="" placeholder="username"
               required autofocus>
	  </span>
            <span style="display: block;">
	    <input type="password" class="input input--dropdown" name="passWord" id="passWord" value=""
               placeholder="password" required>
	  </span>
            <span style="display: block;">
	  	<input type="text" class="input input--dropdown" style="width:85px"
               name="captcha" value="" placeholder="code" required autofocus>
		<img style="vertical-align:middle;"
             onclick="javascript:this.src='bi?input=system_captcha&random='+Math.random();"
             src="bi?input=system_captcha" class="captcha-img">
	  </span>
            <input type="submit" class="button butt" value="OK"/>
        </form>
    </div>
</main><!-- /.site__content -->

<script src="/js/jquery-1.11.0.min.js"></script>
<script src="/js/jquery.md5.js"></script>
<script>
    function generateMd5() {
        $("#passWord").val($.md5($("#passWord").val()).toUpperCase());
    }

    (function handleErr() {
        var msg = "${alert}";
        if (msg) alert(msg);

        var msg2 = "${popup}";
        if (msg2) {
            $("#showMessageText").html(msg2);
            $("#showMessageBg").show(300).delay(3000).hide(300);
        }
    })();
</script>

</body>
</html>