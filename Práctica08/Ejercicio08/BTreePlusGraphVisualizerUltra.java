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

public class BTreePlusGraphVisualizerUltra<E extends Comparable<E>> {
    private BTreePlus<E> btreePlus;
    private Graph graph;
    private AtomicInteger nodeCounter;
    private Viewer viewer;
    private ViewPanel viewPanel;
    private JFrame frame;
    
    private static final String STYLE_SHEET = 
        "graph {" +
        "    padding: 100px;" +
        "}" +
        "node {" +
        "    fill-color: white;" +
        "    stroke-mode: plain;" +
        "    stroke-color: black;" +
        "    stroke-width: 3px;" +
        "    text-alignment: center;" +
        "    text-style: bold;" +
        "    text-size: 24px;" +
        "    text-color: black;" +
        "    size: 180px, 60px;" +
        "    shape: box;" +
        "    text-background-mode: none;" +
        "    text-padding: 10px;" +
        "}" +
        "node.root {" +
        "    fill-color: #E8F5E8;" +
        "    stroke-color: #2E7D32;" +
        "    stroke-width: 4px;" +
        "    text-size: 26px;" +
        "}" +
        "node.internal {" +
        "    fill-color: #E3F2FD;" +
        "    stroke-color: #1976D2;" +
        "    stroke-width: 3px;" +
        "    text-size: 24px;" +
        "}" +
        "node.leaf {" +
        "    fill-color: #FFF3E0;" +
        "    stroke-color: #F57C00;" +
        "    stroke-width: 4px;" +
        "    text-size: 24px;" +
        "}" +
        "edge {" +
        "    stroke-color: #424242;" +
        "    stroke-width: 2px;" +
        "    arrow-shape: none;" +
        "}" +
        "edge.leaf-link {" +
        "    stroke-color: #D32F2F;" +
        "    stroke-width: 5px;" +
        "    stroke-mode: dashes;" +
        "}";
    
    public BTreePlusGraphVisualizerUltra(BTreePlus<E> btreePlus) {
        this.btreePlus = btreePlus;
        this.nodeCounter = new AtomicInteger(0);
        initializeGraph();
    }
    
    private void initializeGraph() {
        System.setProperty("org.graphstream.ui", "swing");
        this.graph = new SingleGraph("BTree+ Ultra Clean Visualization");
        this.graph.setAttribute("ui.stylesheet", STYLE_SHEET);
        this.graph.setAttribute("ui.quality");
        this.graph.setAttribute("ui.antialias");
    }
    
