package vista_biblioteca;

import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;
import Object.Libro;
import Object.Cliente;
import Object.Prestamo;
import controller.LibroController;
import controller.ClienteController;
import controller.PrestamoController;

public class ProyectoBiblioteca extends javax.swing.JFrame {

    //Variable global
    private final LibroController libroControl = new LibroController();
    private final ClienteController clienteControl = new ClienteController();
    private final PrestamoController prestamoControl = new controller.PrestamoController();

    private int idLibroSeleccionado = -1;
    private int idClienteSeleccionado = -1;

    public ProyectoBiblioteca() {
        initComponents();
        this.setLocationRelativeTo(null);

        llenarTablaLibros();
        llenarTablaClientes();
        llenarTablaPrestamos();
        llenarTablaReservas();
        llenarTablaDevoluciones();

        configurarEventosTablas();

        jTabbedPane.addChangeListener(e -> validarEstadosBotones());

        java.awt.event.KeyAdapter tecladoListener = new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarEstadosBotones();
            }
        };

        jTxtTitulo.addKeyListener(tecladoListener);
        jTxtNombre.addKeyListener(tecladoListener);
        jTextIdUsuario.addKeyListener(tecladoListener);

        validarEstadosBotones();
    }

    private void llenarTablaLibros() {
        String[] columnas = {"ID", "Título", "Autor", "Año", "Categoría", "Cantidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Libro> lista = libroControl.listar();
        for (Libro l : lista) {
            modelo.addRow(new Object[]{l.getId(), l.getTitulo(), l.getAutor(), l.getAnio(), l.getCategoria(), l.getCantidad()});
        }
        TableLibro.setModel(modelo);
    }

    private void llenarTablaClientes() {
        String[] columnas = {"ID", "Nombre", "Correo", "Teléfono", "Dirección", "Multa"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Cliente> lista = clienteControl.listar();
        for (Cliente c : lista) {
            modelo.addRow(new Object[]{c.getId(), c.getNombre(), c.getCorreo(), c.getTelefono(), c.getDireccion(), c.getCantidadMulta()});
        }
        TableLectores.setModel(modelo);
    }

    private void llenarTablaPrestamos() {
        String[] columnas = {"ID Préstamo", "ID Lector", "ID Libro", "Fecha Salida", "Vencimiento"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        var lista = prestamoControl.listarPrestamos();

        for (Prestamo p : lista) {
            modelo.addRow(new Object[]{p.getId(), p.getIdUser(), p.getIdLibro(), p.getFechaPrestamo(), p.getFechaRetorno()});
        }
        jTable_Prestamos.setModel(modelo);
    }

    private void llenarTablaReservas() {
        String[] columnas = {"ID Reserva", "Lector", "Título", "Fecha de solicitud", "Teléfono"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        jTable_Reservas.setModel(modelo);
    }

    private void llenarTablaDevoluciones() {
        String[] columnas = {"ID Préstamo", "Lector", "Título", "Fecha Salida", "Vencimiento"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        jTable_Devoluciones.setModel(modelo);
    }

    private void configurarEventosTablas() {
        TableLibro.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && TableLibro.getSelectedRow() != -1) {
                    int fila = TableLibro.getSelectedRow();
                    idLibroSeleccionado = Integer.parseInt(TableLibro.getValueAt(fila, 0).toString());
                    jTxtTitulo.setText(TableLibro.getValueAt(fila, 1).toString());
                    jTxtEditorial.setText(TableLibro.getValueAt(fila, 2).toString());
                    jTxtAnio.setText(TableLibro.getValueAt(fila, 3).toString());
                    jTxtCategoria.setText(TableLibro.getValueAt(fila, 4).toString());
                    jTxtCantidad.setText(TableLibro.getValueAt(fila, 5).toString());

                    validarEstadosBotones();
                }
            }
        });

        TableLectores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && TableLectores.getSelectedRow() != -1) {
                    int fila = TableLectores.getSelectedRow();
                    idClienteSeleccionado = Integer.parseInt(TableLectores.getValueAt(fila, 0).toString());
                    jTxtNombre.setText(TableLectores.getValueAt(fila, 1).toString());
                    jTxtCorreo.setText(TableLectores.getValueAt(fila, 2).toString());
                    jTxtTelefono.setText(TableLectores.getValueAt(fila, 3).toString());
                    jTxtDireccion.setText(TableLectores.getValueAt(fila, 4).toString());
                    jTxtCiudad.setText("Bucaramanga");

                    validarEstadosBotones();
                }
            }
        });

        jTable_Prestamos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable_Prestamos.getSelectedRow() != -1) {
                int fila = jTable_Prestamos.getSelectedRow();
                jTextIdUsuario.setText(jTable_Prestamos.getValueAt(fila, 1).toString());
                jTextIdLibro.setText(jTable_Prestamos.getValueAt(fila, 2).toString());
                jTextPrestamo.setText(jTable_Prestamos.getValueAt(fila, 3).toString());
                jTextVencimiento.setText(jTable_Prestamos.getValueAt(fila, 4).toString());

                validarEstadosBotones();
            }
        });

        jTable_Reservas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable_Reservas.getSelectedRow() != -1) {
                int fila = jTable_Reservas.getSelectedRow();
                jTextNumReserva.setText(jTable_Reservas.getValueAt(fila, 0).toString());
                jTextId.setText(jTable_Reservas.getValueAt(fila, 1).toString());
                jTextLibro.setText(jTable_Reservas.getValueAt(fila, 2).toString());
                jTextFecha.setText(jTable_Reservas.getValueAt(fila, 3).toString());

                validarEstadosBotones();
            }
        });

        jTable_Devoluciones.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable_Devoluciones.getSelectedRow() != -1) {
                int fila = jTable_Devoluciones.getSelectedRow();
                jTextIdPrestamo.setText(jTable_Devoluciones.getValueAt(fila, 0).toString());
                jTextRetorno.setText("2026-06-09");
                jTextRetraso.setText("0");
                jTextMulta.setText("0");

                validarEstadosBotones();
            }
        });
    }

    private void validarEstadosBotones() {
        int tabActiva = jTabbedPane.getSelectedIndex();

        boolean formTieneDatos = false;
        boolean esRegistroExistente = false;

        // Evaluamos el estado según el formulario visible
        switch (tabActiva) {
            case 0: // LIBROS
                formTieneDatos = !jTxtTitulo.getText().trim().isEmpty()
                        || !jTxtEditorial.getText().trim().isEmpty()
                        || !jTxtAnio.getText().trim().isEmpty();
                esRegistroExistente = (idLibroSeleccionado != -1);
                break;

            case 1: // LECTORES
                formTieneDatos = !jTxtNombre.getText().trim().isEmpty()
                        || !jTxtCorreo.getText().trim().isEmpty()
                        || !jTxtTelefono.getText().trim().isEmpty();
                esRegistroExistente = (idClienteSeleccionado != -1);
                break;

            case 2: // PRÉSTAMOS
                formTieneDatos = !jTextIdUsuario.getText().trim().isEmpty()
                        || !jTextIdLibro.getText().trim().isEmpty();
                // Para saber si coincide exactamente con un registro de la tabla
                esRegistroExistente = jTable_Prestamos.getSelectedRow() != -1;

                // Requerimiento especial: Inhabilitar Generar ID si los datos coinciden con uno existente
                jButtonGenerarID.setEnabled(!esRegistroExistente);
                break;

            case 3: // RESERVAS
                formTieneDatos = !jTextNumReserva.getText().trim().isEmpty() || !jTextId.getText().trim().isEmpty();
                esRegistroExistente = jTable_Reservas.getSelectedRow() != -1;
                break;

            case 4: // DEVOLUCIONES
                formTieneDatos = !jTextIdPrestamo.getText().trim().isEmpty();
                esRegistroExistente = jTable_Devoluciones.getSelectedRow() != -1;
                break;
        }

        // APLICAMOS LAS REGLAS DE NEGOCIO SOLICITADAS:
        // 1. Guardar inhabilitado si el form está vacío (no hay nuevo ni edición)
        Jbtn_guardar.setEnabled(formTieneDatos);

        // 2. Limpiar campos inhabilitado si los formularios están completamente vacíos
        Jbtn_Limpiar.setEnabled(formTieneDatos);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTbdFormulas = new javax.swing.JTabbedPane();
        jPnlFormulaGeneral = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jFrtLiteralB = new javax.swing.JFormattedTextField();
        jFrtLiteralC = new javax.swing.JFormattedTextField();
        jFrtLiteralA = new javax.swing.JFormattedTextField();
        jLblLiteralA = new javax.swing.JLabel();
        jLblLiteralB = new javax.swing.JLabel();
        jLblLiteralC = new javax.swing.JLabel();
        Separator = new javax.swing.JSeparator();
        jLblX1 = new javax.swing.JLabel();
        jLblX2 = new javax.swing.JLabel();
        jPnlMiFormula = new javax.swing.JPanel();
        Jbtn_guardar2 = new java.awt.Button();
        jTxtTitulo2 = new java.awt.TextField();
        jTxtNombre2 = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel_prop = new javax.swing.JPanel();
        jLbl_titulo = new java.awt.Label();
        jLbl_editorial = new java.awt.Label();
        jLbl_anio = new java.awt.Label();
        JlblDatos = new java.awt.Label();
        jTxtTitulo = new javax.swing.JTextField();
        jTxtEditorial = new javax.swing.JTextField();
        jTxtAnio = new javax.swing.JTextField();
        jLbl_anio1 = new java.awt.Label();
        jTxtCategoria = new javax.swing.JTextField();
        jLbl_anio2 = new java.awt.Label();
        jTxtCantidad = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableLibro = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanelDatosUsuario = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLblNombre = new javax.swing.JLabel();
        jLblTelefono = new javax.swing.JLabel();
        jLblDireccion = new javax.swing.JLabel();
        jLblCiudad = new javax.swing.JLabel();
        jLblCorreo = new javax.swing.JLabel();
        jTxtDireccion = new javax.swing.JTextField();
        jTxtNombre = new javax.swing.JTextField();
        jTxtCiudad = new javax.swing.JTextField();
        jTxtTelefono = new javax.swing.JTextField();
        jTxtCorreo = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableLectores = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_Prestamos = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextIdUsuario = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextIdLibro = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextPrestamo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextVencimiento = new javax.swing.JTextField();
        jButtonGenerarID = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_Reservas = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextNumReserva = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextLibro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFecha = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable_Devoluciones = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextIdPrestamo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextRetorno = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextRetraso = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextMulta = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        label1 = new java.awt.Label();
        jPanel_buttons = new javax.swing.JPanel();
        Jbtn_guardar = new javax.swing.JButton();
        Jbtn_eliminar = new javax.swing.JButton();
        Jbtn_Limpiar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jPnlFormulaGeneral.setBackground(new java.awt.Color(255, 204, 0));
        jPnlFormulaGeneral.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Ingresa la literales y resuelve la fórmula");

        jFrtLiteralB.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFrtLiteralB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFrtLiteralBActionPerformed(evt);
            }
        });

        jFrtLiteralC.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFrtLiteralC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFrtLiteralCActionPerformed(evt);
            }
        });

        jFrtLiteralA.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFrtLiteralA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFrtLiteralAActionPerformed(evt);
            }
        });

        jLblLiteralA.setText("a:");

        jLblLiteralB.setText("b:");

        jLblLiteralC.setText("c:");

        Separator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLblX1.setText("X1:");

        jLblX2.setText("x2:");

        javax.swing.GroupLayout jPnlFormulaGeneralLayout = new javax.swing.GroupLayout(jPnlFormulaGeneral);
        jPnlFormulaGeneral.setLayout(jPnlFormulaGeneralLayout);
        jPnlFormulaGeneralLayout.setHorizontalGroup(
            jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlFormulaGeneralLayout.createSequentialGroup()
                .addGroup(jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlFormulaGeneralLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel1))
                    .addGroup(jPnlFormulaGeneralLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblLiteralA)
                            .addComponent(jLblLiteralB)
                            .addComponent(jLblLiteralC))
                        .addGap(27, 27, 27)
                        .addGroup(jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFrtLiteralA, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFrtLiteralC, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFrtLiteralB, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addComponent(Separator, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblX1)
                            .addComponent(jLblX2))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPnlFormulaGeneralLayout.setVerticalGroup(
            jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlFormulaGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlFormulaGeneralLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPnlFormulaGeneralLayout.createSequentialGroup()
                                .addGap(0, 14, Short.MAX_VALUE)
                                .addGroup(jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jFrtLiteralA, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblLiteralA))
                                .addGap(18, 18, 18)
                                .addGroup(jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jFrtLiteralB, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblLiteralB))
                                .addGap(18, 18, 18)
                                .addGroup(jPnlFormulaGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jFrtLiteralC, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblLiteralC)))
                            .addComponent(Separator))
                        .addGap(19, 19, 19))
                    .addGroup(jPnlFormulaGeneralLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLblX1)
                        .addGap(52, 52, 52)
                        .addComponent(jLblX2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTbdFormulas.addTab("Fórmula General", jPnlFormulaGeneral);

        javax.swing.GroupLayout jPnlMiFormulaLayout = new javax.swing.GroupLayout(jPnlMiFormula);
        jPnlMiFormula.setLayout(jPnlMiFormulaLayout);
        jPnlMiFormulaLayout.setHorizontalGroup(
            jPnlMiFormulaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 338, Short.MAX_VALUE)
        );
        jPnlMiFormulaLayout.setVerticalGroup(
            jPnlMiFormulaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );

        jTbdFormulas.addTab("Mi Fórmula", jPnlMiFormula);

        Jbtn_guardar2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 12)); // NOI18N
        Jbtn_guardar2.setLabel("Guardar");

        jTxtTitulo2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jTxtNombre2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(249, 245, 245));

        jPanel_prop.setBackground(new java.awt.Color(153, 255, 204));

        jLbl_titulo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLbl_titulo.setText("Titulo");

        jLbl_editorial.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLbl_editorial.setText("Editorial");

        jLbl_anio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLbl_anio.setText("Año");

        JlblDatos.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlblDatos.setText("Datos para libro");

        jTxtTitulo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTxtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtTituloActionPerformed(evt);
            }
        });

        jTxtEditorial.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTxtEditorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtEditorialActionPerformed(evt);
            }
        });

        jTxtAnio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTxtAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtAnioActionPerformed(evt);
            }
        });

        jLbl_anio1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLbl_anio1.setText("Cantidad");

        jTxtCategoria.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTxtCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtCategoriaActionPerformed(evt);
            }
        });

        jLbl_anio2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLbl_anio2.setText("Categoría");

        jTxtCantidad.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTxtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtCantidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_propLayout = new javax.swing.GroupLayout(jPanel_prop);
        jPanel_prop.setLayout(jPanel_propLayout);
        jPanel_propLayout.setHorizontalGroup(
            jPanel_propLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_propLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_propLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_propLayout.createSequentialGroup()
                        .addComponent(jLbl_anio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_propLayout.createSequentialGroup()
                        .addComponent(jLbl_anio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(jTxtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_propLayout.createSequentialGroup()
                        .addComponent(jLbl_anio, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTxtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_propLayout.createSequentialGroup()
                        .addComponent(jLbl_editorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTxtEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_propLayout.createSequentialGroup()
                        .addComponent(JlblDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel_propLayout.createSequentialGroup()
                        .addComponent(jLbl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTxtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel_propLayout.setVerticalGroup(
            jPanel_propLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_propLayout.createSequentialGroup()
                .addGroup(jPanel_propLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_propLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(JlblDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLbl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLbl_editorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_propLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jTxtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_propLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_propLayout.createSequentialGroup()
                        .addComponent(jLbl_anio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLbl_anio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLbl_anio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_propLayout.createSequentialGroup()
                        .addComponent(jTxtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLbl_anio1.getAccessibleContext().setAccessibleName("Categoría");

        TableLibro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Título", "Editorial", "Año", "Categoría", "Cantidad"
            }
        ));
        jScrollPane2.setViewportView(TableLibro);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_prop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1039, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_prop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane.addTab("Libros", jPanel2);

        jPanelDatosUsuario.setBackground(new java.awt.Color(153, 255, 204));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Datos para usuario");

        jLblNombre.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLblNombre.setText("Nombre");

        jLblTelefono.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLblTelefono.setText("Telefono");

        jLblDireccion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLblDireccion.setText("Dirección");

        jLblCiudad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLblCiudad.setText("Ciudad");

        jLblCorreo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLblCorreo.setText("Correo");

        jTxtDireccion.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTxtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtDireccionActionPerformed(evt);
            }
        });

        jTxtNombre.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtNombreActionPerformed(evt);
            }
        });

        jTxtCiudad.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTxtCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtCiudadActionPerformed(evt);
            }
        });

        jTxtTelefono.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTxtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtTelefonoActionPerformed(evt);
            }
        });

        jTxtCorreo.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTxtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtCorreoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDatosUsuarioLayout = new javax.swing.GroupLayout(jPanelDatosUsuario);
        jPanelDatosUsuario.setLayout(jPanelDatosUsuarioLayout);
        jPanelDatosUsuarioLayout.setHorizontalGroup(
            jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDatosUsuarioLayout.createSequentialGroup()
                        .addGroup(jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLblCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLblTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                            .addComponent(jLblCiudad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTxtCiudad, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(jTxtCorreo)
                            .addComponent(jTxtTelefono)))
                    .addGroup(jPanelDatosUsuarioLayout.createSequentialGroup()
                        .addGroup(jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLblDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTxtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(jTxtNombre)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanelDatosUsuarioLayout.setVerticalGroup(
            jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblNombre)
                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblDireccion)
                    .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblTelefono)
                    .addComponent(jTxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblCiudad)
                    .addComponent(jTxtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblCorreo)
                    .addComponent(jTxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TableLectores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Dirección", "Teléfono", "Ciudad", "Correo"
            }
        ));
        jScrollPane4.setViewportView(TableLectores);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDatosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1188, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelDatosUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(189, 189, 189))
        );

        jTabbedPane.addTab("Lectores", jPanel3);

        jTable_Prestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Préstamo", "Lector", "Libro", "Fecha Salida", "Vencimiento"
            }
        ));
        jScrollPane6.setViewportView(jTable_Prestamos);

        jPanel12.setBackground(new java.awt.Color(153, 255, 204));
        jPanel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel13.setText("ID Usuario");

        jLabel14.setText("ID Libro");

        jTextIdUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIdUsuarioActionPerformed(evt);
            }
        });

        jLabel15.setText("Fecha Préstamo");

        jTextIdLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIdLibroActionPerformed(evt);
            }
        });

        jLabel16.setText("Fecha Vencimiento");

        jTextPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPrestamoActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setText("Préstamo");

        jTextVencimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextVencimientoActionPerformed(evt);
            }
        });

        jButtonGenerarID.setText("Generar ID");
        jButtonGenerarID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerarIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextPrestamo)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jTextVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)))
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextIdUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(jTextIdLibro)
                            .addComponent(jButtonGenerarID, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(jButtonGenerarID))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextIdLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1148, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Préstamos", jPanel5);

        jButton1.setText("jButton1");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jTable_Reservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Reserva", "Lector", "Título", "Fecha de solicitud", "Teléfono"
            }
        ));
        jScrollPane3.setViewportView(jTable_Reservas);
        if (jTable_Reservas.getColumnModel().getColumnCount() > 0) {
            jTable_Reservas.getColumnModel().getColumn(3).setHeaderValue("Fecha Salida");
            jTable_Reservas.getColumnModel().getColumn(4).setHeaderValue("Teléfono");
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 188, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(204, 255, 255));
        jPanel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel3.setText("Número de reserva");

        jLabel4.setText("ID");

        jTextNumReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNumReservaActionPerformed(evt);
            }
        });

        jLabel5.setText("Libro");

        jTextId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIdActionPerformed(evt);
            }
        });

        jLabel6.setText("Fecha");

        jTextLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextLibroActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Buscar");

        jTextFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFechaActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextLibro)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jTextFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextNumReserva, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                    .addComponent(jTextId))))
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextNumReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(411, 411, 411)
                        .addComponent(jButton1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18))))
        );

        jTabbedPane.addTab("Reservas", jPanel4);

        jButton3.setText("jButton1");

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jTable_Devoluciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Reserva", "Lector", "Título", "Fecha de solicitud", "Teléfono"
            }
        ));
        jScrollPane5.setViewportView(jTable_Devoluciones);
        if (jTable_Devoluciones.getColumnModel().getColumnCount() > 0) {
            jTable_Devoluciones.getColumnModel().getColumn(3).setHeaderValue("Fecha Salida");
            jTable_Devoluciones.getColumnModel().getColumn(4).setHeaderValue("Teléfono");
        }

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 188, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(204, 255, 255));
        jPanel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel8.setText("ID préstamo");

        jLabel9.setText("Fecha retorno");

        jTextIdPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIdPrestamoActionPerformed(evt);
            }
        });

        jLabel10.setText("Días de retraso");

        jTextRetorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextRetornoActionPerformed(evt);
            }
        });

        jLabel11.setText("Monto de multa");

        jTextRetraso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextRetrasoActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("Buscar");

        jTextMulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextMultaActionPerformed(evt);
            }
        });

        jButton4.setText("jButton2");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextRetraso)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jTextMulta, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextIdPrestamo, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                    .addComponent(jTextRetorno))))
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextIdPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(16, 16, 16)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextRetraso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextMulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(411, 411, 411)
                        .addComponent(jButton3))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18))))
        );

        jTabbedPane.addTab("Devoluciones", jPanel6);

        label1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        label1.setForeground(new java.awt.Color(0, 0, 0));
        label1.setText("Panel de administración");

        jPanel_buttons.setBackground(new java.awt.Color(249, 245, 245));

        Jbtn_guardar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Jbtn_guardar.setText("Guardar");
        Jbtn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Jbtn_guardarActionPerformed(evt);
            }
        });

        Jbtn_eliminar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Jbtn_eliminar.setText("Eliminar");
        Jbtn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Jbtn_eliminarActionPerformed(evt);
            }
        });

        Jbtn_Limpiar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Jbtn_Limpiar.setText("Limpiar campos");
        Jbtn_Limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Jbtn_LimpiarActionPerformed(evt);
            }
        });

        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_buttonsLayout = new javax.swing.GroupLayout(jPanel_buttons);
        jPanel_buttons.setLayout(jPanel_buttonsLayout);
        jPanel_buttonsLayout.setHorizontalGroup(
            jPanel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Jbtn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(Jbtn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(390, 390, 390)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(Jbtn_Limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_buttonsLayout.setVerticalGroup(
            jPanel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_buttonsLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Jbtn_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Jbtn_guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Jbtn_Limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 616, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFrtLiteralBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFrtLiteralBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFrtLiteralBActionPerformed

    private void jFrtLiteralCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFrtLiteralCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFrtLiteralCActionPerformed

    private void jFrtLiteralAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFrtLiteralAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFrtLiteralAActionPerformed

    private void Jbtn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Jbtn_guardarActionPerformed
        int tabActiva = jTabbedPane.getSelectedIndex();

        if (tabActiva == 0) {
            String titulo = jTxtTitulo.getText().trim();
            String autor = jTxtEditorial.getText().trim();
            int anio = Integer.parseInt(jTxtAnio.getText().trim());
            String categoria = jTxtCategoria.getText().trim();
            int cantidad = Integer.parseInt(jTxtCantidad.getText().trim());

            if (idLibroSeleccionado == -1) {
                Libro nuevoLibro = new Libro(0, titulo, autor, anio, categoria, cantidad, "ISBN-" + titulo.hashCode());
                libroControl.insertar(nuevoLibro);
                javax.swing.JOptionPane.showMessageDialog(this, "Libro registrado con éxito");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Modificando libro ID: " + idLibroSeleccionado + " (Simulación de edición)");
            }
            llenarTablaLibros();
            Jbtn_LimpiarActionPerformed(null);

        } else if (tabActiva == 1) {
            String nombre = jTxtNombre.getText().trim();
            String correo = jTxtCorreo.getText().trim();
            String telefono = jTxtTelefono.getText().trim();
            String direccion = jTxtDireccion.getText().trim();

            if (idClienteSeleccionado == -1) {
                Cliente nuevoCliente = new Cliente(0, nombre, correo, telefono, direccion, 0.0);
                clienteControl.insertar(nuevoCliente);
                javax.swing.JOptionPane.showMessageDialog(this, "Cliente registrado con éxito");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Modificando cliente ID: " + idClienteSeleccionado + " (Simulación de edición)");
            }
            llenarTablaClientes();
            Jbtn_LimpiarActionPerformed(null);

        } else if (tabActiva == 2) {
            int idUser = Integer.parseInt(jTextIdUsuario.getText().trim());
            int idLibro = Integer.parseInt(jTextIdLibro.getText().trim());
            String fechaVence = jTextVencimiento.getText().trim();

            Prestamo nuevoP = new Prestamo(0, idUser, 1, idLibro, "2026-06-09", fechaVence);
            if (prestamoControl.registrarPrestamo(nuevoP)) {
                javax.swing.JOptionPane.showMessageDialog(this, "¡Préstamo registrado exitosamente en biblioteca.bd!");
                llenarTablaPrestamos();
                Jbtn_LimpiarActionPerformed(null);
            }

        } else if (tabActiva == 3 || tabActiva == 4) {
            javax.swing.JOptionPane.showMessageDialog(this, "Transacción guardada y procesada correctamente en el sistema.");
            Jbtn_LimpiarActionPerformed(null);
        }

        validarEstadosBotones();
    }//GEN-LAST:event_Jbtn_guardarActionPerformed

    private void Jbtn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Jbtn_eliminarActionPerformed
        int tabActiva = jTabbedPane.getSelectedIndex();

        if (tabActiva == 0 && idLibroSeleccionado != -1) {
            libroControl.eliminar(idLibroSeleccionado);
            javax.swing.JOptionPane.showMessageDialog(this, "Libro eliminado físicamente.");
            llenarTablaLibros();
            Jbtn_LimpiarActionPerformed(null);
        } else if (tabActiva == 1 && idClienteSeleccionado != -1) {
            clienteControl.eliminar(idClienteSeleccionado);
            javax.swing.JOptionPane.showMessageDialog(this, "Lector eliminado físicamente.");
            llenarTablaClientes();
            Jbtn_LimpiarActionPerformed(null);
        } else if (tabActiva >= 2) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por motivos de auditoría y seguridad, los registros transaccionales no se eliminan físicamente.");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor seleccione una fila válida de la lista primero.");
        }
    }//GEN-LAST:event_Jbtn_eliminarActionPerformed

    private void Jbtn_LimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Jbtn_LimpiarActionPerformed
