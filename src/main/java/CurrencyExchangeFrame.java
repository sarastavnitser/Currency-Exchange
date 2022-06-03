import json.CurrencyExchangeServiceFactory;
import json.Symbol;
import dagger.CurrencyExchangeComponent;


import javax.inject.Inject;
import javax.inject.Singleton;
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

@Singleton
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

    @Inject
    public CurrencyExchangeFrame(CurrencyExchangePresenter presenter) {
        this.presenter = presenter;

        CurrencyExchangeServiceFactory factory = new CurrencyExchangeServiceFactory();



        setTitle("Currency Exchange");
        setSize(850, 165);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel fromPanel = new JPanel();
        fromPanel.setLayout(new BoxLayout(fromPanel, Y_AXIS));
        fromPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 20));
        fromPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(fromPanel);

        JPanel toPanel = new JPanel();
        toPanel.setLayout(new BoxLayout(toPanel, Y_AXIS));
        toPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 20));
        toPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(toPanel);

        JPanel ratePanel = new JPanel();
        ratePanel.setLayout(new BoxLayout(ratePanel, Y_AXIS));
        ratePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        ratePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(ratePanel);

        setLayout(new FlowLayout());

        fromLabel = new JLabel("From");
        fromLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fromComboBox = new JComboBox(new String[]{"                            "});
        fromAbbreviatedLabel = new JLabel(" ");
        fromAbbreviatedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountTextField = new JFormattedTextField(NumberFormat.getInstance());
        amountTextField.setValue(1);

        fromPanel.add(fromLabel);
        fromPanel.add(fromComboBox);
        fromPanel.add(fromAbbreviatedLabel);
        fromPanel.add(amountTextField);

        toLabel = new JLabel("To");
        toLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        toComboBox = new JComboBox(new String[]{"                            "});
        toAbbreviatedLabel = new JLabel(" ");

        toAbbreviatedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton = new JButton("SUBMIT");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitButton.addActionListener(this::onSubmitClicked);

        toPanel.add(toLabel);
        toPanel.add(toComboBox);
        toPanel.add(toAbbreviatedLabel);
        toPanel.add(submitButton);

        dateLabel = new JLabel(" ");
        resultLabel = new JLabel("");
        rateLabel = new JLabel("");
        rateLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        ratePanel.add(dateLabel);
        ratePanel.add(rateLabel);
        ratePanel.add(resultLabel);
        presenter.loadSymbolsChoices();
        addComboBoxListeners();
    }

    private void addComboBoxListeners() {
        fromComboBox.addItemListener(this);
        toComboBox.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent itemEvent) {
        if (fromComboBox.getSelectedItem() == null || toComboBox.getSelectedItem() == null) {
            return;
        }

        String fromCode = symbolsArray[indexOf(descriptionsArray, fromComboBox.getSelectedItem())];
        String fromStr = symbolsMap.get(fromCode).getCode();
        fromAbbreviatedLabel.setText(fromStr);
        String toCode = symbolsArray[indexOf(descriptionsArray, toComboBox.getSelectedItem())];
        String toStr = symbolsMap.get(toCode).getCode();
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
        fromComboBox.removeAllItems();
        toComboBox.removeAllItems();

        for (int i = 0; i < descriptionsArray.length; i++) {
            fromComboBox.addItem(descriptionsArray[i]);
            toComboBox.addItem(descriptionsArray[i]);
        }
        fromAbbreviatedLabel.setText(String.valueOf(symbolsMap.get(symbolsArray[indexOf(descriptionsArray, fromComboBox.getSelectedItem())]).getCode()));
        toAbbreviatedLabel.setText(String.valueOf(symbolsMap.get(symbolsArray[indexOf(descriptionsArray, toComboBox.getSelectedItem())]).getCode()));
    }

    private int indexOf(String[] strArray, Object element) {
        int index = Arrays.asList(strArray).indexOf(element);
        return index;
    }

    public static void main(String[] args) {
        CurrencyExchangeFrame frame = DaggerCurrencyExchangeComponent
                .create()
                .getCurrencyExchangeFrame();
        frame.setVisible(true);
    }
}

