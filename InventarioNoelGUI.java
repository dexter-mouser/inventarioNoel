import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InventarioNoelGUI {

    // ===== Mismo arreglo y misma logica de siempre =====
    static String[] inventario = {"ducales", "festival", "tosh", null, null};

    public static void main(String[] args) {
        // Ejecutar la interfaz en el hilo correcto de Swing
        SwingUtilities.invokeLater(InventarioNoelGUI::crearVentana);
    }

    static void crearVentana() {
        JFrame ventana = new JFrame("Inventario Noel");
        ventana.setSize(500, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font("Segoe UI", Font.BOLD, 14));

        pestanas.addTab("📋 Listar", crearPestanaListar());
        pestanas.addTab("🔍 Buscar", crearPestanaBuscar());
        pestanas.addTab("➕ Agregar", crearPestanaAgregar());

        ventana.add(pestanas);
        ventana.setVisible(true);
    }

    // ===== Pestaña 1: Listar productos =====
    static JPanel crearPestanaListar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 16));

        JButton botonActualizar = new JButton("Actualizar lista");
        botonActualizar.addActionListener((ActionEvent e) -> {
            StringBuilder texto = new StringBuilder();
            for (String producto : inventario) {
                if (producto != null) {
                    texto.append("• ").append(producto).append("\n");
                }
            }
            area.setText(texto.toString());
        });

        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        panel.add(botonActualizar, BorderLayout.SOUTH);

        botonActualizar.doClick(); // mostrar la lista apenas se abra
        return panel;
    }

    // ===== Pestaña 2: Buscar producto =====
    static JPanel crearPestanaBuscar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton boton = new JButton("Buscar");
        JLabel resultado = new JLabel(" ");
        resultado.setFont(new Font("Segoe UI", Font.BOLD, 14));

        boton.addActionListener((ActionEvent e) -> {
            String nombreBuscado = campo.getText().trim();
            boolean encontrado = buscarProducto(inventario, nombreBuscado);

            if (encontrado) {
                resultado.setText("✅ '" + nombreBuscado + "' SI existe en el inventario.");
                resultado.setForeground(new Color(0, 128, 0));
            } else {
                resultado.setText("❌ '" + nombreBuscado + "' NO existe en el inventario.");
                resultado.setForeground(Color.RED);
            }
        });

        panel.add(new JLabel("Nombre del producto a buscar:"));
        panel.add(Box.createVerticalStrut(8));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(boton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(resultado);

        return panel;
    }

    // ===== Pestaña 3: Agregar producto =====
    static JPanel crearPestanaAgregar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton boton = new JButton("Agregar");
        JLabel resultado = new JLabel(" ");
        resultado.setFont(new Font("Segoe UI", Font.BOLD, 14));

        boton.addActionListener((ActionEvent e) -> {
            String nuevoProducto = campo.getText().trim();

            if (nuevoProducto.isEmpty()) {
                resultado.setText("⚠️ Escribe un nombre antes de agregar.");
                resultado.setForeground(Color.ORANGE.darker());
                return;
            }

            boolean agregado = false;
            for (int i = 0; i < inventario.length; i++) {
                if (inventario[i] == null) {
                    inventario[i] = nuevoProducto;
                    agregado = true;
                    break;
                }
            }

            if (agregado) {
                resultado.setText("✅ '" + nuevoProducto + "' agregado correctamente.");
                resultado.setForeground(new Color(0, 128, 0));
                campo.setText("");
            } else {
                resultado.setText("❌ Sin espacio disponible, el inventario esta lleno.");
                resultado.setForeground(Color.RED);
            }
        });

        panel.add(new JLabel("Nombre del nuevo producto:"));
        panel.add(Box.createVerticalStrut(8));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(boton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(resultado);

        return panel;
    }

    // ===== Misma funcion de siempre, sin cambios =====
    public static boolean buscarProducto(String[] arreglo, String nombreBuscado) {
        for (String producto : arreglo) {
            if (producto != null && producto.equals(nombreBuscado)) {
                return true;
            }
        }
        return false;
    }
}
