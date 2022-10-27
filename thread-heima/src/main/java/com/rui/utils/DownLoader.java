package com.rui.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.URL;
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

    public static List<String> download() {
        HttpURLConnection connection = (HttpURLConnection) new URL("https://www.baidu.com").openConnection();
        List<String> lines = new ArrayList<>();

    }

}
