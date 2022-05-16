package json;

public class CurrencyExchange {
    Main main;

    public String getInfo(){
        return main.info.toString();
    }

    public String getDate(){
        return main.date;
    }

    public double getResult(){
        return main.result;
    }
}
