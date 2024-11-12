import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static JFrame frame;
    private static Map<String, Double> totalesPorCategoria;

    public static void main(String[] args) {
        // Usamos el Look and Feel predeterminado del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        totalesPorCategoria = new HashMap<>();
        totalesPorCategoria.put("Salud", 0.0);
        totalesPorCategoria.put("Comida", 0.0);
        totalesPorCategoria.put("Hogar", 0.0);
        totalesPorCategoria.put("Entretenimiento", 0.0);
        totalesPorCategoria.put("Otros", 0.0);

        mostrarVentanaInicial();
    }

    private static void mostrarVentanaInicial() {
        frame = new JFrame("Gestión de Gastos Personales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JLabel etiquetaBienvenida = new JLabel("<html><center>Bienvenido a la aplicación de Finanzas Personales.<br>Podrás registrar tus gastos y ver los totales por categorías.</center></html>", SwingConstants.CENTER);
        frame.add(etiquetaBienvenida, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton botonRegistrarGasto = new JButton("Registrar gasto");
        botonRegistrarGasto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaRegistroGasto();
            }
        });
        panelBotones.add(botonRegistrarGasto);

        JButton botonSalir = new JButton("Salir");
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Gracias por usar el programa.");
                System.exit(0);
            }
        });
        panelBotones.add(botonSalir);

        frame.add(panelBotones, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void mostrarVentanaRegistroGasto() {
        frame.getContentPane().removeAll();
        frame.setSize(820, 700);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel etiquetaNombre = new JLabel("Nombre del gasto:");
        JTextField campoNombre = new JTextField(20);

        JLabel etiquetaValor = new JLabel("Valor del gasto:");
        JTextField campoValor = new JTextField(10);

        JLabel etiquetaCategoria = new JLabel("Selecciona la categoría:");
        String[] categorias = {"Salud", "Comida", "Hogar", "Entretenimiento", "Otros"};
        JComboBox<String> comboBoxCategoria = new JComboBox<>(categorias);

        JButton botonAgregarGasto = new JButton("Agregar gasto");
        botonAgregarGasto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = campoNombre.getText();
                    double valor = Double.parseDouble(campoValor.getText());
                    String categoria = (String) comboBoxCategoria.getSelectedItem();

                    // Actualizamos el total por categoría
                    totalesPorCategoria.put(categoria, totalesPorCategoria.get(categoria) + valor);

                    JOptionPane.showMessageDialog(frame, "Gasto agregado: " + nombre + " - " + valor + " en " + categoria);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingresa un valor válido para el gasto.");
                }
            }
        });

        JButton botonVerTotales = new JButton("Ver totales");
        botonVerTotales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaTotales();
            }
        });

        JButton botonReiniciarDatos = new JButton("Reiniciar datos");
        botonReiniciarDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reiniciamos los totales de las categorías
                totalesPorCategoria.put("Salud", 0.0);
                totalesPorCategoria.put("Comida", 0.0);
                totalesPorCategoria.put("Hogar", 0.0);
                totalesPorCategoria.put("Entretenimiento", 0.0);
                totalesPorCategoria.put("Otros", 0.0);

                JOptionPane.showMessageDialog(frame, "Los datos han sido reiniciados.");
            }
        });

        JButton botonSalir = new JButton("Salir");
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Gracias por usar el programa.");
                System.exit(0);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(etiquetaNombre, gbc);

        gbc.gridy = 1;
        frame.add(campoNombre, gbc);

        gbc.gridy = 2;
        frame.add(etiquetaValor, gbc);

        gbc.gridy = 3;
        frame.add(campoValor, gbc);

        gbc.gridy = 4;
        frame.add(etiquetaCategoria, gbc);

        gbc.gridy = 5;
        frame.add(comboBoxCategoria, gbc);

        gbc.gridy = 6;
        frame.add(botonAgregarGasto, gbc);

        gbc.gridy = 7;
        frame.add(botonVerTotales, gbc);

        gbc.gridy = 8;
        frame.add(botonReiniciarDatos, gbc);

        gbc.gridy = 9;
        frame.add(botonSalir, gbc);

        frame.revalidate();
        frame.repaint();
    }

    private static void mostrarVentanaTotales() {
        StringBuilder mensajeTotales = new StringBuilder("<html><center><h3>Total por categorías:</h3>");
        double totalGeneral = 0.0;

        for (Map.Entry<String, Double> entry : totalesPorCategoria.entrySet()) {
            mensajeTotales.append(entry.getKey()).append(": ").append(entry.getValue()).append(" <br>");
            totalGeneral += entry.getValue();
        }

        mensajeTotales.append("<h3>Total General: ").append(totalGeneral).append("</h3></center></html>");

        // mostrar totales de las categorias en la ventana
        JOptionPane.showMessageDialog(frame, mensajeTotales.toString(), "Totales", JOptionPane.INFORMATION_MESSAGE);

        // Después de ver los totales, no se reinicia el flujo, sino que regresamos a la ventana de registro de gastos
        frame.getContentPane().removeAll();  // Limpiamos la ventana actual
        mostrarVentanaRegistroGasto();  // Volvemos a mostrar la ventana de ingreso de gastos
    }
}