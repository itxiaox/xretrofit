package com.itxiaox.retrofit;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截器
 *
 * 向请求头里添加公共参数
 */
public class HeadersInterceptor implements Interceptor {
    private Map<String,String> mHeaderParamsMap = new HashMap<>();
    public HeadersInterceptor() {
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        // 添加新的参数，添加到url 中
        /*HttpUrl.Builder authorizedUrlBuilder = oldRequest.url().newBuilder()
        .scheme(oldRequest.url().scheme())
        .host(oldRequest.url().host());*/

        // 新的请求
        Request.Builder requestBuilder =  oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(), oldRequest.body());

        //添加公共参数,添加到header中
        if(mHeaderParamsMap.size() > 0){
            for(Map.Entry<String,String> params:mHeaderParamsMap.entrySet()){
                requestBuilder.header(params.getKey(),params.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }

    public static class Builder{
        HeadersInterceptor mHttpCommonInterceptor;
        public Builder(){
            mHttpCommonInterceptor = new HeadersInterceptor();
        }

        public Builder addHeaderParam(String key, String value){
            mHttpCommonInterceptor.mHeaderParamsMap.put(key,value);
            return this;
        }

        public Builder  addHeaderParam(String key, int value){
            return addHeaderParam(key, String.valueOf(value));
        }

        public Builder  addHeaderParam(String key, float value){
            return addHeaderParam(key, String.valueOf(value));
        }

        public Builder  addHeaderParam(String key, long value){
            return addHeaderParam(key, String.valueOf(value));
        }

        public Builder  addHeaderParam(String key, double value){
            return addHeaderParam(key, String.valueOf(value));
        }

        public HeadersInterceptor build(){
            return mHttpCommonInterceptor;
        }

    }
}