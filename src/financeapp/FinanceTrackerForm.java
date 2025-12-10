package financeapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class FinanceTrackerForm {
    private JTextField amountField;
    private JTextField descriptionField;
    private JComboBox<String> typeCombo;
    private JButton addButton;
    private JTable transactionTable;
    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel balanceLabel;
    private JPanel mainPanel;
    private JButton updateButton;

    private TransactionManager manager;

    public FinanceTrackerForm() {
        manager = new TransactionManager();
        loadDataIntoTable();
        updateSummary();

        // --------------------
        // ADD NEW TRANSACTION
        // --------------------
        addButton.addActionListener(e -> {
            try {
                String type = (String) typeCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Description cannot be empty.");
                    return;
                }

                Transaction t = new Transaction(type, amount, description);
                manager.addTransaction(t);

                loadDataIntoTable();
                updateSummary();

                amountField.setText("");
                descriptionField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Amount must be a number");
            }
        });

        // --------------------
        // UPDATE TRANSACTION
        // --------------------
        updateButton.addActionListener(e -> updateTransaction());
    }

    // -----------------------------
    // UPDATE TRANSACTION FUNCTION
    // -----------------------------
    private void updateTransaction() {
        int selectedRow = transactionTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Select a row to update.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();

        // New values from GUI fields
        String newType = (String) typeCombo.getSelectedItem();
        double newAmount;

        try {
            newAmount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Amount must be a number.");
            return;
        }

        String newDesc = descriptionField.getText();
        if (newDesc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Description cannot be empty.");
            return;
        }

        // Update table
        model.setValueAt(newType, selectedRow, 0);
        model.setValueAt(newAmount, selectedRow, 1);
        model.setValueAt(newDesc, selectedRow, 2);

        // Update internal list
        Transaction t = manager.getAllTransactions().get(selectedRow);
        t.setType(newType);
        t.setAmount(newAmount);
        t.setDescription(newDesc);

        updateSummary();

        JOptionPane.showMessageDialog(null, "Updated successfully!");
    }

    // -----------------------------
    // LOAD ALL DATA INTO TABLE
    // -----------------------------
    private void loadDataIntoTable() {
        ArrayList<Transaction> list = manager.getAllTransactions();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Type");
        model.addColumn("Amount");
        model.addColumn("Description");

        for (Transaction t : list) {
            model.addRow(new Object[]{
                    t.getType(),
                    t.getAmount(),
                    t.getDescription()
            });
        }
        transactionTable.setModel(model);
    }

    // -----------------------------
    // SUMMARY LABEL UPDATE
    // -----------------------------
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
}
