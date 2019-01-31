# JavaOpenOCR
A small lightweight Java client library for the <a href="https://github.com/tleyden/open-ocr">open-ocr project</a>.

# Prerequisites
You need is a running instance of <a href="https://github.com/tleyden/open-ocr">open-ocr</a>.
Also to run an application you need Tomcat Server and War archive of the application

# Usage
Firstly you need to configure the host url for remote and local image processing accordingly. Beside that, for uploading images from local machine you must set directory where you are going to save them.

<b>Required</b>
```java
public static final String URI_JSON = "YOUR_URL_FOR_REMOTE_UPLOAD";
public static final String URI_FILE = "YOUR_URL_FOR_FILE_UPLOAD";
public static final String PATH_TO_FILE = "YOUR_PATH_TO_FILE"
```
## Analyze image from Url
![remoteupload](https://user-images.githubusercontent.com/23281318/52038611-d8180480-2543-11e9-9298-3a8916261ad1.jpg)
## Analyze image from PC
![localupload](https://user-images.githubusercontent.com/23281318/52038712-fed63b00-2543-11e9-8297-07ba3daae998.jpg)
## Eventually we'll get appropriate result
![result](https://user-images.githubusercontent.com/23281318/52038855-2a592580-2544-11e9-9a08-9945873195a1.jpg)
