<%-- Import Header File --%>
<%@ include file="templates/header.jsp" %>

<h1>LibreSpeed Example</h1>

<h4>IP Address</h4>
<p id="ip"></p>

<h4>Download</h4>
<p id="download"></p>

<h4>Upload</h4>
<p id="upload"></p>

<h4>Latency</h4>
<p id="ping"></p>



<script type="text/javascript">
    s = new Speedtest();
    s.setParameter("url_dl","http://localhost:8080/prototype_BBW/speedtest/garbage.dat");
    s.setParameter("url_ul","http://localhost:8080/prototype_BBW/speedtest/empty.dat");
    s.setParameter("url_ping","http://localhost:8080/prototype_BBW/speedtest/empty.dat");
    s.setParameter("test_order","P_D_U");

    s.onupdate = function (data) { // when status is received, put the values in the appropriate fields
        document.getElementById('download').textContent = data.dlStatus + ' Mbit/s'
        document.getElementById('upload').textContent = data.ulStatus + ' Mbit/s'
        document.getElementById('ping').textContent = data.pingStatus + ' ms, ' + data.jitterStatus + ' ms jitter'
        document.getElementById('ip').textContent = data.clientIp
    }
    s.start();
</script>


<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>