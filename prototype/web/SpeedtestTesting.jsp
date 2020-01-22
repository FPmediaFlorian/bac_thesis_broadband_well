<%--
  Created by IntelliJ IDEA.
  User: florianpichlmann
  Date: 21.01.20
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Import Header File --%>
<%@ include file="templates/header.jsp" %>

<h3>DSLReports Speed Test library demo</h3>

<p>

    <button id='startbutton'>Start Test</button>
    <button id='stopbutton'>Stop</button>
    <br>
    status:
    <span id='status'></span>
<br> Log:
</p>
<div id='logdiv' style='font-size:10px; font-face:courier; border:1px solid #ccc; padding:0.5em; max-height:350px; overflow-y: scroll'>
</div>
<script type="text/javascript">
    function stoptest() {
        dslr_speedtest({op: 'stop'});
    }

    function speedtest() {
        log("Start button pushed");

        var o = new Object();

        // Can hint connection type
        // from 0..
        // ["GPRS", "3G", "4G", "WiFi", "Wireless", "Satellite", "DSL", "Cable", "Fiber", "", "Unsure"];
        // undefined == Unsure
        o.conntype = undefined;
        // for complete results, bufferbloat must be true
        // for faster results lacking grades and URL etc
        // set to false
        o.bufferbloat = false;

        // hz can be 4 (fastest), 2 (default) or 1 (slowest)
        // determines speed that onstatus is called
        o.hz = 4;

        o.apiKey = '12345678'; // Test API key

        // fired continuously with basic info
        o.onstatus = function(e) {
            if (e.direction)
                log(e.direction + " megabit/sec: down/up " + e.down + " / " + e.up + " ping=" + e.ping + "ms");

        };

        // fired at 1hz with progress guesstimate
        o.onprogress = function(o) {
            document.getElementById('status').innerHTML = o.doing + " Progress:" + o.progress + "%";
        };

        o.onerror = function(o) {
            // this also marks the test end. oncomplete is not fired
            alert(o.msg);
        };

        // fired once upon successful conclusion
        // o has results.. see log for json version of structure
        o.oncomplete = function(o) {
            var s = JSON.stringify(o);
            log("oncomplete fired " + s);
        };

        // fired if the test wants to ask a question of the user with
        // a YES/NO answer.
        o.onconfirm = function(s) {
            return confirm(s);
        };

        // pass any user data in, it is stored
        // and also returned with result.
        o.udata = { "myuserfield": "myvalue" };

        dslr_speedtest({
            op: 'start',
            params: o
        });
    }

    var e = document.getElementById('startbutton');

    e.addEventListener("click", function() {
        speedtest();
    });
    e = document.getElementById('stopbutton');
    e.addEventListener("click", function() {
        stoptest();
    });

    function log(s) {
        var log = document.getElementById('logdiv');
        log.innerHTML = s + "<br>" + log.innerHTML;
    }
</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>
