package com.company.server.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 描述: 读取配置文件
 *
 * @author lijinhong
 * @date 20.9.24
 */
public class ConfigUtil {
    private static Properties properties = null;
    static {
        try {
            properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = new FileInputStream("conf/codeAntMQC.properties");
            // 使用properties对象加载输入流
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("配置文件加载失败");
        }
    }

    public static String getValue(String key){
        //获取key对应的value值
        return properties.getProperty(key);
    }
}
