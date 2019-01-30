<%--
  Created by IntelliJ IDEA.
  User: HalaveikaV
  Date: 1/29/2019
  Time: 1:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Open OCR Java Client</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
          integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
</head>
<body>
    <h1 style="text-align: center">Open OCR client in Java</h1>
    <br>
    <div class="form-group col-7">
        <label for="resultForARemoteImage">Result</label>
        <textarea class="form-control" id="resultForARemoteImage" rows="3">${response}</textarea>
    </div>
</body>
</html>
