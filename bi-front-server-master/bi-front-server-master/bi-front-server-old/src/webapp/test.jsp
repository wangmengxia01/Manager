<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="WEB-INF/bi/common_meta.jsp" />
  <jsp:include page="WEB-INF/bi/common_js.jsp" />
</head>
<body>

<div class="notifications"></div>

<div class="col-md-5">
<div class="table-responsive">
    <table id="zfbBasic" class="table table-striped">
        <thead>
          <tr>
            <th>head1</th>
            <th>head2</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>abcdef<br>abcdef</td>
            <td>abcdef<br>abcdef</td>
          </tr>
        </tbody>
    </table>
</div>
</div>

<div class="col-md-5">
<div class="table-responsive">
    <table id="zfbBasic2" class="table table-striped">
        <thead>
          <tr>
            <th>head1</th>
            <th>head2</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>abcdef<br>abcdef</td>
            <td>abcdef<br>abcdef</td>
          </tr>
        </tbody>
    </table>
</div>
</div>


<script>
$( '.notifications' ).notify({
    message: { text: "没有权限" },
    type: "blackgloss"
}).show();
</script>

</body>
</html> 