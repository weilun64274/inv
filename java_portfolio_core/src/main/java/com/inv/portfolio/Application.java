package com.inv.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系統的主應用程式進入點。
 * 負責啟動 Spring Boot 應用程式並提供基礎的健康檢查 API。
 */
@SpringBootApplication
@RestController
public class Application {

    /**
     * Spring Boot 應用程式主程式啟動方法。
     *
     * @param args 給應用程式的命令列參數
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 基礎的首頁 API，用於確認伺服器已正常啟動並能處理請求。
     *
     * @return 簡單的歡迎字串
     */
    @GetMapping("/")
    public String home() {
        return "Welcome to Java Portfolio Core API!";
    }

    /**
     * 系統健康狀態 API，通常用於 Load Balancer (LB) 或 K8s Liveness Probes 來進行伺服器存活檢查。
     *
     * @return 正常回應 "ok"
     */
    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
