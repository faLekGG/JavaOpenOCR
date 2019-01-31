# JavaOpenOCR
A small lightweight Java client library for the <a href="https://github.com/tleyden/open-ocr">open-ocr project</a>.

# Prerequisites
You need is a running instance of <a href="https://github.com/tleyden/open-ocr">open-ocr</a>.
Also to run an application you need Tomcat Server and War archive of the application

# Usage
Firstly you need configure the host url for remote and local image processing accordingly. Beside that, for uploading images from local machine you must set directory where you are going to save them.

<b>Required</b>
```java
public static final String URI_JSON = "YOUR_URL_FOR_REMOTE_UPLOAD";
public static final String URI_FILE = "YOUR_URL_FOR_FILE_UPLOAD";
public static final String PATH_TO_FILE = "YOUR_PATH_TO_FILE"
```
<b>Analyze image from Url</b>
<b>Analyze image from PC</b>
