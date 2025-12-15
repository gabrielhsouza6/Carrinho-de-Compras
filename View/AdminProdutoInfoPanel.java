package View;

import Model.DAO.*;
import Exceptions.ContaExceptions;
import Model.RN.CategoriaRN;
import Model.RN.ProdutoRN;
import Model.VO.Produto;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Objects;

public class AdminProdutoInfoPanel extends JPanel implements ActionListener {
    static JPanel panel;
    static JLabel fotoLabel;
    static JPanel fotoPanel;
    static ImageIcon fotoProduto;
    static JPanel fotoChooserPanel;
    static JTextField fotoTextF;
    static JButton fotoButton;
    static JPanel descricaoPanel;
    static JLabel descricaoLabel;
    static JTextField descricaoTextF;
    static JPanel precoPanel;
    static JLabel precoLabel;
    static JTextField precoTextF;
    static JPanel descontoPanel;
    static JLabel descontoLabel;
    static JTextField descontoTextF;
    static JPanel medidaPanel;
    static JLabel medidaLabel;
    static ButtonGroup medidasButtons;
    static JRadioButton unidadeButton;
    static JRadioButton kgButton;
    static JPanel categoriaPanel;
    static JLabel categoriaLabel;
    static JComboBox<String> categoriasComboBox;
    Font labelFont = new Font("SansSerif", Font.BOLD, 18);
    Font textfFont = new Font("SansSerif", Font.PLAIN, 18);
    static String medida = "";
    static Produto produto = null;
    static String foto = null;

