package com.miladalemansour.api.scheduler;

import com.miladalemansour.api.service.ReportService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ReportScheduler {

    private ReportService reportService;

    public ReportScheduler(ReportService reportService) {
        this.reportService = reportService;
    }

    //Send Report every minute
    @Scheduled(cron = "0 * * * * *")
    public void sendUniqueCountToStream(){
        reportService.sendUniqueCountToStream();
    }
}
