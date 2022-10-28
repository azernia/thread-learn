package com.rui.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 下载器
 * <hr/>
 * date: 2022/10/26
 *
 * @author rui
 */
@Slf4j(topic = "rui.DownLoader")
public class DownLoader {
    private DownLoader() {}

    public static List<String> download() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("https://www.baidu.com").openConnection();
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

}
