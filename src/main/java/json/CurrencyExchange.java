package json;

public class CurrencyExchange {
    String date;
    double result;
    Info info;

    public double getRate(){
        return info.rate;
    }

    public String getDate(){
        return date;
    }

    public double getResult(){
        return result;
    }
}
