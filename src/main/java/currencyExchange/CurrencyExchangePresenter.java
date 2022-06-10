package currencyExchange;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import currencyExchange.json.CurrencyExchange;
import currencyExchange.json.CurrencyExchangeService;
import currencyExchange.json.Symbol;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Map;
@Singleton
public class CurrencyExchangePresenter {

    private final Provider<CurrencyExchangeFrame> viewProvider;
    private final CurrencyExchangeService model;
    private Disposable disposable;
    private Disposable symbolsDisposable;

    @Inject
    public CurrencyExchangePresenter(
            Provider<CurrencyExchangeFrame> viewProvider,
            CurrencyExchangeService model) {
        this.viewProvider = viewProvider;
        this.model = model;
    }

    public void loadResultFromQuery(double amount, String fromComboBox, String toComboBox) {
        disposable = model.getCurrencyExchange(amount, fromComboBox, toComboBox)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onNext, this::onError);
    }

    public void cancel() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void onNext(CurrencyExchange currencyExchange) {
        String date = currencyExchange.getDate();
        double result = currencyExchange.getResult();
        double rate = currencyExchange.getRate();
        viewProvider.get().setDateLabel(date);
        viewProvider.get().setResultLabel(String.valueOf(result));
        viewProvider.get().setRateLabel(rate);
    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
        viewProvider.get().showError();
    }

    public void loadSymbolsChoices() {
        symbolsDisposable = model.getCurrencySymbols()
               .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onSymbolsNext);
    }

    private void onSymbolsNext(CurrencyExchange object) {
        Map<String, Symbol> symbols = object.getSymbols();
        viewProvider.get().setSymbolsChoices(symbols);
    }
}
