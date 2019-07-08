package test.service;

import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import test.entity.FileInfo;
import test.rs.FileController;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;


@Service
public class FileServiceImpl implements FileService{
    private static final Logger log = LoggerFactory.getLogger(FileController.class.getName());

    @Autowired
    AuditService auditService;

    public Resource cleanDocx(MultipartFile file) throws IOException {

        FileInfo fileInfo = new FileInfo();
        fileInfo.setStartDate(new Date());

        log.info("start process file " + file.getName());
        XWPFDocument document = new XWPFDocument(file.getInputStream());
        for(XWPFParagraph p: document.getXWPFDocument().getParagraphs()){

            p.setAlignment(ParagraphAlignment.LEFT);
            for(XWPFRun run : p.getRuns()){
                run.setFontFamily(null);
                run.setBold(false);
                run.setItalic(false);
                run.setColor(null);
                run.setUnderline(UnderlinePatterns.NONE);

                run.setFontSize(11);
            }


        }

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        document.write(b); // doc should be a XWPFDocument
        InputStream inputStream = new ByteArrayInputStream(b.toByteArray());

        Resource result = new InputStreamResource(inputStream);

        long end = System.currentTimeMillis();

        fileInfo.setEndDate(new Date());
        fileInfo.setName(file.getOriginalFilename());
        fileInfo.setSize(file.getSize());
        auditService.saveOrUpdate(fileInfo);


        return result;
    }
}
