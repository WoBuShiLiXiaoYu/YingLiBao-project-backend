package com.ylb.work.micrtask;

import com.node.api.service.IncomeRecordService;
import com.ylb.work.common.utils.HttpClientUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.http.impl.client.HttpClients;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("taskManager")
public class TaskManager {

    @DubboReference(interfaceClass = IncomeRecordService.class, version = "1.0")
    private IncomeRecordService recordService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void invokeGenerateIncomePlan() {
        recordService.generateIncomePlan();
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void invokeGenerateIncomeBack() {
        recordService.generateIncomeBack();
    }
    @Scheduled(cron = "0 0/20 * * * ?")
    public void invokeKuaiQianQuery() {
        String url = "http://localhost:9000/pay/kq/rece/notify";
        try {
            HttpClientUtils.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