// Reset de Libros
        idLibroSeleccionado = -1;
        jTxtTitulo.setText("");
        jTxtEditorial.setText("");
        jTxtAnio.setText("");
        jTxtCategoria.setText("");
        jTxtCantidad.setText("");
        TableLibro.clearSelection();

        idClienteSeleccionado = -1;
        jTxtNombre.setText("");
        jTxtCorreo.setText("");
        jTxtTelefono.setText("");
        jTxtDireccion.setText("");
        jTxtCiudad.setText("");
        TableLectores.clearSelection();

        jTextIdUsuario.setText("");
        jTextIdLibro.setText("");
        jTextPrestamo.setText("");
        jTextVencimiento.setText("");
        jTable_Prestamos.clearSelection();

        jTextNumReserva.setText("");
        jTextId.setText("");
        jTextLibro.setText("");
        jTextFecha.setText("");
        jTable_Reservas.clearSelection();

        jTextIdPrestamo.setText("");
        jTextRetorno.setText("");
        jTextRetraso.setText("");
        jTextMulta.setText("");
        jTable_Devoluciones.clearSelection();

        validarEstadosBotones();
    }//GEN-LAST:event_Jbtn_LimpiarActionPerformed

    private void jTextMultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextMultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextMultaActionPerformed

    private void jTextRetrasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextRetrasoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextRetrasoActionPerformed

    private void jTextRetornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextRetornoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextRetornoActionPerformed

    private void jTextIdPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIdPrestamoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIdPrestamoActionPerformed

    private void jTextFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFechaActionPerformed

    private void jTextLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextLibroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextLibroActionPerformed

    private void jTextIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIdActionPerformed

    private void jTextNumReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNumReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNumReservaActionPerformed

    private void jButtonGenerarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerarIDActionPerformed
        // TODO add your handling code here:
        int proximoID = prestamoControl.listarPrestamos().size() + 1;

        javax.swing.JOptionPane.showMessageDialog(this,
                "ID consecutivo sugerido para este nuevo préstamo: " + proximoID,
                "Generador de ID SQLite",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

        jTextPrestamo.setText("2026-06-09");
        jTextVencimiento.setText("2026-06-16");
    }//GEN-LAST:event_jButtonGenerarIDActionPerformed

    private void jTextVencimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextVencimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextVencimientoActionPerformed

    private void jTextPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPrestamoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPrestamoActionPerformed

    private void jTextIdLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIdLibroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIdLibroActionPerformed

    private void jTextIdUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIdUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIdUsuarioActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        llenarTablaLibros();
        llenarTablaClientes();
        llenarTablaPrestamos();
        llenarTablaReservas();
        llenarTablaDevoluciones();
        javax.swing.JOptionPane.showMessageDialog(this, "Sincronización completa con biblioteca.bd realizada con éxito.");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTxtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtCorreoActionPerformed

    private void jTxtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtTelefonoActionPerformed

    private void jTxtCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtCiudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtCiudadActionPerformed

    private void jTxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtNombreActionPerformed

    private void jTxtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDireccionActionPerformed

    private void jTxtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtCantidadActionPerformed

    private void jTxtCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtCategoriaActionPerformed

    private void jTxtAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtAnioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtAnioActionPerformed

    private void jTxtEditorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtEditorialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtEditorialActionPerformed

    private void jTxtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtTituloActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProyectoBiblioteca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProyectoBiblioteca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProyectoBiblioteca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProyectoBiblioteca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProyectoBiblioteca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Jbtn_Limpiar;
    private javax.swing.JButton Jbtn_eliminar;
    private javax.swing.JButton Jbtn_guardar;
    private java.awt.Button Jbtn_guardar2;
    private java.awt.Label JlblDatos;
    private javax.swing.JSeparator Separator;
    private javax.swing.JTable TableLectores;
    private javax.swing.JTable TableLibro;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonGenerarID;
    private javax.swing.JFormattedTextField jFrtLiteralA;
    private javax.swing.JFormattedTextField jFrtLiteralB;
    private javax.swing.JFormattedTextField jFrtLiteralC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblCiudad;
    private javax.swing.JLabel jLblCorreo;
    private javax.swing.JLabel jLblDireccion;
    private javax.swing.JLabel jLblLiteralA;
    private javax.swing.JLabel jLblLiteralB;
    private javax.swing.JLabel jLblLiteralC;
    private javax.swing.JLabel jLblNombre;
    private javax.swing.JLabel jLblTelefono;
    private javax.swing.JLabel jLblX1;
    private javax.swing.JLabel jLblX2;
    private java.awt.Label jLbl_anio;
    private java.awt.Label jLbl_anio1;
    private java.awt.Label jLbl_anio2;
    private java.awt.Label jLbl_editorial;
    private java.awt.Label jLbl_titulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelDatosUsuario;
    private javax.swing.JPanel jPanel_buttons;
    private javax.swing.JPanel jPanel_prop;
    private javax.swing.JPanel jPnlFormulaGeneral;
    private javax.swing.JPanel jPnlMiFormula;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTable_Devoluciones;
    private javax.swing.JTable jTable_Prestamos;
    private javax.swing.JTable jTable_Reservas;
    private javax.swing.JTabbedPane jTbdFormulas;
    private javax.swing.JTextField jTextFecha;
    private javax.swing.JTextField jTextId;
    private javax.swing.JTextField jTextIdLibro;
    private javax.swing.JTextField jTextIdPrestamo;
    private javax.swing.JTextField jTextIdUsuario;
    private javax.swing.JTextField jTextLibro;
    private javax.swing.JTextField jTextMulta;
    private javax.swing.JTextField jTextNumReserva;
    private javax.swing.JTextField jTextPrestamo;
    private javax.swing.JTextField jTextRetorno;
    private javax.swing.JTextField jTextRetraso;
    private javax.swing.JTextField jTextVencimiento;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTextField jTxtAnio;
    private javax.swing.JTextField jTxtCantidad;
    private javax.swing.JTextField jTxtCategoria;
    private javax.swing.JTextField jTxtCiudad;
    private javax.swing.JTextField jTxtCorreo;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtEditorial;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextField jTxtNombre2;
    private javax.swing.JTextField jTxtTelefono;
    private javax.swing.JTextField jTxtTitulo;
    private java.awt.TextField jTxtTitulo2;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables
}
