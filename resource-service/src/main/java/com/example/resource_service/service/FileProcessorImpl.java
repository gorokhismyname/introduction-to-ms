package com.example.resource_service.service;

import com.example.resource_service.exception.MultipartFileProcessingException;
import jakarta.validation.ValidationException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileProcessorImpl implements FileProcessor {

    public static final String ACCEPTED_CONTENT_TYPE = "audio/mpeg";

    private static final Tika tika = new Tika();

    @Override
    public void validateFile(MultipartFile file) {
        try {
            String detectedType = tika.detect(file.getInputStream());
            if (!ACCEPTED_CONTENT_TYPE.equals(detectedType)) {
                throw new ValidationException();
            }
        } catch (IOException e) {
            throw new MultipartFileProcessingException();
        }
    }

    @Override
    public Map<String, String> extractMetadata(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();

            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();

            ParseContext context = new ParseContext();

            parser.parse(inputStream, handler, metadata, context);

            String[] metadataNames = metadata.names();
            Map<String, String> fileMetadata = new HashMap<>();

            for (String name : metadataNames) {
                fileMetadata.put(name, metadata.get(name));
            }

            return fileMetadata;

        } catch (IOException | TikaException | SAXException e) {
            throw new RuntimeException(e);
        }

    }
}
