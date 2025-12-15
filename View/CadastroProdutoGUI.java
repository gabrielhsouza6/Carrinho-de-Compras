package View;

import Model.DAO.CategoriasDAO;
import Exceptions.ContaExceptions;
import Model.RN.CategoriaRN;
import Model.RN.ProdutoRN;
import Model.VO.Produto;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.Objects;

public class CadastroProdutoGUI extends JFrame implements ActionListener {
    JFrame frame;
    JPanel panel;
    JPanel tituloPanel;
    JLabel tituloLabel;
    JPanel fotoPanel;
    JLabel fotoLabel;
    JTextField fotoTextF;
    JButton fotoButton;
    JPanel descricaoPanel;
    JLabel descricaoLabel;
    JTextField descricaoTextF;
    JPanel precoPanel;
    JLabel precoLabel;
    JTextField precoTextF;
    JPanel descontoPanel;
    JLabel descontoLabel;
    JTextField descontoTextF;
    JPanel categoriaPanel;
    JLabel categoriaLabel;
    JComboBox<String> categoriaComboBox;
    JPanel medidaPanel;
    JLabel medidaLabel;
    ButtonGroup medidasBG;
    JRadioButton kgButton;
    JRadioButton unButton;
    JPanel submitPanel;
    JButton submitButton;
    String medida = "kg";
    String foto = "";

    public CadastroProdutoGUI() throws SQLException {
        frame = new JFrame("Cadastro de Produto");
        panel = new JPanel();
        tituloPanel = new JPanel();
        tituloLabel = new JLabel("Cadastro");
        fotoPanel = new JPanel();
        fotoLabel = new JLabel("Foto: ");
        fotoTextF = new JTextField();
        fotoButton = new JButton("Escolher arquivo");
        descricaoPanel = new JPanel();
        descricaoLabel = new JLabel("Descrição: ");
        descricaoTextF = new JTextField();
        precoPanel = new JPanel();
        precoLabel = new JLabel("Preço(R$): ");
        precoTextF = new JTextField("00.00");
        descontoPanel = new JPanel();
        descontoLabel = new JLabel("Desconto(%): ");
        descontoTextF = new JTextField("00.00");
        categoriaPanel = new JPanel();
        categoriaLabel = new JLabel("Categoria: ");
        categoriaComboBox = new JComboBox<>();
        medidaPanel = new JPanel();
        medidaLabel = new JLabel("Medida: ");
        medidasBG = new ButtonGroup();
        kgButton = new JRadioButton("Peso por Kg");
        unButton = new JRadioButton("Unidade");
        submitPanel = new JPanel();
        submitButton = new JButton("Confirmar");
        Dimension panelDimension = new Dimension(600, 30);
        Dimension textfDimension = new Dimension(250, 22);
        Font labelFont = new Font("SansSerif", Font.BOLD, 15);
        Font textfFont = new Font("SansSerif", Font.PLAIN, 14);

        frame.setSize(650, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        tituloPanel.setPreferredSize(new Dimension(600, 55));
        fotoPanel.setPreferredSize(panelDimension);
        fotoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        descricaoPanel.setPreferredSize(panelDimension);
        precoPanel.setPreferredSize(panelDimension);
        descontoPanel.setPreferredSize(panelDimension);
        categoriaPanel.setPreferredSize(panelDimension);
        medidaPanel.setPreferredSize(panelDimension);
        submitPanel.setPreferredSize(new Dimension(600, 40));
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 25));
        fotoLabel.setFont(labelFont);
        descricaoLabel.setFont(labelFont);
        precoLabel.setFont(labelFont);
        descontoLabel.setFont(labelFont);
        categoriaLabel.setFont(labelFont);
        medidaLabel.setFont(labelFont);
        fotoTextF.setFont(textfFont);
        fotoTextF.setPreferredSize(textfDimension);
        fotoButton.setFont(textfFont);
        fotoButton.setPreferredSize(new Dimension(150, 21));
        descricaoTextF.setFont(textfFont);
        descricaoTextF.setPreferredSize(textfDimension);
        precoTextF.setFont(textfFont);
        precoTextF.setPreferredSize(textfDimension);
        descontoTextF.setFont(textfFont);
        descontoTextF.setPreferredSize(textfDimension);
        categoriaComboBox.setFont(textfFont);
        categoriaComboBox.setPreferredSize(textfDimension);
        atualizarComboBox();
        categoriaComboBox.setSelectedIndex(0);
        categoriaComboBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(Objects.equals(Objects.requireNonNull(categoriaComboBox.getSelectedItem()), "Adicionar nova categoria")) {
                    try {
                        String novaCategoria = "";
                        if((novaCategoria = JOptionPane.showInputDialog(null, "Nome da nova categoria: ")) != null) {
                            CategoriaRN.addCategoriaRN(novaCategoria);
                            atualizarComboBox();
                            categoriaComboBox.setSelectedIndex(categoriaComboBox.getItemCount() - 2);
                            MenuPrincipal.atualizarProductsMenu();
                            JOptionPane.showMessageDialog(null, "Categoria adicionada com sucesso", "", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            categoriaComboBox.setSelectedIndex(0);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        kgButton.setFont(textfFont);
        kgButton.setSelected(true);
        unButton.setFont(textfFont);
        submitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        submitButton.setPreferredSize(new Dimension(150, 30));

        fotoButton.addActionListener(this);
        unButton.addActionListener(this);
        kgButton.addActionListener(this);
        submitButton.addActionListener(this);

        medidasBG.add(kgButton);
        medidasBG.add(unButton);

        tituloPanel.add(tituloLabel);
        fotoPanel.add(fotoLabel);
        fotoPanel.add(fotoTextF);
        fotoPanel.add(fotoButton);
        descricaoPanel.add(descricaoLabel);
        descricaoPanel.add(descricaoTextF);
        precoPanel.add(precoLabel);
        precoPanel.add(precoTextF);
        descontoPanel.add(descontoLabel);
        descontoPanel.add(descontoTextF);
        categoriaPanel.add(categoriaLabel);
        categoriaPanel.add(categoriaComboBox);
        medidaPanel.add(medidaLabel);
        medidaPanel.add(kgButton);
        medidaPanel.add(unButton);
        submitPanel.add(submitButton);

        panel.add(tituloPanel);
        panel.add(fotoPanel);
        panel.add(descricaoPanel);
        panel.add(precoPanel);
        panel.add(descontoPanel);
        panel.add(categoriaPanel);
        panel.add(medidaPanel);
        panel.add(submitPanel);

        frame.add(panel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==unButton){
            medida = "un";
        }
        if(e.getSource()==kgButton){
            medida = "kg";
        }
        if(e.getSource()==submitButton){
            try {
                ProdutoRN.addProdutoRN( new Produto(
                        0,
                        descricaoTextF.getText(),
                        Double.valueOf(precoTextF.getText()),
                        Double.valueOf(descontoTextF.getText()),
                        Objects.requireNonNull(categoriaComboBox.getSelectedItem()).toString(),
                        medida,
                        foto
                ));
                AdminProdutoPanel.alterarProdutoPanel(new AdminProdutoInfoPanel(ProdutoRN.searchProdutoRN(descricaoTextF.getText())).getPanel());
                JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso", "", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch (SQLException | ContaExceptions.NomeInvalidoException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Dados Inválidos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
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

    public void atualizarComboBox() throws SQLException {
        categoriaComboBox.removeAllItems();
        for(String s : CategoriasDAO.getCategorias()){
            categoriaComboBox.addItem(s);
        }
        categoriaComboBox.addItem("Adicionar nova categoria");
        categoriaComboBox.repaint();
    }
}
