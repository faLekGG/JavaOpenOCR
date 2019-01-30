package com.goloveyko.service;

import com.goloveyko.constants.Constants;
import com.goloveyko.utility.MultipartUtility;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class OpenOcrServiceImpl implements OpenOcrService {
	private static final Logger logger = LoggerFactory.getLogger(OpenOcrServiceImpl.class);
	private String responseText = "";

	public String processImageFromUrl(String uri, String language){
		logger.trace("BlueScanEngine:translateFileToText()");
		logger.info("Send request file to" + Constants.URI_JSON);

		String json = "{\"" + Constants.IMAGE_URL + "\":\"" + uri + "\",\"" + Constants.ENGINE  + "\":\"" + Constants.TYPE_OF_ENGINE
						+ "\",\"" + Constants.LANGUAGE + "\":\"" + language + "\"}";

		int status = 0;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(Constants.URI_JSON);
		HttpEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
		httpPost.setEntity(stringEntity);

		try {
			CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
			status = httpResponse.getStatusLine().getStatusCode();
			logger.info("Returned OK status: " + status);
			responseText = EntityUtils.toString(httpResponse.getEntity());
		} catch (IOException e) {
			logger.error("Returned NON-OK status: " + status);
		}
		return responseText;
	}

	public String translateFileToText(String fileTextPath, String language) {
		logger.trace("BlueScanEngine:translateFileToText()");
		logger.info("Send request file to" + Constants.URI_FILE);

		long lStartTime = System.nanoTime();

		String json = "{\"" + Constants.IMAGE_URL + "\":\"\",\"" + Constants.ENGINE + "\":0,\"" +
						Constants.ENGINE_ARGS + "\":{\"" + Constants.LANGUAGE + "\":\"" + language + "\"},\"" +
						Constants.INPLACE_DECODE + "\":" + Constants.INPLACE_DECODE_VALUE + "}";

		System.out.println(json);
		File file = new File(fileTextPath);
		System.out.println(fileTextPath);

		try {
			MultipartUtility multipart = new MultipartUtility(Constants.URI_FILE, "UTF-8");

			multipart.addFormField(json);
			multipart.addFilePart(file);

			responseText = multipart.finish();
		} catch (Exception ex) {
			logger.error("Exception while executing request", ex);
		}

		long lEndTime = System.nanoTime();
		long elapsedTime = (lEndTime - lStartTime) / 1000000000;

		logger.info("Request " + new File(fileTextPath).getName() + " returned in " + elapsedTime + " seconds");

		return responseText;
	}
}
