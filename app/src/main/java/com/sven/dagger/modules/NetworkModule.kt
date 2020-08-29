package com.sven.dagger.modules

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    //////////////////////////////////////////////////
    //////////////////// Retrofit ////////////////////
    //////////////////////////////////////////////////
    @Provides
    @Singleton
    @Named("default_retrofit")
    fun providesDefaultRetrofit(@Named("default_okhttp_client") okHttpClient: OkHttpClient,
                                @Named("null_or_empty_converter_factory") nullOrEmptyConverterFactory: Converter.Factory,
                                gson: Gson): Retrofit = Retrofit.Builder()
//            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(nullOrEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

    @Provides
    @Named("null_or_empty_converter_factory")
    internal fun providesNullOrEmptyConverterFactory(): Converter.Factory {
        return object : Converter.Factory() {
            fun converterFactory() = this
            override fun responseBodyConverter(type: Type,
                                               annotations: Array<out Annotation>,
                                               retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
                override fun convert(value: ResponseBody): Any? {
                    val bodyString = value.string()
                    if (bodyString.isNullOrEmpty()) {
                        return null
                    }
                    return nextResponseBodyConverter.convert(ResponseBody.create(null, bodyString))
                }
            }
        }
    }

    //////////////////////////////////////////////////
    ////////////////// OkHttp Client /////////////////
    //////////////////////////////////////////////////
    @Provides
    @Singleton
    @Named("default_okhttp_client")
    fun providesDefaultOkHttpClient(@Named("default_cache") cache: Cache,
                                    @Named("http_logging_interceptor") loggingInterceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    @Named("default_cache")
    fun providesDefaultCache(context: Context): Cache {
        val cacheDir = File(context.cacheDir, "HttpResponseCache")
        return Cache(cacheDir, 10 * 1024 * 1024)
    }

    @Provides
    @Named("http_logging_interceptor")
    fun providesHttpLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = if (BuildConfig.ENABLE_LOG_HTTP) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }
}