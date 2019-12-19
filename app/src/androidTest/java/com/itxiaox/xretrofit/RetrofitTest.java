package com.itxiaox.xretrofit;


import android.util.Log;

import androidx.test.runner.AndroidJUnit4;

import com.itxiaox.retrofit.HttpConfig;
import com.itxiaox.retrofit.HttpManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(AndroidJUnit4.class)
public class RetrofitTest {

    private static final String TAG = "RetrofitTest";
    String baseUrl ;
    private WXAPIService wxapiService;

    @Before
    public void init(){
//         baseUrl = "https://itxiaox.github.io";
         baseUrl = "https://www.wanandroid.com";
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
         //默认简单调用方式
        HttpManager.init(baseUrl,true);

//        添加日志拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, "log:"+message);

//                Logger.e(message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //添加通用的请求参数，放到heaader中
//        HeadersInterceptor headersInterceptor = new HeadersInterceptor.Builder()
//                .addHeaderParam("Content-Type","")
//                .addHeaderParam("Accept","application/json")
//                .build();
       HttpConfig httpConfig = new HttpConfig.Builder()
               .baseUrl(baseUrl)
//               .addInterceptor(headersInterceptor)
               .addInterceptor(logInterceptor)
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       HttpManager.init(httpConfig);
    }

    @Test
    public void testRetrofit(){
        wxapiService = HttpManager.create(WXAPIService.class);
        wxapiService.getWXArticle().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                  String result = response.body().string();
                    Log.d(TAG, "onResponse: "+result);
                  Logger.json(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("fail:"+t);
            }
        });
    }


//    @Test
//    public void testDownload(){
//        String url = "/16891/89E1C87A75EB3E1221F2CDE47A60824A.apk?fsname=com.snda.wifilocating_4.2.62_3192.apk&csr=1bbd";
//        String path = Environment.getDataDirectory().getAbsolutePath()+File.separator+"net"+File.separator+"test.apk";
//        Log.d(TAG, "testDownload: path="+path);
//        download(url, path, new DownloadListener() {
//            @Override
//            public void onStart() {
//                Log.d(TAG, "onStart: ");
//            }
//
//            @Override
//            public void onProgress(int progress) {
//
//                Log.d(TAG, "onProgress: "+progress);
//            }
//
//            @Override
//            public void onFinish(String path) {
//
//                Log.d(TAG, "onFinish: "+path);
//            }
//
//            @Override
//            public void onFail(String errorInfo) {
//
//                Log.d(TAG, "onFail: "+errorInfo);
//            }
//        });
//    }
//
//    public void download(String url,String path, DownloadListener downloadListener) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://imtt.dd.qq.com")
//                //通过线程池获取一个线程，指定callback在子线程中运行。
//                .callbackExecutor(Executors.newSingleThreadExecutor())
//                .build();
//
//        DownloadService service = retrofit.create(DownloadService.class);
////        String url = "";
//        service.download(url).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                //将Response写入到从磁盘中，详见下面分析
//                //注意，这个方法是运行在子线程中的
//                DownLoadUtils.writeResponseToDisk(path, response, downloadListener);
////                    Logger.json(result);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                System.out.println("fail:"+t);
//            }
//        });
//    }



}
