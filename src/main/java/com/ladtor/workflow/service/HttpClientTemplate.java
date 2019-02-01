/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2015 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package com.ladtor.workflow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.exception.HttpClientException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HttpClientTemplate {

    public static final String STATUS_CODE = "statusCode";
    @Autowired
    private HttpClient httpClient;

    public JSONObject execute(String method, String url, JSONObject params) {
        HttpUriRequest httpUriRequest = getHttpUriRequest(method, url, params);
        try {
            HttpResponse httpResponse = httpClient.execute(httpUriRequest);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            try {
                JSONObject jsonObject = JSON.parseObject(result);
                jsonObject.put(STATUS_CODE, statusCode);
                return jsonObject;
            }catch (Exception e){
                throw new HttpClientException(result);
            }
        } catch (IOException e) {
            throw new HttpClientException("IOException", e);
        }
    }

    private HttpUriRequest getHttpUriRequest(String method, String url, JSONObject params) {
        return RequestBuilder
                .create(method)
                .setUri(url)
                .addParameters(getNameValuePair(params))
                .build();
    }

    private NameValuePair[] getNameValuePair(Map<String, Object> map) {
        List<BasicNameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            String name = e.getKey();
            String value = e.getValue().toString();
            BasicNameValuePair pair = new BasicNameValuePair(name, value);
            params.add(pair);
        }
        return params.toArray(new BasicNameValuePair[params.size()]);
    }
}