    public AdminProdutoInfoPanel(Produto produto) throws SQLException {
        this.produto = produto;
        this.foto = produto.getFoto();
        panel = new JPanel();
        fotoPanel = new JPanel();
        fotoProduto = new ImageIcon(produto.getFoto());
        fotoChooserPanel = new JPanel();
        fotoLabel = new JLabel(fotoProduto);
        fotoTextF = new JTextField(produto.getFoto());
        fotoButton = new JButton("Escolher Arquivo");
        descricaoPanel = new JPanel();
        descricaoLabel = new JLabel("Descrição: " + produto.getDescricao());
        descricaoTextF = new JTextField(produto.getDescricao());
        precoPanel = new JPanel();
        precoLabel = new JLabel(String.format("Preço: R$%.2f", produto.getPreco()));
        precoTextF = new JTextField(String.format("%.2f", produto.getPreco()).replace(',', '.'));
        descontoPanel = new JPanel();
        descontoLabel = new JLabel(String.format("Desconto: %.1f%%", produto.getDesconto()));
        descontoTextF = new JTextField(String.format("%.1f", produto.getDesconto()).replace(',', '.'));
        medidaPanel = new JPanel();
        medidaLabel = new JLabel("Medida: ");
        if(Objects.equals(produto.getMedida(), "un")){
            medidaLabel.setText(medidaLabel.getText() + "Unidade");
        }else{
            medidaLabel.setText(medidaLabel.getText() + "Peso por Kg");
        }
        medidasButtons = new ButtonGroup();
        unidadeButton = new JRadioButton("Unidade");
        kgButton = new JRadioButton("Peso por Kg");
        categoriaPanel = new JPanel();
        categoriaLabel = new JLabel("Categoria: " + produto.getCategoria());
        categoriasComboBox = new JComboBox<>();

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setPreferredSize(new Dimension(600, 300));
        fotoLabel.setPreferredSize(new Dimension(150, 140));
        fotoPanel.setPreferredSize(new Dimension(450, 140));
        fotoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));
        fotoChooserPanel.setPreferredSize(new Dimension(450, 25));
        fotoChooserPanel.setBackground(Color.lightGray);
        fotoChooserPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));
        fotoTextF.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fotoTextF.setPreferredSize(new Dimension(265, 20));
        fotoButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fotoButton.setPreferredSize(new Dimension(150, 20));
        fotoButton.addActionListener(this);
        descricaoPanel.setLayout(new FlowLayout());
        descricaoPanel.setPreferredSize(new Dimension(500, 30));
        precoPanel.setLayout(new FlowLayout());
        precoPanel.setPreferredSize(new Dimension(500, 30));
        descontoPanel.setLayout(new FlowLayout());
        descontoPanel.setPreferredSize(new Dimension(500, 30));
        medidaPanel.setLayout(new FlowLayout());
        medidaPanel.setPreferredSize(new Dimension(500, 30));
        categoriaPanel.setLayout(new FlowLayout());
        categoriaPanel.setPreferredSize(new Dimension(500, 30));
        categoriasComboBox.setPreferredSize(new Dimension(200, 20));
        categoriasComboBox.setFont(textfFont);
        categoriasComboBox.addActionListener(this);
        unidadeButton.addActionListener(this);
        kgButton.addActionListener(this);
        descricaoLabel.setFont(labelFont);
        precoLabel.setFont(labelFont);
        medidaLabel.setFont(labelFont);
        descontoLabel.setFont(labelFont);
        categoriaLabel.setFont(labelFont);
        descricaoTextF.setFont(textfFont);
        descricaoTextF.setPreferredSize(new Dimension(200, 25));
        precoTextF.setFont(textfFont);
        precoTextF.setPreferredSize(new Dimension(200, 25));
        descontoTextF.setFont(textfFont);
        descontoTextF.setPreferredSize(new Dimension(200, 25));
        kgButton.setFont(textfFont);
        unidadeButton.setFont(textfFont);

        medidasButtons.add(kgButton);
        medidasButtons.add(unidadeButton);
        fotoChooserPanel.add(fotoTextF);
        fotoChooserPanel.add(fotoButton);

        if(Objects.equals(produto.getMedida(), "un")){
            unidadeButton.setSelected(true);
            kgButton.setSelected(false);
            medida = "un";
        }else{
            kgButton.setSelected(true);
            unidadeButton.setSelected(false);
            medida = "kg";
        }

        fotoPanel.add(fotoLabel);
        descricaoPanel.add(descricaoLabel);
        precoPanel.add(precoLabel);
        descontoPanel.add(descontoLabel);
        medidaPanel.add(medidaLabel);
        categoriaPanel.add(categoriaLabel);

        panel.add(fotoPanel);
        panel.add(descricaoPanel);
        panel.add(precoPanel);
        panel.add(descontoPanel);
        panel.add(medidaPanel);
        panel.add(categoriaPanel);

        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel.setBackground(Color.LIGHT_GRAY);
        for(Component c : panel.getComponents()){
            c.setBackground(Color.LIGHT_GRAY);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void alterarProdutoInfoPanel() throws SQLException, ContaExceptions.NomeInvalidoException {
        if(AdminProdutoPanel.alterarStatus%2==0) {
            fotoLabel.setPreferredSize(new Dimension(150, 110));
            fotoPanel.add(fotoChooserPanel);
            descricaoLabel.setText("Descrição: ");
            descricaoPanel.add(descricaoTextF);
            precoLabel.setText("Preço: R$");
            precoPanel.add(precoTextF);
            descontoLabel.setText("Desconto: ");
            descontoPanel.add(descontoTextF);
            medidaLabel.setText("Medida: ");
            medidaPanel.add(unidadeButton);
            medidaPanel.add(kgButton);

            categoriaLabel.setText("Categoria: ");
            atualizarComboBox();
            for(int i=0; i<categoriasComboBox.getItemCount(); i++){
                if(Objects.equals(categoriasComboBox.getItemAt(i), produto.getCategoria())){
                    categoriasComboBox.setSelectedIndex(i);
                }
            }
            categoriasComboBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if(Objects.equals(Objects.requireNonNull(categoriasComboBox.getSelectedItem()), "Adicionar nova categoria")) {
                        try {
                            String novaCategoria = "";
                            if((novaCategoria = JOptionPane.showInputDialog(null, "Nome da nova categoria: ")) != null) {
                                CategoriaRN.addCategoriaRN(novaCategoria);
                                atualizarComboBox();
                                categoriasComboBox.setSelectedIndex(categoriasComboBox.getItemCount() - 2);
                                MenuPrincipal.atualizarProductsMenu();
                                JOptionPane.showMessageDialog(null, "Categoria adicionada com sucesso", "", JOptionPane.INFORMATION_MESSAGE);
                            }else{
                                for(int i=0; i<categoriasComboBox.getItemCount(); i++){
                                    if(Objects.equals(categoriasComboBox.getItemAt(i), produto.getCategoria())){
                                        categoriasComboBox.setSelectedIndex(i);
                                    }
                                }
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
            categoriaPanel.add(categoriasComboBox);
            AdminProdutoPanel.alterarButton.setText("Finalizar Alteração");
            AdminProdutoPanel.alterarButton.setBounds(200, 420, 150, 40);
        }else{
            ProdutoRN.updateProdutoRN( new Produto(
                    produto.getId(),
                    descricaoTextF.getText(),
                    Double.valueOf(precoTextF.getText()),
                    Double.valueOf(descontoTextF.getText()),
                    Objects.requireNonNull(categoriasComboBox.getSelectedItem()).toString(),
                    medida,
                    foto
            ));
            if(CategoriasDAO.getNumProdutosCategoria(CategoriasDAO.getIdCategoria(produto.getCategoria()))==0){
                CategoriaRN.deleteCategoriaRN(produto.getCategoria());
                MenuPrincipal.atualizarProductsMenu();
            }
            produto = ProdutoRN.searchProdutoRN(produto.getId());
//            fotoLabel.setPreferredSize(new Dimension(150, 140));
//            fotoPanel.remove(fotoChooserPanel);
//            descricaoLabel.setText("Descrição: " + produto.getDescricao());
//            descricaoPanel.remove(descricaoTextF);
//            precoLabel.setText(String.format("Preço: R$%.2f", produto.getPreco()));
//            precoPanel.remove(precoTextF);
//            descontoLabel.setText(String.format("Desconto: %.1f%%", produto.getDesconto()));
//            descontoPanel.remove(descontoTextF);
//            if(Objects.equals(produto.getMedida(), "un")){
//                medidaLabel.setText(medidaLabel.getText() + "Unidade");
//            }else{
//                medidaLabel.setText(medidaLabel.getText() + "Peso por Kg");
//            }
//            medidaPanel.remove(unidadeButton);
//            medidaPanel.remove(kgButton);
//            categoriaLabel.setText("Categoria: " + produto.getCategoria());
//            categoriaPanel.remove(categoriasComboBox);
            AdminProdutoPanel.alterarButton.setBounds(250, 420, 100, 40);
            AdminProdutoPanel.alterarButton.setText("Alterar");
            AdminProdutoPanel.alterarProdutoPanel(new AdminProdutoInfoPanel(produto).getPanel());
        }
        panel.repaint();
    }

    public static void atualizarComboBox() throws SQLException {
        categoriasComboBox.removeAllItems();
        for(String s : CategoriasDAO.getCategorias()){
            categoriasComboBox.addItem(s);
        }
        categoriasComboBox.addItem("Adicionar nova categoria");
        categoriasComboBox.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==kgButton){
            medida = "kg";
        }
        if(e.getSource()==unidadeButton){
            medida = "un";
        }
        if(e.getSource()==fotoButton){
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Filter", "jpg", "png");
            JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.home")));
            fileChooser.setFileFilter(filter);
            int resposta = fileChooser.showSaveDialog(null);
            if(resposta == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                try (InputStream in = new FileInputStream(file);
                     OutputStream out = new FileOutputStream("src/Imagens/" + file.getName())){

                    byte[] buffer = new byte[1024];
                    int bytesRead = in.read(buffer);

                    while(bytesRead != -1){
                        out.write(buffer, 0, bytesRead);
                        bytesRead = in.read(buffer);
                    }

                    foto = String.format("src//Imagens//%s", file.getName());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                fotoTextF.setText(file.getAbsolutePath());
            }
        }
    }
}
