package com.inv.portfolio.util;

import com.inv.portfolio.exception.NaviPortSysException;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 讀取資源目錄下 SQL 檔案的工具類別。
 */
public class SqlReader {

    private SqlReader() {
    }

    /**
     * 從 classpath 讀取指定的 SQL 檔案。
     * 
     * @param sqlPath 統一管理的 SQL 檔案路徑列舉
     * @return 檔案內容字串
     */
    public static String read(SqlPath sqlPath) {
        String path = sqlPath.getPath();
        try {
            return new String(new ClassPathResource(path).getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new NaviPortSysException("Failed to read SQL file: " + path, e);
        }
    }
}
