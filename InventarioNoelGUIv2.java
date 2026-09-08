import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioNoelGUIv2 {

    // ===== Ahora usamos ArrayList en vez de arreglo fijo con null =====
    static List<String> inventario = new ArrayList<>(List.of("ducales", "festival", "tosh"));

    // Componentes que varias pestañas necesitan actualizar
    static DefaultListModel<String> modeloLista = new DefaultListModel<>();
    static JLabel footer = new JLabel();

    public static void main(String[] args) {
        // Look and feel mas moderno que el gris por defecto de Swing
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Si Nimbus no esta disponible, seguimos con el look por defecto
        }

        SwingUtilities.invokeLater(InventarioNoelGUIv2::crearVentana);
    }

    static void crearVentana() {
        JFrame ventana = new JFrame("Inventario Noel");
        ventana.setSize(520, 460);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new BorderLayout());

        // ---- Cabecera ----
        JLabel titulo = new JLabel("🍪  Inventario Noel", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(178, 34, 52));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        ventana.add(titulo, BorderLayout.NORTH);

        // ---- Pestañas ----
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pestanas.addTab("📋 Listar", crearPestanaListar());
        pestanas.addTab("🔍 Buscar", crearPestanaBuscar());
        pestanas.addTab("➕ Agregar", crearPestanaAgregar());
        pestanas.addTab("➖ Eliminar", crearPestanaEliminar());
        ventana.add(pestanas, BorderLayout.CENTER);

        // ---- Barra de estado ----
        footer.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ventana.add(footer, BorderLayout.SOUTH);

        actualizarLista();
        ventana.setVisible(true);
    }

    // Refresca la lista visual y el contador cada vez que algo cambia
    static void actualizarLista() {
        modeloLista.clear();
        for (String producto : inventario) {
            modeloLista.addElement(producto);
        }
        footer.setText("Productos registrados: " + inventario.size());
    }

    // ===== Pestaña 1: Listar =====
    static JPanel crearPestanaListar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JList<String> lista = new JList<>(modeloLista);
        lista.setFont(new Font("Consolas", Font.PLAIN, 16));
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panel.add(new JScrollPane(lista), BorderLayout.CENTER);
        return panel;
    }

    // ===== Pestaña 2: Buscar =====
    static JPanel crearPestanaBuscar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton boton = new JButton("Buscar");
        JLabel resultado = new JLabel(" ");
        resultado.setFont(new Font("Segoe UI", Font.BOLD, 14));

        boton.addActionListener(e -> {
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

    // ===== Pestaña 3: Agregar =====
    static JPanel crearPestanaAgregar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton boton = new JButton("Agregar");
        JLabel resultado = new JLabel(" ");
        resultado.setFont(new Font("Segoe UI", Font.BOLD, 14));

        boton.addActionListener(e -> {
            String nuevoProducto = campo.getText().trim();

            if (nuevoProducto.isEmpty()) {
                resultado.setText("⚠️ Escribe un nombre antes de agregar.");
                resultado.setForeground(Color.ORANGE.darker());
                return;
            }

            if (buscarProducto(inventario, nuevoProducto)) {
                resultado.setText("⚠️ '" + nuevoProducto + "' ya existe, no se duplico.");
                resultado.setForeground(Color.ORANGE.darker());
                return;
            }

            inventario.add(nuevoProducto);
            actualizarLista();
            resultado.setText("✅ '" + nuevoProducto + "' agregado correctamente.");
            resultado.setForeground(new Color(0, 128, 0));
            campo.setText("");
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

    // ===== Pestaña 4: Eliminar (nueva) =====
    static JPanel crearPestanaEliminar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton boton = new JButton("Eliminar");
        JLabel resultado = new JLabel(" ");
        resultado.setFont(new Font("Segoe UI", Font.BOLD, 14));

        boton.addActionListener(e -> {
            String nombreEliminar = campo.getText().trim();
            boolean eliminado = inventario.removeIf(p -> p.equals(nombreEliminar));

            if (eliminado) {
                actualizarLista();
                resultado.setText("🗑️ '" + nombreEliminar + "' eliminado correctamente.");
                resultado.setForeground(new Color(0, 128, 0));
                campo.setText("");
            } else {
                resultado.setText("❌ '" + nombreEliminar + "' no se encontro en el inventario.");
                resultado.setForeground(Color.RED);
            }
        });

        panel.add(new JLabel("Nombre del producto a eliminar:"));
        panel.add(Box.createVerticalStrut(8));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(boton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(resultado);
        return panel;
    }

    // ===== Misma logica de siempre, adaptada a List<String> =====
    public static boolean buscarProducto(List<String> lista, String nombreBuscado) {
        for (String producto : lista) {
            if (producto != null && producto.equals(nombreBuscado)) {
                return true;
            }
        }
        return false;
    }
}
