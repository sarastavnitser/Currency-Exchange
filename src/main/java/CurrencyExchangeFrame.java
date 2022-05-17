import json.CurrencyExchangeServiceFactory;
import json.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Map;
import java.lang.*;

import static javax.swing.BoxLayout.*;


public class CurrencyExchangeFrame extends JFrame implements ItemListener {
    private final JLabel fromLabel;
    private final JLabel toLabel;
    private final JComboBox fromComboBox;
    private final JLabel fromAbbreviatedLabel;
    private final JFormattedTextField amountTextField;
    private final JComboBox toComboBox;
    private final JLabel toAbbreviatedLabel;
    private final JButton submitButton;
    private final JLabel dateLabel;
    private final JLabel resultLabel;
    private final JLabel rateLabel;
    private final CurrencyExchangePresenter presenter;
    private Map<String, Symbol> symbolsMap;
    private String[] symbolsArray;
    private String[] descriptionsArray;

    public CurrencyExchangeFrame() {
        CurrencyExchangeServiceFactory factory = new CurrencyExchangeServiceFactory();
        presenter = new CurrencyExchangePresenter(this, factory.getInstance());
        presenter.loadSymbolsChoices();

        setTitle("Currency Exchange");
        setSize(850, 165);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel fromPanel = new JPanel();
        fromPanel.setLayout(new BoxLayout(fromPanel, Y_AXIS));
        fromPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 20));
        fromPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(fromPanel);

        JPanel toPanel = new JPanel();
        toPanel.setLayout(new BoxLayout(toPanel, Y_AXIS));
        toPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 20));
        toPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(toPanel);

        JPanel ratePanel = new JPanel();
        ratePanel.setLayout(new BoxLayout(ratePanel, Y_AXIS));
        ratePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        ratePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(ratePanel);

        setLayout(new FlowLayout());

        presenter.loadSymbolsChoices();

        fromLabel = new JLabel("From");
        fromComboBox = new JComboBox<>(descriptionsArray);
        fromAbbreviatedLabel = new JLabel(String.valueOf(symbolsMap.get(symbolsArray[indexOf(descriptionsArray, fromComboBox.getSelectedItem())]).getCode()));
        amountTextField = new JFormattedTextField(NumberFormat.getInstance());

        fromPanel.add(fromLabel);
        fromPanel.add(fromComboBox);
        fromPanel.add(fromAbbreviatedLabel);
        fromPanel.add(amountTextField);

        toLabel = new JLabel("To");
        toComboBox = new JComboBox<>(descriptionsArray);
        toAbbreviatedLabel = new JLabel(String.valueOf(symbolsMap.get(symbolsArray[indexOf(descriptionsArray, toComboBox.getSelectedItem())]).getCode()));
        submitButton = new JButton("SUBMIT");

        submitButton.addActionListener(this::onSubmitClicked);

        toPanel.add(toLabel);
        toPanel.add(toComboBox);
        toPanel.add(toAbbreviatedLabel);
        toPanel.add(submitButton);

        dateLabel = new JLabel(" ");
        resultLabel = new JLabel("");
        resultLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        rateLabel = new JLabel("");

        ratePanel.add(dateLabel);
        ratePanel.add(resultLabel);
        ratePanel.add(rateLabel);

        setAction();
    }

    private void setAction() {
        fromComboBox.addItemListener(this);
        toComboBox.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent itemEvent) {
        String fromStr = String.valueOf(symbolsMap.get(symbolsArray[indexOf(descriptionsArray, fromComboBox.getSelectedItem())]).getCode());
        String toStr = String.valueOf(symbolsMap.get(symbolsArray[indexOf(descriptionsArray, toComboBox.getSelectedItem())]).getCode());
        fromAbbreviatedLabel.setText(fromStr);
        toAbbreviatedLabel.setText(toStr);
    }

    private void onSubmitClicked(ActionEvent actionEvent) {
        presenter.loadResultFromQuery(((Number) amountTextField.getValue()).doubleValue(), fromAbbreviatedLabel.getText(), toAbbreviatedLabel.getText());
    }

    public void setDateLabel(String date) {
        dateLabel.setText("date: " + date);
    }

    public void setResultLabel(String result) {
        resultLabel.setText("result: " + result);
    }

    public void setRateLabel(double rate) {
        rateLabel.setText("rate: " + String.valueOf(rate));
    }


    public static void main(String[] args) {
        CurrencyExchangeFrame frame = new CurrencyExchangeFrame();
        frame.setVisible(true);
    }

    public void showError() {
        dateLabel.setText("Error: Incompatible values entered.");
        resultLabel.setText("Please enter numbers only.");
    }


    public void setSymbolsChoices(Map<String, Symbol> symbols) {
        symbolsMap = symbols;
        symbolsArray = symbolsMap.keySet().toArray(new String[0]);
        descriptionsArray = new String[symbolsArray.length];
        for (int i = 0; i < symbolsArray.length; i++) {
            descriptionsArray[i] = symbolsMap.get(symbolsArray[i]).getDescription();
        }
    }

    private static int indexOf(Object[] strArray, Object element) {
        int index = Arrays.asList(strArray).indexOf(element);
        return index;
    }
}

