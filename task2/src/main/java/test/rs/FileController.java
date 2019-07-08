package test.rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import test.entity.FileInfo;
import test.service.AuditService;
import test.service.FileService;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@RestController
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class.getName());

    private final FileService fileService;
    private final AuditService auditService;

    @Autowired
    public FileController(FileService fileService, AuditService auditService) {
        this.fileService = fileService;
        this.auditService = auditService;
    }

    @RequestMapping("/api")
    public String test() {
        log.error("ping test");
        return "Test endpoint!";
    }

    @RequestMapping("/api/docx/post")
    public ResponseEntity<Resource> cleanFile(@RequestParam("file") MultipartFile file) {

        log.info("Start process fileName: " + file.getName());

        Resource resource = null;

        try {
            resource = fileService.cleanDocx(file);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=update_" + file.getOriginalFilename() + ".docx")
                    .body(resource);

        } catch (IOException e) {
            throw new RuntimeException("Failed to process");
        }
    }

    @RequestMapping("/api/docx/audit")
    public List<FileInfo> getAudit(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        log.info("From date " + fromDate + " toDate " + toDate);

        return auditService.getFileInfosByDate(fromDate, toDate);
    }

}
