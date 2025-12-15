package View;

import Model.DAO.CartoesClientesDAO;
import Model.DAO.EnderecoDAO;
import Exceptions.ContaExceptions;
import Model.RN.ClienteRN;
import Model.VO.Cartao;
import Model.VO.Cliente;
import Model.VO.Endereco;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class AdminClienteInfoPanel extends JPanel implements ActionListener{
    static JPanel panel;
    static JPanel nomePanel;
    static JLabel nomeLabel;
    static JTextField nomeTextF;
    static JPanel cpfPanel;
    static JLabel cpfLabel;
    static JTextField cpfTextF;
    static JPanel dataDeNascimentoPanel;
    static JLabel dataDeNascimentoLabel;
    static JDateChooser jDateChooser;
    static JPanel sexoPanel;
    static JLabel sexoLabel;
    static ButtonGroup sexoButtons;
    static JRadioButton masculinoButton;
    static JRadioButton femininoButton;
    static JPanel senhaPanel;
    static JLabel senhaLabel;
    static JPasswordField senhaTextF;
    JPanel enderecosPanel;
    JLabel enderecosLabel;
    JComboBox<String> enderecosComboBox;
    JPanel cartoesPanel;
    JLabel cartoesLabel;
    JComboBox<String> cartoesComboBox;
    Font labelFont = new Font("SansSerif", Font.BOLD, 16);
    Font textfFont = new Font("SansSerif", Font.PLAIN, 16);
    static String sexo = null;
    static Cliente cliente = null;

    public AdminClienteInfoPanel(Cliente cliente) throws SQLException, ContaExceptions.UsuarioInexistenteException {
        this.cliente = cliente;
        if(cliente == null){
            throw new ContaExceptions.UsuarioInexistenteException("Nenhum usuário selecionado");
        }
        panel = new JPanel();
        nomePanel = new JPanel();
        nomeLabel = new JLabel("Nome: " + cliente.getNome());
        nomeTextF = new JTextField(cliente.getNome());
        cpfPanel = new JPanel();
        cpfLabel = new JLabel("CPF: " + cliente.getCPF());
        cpfTextF = new JTextField(cliente.getCPF());
        dataDeNascimentoPanel = new JPanel();
        dataDeNascimentoLabel = new JLabel("Data de Nascimento: " + new SimpleDateFormat("dd/MM/yyyy").format(cliente.getDataDeNascimento()));
        jDateChooser = new JDateChooser(cliente.getDataDeNascimento());
        sexoPanel = new JPanel();
        sexoLabel = new JLabel("Sexo: " + cliente.getSexo());
        sexoButtons = new ButtonGroup();
        masculinoButton = new JRadioButton("Masculino");
        femininoButton = new JRadioButton("Feminino");
        senhaPanel = new JPanel();
        senhaLabel = new JLabel("Senha: ");
        for(int i=0; i<cliente.getSenha().length(); i++){
            senhaLabel.setText(senhaLabel.getText()+"*");
        }
        senhaTextF = new JPasswordField(cliente.getSenha());
        enderecosPanel = new JPanel();
        enderecosLabel = new JLabel("Endereços: ");
        ArrayList<Endereco> enderecos = EnderecoDAO.getEnderecosClienteDAO(cliente);
        String[] enderecosStrings = new String[enderecos.size()];
        for(int i = 0; i< enderecosStrings.length; i++){
            enderecosStrings[i] = enderecos.get(i).toString();
        }
        enderecosComboBox = new JComboBox<>(enderecosStrings);
        cartoesPanel = new JPanel();
        cartoesLabel = new JLabel("Cartões: ");
        ArrayList<Cartao> cartoes = CartoesClientesDAO.getCartoesCliente(cliente);
        String[] cartoesStrings = new String[cartoes.size()];
        for(int i = 0; i< cartoesStrings.length; i++){
            cartoesStrings[i] = cartoes.get(i).toString();
        }
        cartoesComboBox = new JComboBox<>(cartoesStrings);

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setPreferredSize(new Dimension(600, 300));
        nomePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        nomePanel.setPreferredSize(new Dimension(500, 40));
        cpfPanel.setLayout(new FlowLayout());
        cpfPanel.setPreferredSize(new Dimension(500, 40));
        dataDeNascimentoPanel.setLayout(new FlowLayout());
        dataDeNascimentoPanel.setPreferredSize(new Dimension(500, 40));
        sexoPanel.setLayout(new FlowLayout());
        sexoPanel.setPreferredSize(new Dimension(500, 40));
        senhaPanel.setLayout(new FlowLayout());
        senhaPanel.setPreferredSize(new Dimension(500, 40));
        enderecosPanel.setLayout(new FlowLayout());
        enderecosPanel.setPreferredSize(new Dimension(500, 40));
        enderecosComboBox.setPreferredSize(new Dimension(350, 20));
        cartoesPanel.setLayout(new FlowLayout());
        cartoesPanel.setPreferredSize(new Dimension(500, 40));
        cartoesComboBox.setPreferredSize(new Dimension(350, 20));
        jDateChooser.setDateFormatString("dd/MM/yyyy");
        jDateChooser.setPreferredSize(new Dimension(120, 25));
        masculinoButton.addActionListener(this);
        femininoButton.addActionListener(this);
        nomeLabel.setFont(labelFont);
        cpfLabel.setFont(labelFont);
        sexoLabel.setFont(labelFont);
        dataDeNascimentoLabel.setFont(labelFont);
        senhaLabel.setFont(labelFont);
        enderecosLabel.setFont(labelFont);
        cartoesLabel.setFont(labelFont);
        jDateChooser.setFont(labelFont);
        nomeTextF.setFont(textfFont);
        nomeTextF.setPreferredSize(new Dimension(200, 25));
        cpfTextF.setFont(textfFont);
        cpfTextF.setPreferredSize(new Dimension(200, 25));
        jDateChooser.setFont(textfFont);
        femininoButton.setFont(textfFont);
        masculinoButton.setFont(textfFont);
        senhaTextF.setFont(textfFont);
        senhaTextF.setPreferredSize(new Dimension(200, 25));

        sexoButtons.add(femininoButton);
        sexoButtons.add(masculinoButton);

        if(Objects.equals(cliente.getSexo(), "Masculino")){
            masculinoButton.setSelected(true);
            femininoButton.setSelected(false);
            sexo = "Masculino";
        }else{
            femininoButton.setSelected(true);
            masculinoButton.setSelected(false);
            sexo = "Feminino";
        }

        nomePanel.add(nomeLabel);
        cpfPanel.add(cpfLabel);
        dataDeNascimentoPanel.add(dataDeNascimentoLabel);
        sexoPanel.add(sexoLabel);
        senhaPanel.add(senhaLabel);
        enderecosPanel.add(enderecosLabel);
        enderecosPanel.add(enderecosComboBox);
        cartoesPanel.add(cartoesLabel);
        cartoesPanel.add(cartoesComboBox);

        panel.add(nomePanel);
        panel.add(cpfPanel);
        panel.add(dataDeNascimentoPanel);
        panel.add(sexoPanel);
        panel.add(senhaPanel);
        panel.add(enderecosPanel);
        panel.add(cartoesPanel);

        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel.setBackground(Color.LIGHT_GRAY);
        for(Component c : panel.getComponents()){
            c.setBackground(Color.LIGHT_GRAY);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void alterarClienteInfoPanel() throws SQLException, ContaExceptions.NomeInvalidoException, ContaExceptions.SexoInvalidoException, ContaExceptions.CPFInvalidoException, ContaExceptions.SenhaInvalidaException, ContaExceptions.DtNascInvalidaException, ContaExceptions.UsuarioInexistenteException {
        if(AdminClientePanel.alterarStatus%2==0) {
            if(cliente == null){
                throw new ContaExceptions.UsuarioInexistenteException("Nenhum usuário selecionado");
            }
            nomeLabel.setText("Nome: ");
            nomePanel.add(nomeTextF);
            cpfLabel.setText("CPF: ");
            cpfPanel.add(cpfTextF);
            dataDeNascimentoLabel.setText("Data de Nascimento: ");
            dataDeNascimentoPanel.add(jDateChooser);
            sexoLabel.setText("Sexo: ");
            sexoPanel.add(masculinoButton);
            sexoPanel.add(femininoButton);
            senhaLabel.setText("Senha: ");
            senhaPanel.add(senhaTextF);
            AdminClientePanel.alterarButton.setText("Finalizar Alteração");
            AdminClientePanel.alterarButton.setBounds(200, 420, 150, 40);
        }else{
            ClienteRN.updateClienteRN(new Cliente(
                    cliente.getId(),
                    nomeTextF.getText(),
                    cpfTextF.getText(),
                    new Date(jDateChooser.getCalendar().getTimeInMillis()),
                    sexo,
                    new String(senhaTextF.getPassword())
            ));
            cliente = ClienteRN.searchClienteRN(cliente.getId());
            nomeLabel.setText("Nome: " + cliente.getNome());
            nomePanel.remove(nomeTextF);
            cpfLabel.setText("CPF: " + cliente.getCPF());
            cpfPanel.remove(cpfTextF);
            dataDeNascimentoLabel.setText("Data de Nascimento: " + new SimpleDateFormat("dd/MM/yyyy").format(cliente.getDataDeNascimento()));
            dataDeNascimentoPanel.remove(jDateChooser);
            sexoLabel.setText("Sexo: " + cliente.getSexo());
            sexoPanel.remove(masculinoButton);
            sexoPanel.remove(femininoButton);
            senhaLabel.setText("Senha: ");
            for(int i=0; i<cliente.getSenha().length(); i++){
                senhaLabel.setText(senhaLabel.getText()+"*");
            }
            senhaPanel.remove(senhaTextF);
            AdminClientePanel.alterarButton.setBounds(250, 420, 100, 40);
            AdminClientePanel.alterarButton.setText("Alterar");
        }
        panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==femininoButton){
            sexo = "Feminino";
        }
        if(e.getSource()==masculinoButton){
            sexo = "Masculino";
        }
    }
}
