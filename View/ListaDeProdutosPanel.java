package View;

import Model.DAO.ProdutosDAO;
import Exceptions.ContaExceptions;
import Exceptions.EntradaInvalidaException;
import Exceptions.ProdutoRepetidoException;
import Model.VO.Carrinho;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ListaDeProdutosPanel extends JPanel{
    JPanel panel;
    JScrollPane scrollPane;
    GridBagConstraints gbc = new GridBagConstraints();
    Carrinho carrinho = MenuPrincipal.getCarrinho();

    public ListaDeProdutosPanel(String categoria) throws SQLException {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
//        scrollPane.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        layeredPane.setBorder(BorderFactory.createLineBorder(Color.blue, 2));

        AtomicInteger x = new AtomicInteger();
        AtomicInteger y = new AtomicInteger();
        gbc.insets = new Insets(2, 2, 2, 2);
        ProdutosDAO.getListaDeProdutosDAO().forEach((produto) -> {
            if(Objects.equals(produto.getCategoria(), categoria)) {
                alterarGbc(x.get(), y.get());
                JPanel temp = new ProdutoPanel(produto).getPanel();
                temp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            if(carrinho.verificarCarrinho(produto.getDescricao())){
                                throw new ProdutoRepetidoException("Produto já adicionado ao carrinho");
                            }
                            if(MenuPrincipal.getUsuario()==null){
                                throw new ContaExceptions.UsuarioInexistenteException("Nenhum usuário vinculado");
                            }
                            double qtd = Double.parseDouble(JOptionPane.showInputDialog(MenuPrincipal.currentFrame, "Insira a quantidade desejada:  (" + produto.getMedida() + ")", "Comprar produto", JOptionPane.QUESTION_MESSAGE));
                            if (qtd <= 0 || Objects.equals(produto.getMedida(), "un") && qtd!=Math.round(qtd)) {
                                throw new EntradaInvalidaException("Quantidade Inválida");
                            }
                            carrinho.addProdutoCarrinho(produto, qtd);
                        }catch (EntradaInvalidaException | ProdutoRepetidoException | ContaExceptions.UsuarioInexistenteException exc) {
                            JOptionPane.showMessageDialog(MenuPrincipal.currentFrame, exc.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }catch (NumberFormatException exc){
                            JOptionPane.showMessageDialog(MenuPrincipal.currentFrame, "Entrada Inválida", "Erro", JOptionPane.ERROR_MESSAGE);
                        } catch (NullPointerException _) {

                        }
                        catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                });
                panel.add(temp, gbc);

                x.getAndIncrement();
                if(x.get() == 3){
                    x.set(0);
                    y.getAndIncrement();
                }

            }
        });
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }
}
