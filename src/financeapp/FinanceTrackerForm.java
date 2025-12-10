package financeapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

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

    private TransactionManager manager;

    public FinanceTrackerForm() {
        manager = new TransactionManager();

        typeCombo.addItem("Prihod");
        typeCombo.addItem("Rashod");

        String[] kategorije = {"Plata", "Hrana", "Racuni", "Zabava", "Prijevoz", "Ostalo"};
        for (String k : kategorije) {
            vrsteCombo.addItem(k);
        }

        loadDataIntoTable();
        updateSummary();

        addButton.addActionListener(e -> {
            try {
                String type = (String) typeCombo.getSelectedItem();
                String category = (String) vrsteCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Description cannot be empty.");
                    return;
                }

                Transaction t = new Transaction(type, amount, description, category);
                manager.addTransaction(t);

                loadDataIntoTable();
                updateSummary();

                amountField.setText("");
                descriptionField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Amount must be a number");
            }
        });

        updateButton.addActionListener(e -> updateTransaction());
        deleteButton.addActionListener(e -> deleteTransaction());
    }

    private void updateTransaction() {
        int selectedRow = transactionTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Select a row to update.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();

        String newType = (String) typeCombo.getSelectedItem();
        String newCategory = (String) vrsteCombo.getSelectedItem();
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

        model.setValueAt(newType, selectedRow, 0);
        model.setValueAt(newAmount, selectedRow, 1);
        model.setValueAt(newDesc, selectedRow, 2);
        model.setValueAt(newCategory, selectedRow, 3);

        Transaction t = manager.getAllTransactions().get(selectedRow);
        t.setType(newType);
        t.setAmount(newAmount);
        t.setDescription(newDesc);
        t.setCategory(newCategory);

        updateSummary();

        JOptionPane.showMessageDialog(null, "Updated successfully!");
    }

    private void deleteTransaction() {
        int selectedRow = transactionTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Odaberite red za brisanje.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Jeste li sigurni da želite izbrisati ovu transakciju?",
                "Potvrda brisanja",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            Transaction t = manager.getAllTransactions().get(selectedRow);
            manager.deleteTransaction(t);

            loadDataIntoTable();
            updateSummary();

            JOptionPane.showMessageDialog(null, "Transakcija obrisana.");
        }
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
}
