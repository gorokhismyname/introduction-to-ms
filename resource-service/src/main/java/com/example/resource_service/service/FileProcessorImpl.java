package com.example.resource_service.service;

import com.example.resource_service.exception.FileProcessingException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileProcessorImpl implements FileProcessor {

    public static final String ACCEPTED_CONTENT_TYPE = "audio/mpeg";

    private static final Tika tika = new Tika();

    @Override
    public void validateDatatype(byte[] file) {
        String detectedType = tika.detect(file);
        if (!ACCEPTED_CONTENT_TYPE.equals(detectedType)) {
            throw new FileProcessingException("Invalid data type: " + detectedType);
        }
    }

    @Override
    public Metadata extractMetadata(byte[] file) {
        try {
            InputStream inputStream = new ByteArrayInputStream(file);

            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();

            ParseContext context = new ParseContext();

            parser.parse(inputStream, handler, metadata, context);

            return metadata;

        } catch (IOException | TikaException | SAXException e) {
            throw new RuntimeException(e);
        }

    }
}
