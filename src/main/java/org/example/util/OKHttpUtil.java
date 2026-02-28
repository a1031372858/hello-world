package org.example.util;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * OKHttp工具类，用于发送HTTP请求
 * 支持GET、POST（JSON、Form、文件）请求，并可设置请求头
 * @author xuyachang
 * @date 2024/2/26
 */
public class OKHttpUtil {

    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .retryOnConnectionFailure(false)
            .connectionPool(new ConnectionPool(200, 5, TimeUnit.MINUTES))
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build();

    private OKHttpUtil(){}

    public static OkHttpClient getClient(){
        return client;
    }

    /**
     * 构建请求头
     * @param header 请求头参数
     * @return 构建后的Headers对象
     */
    private static Headers buildHeaders(Map<String, String> header) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (Objects.nonNull(header)) {
            header.forEach(headerBuilder::add);
        }
        return headerBuilder.build();
    }

    /**
     * 检查URL是否合法
     * @param url URL字符串
     * @return 解析后的HttpUrl对象
     * @throws IllegalArgumentException 如果URL不合法
     */
    private static HttpUrl checkUrl(String url) {
        HttpUrl builderUrl = HttpUrl.parse(url);
        if (Objects.isNull(builderUrl)) {
            throw new IllegalArgumentException("url不合法");
        }
        return builderUrl;
    }

    /**
     * get请求，指定url，入参
     * @param url 请求URL
     * @param param 请求参数
     * @return 响应结果
     */
    public static String get(String url,Map<String,String> param) {
        return get(url,param,null);
    }

    /**
     * get请求，指定url，入参，请求头
     * @param url 请求URL
     * @param param 请求参数
     * @param header 请求头
     * @return 响应结果
     */
    public static String get(String url,Map<String,String> param,Map<String,String> header) {

        HttpUrl builderUrl = checkUrl(url);
        Headers headers = buildHeaders(header);

        HttpUrl.Builder urlBuilder = builderUrl.newBuilder();
        if(Objects.nonNull(param)){
            param.forEach(urlBuilder::addQueryParameter);
        }
        HttpUrl httpUrl = urlBuilder.build();
        //构建请求
        Request request = new Request.Builder()
                .get()
                .headers(headers)
                .url(httpUrl)
                .build();

        //执行请求
        return executeRequest(request);
    }

    /**
     * post请求，指定url，json入参
     * @param url 请求URL
     * @param json JSON格式的请求体
     * @return 响应结果
     * @throws IllegalArgumentException 如果URL或JSON为空
     */
    public static String postJson(String url,String json) {
        return postJson(url, json, null);
    }

    /**
     * post请求，指定url，json入参，请求头
     * @param url 请求URL
     * @param json JSON格式的请求体
     * @param header 请求头
     * @return 响应结果
     * @throws IllegalArgumentException 如果URL或JSON为空
     */
    public static String postJson(String url,String json,Map<String,String> header) {
        if (Objects.isNull(json) || json.isEmpty()) {
            throw new IllegalArgumentException("json参数不能为空");
        }
        checkUrl(url);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        Headers headers = buildHeaders(header);

        Request request = new Request.Builder()
                .post(body)
                .headers(headers)
                .url(url)
                .build();

        return executeRequest(request);
    }

    /**
     * post请求，指定url，form入参
     * @param url 请求URL
     * @param param Form表单参数
     * @return 响应结果
     * @throws IllegalArgumentException 如果URL或参数为空
     */
    public static String postFrom(String url,Map<String,String> param) {
        return postFrom(url, param, null);
    }

    /**
     * post请求，指定url，form入参，请求头
     * @param url 请求URL
     * @param param Form表单参数
     * @param header 请求头
     * @return 响应结果
     * @throws IllegalArgumentException 如果URL或参数为空
     */
    public static String postFrom(String url,Map<String,String> param,Map<String,String> header) {
        if (Objects.isNull(param)) {
            throw new IllegalArgumentException("param参数不能为空");
        }
        checkUrl(url);
        FormBody.Builder formBuilder = new FormBody.Builder();
        param.forEach(formBuilder::add);
        RequestBody formBody = formBuilder.build();
        Headers headers = buildHeaders(header);

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(formBody)
                .build();

        //执行请求
        return executeRequest(request);
    }

    /**
     * post请求，指定url，文件入参
     * @param url 请求URL
     * @param fileUrl 文件路径
     * @return 响应结果
     * @throws IllegalArgumentException 如果URL或文件路径为空
     */
    public static String postFile(String url,String fileUrl) {
        return postFile(url, fileUrl, null);
    }

    /**
     * post请求，指定url，文件入参，请求头
     * @param url 请求URL
     * @param fileUrl 文件路径
     * @param header 请求头
     * @return 响应结果
     * @throws IllegalArgumentException 如果URL或文件路径为空
     */
    public static String postFile(String url,String fileUrl,Map<String,String> header) {
        if (Objects.isNull(fileUrl) || fileUrl.isEmpty()) {
            throw new IllegalArgumentException("fileUrl参数不能为空");
        }
        checkUrl(url);
        File file = new File(fileUrl);
        if (!file.exists() || !file.isFile()) {
            System.err.println("文件不存在或不是普通文件: " + fileUrl);
            return "文件不存在或不是普通文件: " + fileUrl;
        }
        if (!file.canRead()) {
            System.err.println("文件不可读: " + fileUrl);
            return "文件不可读: " + fileUrl;
        }
        Headers headers = buildHeaders(header);

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), file))
                .build();

        return executeRequest(request);
    }

    private static String executeRequest(Request request) {
        //执行请求
        try (Response response = client.newCall(request).execute()){
            //返回响应
            return handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
            return "请求失败: " + e.getMessage();
        }
    }

    private static String handleResponse(Response response) {
        try {
            if(response.isSuccessful()){
                return response.body().string();
            }else{
                return "请求失败，状态码: " + response.code() + ", 消息: " + response.message();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "处理响应失败: " + e.getMessage();
        }
    }
}
