package json;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencySymbolsService {
    @GET("symbols")
    Single<CurrencyExchange> getCurrencySymbols();

}
