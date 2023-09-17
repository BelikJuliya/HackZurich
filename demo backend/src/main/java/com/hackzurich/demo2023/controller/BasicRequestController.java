package com.hackzurich.demo2023.controller;

import com.hackzurich.demo2023.dto.TextRequestDTO;
import com.hackzurich.demo2023.dto.TextResponseDTO;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BasicRequestController {
    final CloseableHttpClient httpClient = HttpClients.createDefault();

    @PostMapping("request")
    public ResponseEntity<TextResponseDTO> SimpleRequest(@RequestBody TextRequestDTO body) throws Exception {



            try {
                System.out.println("Testing 1 - Send Http GET request");
                sendGet();

                System.out.println("Testing 2 - Send Http POST request");
                sendPost();
            } finally {
                close();

        }



        return new ResponseEntity<TextResponseDTO>(TextResponseDTO.builder().answer("something wrong").build(), HttpStatusCode.valueOf(200));
    }

    private void close() throws IOException {
        httpClient.close();
    }

    private void sendGet() throws Exception {

        HttpGet request = new HttpGet("api gateway address");



        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        }

    }

    private void sendPost() throws Exception {

        HttpPost post = new HttpPost("api gateway address");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("custom", "secret"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }

}

