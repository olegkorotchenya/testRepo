package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.entity.FileInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuditService {

    private final FileInfoRepository FileInfoRepository;

    @Autowired
    public AuditService(FileInfoRepository FileInfoRepository) {
        this.FileInfoRepository = FileInfoRepository;
    }

    public List<FileInfo> getFileInfosByDate(Date fromDate, Date toDate) {
        if(fromDate != null && toDate != null)
            return  FileInfoRepository.findByDates(fromDate, toDate);
        else
            return getAllFileInfos();
    }

    public List<FileInfo> getAllFileInfos() {
        List<FileInfo> FileInfos = new ArrayList<FileInfo>();
        FileInfoRepository.findAll().forEach(FileInfos::add);
        return FileInfos;
    }


    public FileInfo getFileInfoById(int id) {
        return FileInfoRepository.findById(id).get();
    }

    public void saveOrUpdate(FileInfo FileInfo) {
        FileInfoRepository.save(FileInfo);
    }

}
