<%--
  Created by IntelliJ IDEA.
  User: HalaveikaV
  Date: 1/30/2019
  Time: 10:03 AM
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
<form action="/file" method="post" enctype="multipart/form-data">
    <div class="col-5">
        <div class="form-group">
            <div class="input-group input-file" name="fileUpload">
			<span class="input-group-btn">
        		<button class="btn btn-danger btn-choose" type="button">Choose</button>
    		</span>
                <input type="text" class="form-control" placeholder='Choose a file...' />
                <span class="input-group-btn">
       			 <button class="btn btn-warning btn-reset" type="button">Reset</button>
    		</span>
            </div>
        </div>
        <%--<div class="input-default-wrapper mt-3">
            <span class="input-group-text mb-3" id="input1">Upload</span>
            <input type="file" id="file-with-current" class="input-default-js" name="fileUpload">
            <label class="label-for-default-js rounded-right mb-3" for="file-with-current">
                <span class="span-choose-file">Choose a file</span>
                <div class="float-right span-browse">Browse</div>
            </label>
        </div>--%>
    </div>
    <div class="col-5">
        <label for="select-language">Select Language</label>
        <select class="custom-select" id="select-language" name="language">
            <option value="eng">English</option>
            <option value="deu">German</option>
            <option value="rus">Russian</option>
        </select>
    </div>
    <br>
    <button style="margin-left: 20px" type="submit" class="btn btn-primary btn-lg">Submit</button>
</form>



<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
        integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
        integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
<script type="text/javascript">
    function bs_input_file() {
        $(".input-file").before(
            function() {
                if ( ! $(this).prev().hasClass('input-ghost') ) {
                    var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0'>");
                    element.attr("name",$(this).attr("name"));
                    element.change(function(){
                        element.next(element).find('input').val((element.val()).split('\\').pop());
                    });
                    $(this).find("button.btn-choose").click(function(){
                        element.click();
                    });
                    $(this).find("button.btn-reset").click(function(){
                        element.val(null);
                        $(this).parents(".input-file").find('input').val('');
                    });
                    $(this).find('input').css("cursor","pointer");
                    $(this).find('input').mousedown(function() {
                        $(this).parents('.input-file').prev().click();
                        return false;
                    });
                    return element;
                }
            }
        );
    }

    $(function() {
        bs_input_file();
    });
</script>
</body>
</html>
