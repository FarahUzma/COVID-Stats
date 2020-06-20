package m.covidstat;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://covid-193.p.rapidapi.com/statistics/";
    private static final String INDIA_URL="https://api.covid19india.org/";
    private static final String append="data.json";
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getRetrofitInstance2() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(INDIA_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface GetDataService {
        @Headers({"x-rapidapi-host:covid-193.p.rapidapi.com","x-rapidapi-key:cr5B3llEK4msh4givsGN9NMjxkQ7p1f80Wkjsne8E8h2qGa6CG"})
        @GET(".")
        Flowable<StatisticsModel> getStatistics();
    }
    public interface GetIndiaDataService {
        @GET("data.json")
        Flowable<IndiaModel> getStatistics();
    }
}
