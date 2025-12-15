package View;

import Exceptions.ContaExceptions;
import Model.RN.ClienteRN;
import Model.VO.Cliente;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class PerfilPanel extends JPanel implements ActionListener {
    JPanel panel;
    JPanel nomePanel;
    JPanel sexoPanel;
    JPanel cpfPanel;
    JPanel dtNascPanel;
    JPanel senhaPanel;
    JPanel buttonsPanel;
    JLabel title;
    JLabel labelNome;
    JLabel labelDtNasc;
    JLabel labelSexo;
    JLabel labelCpf;
    JLabel labelSenha;
    JButton alterarButton;
    JButton excluirButton;
    JTextField textfNome;
    JTextField textfCpf;
    JPasswordField textfSenha;
    JRadioButton rbuttonM;
    JRadioButton rbuttonF;
    ButtonGroup sexButtons;
    JDateChooser jDateChooser;
    GridBagConstraints gbc = new GridBagConstraints();
    Font labelFont = new Font("Arial", Font.BOLD, 15);
    Font textfFont = new Font("Arial", Font.PLAIN, 15);
    int EditMode = 0;
    String sexo;

    public PerfilPanel() throws ContaExceptions.UsuarioInexistenteException {
        if(MenuPrincipal.getUsuario()==null){
            throw new ContaExceptions.UsuarioInexistenteException("Nenhum usuário vinculado");
        }
        sexo = MenuPrincipal.getUsuario().getSexo();
        panel = new JPanel();
        title = new JLabel("Perfil");
        labelNome = new JLabel("Nome: " + MenuPrincipal.getUsuario().getNome());
        labelDtNasc = new JLabel("Data de Nascimento: " + new SimpleDateFormat("dd/MM/yyyy").format(MenuPrincipal.getUsuario().getDataDeNascimento()));
        labelSexo = new JLabel("Sexo: " + MenuPrincipal.getUsuario().getSexo());
        labelCpf = new JLabel("CPF: " + MenuPrincipal.getUsuario().getCPF());
        labelSenha = new JLabel("Senha: ");
        for(int i=0; i<MenuPrincipal.getUsuario().getSenha().length(); i++){
            labelSenha.setText(labelSenha.getText() + "*");
        }
        textfNome = new JTextField(MenuPrincipal.getUsuario().getNome());
        textfNome.setPreferredSize(new Dimension(150, 18));
        textfNome.setFont(textfFont);
        textfCpf = new JTextField(MenuPrincipal.getUsuario().getCPF());
        textfCpf.setPreferredSize(new Dimension(150, 18));
        textfCpf.setFont(textfFont);
        textfSenha = new JPasswordField(MenuPrincipal.getUsuario().getSenha());
        textfSenha.setPreferredSize(new Dimension(150, 18));
        textfSenha.setFont(textfFont);
        jDateChooser = new JDateChooser(MenuPrincipal.getUsuario().getDataDeNascimento());
        jDateChooser.setFont(textfFont);
        jDateChooser.setDateFormatString("dd/MM/yyyy");
        jDateChooser.setPreferredSize(new Dimension(120, 19));
        sexButtons = new ButtonGroup();
        rbuttonF = new JRadioButton("Feminino");
        rbuttonF.setFont(textfFont);
        rbuttonF.addActionListener(this);
        rbuttonM = new JRadioButton("Masculino");
        rbuttonM.setFont(textfFont);
        rbuttonM.addActionListener(this);
        sexButtons.add(rbuttonM);
        sexButtons.add(rbuttonF);
        nomePanel = new JPanel();
        nomePanel.setLayout(new FlowLayout());
        sexoPanel = new JPanel();
        sexoPanel.setLayout(new FlowLayout());
        cpfPanel = new JPanel();
        cpfPanel.setLayout(new FlowLayout());
        dtNascPanel = new JPanel();
        dtNascPanel.setLayout(new FlowLayout());
        senhaPanel = new JPanel();
        senhaPanel.setLayout(new FlowLayout());
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        nomePanel.add(labelNome);
        sexoPanel.add(labelSexo);
        cpfPanel.add(labelCpf);
        dtNascPanel.add(labelDtNasc);
        senhaPanel.add(labelSenha);
        alterarButton = new JButton("Alterar");
        excluirButton = new JButton("Excluir");
        buttonsPanel.add(alterarButton);
        buttonsPanel.add(excluirButton);

        panel.setLayout(new GridBagLayout());
        title.setFont(new Font("Arial", Font.BOLD, 25));
        labelNome.setFont(labelFont);
        labelSenha.setFont(labelFont);
        labelSexo.setFont(labelFont);
        labelCpf.setFont(labelFont);
        labelDtNasc.setFont(labelFont);
        alterarButton.setPreferredSize(new Dimension(100, 30));
        alterarButton.addActionListener(this);
        excluirButton.setPreferredSize(new Dimension(100, 30));
        excluirButton.addActionListener(this);


        //Label Title
        alterarGbc(0, 0);
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 30, 0);
        panel.add(title, gbc);

        //Label Nome
        alterarGbc(0, 1);
        gbc.insets = new Insets(0 ,0, 8, 0);
        panel.add(nomePanel, gbc);

        //Label Sexo
        alterarGbc(0, 2);
        panel.add(sexoPanel, gbc);

        //Label CPF
        alterarGbc(0, 3);
        panel.add(cpfPanel, gbc);

        //Label DtNasc
        alterarGbc(0, 4);
        panel.add(dtNascPanel, gbc);

        //Label Senha
        alterarGbc(0, 5);
        panel.add(senhaPanel, gbc);

        //Buttons
        alterarGbc(0, 6);
        panel.add(buttonsPanel, gbc);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==alterarButton){
            if(EditMode%2==0) {
                //Label Nome
                labelNome.setText("Nome: ");
                nomePanel.add(textfNome);

                //Label Sexo
                labelSexo.setText("Sexo: ");
                sexoPanel.add(rbuttonM);
                sexoPanel.add(rbuttonF);
                if (Objects.equals(MenuPrincipal.getUsuario().getSexo(), "Masculino")) {
                    rbuttonM.setSelected(true);
                }else{
                    rbuttonF.setSelected(true);
                }

                //Label Cpf
                labelCpf.setText("CPF: ");
                cpfPanel.add(textfCpf);

                //Label DtNasc
                labelDtNasc.setText("Data de Nascimento: ");
                dtNascPanel.add(jDateChooser);

                //Label Senha
                labelSenha.setText("Senha: ");
                senhaPanel.add(textfSenha);

                alterarButton.setText("Finalizar Alteração");
                alterarButton.setPreferredSize(new Dimension(150, 30));
            }else {
                try {
                    ClienteRN.updateClienteRN(new Cliente(MenuPrincipal.getUsuario().getId(),
                            textfNome.getText(),
                            textfCpf.getText(),
                            new Date(jDateChooser.getCalendar().getTimeInMillis()),
                            sexo,
                            new String(textfSenha.getPassword())
                    ));

                    MenuPrincipal.setUsuario(Objects.requireNonNull(ClienteRN.searchClienteRN(MenuPrincipal.getUsuario().getId())));

                    //Label Nome
                    labelNome.setText("Nome: " + MenuPrincipal.getUsuario().getNome());
                    nomePanel.remove(textfNome);

                    //Label Sexo
                    labelSexo.setText("Sexo: " + MenuPrincipal.getUsuario().getSexo());
                    sexoPanel.remove(rbuttonM);
                    sexoPanel.remove(rbuttonF);

                    //Label Cpf
                    labelCpf.setText("CPF: " + MenuPrincipal.getUsuario().getCPF());
                    cpfPanel.remove(textfCpf);

                    //Label DtNasc
                    labelDtNasc.setText("Data de Nascimento: " + new SimpleDateFormat("dd/MM/yyyy").format(MenuPrincipal.getUsuario().getDataDeNascimento()));
                    dtNascPanel.remove(jDateChooser);

                    //Label Senha
                    labelSenha.setText("Senha: ");
                    for (int i = 0; i < MenuPrincipal.getUsuario().getSenha().length(); i++) {
                        labelSenha.setText(labelSenha.getText() + "*");
                    }
                    senhaPanel.remove(textfSenha);

                    alterarButton.setText("Alterar");
                    alterarButton.setPreferredSize(new Dimension(100, 30));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao alterar dados", "Erro", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Dados Inconsistentes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
            panel.revalidate();
            panel.repaint();
            EditMode++;
        }
        if(e.getSource()==rbuttonF){
            sexo = "Feminino";
        }
        if(e.getSource()==rbuttonM){
            sexo = "Masculino";
        }
        if(e.getSource()==excluirButton){
            try {
                if(JOptionPane.showConfirmDialog(null, "Deseja excluir seu login?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    ClienteRN.deleteClienteRN(MenuPrincipal.getUsuario());
                    MenuPrincipal.setUsuario(null);
                    MenuPrincipal.currentFrame.dispose();
                    new MenuPrincipal();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    public JPanel getPanel() {
        return panel;
    }
}
