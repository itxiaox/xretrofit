# Retrofit封装库

[![](https://jitpack.io/v/itxiaox/xretrofit.svg)](https://jitpack.io/#itxiaox/xretrofit)

 对retrofit 进行封装 ，在项目更加简单易用  ,可在实际项目中正式使用

# 使用方法
***gradle 引用***

在根gradle中添加
    
```
    allprojects {
        repositories {
            jcenter()
          maven { url 'https://jitpack.io' }
        }
    }
   ```
   
在module 中添加依赖
```
  implementation 'com.github.itxiaox:xretrofit:1.0.3'
  ```

***代码中使***

 - 一般情况下可以采用默认的调用方式
    
```
// 初始一次，一般可以采用在Application的onCreate中进行调用
HttpManager.init(baseUrl,true)
```

- 具体发送请求的
 
```
  wxapiService = HttpManager.create(WXAPIService.class);
  wxapiService.getWXArticle().enqueue(...)
```
-  但如果需要自己去配置相关请求设置，如重新设置timeout, interceptor，convertFactory, adapterFactory等，可以采用如下方式

```
 HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog());
 logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
 HttpConfig httpConfig = new HttpConfig.Builder()
        .baseUrl(baseUrl).addInterceptor(logInterceptor)
        .addConverterFactory(GsonConverterFactory.create())
         .build();
 HttpManager.init(httpConfig);
```



 # LICENSE

    Copyright 2018 Xiaox

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
