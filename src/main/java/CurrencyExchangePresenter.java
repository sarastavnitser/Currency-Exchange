import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import json.CurrencyExchange;
import json.CurrencyExchangeService;

public class CurrencyExchangePresenter {

    private final CurrencyExchangeFrame view;
    private final CurrencyExchangeService model;
    private Disposable disposable;

    public CurrencyExchangePresenter(CurrencyExchangeFrame view, CurrencyExchangeService model) {
        this.view = view;
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
        double result = currencyExchange.getResult();
        String rate = currencyExchange.getInfo();
        view.setResultLabel(String.valueOf(result));
        view.setRateLabel(rate);

    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
        view.showError();

    }
}