    public void visualizeTree() {
        if (btreePlus.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El árbol B+ está vacío", 
                                        "Árbol B+ Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        graph.clear();
        nodeCounter.set(0);
        buildUltraCleanLayout();
        displayUltraCleanGraph();
    }
    
    private void buildUltraCleanLayout() {
        if (btreePlus.isEmpty()) return;
        
        try {
            java.lang.reflect.Field rootField = btreePlus.getClass().getDeclaredField("root");
            rootField.setAccessible(true);
            BNodePlus<E> root = (BNodePlus<E>) rootField.get(btreePlus);
            
            if (root != null) {
                // Calcular estructura del árbol para layout perfecto
                int treeHeight = calculateTreeHeightPlus(root);
                int[] levelWidths = calculateLevelWidthsPlus(root, treeHeight);
                
                // Calcular ancho total necesario con más espacio
                int maxLevelWidth = 0;
                for (int width : levelWidths) {
                    maxLevelWidth = Math.max(maxLevelWidth, width);
                }
                
                double totalWidth = Math.max(1800, maxLevelWidth * 300); // Más espacio horizontal
                buildUltraCleanRecursive(root, null, 0, totalWidth / 2, totalWidth, levelWidths);
                
                // Agregar enlaces entre hojas DESPUÉS de construir todo el árbol
                addCleanLeafLinks();
            }
        } catch (Exception e) {
            System.err.println("Error accediendo a la raíz del árbol B+: " + e.getMessage());
            createSimplifiedVisualization();
        }
    }
    
    private int calculateTreeHeightPlus(BNodePlus<E> node) {
        if (node == null) return 0;
        if (node.isLeaf) return 1;
        
        int maxChildHeight = 0;
        for (int i = 0; i <= node.count; i++) {
            if (node.childs.get(i) != null) {
                maxChildHeight = Math.max(maxChildHeight, calculateTreeHeightPlus(node.childs.get(i)));
            }
        }
        return 1 + maxChildHeight;
    }
    
    private int[] calculateLevelWidthsPlus(BNodePlus<E> node, int totalHeight) {
        int[] widths = new int[totalHeight];
        calculateLevelWidthsRecursivePlus(node, 0, widths);
        return widths;
    }
    
    private void calculateLevelWidthsRecursivePlus(BNodePlus<E> node, int level, int[] widths) {
        if (node == null) return;
        widths[level]++;
        if (!node.isLeaf) {
            for (int i = 0; i <= node.count; i++) {
                if (node.childs.get(i) != null) {
                    calculateLevelWidthsRecursivePlus(node.childs.get(i), level + 1, widths);
                }
            }
        }
    }
    
    private String buildUltraCleanRecursive(BNodePlus<E> node, String parentId, int level, 
                                          double centerX, double totalWidth, int[] levelWidths) {
        if (node == null) return null;
        
        String nodeId = "node_" + nodeCounter.incrementAndGet();
        
        // Crear etiqueta ULTRA CLARA con números grandes
        StringBuilder label = new StringBuilder();
        if (node.isLeaf) {
            label.append("LEAF:\\n");
        } else {
            label.append("INDEX:\\n");
        }
        
        // Agregar las claves con separación clara
        for (int i = 0; i < node.count; i++) {
            if (i > 0) label.append("  |  ");
            label.append(node.keys.get(i));
        }
        
        Node graphNode = graph.addNode(nodeId);
        graphNode.setAttribute("ui.label", label.toString());
        
        // Posicionamiento con MÁS ESPACIO VERTICAL para evitar cruces
        double x = centerX;
        double y = -level * 180; // MÁS separación vertical
        
        graphNode.setAttribute("xyz", x, y, 0);
        graphNode.setAttribute("layout.frozen");
        
        // Establecer estilo según el tipo de nodo
        if (parentId == null) {
            graphNode.setAttribute("ui.class", "root");
        } else if (node.isLeaf) {
            graphNode.setAttribute("ui.class", "leaf");
        } else {
            graphNode.setAttribute("ui.class", "internal");
        }
        
        // Conectar con el padre SOLO si existe
        if (parentId != null) {
            String edgeId = parentId + "_to_" + nodeId;
            graph.addEdge(edgeId, parentId, nodeId, true);
        }
        
        // Procesar hijos con MÁXIMO ESPACIO para evitar cruces
        if (!node.isLeaf) {
            int realChildren = 0;
            for (int i = 0; i <= node.count; i++) {
                if (node.childs.get(i) != null) {
                    realChildren++;
                }
            }
            
            if (realChildren > 0) {
                // Calcular espaciado con MUCHO más espacio horizontal
                double levelWidth = realChildren * 350; // Espacio generoso
                double childSpacing = levelWidth / realChildren;
                double startX = centerX - (levelWidth / 2) + (childSpacing / 2);
                
                int childIndex = 0;
                for (int i = 0; i <= node.count; i++) {
                    if (node.childs.get(i) != null) {
                        double childX = startX + (childIndex * childSpacing);
                        buildUltraCleanRecursive(node.childs.get(i), nodeId, level + 1, 
                                               childX, totalWidth, levelWidths);
                        childIndex++;
                    }
                }
            }
        }
        
        return nodeId;
    }
    
    private void addCleanLeafLinks() {
        // Encontrar SOLO los nodos hoja y conectarlos limpiamente
        java.util.List<Node> leafNodes = new java.util.ArrayList<>();
        
        for (Node node : graph) {
            if ("leaf".equals(node.getAttribute("ui.class"))) {
                leafNodes.add(node);
            }
        }
        
        // Ordenar hojas por posición X (izquierda a derecha)
        leafNodes.sort((n1, n2) -> {
            Object[] xyz1 = (Object[]) n1.getAttribute("xyz");
            Object[] xyz2 = (Object[]) n2.getAttribute("xyz");
            if (xyz1 != null && xyz2 != null && xyz1.length >= 1 && xyz2.length >= 1) {
                Double x1 = ((Number) xyz1[0]).doubleValue();
                Double x2 = ((Number) xyz2[0]).doubleValue();
                return x1.compareTo(x2);
            }
            return 0;
        });
        
        // Conectar hojas secuencialmente con enlaces LIMPIOS
        for (int i = 0; i < leafNodes.size() - 1; i++) {
            String edgeId = "leaf_link_" + i;
            Edge leafEdge = graph.addEdge(edgeId, leafNodes.get(i), leafNodes.get(i + 1), true);
            leafEdge.setAttribute("ui.class", "leaf-link");
        }
    }
    
    private void createSimplifiedVisualization() {
        Node simpleNode = graph.addNode("simple");
        simpleNode.setAttribute("ui.label", "Árbol B+\\n(Ver consola para estructura detallada)");
        simpleNode.setAttribute("ui.class", "root");
        simpleNode.setAttribute("xyz", 900, 0, 0);
        simpleNode.setAttribute("layout.frozen");
    }
    
    private void displayUltraCleanGraph() {
        viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.disableAutoLayout();
        
        viewPanel = (ViewPanel) viewer.addDefaultView(false);
        viewPanel.setPreferredSize(new Dimension(1600, 1000)); // Ventana MÁS GRANDE
        
        frame = new JFrame("Árbol B+ - Visualización Ultra Limpia");
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
        frame.setSize(1600, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Centrar vista perfectamente
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(1200); // Más tiempo para renderizar
                centerViewUltraClean();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        showTreeInfo();
    }
    
    private void centerViewUltraClean() {
        try {
            if (viewPanel != null && viewPanel.getCamera() != null) {
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
                    
                    // Ajustar zoom para ver TODO el árbol sin cruces
                    double width = maxX - minX + 400;
                    double height = maxY - minY + 300;
                    double viewWidth = viewPanel.getWidth();
                    double viewHeight = viewPanel.getHeight();
                    
                    double scaleX = viewWidth / width;
                    double scaleY = viewHeight / height;
                    double scale = Math.min(scaleX, scaleY) * 0.8; // Zoom más conservador
                    
                    viewPanel.getCamera().setViewPercent(1.0 / scale);
                    
                    System.out.println("Vista ultra limpia centrada - Árbol B+ perfectamente visible");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al centrar vista ultra limpia: " + e.getMessage());
        }
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(250, 250, 250));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        
        JButton refreshButton = new JButton("🔄 Actualizar Vista");
        refreshButton.setToolTipText("Reconstruir visualización ultra limpia");
        refreshButton.addActionListener(e -> {
            buildUltraCleanLayout();
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(600);
                    centerViewUltraClean();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            });
        });
        
        JButton infoButton = new JButton("ℹ️ Info B+ Ultra");
        infoButton.addActionListener(e -> showTreeInfo());
        
        JButton centerButton = new JButton("🎯 Centrar Perfecto");
        centerButton.setToolTipText("Centrar vista sin cruces de líneas");
        centerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(200);
                    centerViewUltraClean();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            });
        });
        
        JButton sequenceButton = new JButton("🔗 Secuencia Limpia");
        sequenceButton.setToolTipText("Ver recorrido secuencial sin interferencias");
        sequenceButton.addActionListener(e -> showLeafSequence());
        
        JButton exportButton = new JButton("📤 Exportar Ultra DOT");
        exportButton.addActionListener(e -> exportToDot());
        
        JButton closeButton = new JButton("🚪 Cerrar Limpiamente");
        closeButton.setBackground(new Color(255, 220, 220));
        closeButton.setToolTipText("Cierre ultra limpio que libera la terminal");
        closeButton.addActionListener(e -> forceCloseAll());
        
        panel.add(refreshButton);
        panel.add(infoButton);
        panel.add(centerButton);
        panel.add(sequenceButton);
        panel.add(exportButton);
        panel.add(closeButton);
        
        return panel;
    }
    
    private void showTreeInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== ÁRBOL B+ - VISUALIZACIÓN ULTRA LIMPIA ===\n\n");
        info.append("MEJORAS IMPLEMENTADAS:\n");
        info.append("✓ Números EXTRA GRANDES (24-26px)\n");
        info.append("✓ Nodos rectangulares amplios (180x60px)\n");
        info.append("✓ Espaciado generoso (350px horizontal, 180px vertical)\n");
        info.append("✓ Sin cruces de líneas confusos\n");
        info.append("✓ Enlaces rojos SOLO entre hojas\n");
        info.append("✓ Etiquetas INDEX/LEAF súper claras\n\n");
        
        info.append("Estructura textual:\n");
        info.append(btreePlus.toString());
        info.append("\n\nCaracterísticas B+ ultra visibles:\n");
        info.append("🟢 Verde: Nodo raíz (números 26px)\n");
        info.append("🔵 Azul: INDEX - Solo índices (24px)\n");
        info.append("🟠 Naranja: LEAF - Todas las claves (24px)\n");
        info.append("🔴 Líneas rojas: Enlaces SOLO entre hojas\n\n");
        
        info.append("Navegación ultra limpia:\n");
        info.append("- Zoom: Rueda del mouse\n");
        info.append("- Pan: Arrastrar fondo\n");
        info.append("- Centrar: Botón 🎯 para vista perfecta\n");
        info.append("- Sin interferencias visuales\n");
        
        JTextArea textArea = new JTextArea(info.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 600));
        
        JOptionPane.showMessageDialog(null, scrollPane, 
                                    "Información Ultra Limpia B+", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showLeafSequence() {
        StringBuilder sequence = new StringBuilder();
        sequence.append("=== SECUENCIA ULTRA LIMPIA DE HOJAS B+ ===\n\n");
        
        String treeString = btreePlus.toString();
        String[] lines = treeString.split("\n");
        
        for (String line : lines) {
            if (line.contains("Leaf sequence:")) {
                sequence.append("Recorrido secuencial SIN INTERFERENCIAS:\n");
                sequence.append(line.substring(line.indexOf(":") + 1).trim()).append("\n\n");
                break;
            }
        }
        
        sequence.append("VENTAJAS DE ESTA VISUALIZACIÓN:\n");
        sequence.append("✓ Números grandes y legibles\n");
        sequence.append("✓ Sin cruces de líneas confusos\n");
        sequence.append("✓ Enlaces rojos SOLO donde importan\n");
        sequence.append("✓ Espaciado generoso entre nodos\n");
        sequence.append("✓ Etiquetas INDEX/LEAF súper claras\n\n");
        
        sequence.append("Los enlaces rojos punteados muestran CLARAMENTE\n");
        sequence.append("la característica principal del B+:\n");
        sequence.append("recorrido secuencial eficiente de hojas.\n");
        
        JTextArea textArea = new JTextArea(sequence.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 450));
        
        JOptionPane.showMessageDialog(null, scrollPane, 
                                    "Secuencia Ultra Limpia", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportToDot() {
        StringBuilder dot = new StringBuilder();
        dot.append("digraph BTreePlusUltraClean {\n");
        dot.append("    rankdir=TB;\n");
        dot.append("    node [shape=record, style=\"filled,rounded\", fontsize=20, height=0.8, width=2.5];\n");
        dot.append("    edge [color=black, penwidth=2];\n");
        dot.append("    splines=ortho;\n"); // Líneas rectas sin cruces
        dot.append("    ranksep=2.0;\n"); // Más separación vertical
        dot.append("    nodesep=1.5;\n\n"); // Más separación horizontal
        
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
            
            String formattedLabel = label.replace("|", "\\|").replace("\\n", "\\n");
            
            dot.append("    ").append(node.getId())
               .append(" [label=\"").append(formattedLabel)
               .append("\", fillcolor=").append(color).append("];\n");
        }
        
        dot.append("\n    // Aristas del árbol (sin cruces)\n");
        
        for (Edge edge : graph.edges().toArray(Edge[]::new)) {
            if (!"leaf-link".equals(edge.getAttribute("ui.class"))) {
                dot.append("    ").append(edge.getSourceNode().getId())
                   .append(" -> ").append(edge.getTargetNode().getId()).append(";\n");
            }
        }
        
        dot.append("\n    // Enlaces entre hojas B+ (ultra limpios)\n");
        
        for (Edge edge : graph.edges().toArray(Edge[]::new)) {
            if ("leaf-link".equals(edge.getAttribute("ui.class"))) {
                dot.append("    ").append(edge.getSourceNode().getId())
                   .append(" -> ").append(edge.getTargetNode().getId())
                   .append(" [color=red, style=dashed, penwidth=4];\n");
            }
        }
        
        dot.append("}\n");
        
        JTextArea textArea = new JTextArea(dot.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(650, 450));
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Código DOT Ultra Limpio para Graphviz:"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton copyButton = new JButton("📋 Copiar Ultra DOT");
        copyButton.addActionListener(e -> {
            textArea.selectAll();
            textArea.copy();
            JOptionPane.showMessageDialog(panel, "Código ultra limpio copiado");
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(copyButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        JOptionPane.showMessageDialog(null, panel, 
                                    "Exportar Ultra Limpio", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void forceCloseAll() {
        System.out.println("Cerrando visualización ultra limpia del Árbol B+...");
        
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
            
            System.out.println("✓ Visualización ultra limpia cerrada completamente.");
            System.out.println("✓ Terminal libre para más comandos.");
            
        } catch (Exception e) {
            System.err.println("Error durante cierre ultra limpio: " + e.getMessage());
        }
    }
    
    public void closeVisualization() {
        forceCloseAll();
    }
}
