package ar.com.unlu.sdypp.integrador.file.manager.services;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import ar.com.unlu.sdypp.integrador.file.manager.cruds.TimeLogs;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileDownloadLog;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileLogsModel;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.TimeLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TimeLogService {

    public static final String DOWNLOAD_FILE = "DescargaArchivo";
    public static final String DOWNLOAD_PART = "DescargaParte";

    private final TimeLogsRepository timeLogsRepository;

    @Autowired
    public TimeLogService(TimeLogsRepository timeLogsRepository) {
        this.timeLogsRepository = timeLogsRepository;
    }

    public TimeLogs startFileDownload(FileCrud fileCrud) {
        TimeLogs timeLogs = new TimeLogs();
        timeLogs.setSessionId(UUID.randomUUID().toString());
        timeLogs.setFile(fileCrud.getNombreArchivo());
        timeLogs.setInitTime(new Date());
        timeLogs.setProcessType(TimeLogService.DOWNLOAD_FILE);
        return timeLogs;
    }

    public TimeLogs finishFileDownload(TimeLogs timeLogs) {
        timeLogs.setFinishTime(new Date());
        timeLogs.setProcessTime(timeLogs.getFinishTime().getTime() - timeLogs.getInitTime().getTime());
        return this.timeLogsRepository.save(timeLogs);
    }

    public void logPartDownload(TimeLogs fileLog, Date initTime, Date finishTime, String partName) {
        TimeLogs partLog = new TimeLogs();
        partLog.setSessionId(fileLog.getSessionId());
        partLog.setProcessType(TimeLogService.DOWNLOAD_PART);
        partLog.setInitTime(initTime);
        partLog.setFinishTime(finishTime);
        partLog.setProcessTime(finishTime.getTime() - initTime.getTime());
        partLog.setFile(partName);
        this.timeLogsRepository.save(partLog);
    }

    public FileLogsModel getFileLogs(String fileName) {
        FileLogsModel fileLogsModel = new FileLogsModel();
        var logs = this.timeLogsRepository.findByFile(fileName);
        if (logs != null) {
            for (var log : logs) {
                FileDownloadLog fileDownloadLog = new FileDownloadLog();
                fileDownloadLog.setFileLog(log);
                var partsLogs = this.timeLogsRepository.findBySessionIdAndFileNot(log.getSessionId(), fileName);
                fileDownloadLog.setPartsLogs(partsLogs);
                fileLogsModel.getFileDownloadLog().add(fileDownloadLog);
            }
        }
        return fileLogsModel;
    }

    public void deleteLogs(String name) {
        var logs = this.timeLogsRepository.findAll();
        if (logs != null) {
            this.timeLogsRepository.deleteAll(logs);
        }
    }
}
