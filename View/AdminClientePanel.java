package View;

import Model.DAO.ClientesDAO;
import Exceptions.ContaExceptions;
import Model.RN.ClienteRN;
import Model.VO.Cliente;

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

public class AdminClientePanel extends JPanel implements DocumentListener, ActionListener {
    static JLayeredPane layeredPane;
    static JPanel panel;
    JPanel overallPanel;
    JLabel pesquisaLabel;
    JTextField pesquisaTextF;
    JPanel pesquisaPanel;
    JButton pesquisaButton;
    JPanel sugestoesPanel;
    static JPanel clientePanel;
    static JButton alterarButton;
    JButton excluirButton;
    JButton historicoButton;
    static int alterarStatus;
    static Cliente cliente;

    public AdminClientePanel(){
        panel = new JPanel();
        layeredPane = new JLayeredPane();
        overallPanel = new JPanel();
        clientePanel = new JPanel();
        pesquisaLabel = new JLabel("Cliente: ");
        pesquisaTextF = new JTextField();
        pesquisaPanel = new JPanel();
        pesquisaButton = new JButton("Pesquisar");
        sugestoesPanel = new JPanel();
        alterarButton = new JButton("Alterar");
        excluirButton = new JButton("Excluir");
        historicoButton = new JButton("Histórico");
        alterarStatus = 0;
        cliente = null;

        panel.setPreferredSize(new Dimension(900, 550));
        panel.setLayout(new BorderLayout());
        clientePanel.setLayout(new BorderLayout());
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
        historicoButton.addActionListener(this);

        pesquisaPanel.setBounds(140, 50, 600, 50);
        clientePanel.setBounds(150, 100, 600, 300);
        alterarButton.setBounds(250, 420, 100, 40);
        excluirButton.setBounds(400, 420, 100, 40);
        historicoButton.setBounds(550, 420, 100, 40);

        sugestoesPanel.setVisible(false);

        sugestoesPanel.setBackground(Color.WHITE);
        clientePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        clientePanel.setBackground(Color.LIGHT_GRAY);

        pesquisaPanel.add(pesquisaLabel);
        pesquisaPanel.add(pesquisaTextF);
        pesquisaPanel.add(pesquisaButton);

        layeredPane.add(pesquisaPanel, Integer.valueOf(0));
        layeredPane.add(clientePanel, Integer.valueOf(0));
        layeredPane.add(alterarButton, Integer.valueOf(0));
        layeredPane.add(excluirButton, Integer.valueOf(0));
        layeredPane.add(historicoButton, Integer.valueOf(0));
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
                for(String nome : ClientesDAO.getNomes()){
                    if(nome.length()>=pesquisaTextF.getText().length()) {
                        if (nome.substring(0, pesquisaTextF.getText().length()).equalsIgnoreCase(pesquisaTextF.getText()) && !nome.equals("Admin")) {
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
                cliente = ClienteRN.searchClienteRN(pesquisaTextF.getText());
                alterarClientePanel(new AdminClienteInfoPanel(cliente).getPanel());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ContaExceptions.UsuarioInexistenteException ex) {
                JOptionPane.showMessageDialog(null, "Nenhum usuário selecionado", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            sugestoesPanel.setVisible(false);
        }
        if(e.getSource()==alterarButton){
            try {
                AdminClienteInfoPanel.alterarClienteInfoPanel();
                panel.repaint();
                alterarStatus++;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "Erro ao alterar dados", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Dados Inconsistentes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==excluirButton && alterarStatus%2 == 0){
            if (JOptionPane.showConfirmDialog(panel, "Deseja excluir a conta desse cliente?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                try {
                    ClienteRN.deleteClienteRN(cliente);
                    pesquisaTextF.setText("");
                    clientePanel.removeAll();
                    panel.repaint();
                    JOptionPane.showMessageDialog(panel, "Cliente excluído om sucesso", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Nenhum usuário selecionado", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if(e.getSource()==historicoButton && alterarStatus%2 == 0){
            try {
                alterarClientePanel(new MinhasComprasPanel(cliente).getPanel());
            } catch (ContaExceptions.NomeInvalidoException | SQLException ex) {
                throw new RuntimeException(ex);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Nenhum usuário selecionado", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void alterarClientePanel(JPanel newPanel){
        layeredPane.remove(clientePanel);
        clientePanel = newPanel;
        clientePanel.setBounds(150, 100, 600, 300);
        layeredPane.add(clientePanel);
        layeredPane.repaint();
    }
}
