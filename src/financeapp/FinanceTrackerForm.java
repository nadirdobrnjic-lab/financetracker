package financeapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FinanceTrackerForm {
    private JTextField amountField;
    private JTextField descriptionField;
    private JComboBox<String> typeCombo;
    private JComboBox<String> vrsteCombo;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable transactionTable;
    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel balanceLabel;
    private JPanel mainPanel;
    private JButton exportButton;

    private TransactionManager manager;

    public FinanceTrackerForm() {
        manager = new TransactionManager();

        typeCombo.removeAllItems();
        vrsteCombo.removeAllItems();

        typeCombo.addItem("Prihod");
        typeCombo.addItem("Rashod");

        String[] kategorije = {"Plata", "Hrana", "Racuni", "Zabava", "Prijevoz", "Ostalo"};
        for (String k : kategorije) vrsteCombo.addItem(k);

        loadDataIntoTable();
        updateSummary();

        addButton.addActionListener(e -> addTransaction());
        updateButton.addActionListener(e -> updateTransaction());
        deleteButton.addActionListener(e -> deleteTransaction());
        exportButton.addActionListener(e -> exportData());
    }

    private void addTransaction() {
        try {
            String type = (String) typeCombo.getSelectedItem();
            String category = (String) vrsteCombo.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();

            if (description.isEmpty()) return;

            Transaction t = new Transaction(type, amount, description, category);
            manager.addTransaction(t);

            loadDataIntoTable();
            updateSummary();

            amountField.setText("");
            descriptionField.setText("");

        } catch (NumberFormatException ex) {
        }
    }

    private void updateTransaction() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) return;

        try {
            String newType = (String) typeCombo.getSelectedItem();
            String newCategory = (String) vrsteCombo.getSelectedItem();
            double newAmount = Double.parseDouble(amountField.getText());
            String newDesc = descriptionField.getText();

            if (newDesc.isEmpty()) return;

            ArrayList<Transaction> list = manager.getAllTransactions();
            Transaction oldTransaction = list.get(selectedRow);

            manager.updateTransaction(oldTransaction, newAmount);
            oldTransaction.setType(newType);
            oldTransaction.setAmount(newAmount);
            oldTransaction.setDescription(newDesc);
            oldTransaction.setCategory(newCategory);

            loadDataIntoTable();
            updateSummary();

        } catch (Exception ex) {
        }
    }

    private void deleteTransaction() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) return;

        int confirm = JOptionPane.showConfirmDialog(null, "Obrisati?", "Potvrda", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        Transaction t = manager.getAllTransactions().get(selectedRow);
        manager.deleteTransaction(t);

        loadDataIntoTable();
        updateSummary();
    }

    private void loadDataIntoTable() {
        ArrayList<Transaction> list = manager.getAllTransactions();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Type");
        model.addColumn("Amount");
        model.addColumn("Description");
        model.addColumn("Category");

        for (Transaction t : list) {
            model.addRow(new Object[]{
                    t.getType(),
                    t.getAmount(),
                    t.getDescription(),
                    t.getCategory()
            });
        }

        transactionTable.setModel(model);
    }

    private void updateSummary() {
        double income = manager.getTotalIncome();
        double expense = manager.getTotalExpense();
        double balance = income - expense;

        incomeLabel.setText("Income: " + income);
        expenseLabel.setText("Expense: " + expense);
        balanceLabel.setText("Balance: " + balance);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void exportData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Odaberite lokaciju");

        int result = fileChooser.showSaveDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getAbsolutePath();

        try {
            double income = manager.getTotalIncome();
            double expense = manager.getTotalExpense();
            double balance = income - expense;

            ArrayList<Transaction> list = manager.getAllTransactions();
            Map<String, Double> kategorije = new HashMap<>();

            for (Transaction t : list) {
                if (t.getType().equals("Rashod")) {
                    kategorije.put(t.getCategory(),
                            kategorije.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Ukupni prihod: ").append(income).append("\n");
            sb.append("Ukupni rashod: ").append(expense).append("\n");
            sb.append("Stanje: ").append(balance).append("\n\n");

            sb.append("Rashodi po kategorijama:\n");
            for (String k : kategorije.keySet())
                sb.append(k).append(": ").append(kategorije.get(k)).append("\n");

            FileWriter writer = new FileWriter(path + ".txt");
            writer.write(sb.toString());
            writer.close();

        } catch (Exception ex) {
        }
    }
}
