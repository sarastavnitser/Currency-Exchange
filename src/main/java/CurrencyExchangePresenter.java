import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import json.CurrencyExchange;
import json.CurrencyExchangeService;
import json.CurrencySymbolsService;

public class CurrencyExchangePresenter {

    private final CurrencyExchangeFrame view;
    private final CurrencyExchangeService model;
    private final CurrencySymbolsService symbolsModel;
    private Disposable disposable;

    public CurrencyExchangePresenter(CurrencyExchangeFrame view, CurrencyExchangeService model, CurrencySymbolsService symbolsModel) {
        this.view = view;
        this.model = model;
        this.symbolsModel = symbolsModel;
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
        double result = currencyExchange.getResult();
        double rate = currencyExchange.getRate();
        view.setResultLabel(String.valueOf(result));
        view.setRateLabel(rate);

    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
        view.showError();

    }
}
