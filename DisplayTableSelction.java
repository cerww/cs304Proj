import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DisplayTableSelction {
    Program mainProgram;

    String tableName;
    String joinStatement;
    List<String> attributesToDisplay;
    List<String> attributes;
    String orderByAttribute;



    JPanel sidePanel;
    private JPanel filterOptionsPanel;
    private List<FilterOption> whereFilters = new ArrayList<>();
    private List<Consumer<Void>> thingsToFireBeforeFilters = new ArrayList<>();

    public DisplayTableSelction(Program p, String tableName, String joinStatment, Stream<String> attributesToDisplay) {
        mainProgram = p;
        this.tableName = tableName;
        this.joinStatement = joinStatment;
        sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());

        filterOptionsPanel = new JPanel();
        filterOptionsPanel.setLayout(new GridLayout(0, 2));
        sidePanel.add(filterOptionsPanel, BorderLayout.NORTH);

        JButton applyFiltersButton = new JButton();
        applyFiltersButton.setText("apply filters");
        applyFiltersButton.addActionListener(this::applyFilters);


        sidePanel.add(applyFiltersButton, BorderLayout.SOUTH);


        this.attributes = attributesToDisplay.collect(Collectors.toList());
        this.attributesToDisplay = this.attributes.stream().map(DisplayTableSelction::getName).collect(Collectors.toList());


        initOrderByStuff();
    }

    private void initOrderByStuff() {
        JLabel orderByText = new JLabel();
        orderByText.setText("sort by:");
        JComboBox<String> orderByBox = new JComboBox<>();
        orderByBox.addItem("None");
        attributes.forEach(orderByBox::addItem);
        filterOptionsPanel.add(orderByText);
        filterOptionsPanel.add(orderByBox);
        orderByBox.addActionListener((e)->orderByAttribute = (String)orderByBox.getSelectedItem());
        orderByAttribute = "None";
    }


    JTable getTable() {
        //TODO: getTable will execute an sql statement to get data, then returns a table with the stuff
        //stuff in whereFilters will be part of the statement
        String statement = "Select " + joinedAttributes() + " from "
                + tableName
                + " where "
                + joinStatement
                + whereFilters.stream()
                .map(FilterOption::getQueryString)
                .filter(s -> !(s.length() == 0))
                .map(s -> " and " + s)
                .collect(Collectors.joining());
        //                //+ "group by";
        if(!orderByAttribute.equals("None")){
            statement+=" order by " + orderByAttribute;
        }

        System.out.println(statement);
        return new JTable();
    }

    ;

    DisplayTableSelction addFilterOptionEqual(String optionName) {
        EqualFilterOption fo = new EqualFilterOption();
        fo.optionName = optionName;
        addOptionLabelFor(removeDot(optionName));
        JTextField textField = new JTextField();

        //textField.addActionListener((a -> fo.value = textField.getText()));
        thingsToFireBeforeFilters.add((q) -> fo.value = textField.getText());

        whereFilters.add(fo);
        filterOptionsPanel.add(textField);
        filterOptionsPanel.repaint();
        return this;
    }

    DisplayTableSelction addFilterOptionLike(String optionName) {
        LikeFilterOption fo = new LikeFilterOption();
        fo.optionName = optionName;
        addOptionLabelFor(removeDot(optionName));
        JTextField textField = new JTextField();

        thingsToFireBeforeFilters.add((q) -> fo.value = textField.getText());

        whereFilters.add(fo);
        filterOptionsPanel.add(textField);
        return this;
    }

    DisplayTableSelction addIntFilterOption(String optionName) {
        GreaterFilterOption gtFilterOption = new GreaterFilterOption();
        LessFilterOption ltFilterOption = new LessFilterOption();
        gtFilterOption.optionName = optionName;
        ltFilterOption.optionName = optionName;
        addOptionLabelFor(removeDot(optionName));
        JPanel panelThatHoldsBothTextFields = new JPanel();
        panelThatHoldsBothTextFields.setLayout(new GridLayout(1,3));
        JLabel label1 = new JLabel("      ---");


        JTextField gtTextField = new JTextField();
        JTextField ltTextField = new JTextField();
        panelThatHoldsBothTextFields.add(gtTextField);

        panelThatHoldsBothTextFields.add(label1);
        panelThatHoldsBothTextFields.add(ltTextField);

        thingsToFireBeforeFilters.add((q) -> gtFilterOption.amount = parseIntDouble(gtTextField.getText()));
        thingsToFireBeforeFilters.add((q) -> ltFilterOption.amount = parseIntDouble(ltTextField.getText()));

        whereFilters.add(gtFilterOption);
        whereFilters.add(ltFilterOption);
        filterOptionsPanel.add(panelThatHoldsBothTextFields);
        return this;
    }

    DisplayTableSelction addDateFilterOption(String optionName){
        //TODO
        return this;
    }

    private void addOptionLabelFor(String optionName) {
        JLabel label = new JLabel();
        label.setText(optionName);
        filterOptionsPanel.add(label);
    }

    private void applyFilters(ActionEvent e) {
        thingsToFireBeforeFilters.forEach((a) -> a.accept(null));
        mainProgram.setTable(getTable());
    }

    private static String removeDot(String s) {
        int idx = s.indexOf(".");
        if (idx == -1) {
            return s;
        }
        return s.substring(idx + 1);
    }

    private static String getName(String s) {
        int idx = s.lastIndexOf(" ");
        if (idx == -1) {
            return s;
        } else {
            return s.substring(idx + 1);
        }
    }

    static private Optional<Double> parseIntDouble(String s) {
        try {
            if ((s.length() == 0)) {
                return Optional.empty();
            } else {
                return Optional.of(Double.parseDouble(s));
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private String joinedAttributes(){
        return String.join(", ", attributes);
    }

}
