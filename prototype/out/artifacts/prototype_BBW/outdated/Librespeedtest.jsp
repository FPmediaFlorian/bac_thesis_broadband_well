<%-- Import Header File --%>
<%@ include file="../templates/header.jsp" %>

<h1>LibreSpeed Example</h1>


<h4>Download</h4>
<p id="download"></p>

<h4>Upload</h4>
<p id="upload"></p>




<script type="text/javascript">
    s = new Speedtest();
    s.setParameter("url_dl","//st-be-bo1.infra.garr.it/garbage.php");
    s.setParameter("url_ul","//st-be-bo1.infra.garr.it/empty.php");
    s.setParameter("test_order","D_U");

    s.onupdate = function (data) { // when status is received, put the values in the appropriate fields
        document.getElementById('download').textContent = data.dlStatus + ' Mbit/s'
        document.getElementById('upload').textContent = data.ulStatus + ' Mbit/s'
    }
    s.start();
</script>


<%-- Import Footer File --%>
<%@ include file="../templates/footer.jsp" %>