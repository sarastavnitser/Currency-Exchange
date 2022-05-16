import json.CurrencyExchangeServiceFactory;
import json.CurrencySymbols;
import json.CurrencySymbolsService;
import json.CurrencySymbolsServiceFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import static javax.swing.BoxLayout.*;

public class CurrencyExchangeFrame extends JFrame {
    private final JLabel fromLabel;
    private final JLabel toLabel;
    private final JComboBox fromComboBox;
    private final JFormattedTextField amountTextField;
    private final JComboBox toComboBox;
    private final JButton submitButton;
    private final JLabel resultLabel;
    private final JLabel rateLabel;
    private final CurrencyExchangePresenter presenter;

    public CurrencyExchangeFrame() {
        setTitle("Currency Exchange");
        setSize(400, 140);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel fromPanel = new JPanel();
        fromPanel.setLayout(new BoxLayout(fromPanel, Y_AXIS));
        add (fromPanel);

        JPanel toPanel = new JPanel();
        toPanel.setLayout(new BoxLayout(toPanel, Y_AXIS));
        add (toPanel);

        JPanel ratePanel = new JPanel();
        ratePanel.setLayout(new BoxLayout(ratePanel, Y_AXIS));
        add(ratePanel);


        setLayout(new FlowLayout());

        String[] choices = {"USD", "GBP", "EUR"};

        fromLabel = new JLabel("From");
        fromComboBox = new JComboBox<>(choices);
        amountTextField = new JFormattedTextField(NumberFormat.getInstance());

        fromPanel.add(fromLabel);
        fromPanel.add(fromComboBox);
        fromPanel.add(amountTextField);

        toLabel = new JLabel("To");
        toComboBox = new JComboBox<>(choices);
        submitButton = new JButton("SUBMIT");

        submitButton.addActionListener(this::onSubmitClocked);

        toPanel.add(toLabel);
        toPanel.add(toComboBox);
        toPanel.add(submitButton);

        resultLabel = new JLabel("");
        rateLabel = new JLabel("");

        ratePanel.add(resultLabel);
        ratePanel.add(rateLabel);



        CurrencyExchangeServiceFactory factory = new CurrencyExchangeServiceFactory();
        CurrencySymbolsServiceFactory symbolsFactory = new CurrencySymbolsServiceFactory();
        presenter = new CurrencyExchangePresenter(this, factory.getInstance(),  symbolsFactory.getInstance());


    }

    private void onSubmitClocked(ActionEvent actionEvent) {
        presenter.loadResultFromQuery(((Number)amountTextField.getValue()).doubleValue(), (String) fromComboBox.getSelectedItem(), (String) toComboBox.getSelectedItem());
    }

    public void setResultLabel(String result) {
        resultLabel.setText("result: " +result);
    }

    public void setRateLabel(double rate) {
        rateLabel.setText("rate: "  + String.valueOf(rate));
    }

    public static void main(String[] args) {
        CurrencyExchangeFrame frame = new CurrencyExchangeFrame();
        frame.setVisible(true);
    }

    public void showError() {

    }
}

