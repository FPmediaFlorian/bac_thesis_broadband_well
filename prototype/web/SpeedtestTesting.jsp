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
Hello
<script>
    function ch_st_loaded(speedtest) {
        var st_callback = function() {
            /*
              * This method is invoked before the speedtest starts. If it returns false
              * test execution will stop
              *
              * @param array tests an ordered array representing the tests to be performed
              * and the test order. Each item in the array is an object with the following
              * attributes:
              *   concurrency: (optional) concurrency for downlink/uplink tests - this
              *     signifies the number of concurrent threads that will be used during
              *     each test iteration. Test result metrics are an aggregation of metrics
              *     for each thread
              *   duration: estimated test duration in seconds
              *   iterations: total number of test iterations to be performed - including
              *     warmup iterations
              *   location: (optional) location metadata for the test endpoint - an object
              *     with these attributes: city, state, country, lat, long. Not present
              *     for endpoints without location information (e.g. CDN, DNS)
              *   max_size: (optional) maximum size (KB) for uplink/downlink tests
              *   min_size: (optional) minimum size (KB) for uplink/downlink tests
              *   provider_id: identifier of the provider (e.g. aws) - see
              *     https://cloudharmony.com/docs/api#!/api/Get_Providers
              *   region: (optional) identifier of the service region (e.g. us-east-1) - see
              *     https://cloudharmony.com/docs/api#!/api/Get_Service
              *   service: name of the service (e.g. Amazon EC2)
              *   service_id: identifier of the service (e.g. aws:ec2) - see
              *     https://cloudharmony.com/docs/api#!/api/Get_Services
              *   service_type: service type identifier - one of: cdn, compute, dns, paas,
              *     storage
              *   subregion: (optional) identifier of the provider subregion (e.g. us-east-1a)
              *   type: test type identifier - one of: downlink, uplink, latency, dns
              *   warmup: number of warmup iterations - these precede test iterations and
              *     are excluded from result metrics
              *
              * @param object types object with keys corresponding with every test type and
              * values describing associated test parameters. Value objects include the
              * following attributes:
              *   duration: total estimated duration for all tests of this type in seconds
              *   tests: total number of tests of this type
              * @return boolean
              */

            this.started = function(tests, types) {
                 console.log("START CALLBACK");
                 console.log(tests);
                 console.log(types);
        };
        var uplinkRedirectUri = "resources/speedtest/up.html"; // change this to the URI where up.html is accessible on your server
        speedtest.start(st_callback, uplinkRedirectUri);
    }
</script>

<%-- Import Footer File --%>
<%@ include file="templates/footer.jsp" %>
