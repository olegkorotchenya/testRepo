package test.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import test.entity.FileInfo;

import java.util.Date;
import java.util.List;

public interface FileInfoRepository extends CrudRepository<FileInfo, Integer> {

    @Query("select a from FileInfo a where a.startDate >= :startDate and a.endDate >= :endDate")
    List<FileInfo> findByDates(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}
