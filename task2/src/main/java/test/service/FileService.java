package test.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public Resource cleanDocx(MultipartFile file) throws IOException;
}
