import json.CurrencyExchangeServiceFactory;
import json.CurrencySymbolsServiceFactory;
import json.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.Map;

import static javax.swing.BoxLayout.*;

public class CurrencyExchangeFrame extends JFrame {
    private final JLabel fromLabel;
    private final JLabel toLabel;
    private final JComboBox fromComboBox;
    private final JLabel fromAbbreviatedLabel;
    private final JFormattedTextField amountTextField;
    private final JComboBox toComboBox;
    private final JLabel toAbbreviatedLabel;
    private final JButton submitButton;
    private final JLabel resultLabel;
    private final JLabel rateLabel;
    private final CurrencyExchangePresenter presenter;
    private Map<String,Symbol> symbolsMap;
    private String[] symbolsArray;

    public CurrencyExchangeFrame() {
        CurrencyExchangeServiceFactory factory = new CurrencyExchangeServiceFactory();
        CurrencySymbolsServiceFactory symbolsFactory = new CurrencySymbolsServiceFactory();
        presenter = new CurrencyExchangePresenter(this, factory.getInstance());
        presenter.loadSymbolsChoices();
        
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
        
        presenter.loadSymbolsChoices();
        
        fromLabel = new JLabel("From");
        fromComboBox = new JComboBox<>(symbolsArray);
        fromAbbreviatedLabel = new JLabel(String.valueOf(symbolsMap.get(fromComboBox.getSelectedItem()).getDescription()));
        amountTextField = new JFormattedTextField(NumberFormat.getInstance());

        fromPanel.add(fromLabel);
        fromPanel.add(fromComboBox);
        fromPanel.add(fromAbbreviatedLabel);
        fromPanel.add(amountTextField);

        toLabel = new JLabel("To");
        toComboBox = new JComboBox<>(symbolsArray);
        toAbbreviatedLabel = new JLabel(String.valueOf(symbolsMap.get(toComboBox.getSelectedItem()).getDescription()));
        submitButton = new JButton("SUBMIT");

        submitButton.addActionListener(this::onSubmitClicked);

        toPanel.add(toLabel);
        toPanel.add(toComboBox);
        toPanel.add(toAbbreviatedLabel);
        toPanel.add(submitButton);

        resultLabel = new JLabel("");
        rateLabel = new JLabel("");

        ratePanel.add(resultLabel);
        ratePanel.add(rateLabel);

        
    }

    private void onSubmitClicked(ActionEvent actionEvent) {
        presenter.loadResultFromQuery(((Number)amountTextField.getValue()).doubleValue(), (String) symbolsMap.get(fromComboBox.getSelectedItem()).getCode(), (String) symbolsMap.get(toComboBox.getSelectedItem()).getCode());
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
    

    public void setSymbolsChoices(Map<String,Symbol> symbols) {
        symbolsMap = symbols;
        symbolsArray = symbolsMap.keySet().toArray(new String[0]);

        

    }
}

