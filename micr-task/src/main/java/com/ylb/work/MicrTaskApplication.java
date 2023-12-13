package com.ylb.work;

import com.ylb.work.micrtask.TaskManager;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDubbo
// 启用定时任务
@EnableScheduling
@SpringBootApplication
public class MicrTaskApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MicrTaskApplication.class, args);
        TaskManager taskManager = (TaskManager) applicationContext.getBean("taskManager");
        //taskManager.invokeGenerateIncomePlan();
        taskManager.invokeGenerateIncomeBack();

    }

}
