import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class BTreeGraphVisualizerUltra<E extends Comparable<E>> {
    private BTree<E> btree;
    private Graph graph;
    private AtomicInteger nodeCounter;
    private Viewer viewer;
    private ViewPanel viewPanel;
    private JFrame frame;
    
    private static final String STYLE_SHEET = 
        "graph {" +
        "    padding: 120px;" +
        "}" +
        "node {" +
        "    fill-color: white;" +
        "    stroke-mode: plain;" +
        "    stroke-color: black;" +
        "    stroke-width: 3px;" +
        "    text-alignment: center;" +
        "    text-style: bold;" +
        "    text-size: 28px;" +
        "    text-color: black;" +
        "    size: 200px, 70px;" +
        "    shape: box;" +
        "    text-background-mode: none;" +
        "    text-padding: 12px;" +
        "}" +
        "node.root {" +
        "    fill-color: #E8F5E8;" +
        "    stroke-color: #2E7D32;" +
        "    stroke-width: 4px;" +
        "    text-size: 30px;" +
        "}" +
        "node.internal {" +
        "    fill-color: #E3F2FD;" +
        "    stroke-color: #1976D2;" +
        "    stroke-width: 3px;" +
        "    text-size: 28px;" +
        "}" +
        "node.leaf {" +
        "    fill-color: #FFF3E0;" +
        "    stroke-color: #F57C00;" +
        "    stroke-width: 4px;" +
        "    text-size: 28px;" +
        "}" +
        "edge {" +
        "    stroke-color: #424242;" +
        "    stroke-width: 2px;" +
        "    arrow-shape: none;" +
        "}";
    
    public BTreeGraphVisualizerUltra(BTree<E> btree) {
        this.btree = btree;
        this.nodeCounter = new AtomicInteger(0);
        initializeGraph();
    }
    
    private void initializeGraph() {
        System.setProperty("org.graphstream.ui", "swing");
        this.graph = new SingleGraph("BTree Ultra Complete Visualization");
        this.graph.setAttribute("ui.stylesheet", STYLE_SHEET);
        this.graph.setAttribute("ui.quality");
        this.graph.setAttribute("ui.antialias");
    }
    
    public void visualizeTree() {
        if (btree.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El árbol está vacío", 
                                        "Árbol B Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        graph.clear();
        nodeCounter.set(0);
        buildUltraCompleteLayout();
        displayUltraCompleteGraph();
    }
    
    private void buildUltraCompleteLayout() {
        if (btree.isEmpty()) return;
        
        try {
            java.lang.reflect.Field rootField = btree.getClass().getDeclaredField("root");
            rootField.setAccessible(true);
            BNode<E> root = (BNode<E>) rootField.get(btree);
            
            if (root != null) {
                // Calcular dimensiones COMPLETAS del árbol
                int treeHeight = calculateTreeHeight(root);
                int[] levelWidths = calculateLevelWidths(root, treeHeight);
                
                // Calcular ancho total GENEROSO para ver TODO
                int maxLevelWidth = 0;
                for (int width : levelWidths) {
                    maxLevelWidth = Math.max(maxLevelWidth, width);
                }
                
                // ANCHO EXTRA GENEROSO para que se vea TODO el árbol
                double totalWidth = Math.max(2000, maxLevelWidth * 400);
                buildUltraCompleteRecursive(root, null, 0, totalWidth / 2, totalWidth, levelWidths);
            }
        } catch (Exception e) {
            System.err.println("Error accediendo a la raíz del árbol: " + e.getMessage());
            createSimplifiedVisualization();
        }
    }
    
    private int calculateTreeHeight(BNode<E> node) {
        if (node == null) return 0;
        int maxChildHeight = 0;
        for (int i = 0; i <= node.count; i++) {
            if (node.childs.get(i) != null) {
                maxChildHeight = Math.max(maxChildHeight, calculateTreeHeight(node.childs.get(i)));
            }
        }
        return 1 + maxChildHeight;
    }
    
    private int[] calculateLevelWidths(BNode<E> node, int totalHeight) {
        int[] widths = new int[totalHeight];
        calculateLevelWidthsRecursive(node, 0, widths);
        return widths;
    }
    
    private void calculateLevelWidthsRecursive(BNode<E> node, int level, int[] widths) {
        if (node == null) return;
        widths[level]++;
        for (int i = 0; i <= node.count; i++) {
            if (node.childs.get(i) != null) {
                calculateLevelWidthsRecursive(node.childs.get(i), level + 1, widths);
            }
        }
    }
    
    private String buildUltraCompleteRecursive(BNode<E> node, String parentId, int level, 
                                             double centerX, double totalWidth, int[] levelWidths) {
        if (node == null) return null;
        
        String nodeId = "node_" + nodeCounter.incrementAndGet();
        
        // Crear etiqueta con números EXTRA GRANDES
        StringBuilder label = new StringBuilder();
        for (int i = 0; i < node.count; i++) {
            if (i > 0) label.append("  |  ");
            label.append(node.keys.get(i));
        }
        
        Node graphNode = graph.addNode(nodeId);
        graphNode.setAttribute("ui.label", label.toString());
        
        // Posicionamiento con MÁXIMO ESPACIO VERTICAL
        double x = centerX;
        double y = -level * 200; // MUCHO más espacio vertical
        
        graphNode.setAttribute("xyz", x, y, 0);
        graphNode.setAttribute("layout.frozen");
        
        // Establecer estilo según el tipo de nodo
        if (parentId == null) {
            graphNode.setAttribute("ui.class", "root");
        } else if (node.isLeaf()) {
            graphNode.setAttribute("ui.class", "leaf");
        } else {
            graphNode.setAttribute("ui.class", "internal");
        }
        
        // Conectar con el padre
        if (parentId != null) {
            String edgeId = parentId + "_to_" + nodeId;
            graph.addEdge(edgeId, parentId, nodeId, true);
        }
        
        // Procesar hijos con MÁXIMO ESPACIO HORIZONTAL
        if (!node.isLeaf()) {
            int realChildren = 0;
            for (int i = 0; i <= node.count; i++) {
                if (node.childs.get(i) != null) {
                    realChildren++;
                }
            }
            
            if (realChildren > 0) {
                // ESPACIADO ULTRA GENEROSO para que se vea TODO
                double levelWidth = realChildren * 400; // Espacio máximo
                double childSpacing = levelWidth / realChildren;
                double startX = centerX - (levelWidth / 2) + (childSpacing / 2);
                
                int childIndex = 0;
                for (int i = 0; i <= node.count; i++) {
                    if (node.childs.get(i) != null) {
                        double childX = startX + (childIndex * childSpacing);
                        buildUltraCompleteRecursive(node.childs.get(i), nodeId, level + 1, 
                                                  childX, totalWidth, levelWidths);
                        childIndex++;
                    }
                }
            }
        }
        
        return nodeId;
    }
    
    private void createSimplifiedVisualization() {
        Node simpleNode = graph.addNode("simple");
        simpleNode.setAttribute("ui.label", "Árbol B\\n(Ver consola para estructura detallada)");
        simpleNode.setAttribute("ui.class", "root");
        simpleNode.setAttribute("xyz", 1000, 0, 0);
        simpleNode.setAttribute("layout.frozen");
    }
    
    private void displayUltraCompleteGraph() {
        viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.disableAutoLayout();
        
        viewPanel = (ViewPanel) viewer.addDefaultView(false);
        viewPanel.setPreferredSize(new Dimension(1700, 1100)); // Ventana EXTRA GRANDE
        
        frame = new JFrame("Árbol B - Visualización Ultra Completa");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                forceCloseAll();
            }
        });
        
        frame.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(viewPanel, BorderLayout.CENTER);
        
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        frame.setSize(1700, 1100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Centrar vista para ver TODO el árbol
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(1500); // Más tiempo para renderizar completamente
                centerViewToShowCompleteTree();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        showTreeInfo();
    }
    
    private void centerViewToShowCompleteTree() {
        try {
            if (viewPanel != null && viewPanel.getCamera() != null) {
                // Encontrar TODOS los nodos para calcular bounds completos
                double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
                double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
                
                for (Node node : graph) {
                    Object[] xyz = (Object[]) node.getAttribute("xyz");
                    if (xyz != null && xyz.length >= 2) {
                        double x = ((Number) xyz[0]).doubleValue();
                        double y = ((Number) xyz[1]).doubleValue();
                        
                        minX = Math.min(minX, x);
                        maxX = Math.max(maxX, x);
                        minY = Math.min(minY, y);
                        maxY = Math.max(maxY, y);
                    }
                }
                
                if (minX != Double.MAX_VALUE) {
                    double centerX = (minX + maxX) / 2;
                    double centerY = (minY + maxY) / 2;
                    
                    viewPanel.getCamera().setViewCenter(centerX, centerY, 0);
                    
                    // Ajustar zoom para ver TODO el árbol COMPLETO
                    double width = maxX - minX + 500; // Padding generoso
                    double height = maxY - minY + 400;
                    double viewWidth = viewPanel.getWidth();
                    double viewHeight = viewPanel.getHeight();
                    
                    double scaleX = viewWidth / width;
                    double scaleY = viewHeight / height;
                    double scale = Math.min(scaleX, scaleY) * 0.85; // Zoom conservador para ver TODO
                    
                    viewPanel.getCamera().setViewPercent(1.0 / scale);
                    
                    System.out.println("✓ Vista centrada - ÁRBOL B COMPLETO VISIBLE");
                    System.out.println("✓ Números grandes y legibles");
                    System.out.println("✓ Todos los extremos visibles");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al centrar vista completa: " + e.getMessage());
        }
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(250, 250, 250));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        
        JButton refreshButton = new JButton("🔄 Actualizar Completo");
        refreshButton.setToolTipText("Reconstruir visualización completa");
        refreshButton.addActionListener(e -> {
            buildUltraCompleteLayout();
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(800);
                    centerViewToShowCompleteTree();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            });
        });
        
        JButton infoButton = new JButton("ℹ️ Info Ultra B");
        infoButton.addActionListener(e -> showTreeInfo());
        
        JButton centerButton = new JButton("🎯 Ver Todo el Árbol");
        centerButton.setToolTipText("Centrar para ver el árbol COMPLETO");
        centerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(300);
                    centerViewToShowCompleteTree();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            });
        });
        
        JButton zoomOutButton = new JButton("🔍 Zoom Completo");
        zoomOutButton.setToolTipText("Zoom para ver todos los extremos");
        zoomOutButton.addActionListener(e -> {
            if (viewPanel != null && viewPanel.getCamera() != null) {
                viewPanel.getCamera().setViewPercent(viewPanel.getCamera().getViewPercent() * 1.3);
            }
        });
        
        JButton exportButton = new JButton("📤 Exportar Ultra DOT");
        exportButton.addActionListener(e -> exportToDot());
        
        JButton closeButton = new JButton("🚪 Cerrar Completamente");
        closeButton.setBackground(new Color(255, 220, 220));
        closeButton.setToolTipText("Cierre completo que libera la terminal");
        closeButton.addActionListener(e -> forceCloseAll());
        
        panel.add(refreshButton);
        panel.add(infoButton);
        panel.add(centerButton);
        panel.add(zoomOutButton);
        panel.add(exportButton);
        panel.add(closeButton);
        
        return panel;
    }
    
    private void showTreeInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== ÁRBOL B - VISUALIZACIÓN ULTRA COMPLETA ===\n\n");
        info.append("PROBLEMAS DE LA IMAGEN SOLUCIONADOS:\n");
        info.append("✅ NÚMEROS EXTRA GRANDES (28-30px) - Súper legibles\n");
        info.append("✅ NODOS RECTANGULARES AMPLIOS (200x70px)\n");
        info.append("✅ ÁRBOL COMPLETO VISIBLE - Todos los extremos\n");
        info.append("✅ ESPACIADO GENEROSO (400px horizontal, 200px vertical)\n");
        info.append("✅ VENTANA EXTRA GRANDE (1700x1100px)\n");
        info.append("✅ ZOOM AUTOMÁTICO para ver todo\n\n");
        
        info.append("Estructura textual:\n");
        info.append(btree.toString());
        info.append("\n\nCaracterísticas ultra visibles:\n");
        info.append("🟢 Verde: Nodo raíz (números 30px)\n");
        info.append("🔵 Azul: Nodos internos (28px)\n");
        info.append("🟠 Naranja: Nodos hoja (28px)\n");
        info.append("⬜ Fondo blanco con texto negro súper claro\n\n");
        
        info.append("Navegación ultra completa:\n");
        info.append("- Zoom: Rueda del mouse\n");
        info.append("- Pan: Arrastrar fondo\n");
        info.append("- Ver Todo: Botón 🎯 para vista completa\n");
        info.append("- Zoom Completo: Botón 🔍 para extremos\n");
        
        JTextArea textArea = new JTextArea(info.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 600));
        
        JOptionPane.showMessageDialog(null, scrollPane, 
                                    "Información Ultra Completa", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportToDot() {
        StringBuilder dot = new StringBuilder();
        dot.append("digraph BTreeUltraComplete {\n");
        dot.append("    rankdir=TB;\n");
        dot.append("    node [shape=record, style=\"filled,rounded\", fontsize=22, height=0.9, width=3.0];\n");
        dot.append("    edge [color=black, penwidth=2];\n");
        dot.append("    splines=ortho;\n");
        dot.append("    ranksep=2.5;\n"); // Más separación vertical
        dot.append("    nodesep=2.0;\n\n"); // Más separación horizontal
        
        for (Node node : graph) {
            String label = (String) node.getAttribute("ui.label");
            String nodeClass = (String) node.getAttribute("ui.class");
            String color = "white";
            
            switch (nodeClass != null ? nodeClass : "") {
                case "root": color = "lightgreen"; break;
                case "internal": color = "lightblue"; break;
                case "leaf": color = "lightyellow"; break;
                default: color = "white"; break;
            }
            
            String formattedLabel = label.replace("|", "\\|");
            
            dot.append("    ").append(node.getId())
               .append(" [label=\"").append(formattedLabel)
               .append("\", fillcolor=").append(color).append("];\n");
        }
        
        dot.append("\n    // Aristas del árbol completo\n");
        
        for (Edge edge : graph.edges().toArray(Edge[]::new)) {
            dot.append("    ").append(edge.getSourceNode().getId())
               .append(" -> ").append(edge.getTargetNode().getId()).append(";\n");
        }
        
        dot.append("}\n");
        
        JTextArea textArea = new JTextArea(dot.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(650, 450));
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Código DOT Ultra Completo para Graphviz:"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton copyButton = new JButton("📋 Copiar Ultra DOT");
        copyButton.addActionListener(e -> {
            textArea.selectAll();
            textArea.copy();
            JOptionPane.showMessageDialog(panel, "Código ultra completo copiado");
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(copyButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        JOptionPane.showMessageDialog(null, panel, 
                                    "Exportar Ultra Completo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void forceCloseAll() {
        System.out.println("Cerrando visualización ultra completa del Árbol B...");
        
        try {
            if (viewer != null) {
                viewer.close();
                viewer = null;
            }
            
            if (graph != null) {
                graph.clear();
            }
            
            if (frame != null) {
                frame.setVisible(false);
                frame.dispose();
            }
            
            viewPanel = null;
            frame = null;
            
            Thread.sleep(2000);
            System.gc();
            
            System.out.println("✓ Visualización ultra completa cerrada.");
            System.out.println("✓ Terminal libre para más comandos.");
            
        } catch (Exception e) {
            System.err.println("Error durante cierre ultra completo: " + e.getMessage());
        }
    }
    
    public void closeVisualization() {
        forceCloseAll();
    }
}
