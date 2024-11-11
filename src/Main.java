import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static JFrame frame;
    private static Map<String, Double> categoriaTotales;

    public static void main(String[] args) {
        // Usamos el Look and Feel predeterminado del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        categoriaTotales = new HashMap<>();
        categoriaTotales.put("Salud", 0.0);
        categoriaTotales.put("Comida", 0.0);
        categoriaTotales.put("Hogar", 0.0);
        categoriaTotales.put("Entretenimiento", 0.0);
        categoriaTotales.put("Otros", 0.0);

        showInitialWindow();
    }

    private static void showInitialWindow() {
        frame = new JFrame("Gestión de Gastos Personales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("<html><center>Bienvenido a la aplicación de Finanzas Personales.<br>Podrás registrar tus gastos y ver los totales por categorías.</center></html>", SwingConstants.CENTER);
        frame.add(welcomeLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton("Registrar gasto");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExpenseEntryWindow();
            }
        });
        buttonPanel.add(startButton);

        JButton exitButton = new JButton("Salir");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Gracias por usar el programa.");
                System.exit(0);
            }
        });
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void showExpenseEntryWindow() {
        frame.getContentPane().removeAll();
        frame.setSize(820, 700);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Nombre del gasto:");
        JTextField nameField = new JTextField(20);

        JLabel valueLabel = new JLabel("Valor del gasto:");
        JTextField valueField = new JTextField(10);

        JLabel categoryLabel = new JLabel("Selecciona la categoría:");
        String[] categories = {"Salud", "Comida", "Hogar", "Entretenimiento", "Otros"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);

        JButton addButton = new JButton("Agregar gasto");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    double value = Double.parseDouble(valueField.getText());
                    String category = (String) categoryComboBox.getSelectedItem();

                    // Actualizamos el total por categoría
                    categoriaTotales.put(category, categoriaTotales.get(category) + value);

                    JOptionPane.showMessageDialog(frame, "Gasto agregado: " + name + " - " + value + " en " + category);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingresa un valor válido para el gasto.");
                }
            }
        });

        JButton finishButton = new JButton("Ver totales");
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTotalsWindow();
            }
        });

        JButton resetButton = new JButton("Reiniciar datos");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reiniciamos los totales de las categorías
                categoriaTotales.put("Salud", 0.0);
                categoriaTotales.put("Comida", 0.0);
                categoriaTotales.put("Hogar", 0.0);
                categoriaTotales.put("Entretenimiento", 0.0);
                categoriaTotales.put("Otros", 0.0);

                JOptionPane.showMessageDialog(frame, "Los datos han sido reiniciados.");
            }
        });

        JButton exitButton = new JButton("Salir");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Gracias por usar el programa.");
                System.exit(0);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(nameLabel, gbc);

        gbc.gridy = 1;
        frame.add(nameField, gbc);

        gbc.gridy = 2;
        frame.add(valueLabel, gbc);

        gbc.gridy = 3;
        frame.add(valueField, gbc);

        gbc.gridy = 4;
        frame.add(categoryLabel, gbc);

        gbc.gridy = 5;
        frame.add(categoryComboBox, gbc);

        gbc.gridy = 6;
        frame.add(addButton, gbc);

        gbc.gridy = 7;
        frame.add(finishButton, gbc);

        gbc.gridy = 8;
        frame.add(resetButton, gbc);

        gbc.gridy = 9;
        frame.add(exitButton, gbc);

        frame.revalidate();
        frame.repaint();
    }

    private static void showTotalsWindow() {
        StringBuilder totalsMessage = new StringBuilder("<html><center><h3>Total por categorías:</h3>");
        double totalGeneral = 0.0;

        for (Map.Entry<String, Double> entry : categoriaTotales.entrySet()) {
            totalsMessage.append(entry.getKey()).append(": ").append(entry.getValue()).append(" <br>");
            totalGeneral += entry.getValue();
        }

        totalsMessage.append("<h3>Total General: ").append(totalGeneral).append("</h3></center></html>");

        // Mostrar los totales en un JOptionPane
        JOptionPane.showMessageDialog(frame, totalsMessage.toString(), "Totales", JOptionPane.INFORMATION_MESSAGE);

        // Después de ver los totales, no se reinicia el flujo, sino que regresamos a la ventana de registro de gastos
        frame.getContentPane().removeAll();  // Limpiamos la ventana actual
        showExpenseEntryWindow();  // Volvemos a mostrar la ventana de ingreso de gastos
    }
}
