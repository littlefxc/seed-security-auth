package com.fengxuechao.seed.security.web.timeout;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author fengxuechao
 * @date 2019-08-01
 */
public class RestTemplateExceptionTest {

    private String url = "http://localhost:8081";

    @Test
    public void timeout() {
        long start = System.currentTimeMillis();
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map responseObject = restTemplate.getForObject(url, Map.class);
            System.out.println(responseObject);
        } catch (Exception e) {
            Assert.assertNotNull(e);
            e.printStackTrace();
            System.out.println("timeout = " + (System.currentTimeMillis() - start));
        }
    }

    @Test
    public void simple() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(1000);
        clientHttpRequestFactory.setReadTimeout(50);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        long start = System.currentTimeMillis();
        try {
            Map responseObject = restTemplate.getForObject(url, Map.class);
            System.out.println(responseObject);
        } catch (Exception e) {
            Assert.assertNotNull(e);
            e.printStackTrace();
            System.out.println("timeout = " + (System.currentTimeMillis() - start));
        }
    }

    @Test
    public void comonents() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(1000);
        clientHttpRequestFactory.setReadTimeout(50);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        long start = System.currentTimeMillis();
        try {
            Map responseObject = restTemplate.getForObject(url, Map.class);
            System.out.println(responseObject);
        } catch (Exception e) {
            Assert.assertNotNull(e);
            e.printStackTrace();
            System.out.println("timeout = " + (System.currentTimeMillis() - start));
            throw new RuntimeException(e);
        }
    }
}
