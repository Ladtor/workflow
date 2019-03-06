package com.ladtor.workflow.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.core.exception.HttpClientException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String STATUS_CODE = "statusCode";
    public static final String UTF_8 = "UTF-8";
    @Autowired
    private HttpClient httpClient;

    public JSONObject execute(String method, String url) {
        return execute(method, url, null);
    }

    public JSONObject execute(String method, String url, JSONObject params) {
        HttpUriRequest httpUriRequest = getHttpUriRequest(method, url, params);
        try {
            HttpResponse httpResponse = httpClient.execute(httpUriRequest);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            try {
                JSONObject jsonObject;
                if (result != null) {
                    jsonObject = JSON.parseObject(result);
                }else {
                    jsonObject = new JSONObject();
                }
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
        url = url.replaceAll(" ", "%20");
        RequestBuilder requestBuilder = RequestBuilder
                .create(method)
                .setUri(url)
                .addHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
        if (GET.equals(method)) {
            requestBuilder.addParameters(getNameValuePair(params));
        } else if (POST.equals(method)) {
            requestBuilder.setEntity(new StringEntity(params.toJSONString(), UTF_8));
        }
        return requestBuilder.build();
    }

    private NameValuePair[] getNameValuePair(Map<String, Object> map) {
        if (map == null) {
            return new NameValuePair[0];
        }
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