
package com.itheima.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.qianfan")
@Data
public class QianfanProperties {
    private String accessKey;
    private String secretKey;
}

