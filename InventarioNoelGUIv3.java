import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioNoelGUIv3 {

    static List<String> inventario = new ArrayList<>(List.of("ducales", "festival", "tosh"));
    static DefaultListModel<String> modeloLista = new DefaultListModel<>();
    static JLabel footer = new JLabel();

    // Paleta de colores de la app
    static final Color ROJO_NOEL = new Color(178, 34, 52);
    static final Color ROJO_OSCURO = new Color(120, 20, 35);
    static final Color VERDE_OK = new Color(46, 139, 87);
    static final Color GRIS_FONDO = new Color(245, 245, 247);
    static final Color GRIS_TEXTO = new Color(140, 140, 140);
    static final Font FUENTE_BASE = new Font("Segoe UI", Font.PLAIN, 14);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // seguimos con el look por defecto si Nimbus no esta disponible
        }
        SwingUtilities.invokeLater(InventarioNoelGUIv3::crearVentana);
    }

    static void crearVentana() {
        JFrame ventana = new JFrame("Inventario Noel");
        ventana.setSize(540, 480);
        ventana.setMinimumSize(new Dimension(460, 420));
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new BorderLayout());

        ventana.add(crearCabecera(), BorderLayout.NORTH);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pestanas.setBackground(Color.WHITE);
        pestanas.addTab("📋  Listar", crearPestanaListar());
        pestanas.addTab("🔍  Buscar", crearPestanaBuscar());
        pestanas.addTab("➕  Agregar", crearPestanaAgregar());
        pestanas.addTab("➖  Eliminar", crearPestanaEliminar());
        ventana.add(pestanas, BorderLayout.CENTER);

        footer.setBorder(new EmptyBorder(8, 16, 8, 16));
        footer.setFont(FUENTE_BASE);
        footer.setForeground(GRIS_TEXTO);
        footer.setBackground(GRIS_FONDO);
        footer.setOpaque(true);
        ventana.add(footer, BorderLayout.SOUTH);

        actualizarLista();
        ventana.setVisible(true);
    }

    // ---- Cabecera con degradado en vez de color plano ----
    static JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint degradado = new GradientPaint(
                        0, 0, ROJO_NOEL, getWidth(), getHeight(), ROJO_OSCURO);
                g2.setPaint(degradado);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        cabecera.setPreferredSize(new Dimension(0, 70));
        cabecera.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("🍪  Inventario Noel");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Gestion basica de productos en bodega");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(255, 255, 255, 200));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(titulo);
        textos.add(subtitulo);

        cabecera.add(textos, BorderLayout.WEST);
        return cabecera;
    }

    static void actualizarLista() {
        modeloLista.clear();
        for (String producto : inventario) {
            modeloLista.addElement(producto);
        }
        footer.setText("●  " + inventario.size() + " producto(s) registrado(s)");
    }

    // ===== Pestaña 1: Listar =====
    static JPanel crearPestanaListar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JList<String> lista = new JList<>(modeloLista);
        lista.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lista.setFixedCellHeight(36);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setCellRenderer(new RendererProducto());

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225)));

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // Renderer con filas alternadas y un icono antes del nombre
    static class RendererProducto extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                        boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, "🍪  " + value, index, isSelected, cellHasFocus);
            label.setBorder(new EmptyBorder(6, 12, 6, 12));
            if (!isSelected) {
                label.setBackground(index % 2 == 0 ? Color.WHITE : new Color(250, 245, 245));
            } else {
                label.setBackground(new Color(178, 34, 52, 60));
            }
            label.setOpaque(true);
            return label;
        }
    }

    // ===== Pestaña 2: Buscar =====
    static JPanel crearPestanaBuscar() {
        return crearPestanaConCampo(
                "Buscar producto",
                "Ej: festival",
                "Buscar",
                ROJO_NOEL,
                (campo, resultado) -> {
                    String nombreBuscado = campo.getTexto().trim();
                    boolean encontrado = buscarProducto(inventario, nombreBuscado);
                    if (encontrado) {
                        mostrarExito(resultado, "'" + nombreBuscado + "' SI existe en el inventario.");
                    } else {
                        mostrarError(resultado, "'" + nombreBuscado + "' NO existe en el inventario.");
                    }
                });
    }

    // ===== Pestaña 3: Agregar =====
    static JPanel crearPestanaAgregar() {
        return crearPestanaConCampo(
                "Agregar producto",
                "Ej: nucita",
                "Agregar",
                VERDE_OK,
                (campo, resultado) -> {
                    String nuevoProducto = campo.getTexto().trim();
                    if (nuevoProducto.isEmpty()) {
                        mostrarAdvertencia(resultado, "Escribe un nombre antes de agregar.");
                        return;
                    }
                    if (buscarProducto(inventario, nuevoProducto)) {
                        mostrarAdvertencia(resultado, "'" + nuevoProducto + "' ya existe, no se duplico.");
                        return;
                    }
                    inventario.add(nuevoProducto);
                    actualizarLista();
                    mostrarExito(resultado, "'" + nuevoProducto + "' agregado correctamente.");
                    campo.limpiar();
                });
    }

    // ===== Pestaña 4: Eliminar =====
    static JPanel crearPestanaEliminar() {
        return crearPestanaConCampo(
                "Eliminar producto",
                "Ej: tosh",
                "Eliminar",
                new Color(200, 90, 40),
                (campo, resultado) -> {
                    String nombreEliminar = campo.getTexto().trim();
                    boolean eliminado = inventario.removeIf(p -> p.equals(nombreEliminar));
                    if (eliminado) {
                        actualizarLista();
                        mostrarExito(resultado, "'" + nombreEliminar + "' eliminado correctamente.");
                        campo.limpiar();
                    } else {
                        mostrarError(resultado, "'" + nombreEliminar + "' no se encontro en el inventario.");
                    }
                });
    }

    // ---- Plantilla reutilizada por Buscar / Agregar / Eliminar para que se vean iguales ----
    interface AccionConCampo {
        void ejecutar(CampoConPlaceholder campo, JLabel resultado);
    }

    static JPanel crearPestanaConCampo(String titulo, String placeholder, String textoBoton,
                                        Color colorBoton, AccionConCampo accion) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(6, 30, 6, 30);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel etiqueta = new JLabel(titulo);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 18));
        etiqueta.setForeground(new Color(60, 60, 60));

        CampoConPlaceholder campo = new CampoConPlaceholder(placeholder);
        campo.setFont(FUENTE_BASE);
        campo.setPreferredSize(new Dimension(0, 38));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                new EmptyBorder(6, 10, 6, 10)));

        BotonRedondeado boton = new BotonRedondeado(textoBoton, colorBoton);

        JLabel resultado = new JLabel(" ");
        resultado.setFont(new Font("Segoe UI", Font.BOLD, 13));
        resultado.setHorizontalAlignment(SwingConstants.CENTER);

        boton.addActionListener(e -> accion.ejecutar(campo, resultado));
        campo.addActionListener(e -> accion.ejecutar(campo, resultado)); // Enter tambien funciona

        c.gridy = 0;
        c.insets = new Insets(30, 30, 15, 30);
        panel.add(etiqueta, c);
        c.gridy = 1;
        c.insets = new Insets(6, 30, 6, 30);
        panel.add(campo, c);
        c.gridy = 2;
        c.insets = new Insets(15, 100, 10, 100);
        panel.add(boton, c);
        c.gridy = 3;
        c.insets = new Insets(15, 30, 6, 30);
        panel.add(resultado, c);

        return panel;
    }

    static void mostrarExito(JLabel resultado, String texto) {
        resultado.setText("✅ " + texto);
        resultado.setForeground(VERDE_OK);
    }

    static void mostrarError(JLabel resultado, String texto) {
        resultado.setText("❌ " + texto);
        resultado.setForeground(new Color(200, 50, 50));
    }

    static void mostrarAdvertencia(JLabel resultado, String texto) {
        resultado.setText("⚠️ " + texto);
        resultado.setForeground(new Color(200, 140, 20));
    }

    // ---- Boton con esquinas redondeadas y color solido ----
    static class BotonRedondeado extends JButton {
        Color colorFondo;

        BotonRedondeado(String texto, Color color) {
            super(texto);
            this.colorFondo = color;
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(120, 40));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isPressed() ? colorFondo.darker() : colorFondo);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ---- Campo de texto con texto de ejemplo (placeholder) en gris ----
    static class CampoConPlaceholder extends JTextField {
        String placeholder;

        CampoConPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        String getTexto() {
            return getText();
        }

        void limpiar() {
            setText("");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GRIS_TEXTO);
                g2.setFont(getFont());
                Insets insets = getInsets();
                g2.drawString(placeholder, insets.left, getHeight() / 2 + getFont().getSize() / 2 - 2);
                g2.dispose();
            }
        }
    }

    // ===== Misma logica de siempre =====
    public static boolean buscarProducto(List<String> lista, String nombreBuscado) {
        for (String producto : lista) {
            if (producto != null && producto.equals(nombreBuscado)) {
                return true;
            }
        }
        return false;
    }
}
