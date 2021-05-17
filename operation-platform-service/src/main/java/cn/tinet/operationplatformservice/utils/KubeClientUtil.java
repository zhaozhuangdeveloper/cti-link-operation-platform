package cn.tinet.operationplatformservice.utils;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Time : 2021/3/2 23:58
 * @Author : zhaozhuang
 * @Email : zhaozhuang@ti-net.com.cn
 * @File : ApiClientUtil.java
 * @Software: IntelliJ IDEA
 **/
@Configuration
public class KubeClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(KubeClientUtil.class);

    @Value(value = "${kubeconfig}")
    private Resource kubeconfig = null;

    @Bean(name = "kubeClient")
    public KubernetesClient getClient(){
        KubernetesClient client = null;
        try {
            InputStream is = kubeconfig.getInputStream();
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            Config config = Config.fromKubeconfig(new String(bytes));
            client = new DefaultKubernetesClient(config);
        } catch (IOException e) {
            logger.error("IOException: {}", e.getMessage());
        }
        return client;
    }

}
