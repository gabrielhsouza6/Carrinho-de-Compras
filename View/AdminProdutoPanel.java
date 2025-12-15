package View;

import Model.DAO.ProdutosDAO;
import Exceptions.ContaExceptions;
import Model.RN.ProdutoRN;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class AdminProdutoPanel extends JPanel implements ActionListener, DocumentListener {
    static JLayeredPane layeredPane;
    static JPanel panel;
    JPanel overallPanel;
    JLabel pesquisaLabel;
    JTextField pesquisaTextF;
    JPanel pesquisaPanel;
    JButton pesquisaButton;
    JPanel sugestoesPanel;
    static JPanel produtoPanel;
    static JButton alterarButton;
    JButton excluirButton;
    JButton cadastrarProdButton;
    static int alterarStatus;

    public AdminProdutoPanel(){
        panel = new JPanel();
        layeredPane = new JLayeredPane();
        overallPanel = new JPanel();
        produtoPanel = new JPanel();
        pesquisaLabel = new JLabel("Produto: ");
        pesquisaTextF = new JTextField();
        pesquisaPanel = new JPanel();
        pesquisaButton = new JButton("Pesquisar");
        sugestoesPanel = new JPanel();
        alterarButton = new JButton("Alterar");
        excluirButton = new JButton("Excluir");
        cadastrarProdButton = new JButton("Cadastrar Novo Produto");
        alterarStatus = 0;

        panel.setPreferredSize(new Dimension(900, 550));
        panel.setLayout(new BorderLayout());
        produtoPanel.setLayout(new BorderLayout());
        layeredPane.setBounds(0, 0, 900, 540);
        overallPanel.setLayout(new FlowLayout());
        pesquisaTextF.setPreferredSize(new Dimension(350, 25));
        pesquisaTextF.setFont(new Font("SansSerif", Font.PLAIN, 15));
        pesquisaButton.setPreferredSize(new Dimension(100, 25));
        pesquisaButton.addActionListener(this);
        pesquisaLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        pesquisaPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        sugestoesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        alterarButton.addActionListener(this);
        excluirButton.addActionListener(this);
        cadastrarProdButton.addActionListener(this);

        pesquisaPanel.setBounds(140, 50, 600, 50);
        produtoPanel.setBounds(150, 100, 600, 300);
        alterarButton.setBounds(250, 420, 100, 40);
        excluirButton.setBounds(400, 420, 100, 40);
        cadastrarProdButton.setBounds(550, 420, 175, 40);

        sugestoesPanel.setVisible(false);

        sugestoesPanel.setBackground(Color.WHITE);
        produtoPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        produtoPanel.setBackground(Color.LIGHT_GRAY);

        pesquisaPanel.add(pesquisaLabel);
        pesquisaPanel.add(pesquisaTextF);
        pesquisaPanel.add(pesquisaButton);

        layeredPane.add(pesquisaPanel, Integer.valueOf(0));
        layeredPane.add(produtoPanel, Integer.valueOf(0));
        layeredPane.add(alterarButton, Integer.valueOf(0));
        layeredPane.add(excluirButton, Integer.valueOf(0));
        layeredPane.add(cadastrarProdButton, Integer.valueOf(0));
        layeredPane.add(sugestoesPanel, Integer.valueOf(1));

        pesquisaTextF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizarSugestoesPanel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atualizarSugestoesPanel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        panel.add(layeredPane);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void atualizarSugestoesPanel(){
        if(!pesquisaTextF.getText().isEmpty() && !pesquisaTextF.getText().isBlank()){
            ArrayList<String> nomesProximos = new ArrayList<>();
            try {
                for(String nome : ProdutosDAO.getNomes()){
                    if(nome.length()>=pesquisaTextF.getText().length()) {
                        if (nome.substring(0, pesquisaTextF.getText().length()).equalsIgnoreCase(pesquisaTextF.getText())) {
                            nomesProximos.add(nome);
                        }
                        if (nomesProximos.size() == 5) {
                            break;
                        }
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            sugestoesPanel.removeAll();
            for(String nomeP : nomesProximos){
                JLabel temp = new JLabel();
                temp.setText(nomeP);
                temp.setPreferredSize(new Dimension(349, 25));
                temp.setBackground(Color.WHITE);
                temp.setOpaque(true);
                temp.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                temp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        SwingUtilities.invokeLater(() -> {
                            pesquisaTextF.setText(nomeP);
                            sugestoesPanel.setVisible(false);
                        });
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        temp.setBackground(temp.getBackground().darker());
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        temp.setBackground(temp.getBackground().brighter());
                    }
                });
                sugestoesPanel.add(temp);
            }

            sugestoesPanel.setBounds(245, 74, 349, sugestoesPanel.getComponentCount()*25);
            sugestoesPanel.setVisible(false);
            sugestoesPanel.repaint();
            sugestoesPanel.setVisible(true);
        }else{
            sugestoesPanel.setVisible(false);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==pesquisaButton){
            try {
                alterarProdutoPanel(new AdminProdutoInfoPanel(
                        Objects.requireNonNull(ProdutoRN.searchProdutoRN(pesquisaTextF.getText()))
                ).getPanel());
            } catch (SQLException  ex) {
                throw new RuntimeException(ex);
            } catch (ContaExceptions.NomeInvalidoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            sugestoesPanel.setVisible(false);
        }
        if(e.getSource()==alterarButton){
            try {
                AdminProdutoInfoPanel.alterarProdutoInfoPanel();
                panel.repaint();
                alterarStatus++;
            } catch (SQLException | ContaExceptions.NomeInvalidoException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==excluirButton){
            if (JOptionPane.showConfirmDialog(panel, "Deseja excluir esse produto?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                try {
                    ProdutoRN.deleteProdutoRN(ProdutoRN.searchProdutoRN(pesquisaTextF.getText()));
                    pesquisaTextF.setText("");
                    produtoPanel.removeAll();
                    panel.repaint();
                    JOptionPane.showMessageDialog(panel, "Produto excluído om sucesso", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException | ContaExceptions.NomeInvalidoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if(e.getSource()==cadastrarProdButton){
            try {
                new CadastroProdutoGUI();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void alterarProdutoPanel(JPanel newPanel){
        layeredPane.remove(produtoPanel);
        produtoPanel = newPanel;
        produtoPanel.setBounds(150, 100, 600, 300);
        layeredPane.add(produtoPanel);
        layeredPane.repaint();
    }
}
