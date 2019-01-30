package com.goloveyko.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 * <p>
 * This utility class uses java.net.HttpURLConnection class and follows the RFC 1867 (Form-based File Upload in HTML) to make an HTTP POST request with multipart/form-data content type in order to upload files to a given URL. It has one constructor and three methods:
 * MultipartUtility(String requestURL, String charset): creates a new instance of this class for a given request URL and charset.
 * void addFormField(String name, String value): adds a regular text field to the request.
 * void addHeaderField(String name, String value): adds an HTTP header field to the request.
 * void addFilePart(String fieldName, File uploadFile): attach a file to be uploaded to the request.
 * List<String> finish(): this method must be invoked lastly to complete the request and receive response from server as a list of String.
 *
 * @author www.codejava.net
 */
public class MultipartUtility {
	private static final Logger logger = LoggerFactory.getLogger(MultipartUtility.class);

	private final String boundary = UUID.randomUUID().toString();
	private static final String LINE_FEED = "\r\n";
	private HttpURLConnection httpConn;
	private String charset;
	private OutputStream outputStream;
	private PrintWriter writer;

	/**
	 * This constructor initializes a new HTTP POST request with content type
	 * is set to multipart/form-data
	 *
	 * @param requestURL
	 * @param charset
	 * @throws IOException
	 */
	public MultipartUtility(String requestURL, String charset) {
		this.charset = charset;

		try {
			URL url = new URL(requestURL);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setUseCaches(false);
			httpConn.setDoOutput(true); // indicates POST method
			httpConn.setDoInput(true);
			httpConn.setRequestProperty("Content-Type", "multipart/related; boundary=\"" + boundary + "\"");
			httpConn.setRequestProperty("Accept-Encoding", "gzip");
			outputStream = httpConn.getOutputStream();
			//outputStream = System.out;
			writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
		} catch (IOException ex) {
			logger.error("Error during creation of MultiPart: ", ex);
		}
	}

	/**
	 * Adds a form field to the request
	 */
	public void addFormField(String jsonBody) {
		writer.append("--").append(boundary).append(LINE_FEED);
		writer.append("Content-Type: application/json;").append(LINE_FEED);
		writer.append(LINE_FEED);
		writer.append(jsonBody).append(LINE_FEED);
		writer.flush();
	}

	/**
	 * Adds a upload file section to the request
	 *
	 * @param uploadFile a File to be uploaded
	 * @throws IOException
	 */
	public void addFilePart(File uploadFile) {
		String fileName = uploadFile.getName();
		writer.append("--").append(boundary).append(LINE_FEED);
		writer.append("Content-Disposition: attachment;");
        writer.append(" filename=\"" + fileName + "\".").append(LINE_FEED);
        writer.append("Content-Type: image/*").append(LINE_FEED);
		writer.append(LINE_FEED);
		writer.flush();

		try {
			FileInputStream inputStream = new FileInputStream(uploadFile);
			//byte[] buffer = new byte[(int) uploadFile.length()];
			byte[] buffer = Files.readAllBytes(Paths.get(uploadFile.getPath()));
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			inputStream.close();
		} catch (IOException ex) {
			logger.error("File transformation to bytes went wrong: {}", ex);
		}

		writer.append(LINE_FEED);
		writer.flush();
	}

	/**
	 * Adds a header field to the request.
	 *
	 * @param name  - name of the header field
	 * @param value - value of the header field
	 */
	public void addHeaderField(String name, String value) {
		writer.append(name + ": " + value).append(LINE_FEED);
		writer.flush();
	}

	/**
	 * Completes the request and receives response from the server.
	 *
	 * @return a list of Strings as response in case the server returned
	 * status OK, otherwise an exception is thrown.
	 * @throws IOException
	 */
	public String finish() {
		String response = "";
		int status = 0;

		writer.flush();
		writer.append("--").append(boundary).append("--").append(LINE_FEED);
		writer.println();
		writer.close();

		try {
			// checks server's status code first
			status = httpConn.getResponseCode();

			if (status == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					response += line;
				}

				reader.close();
				httpConn.disconnect();
			} else {
				logger.error("OCR API returned error stream: {}", printErrorStream());
				throw new IOException("Server returned non-OK status: " + status + " : " + httpConn.getResponseMessage());
				//logger.error("Server returned non-OK status: " + status + " : " + httpConn.getResponseMessage());
			}
		} catch(IOException ex) {
			logger.error("Response message in Multipart finish has been received with problems: ", ex);
		}

		return response;
	}

	private String printErrorStream() throws IOException {
		//System.out.print("DEBUG System out ocr API error stream: ");
		InputStream errorStream = httpConn.getErrorStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
		String errLine = "", tempLine;
		while ((tempLine = reader.readLine()) != null) {
			errLine += tempLine;
		}

		return errLine;
	}

	private void getRequestHeaders(HttpURLConnection httpURLConnection) {
		for (Map.Entry<String, List<String>> entries : httpURLConnection.getRequestProperties().entrySet()) {
			String values = "";
			for (String value : entries.getValue()) {
				values += value + ",";
			}
			System.out.println("Request" + " " + entries.getKey() + " - " +  values );
		}
	}
}
