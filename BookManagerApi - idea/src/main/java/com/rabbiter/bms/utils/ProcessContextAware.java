package com.rabbiter.bms.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.lang.NonNull;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ProcessContextAware implements ServletContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ProcessContextAware.class);
    @Value("${server.port}")
    private String port;

    @Override
    public void setServletContext(@NonNull ServletContext servletContext) {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Windows系统关闭占用指定端口的逻辑
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "netstat -ano | findstr " + port);
                Process process = processBuilder.start();
                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.trim().split("\\s+");
                    String pid = tokens[tokens.length - 1];
                    ProcessBuilder killProcess = new ProcessBuilder("cmd.exe", "/c", "taskkill /F /PID " + pid);
                    killProcess.start();
                }
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                // Linux或Mac OS系统关闭占用指定端口的逻辑
                ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "lsof -ti:" + port + " | xargs kill -9");
                processBuilder.start();
            }
        } catch (IOException e) {
            logger.error("关闭端口{}的进程时发生错误", port, e);
        }

    }
}