package com.goloveyko.service;

public interface OpenOcrService {
    String processImageFromUrl(String URI, String JSON);
    String translateFileToText(String fileTextPath, String language);
}
