package com.jiangm.update.uptutil;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2018/7/24.
 */

public interface DownloadService {

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

}
