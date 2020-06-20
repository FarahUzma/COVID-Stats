package m.covidstat;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class RetrofitClientInstance2 {

    private static Retrofit retrofit;
    private static final String INDIA_URL="https://api.covid19india.org/";
    private static final String append="data.json";
    public static Retrofit getRetrofitInstance2() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(INDIA_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface GetIndiaDataService {
        @GET("data.json")
        Flowable<IndiaModel> getStatistics();
    }
}
