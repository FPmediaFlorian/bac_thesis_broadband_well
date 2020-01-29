<%--
  Created by IntelliJ IDEA.
  User: florianpichlmann
  Date: 07.01.20
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.time.LocalDateTime" %>

<footer>
    <div class="container mb-2">
        <div class="row align-items-center ">
            <div class="col-lg-6">
                <a href="mailt:bbw@randomurl.net">Contact us</a> | <a href="#">Privacy</a> | <a href="#">Terms of
                Use</a> <br>
                &copy; <%out.print(LocalDateTime.now().getYear());%> Broadbandwell Prototype
            </div>

            <div class="col-lg-6">
                <img src="resources/img/BBW_Logo_trans.png" class="d-inline-block align-middle mr-2 logo-small">
            </div>

        </div>
    </div>

</footer>

<script>

</script>
</body>
</html>
