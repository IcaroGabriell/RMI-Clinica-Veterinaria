import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Classe do cliente com a tela (rodar na máquina do cliente)
public class Cliente {

    // IP da máquina do servidor
    private static final String IP_SERVIDOR = "127.0.0.1";

    private static Servico servico;

    // Pequena interface para guardar a chamada remota que o botão vai executar
    interface Chamada {
        String executar() throws Exception;
    }

    /*
    * IAG: Ferramenta: Claude (Anthropic)
    * Prompt: "Faça comentários pontuais pelo código para facilitar a explicação do código depois."
    * "Verifique e corrija os erros que estão dando."
    * Validação humana: Após cada correção, compilei e executei o programa,
    *   testei as funções pela tela e conferi os dados no MySQL.
    */
    private static void chamar(JTextArea saida, Chamada chamada) {
        try {
            saida.setText(chamada.executar());
        } catch (Exception e) {
            saida.setText("Erro: " + e.getMessage());
        }
    }

    /*
     * IAG: Ferramenta: Claude (Anthropic)
     * Prompt: "Crie um método que monte o painel de uma aba com campos de texto,
     *          botões Cadastrar e Listar e uma área de texto para o resultado."
     * Validação humana: Li o código, entendi o que cada parte faz e testei
     */
    private static JPanel montarAba(String[] rotulos, JTextField[] campos,
                                    JButton cadastrar, JButton listar, JTextArea saida) {
        // painel com os rótulos e os campos
        JPanel formulario = new JPanel(new GridLayout(0, 2));
        for (int i = 0; i < rotulos.length; i++) {
            campos[i] = new JTextField();
            formulario.add(new JLabel(rotulos[i]));
            formulario.add(campos[i]);
        }
        formulario.add(cadastrar);
        formulario.add(listar);

        // junta o formulário (em cima) com a área de resultado (embaixo)
        JPanel aba = new JPanel(new BorderLayout());
        aba.add(formulario, BorderLayout.NORTH);
        aba.add(new JScrollPane(saida), BorderLayout.CENTER);
        return aba;
    }

    /*
     * IAG: Ferramenta: Claude (Anthropic)
     * Prompt: "Verifique e corrija os erros que estão dando."
     * Validação humana: testei cadastrar e listar em todas as abas
     */
    public static void main(String[] args) throws Exception {

        // conecta no servidor RMI
        Registry registry = LocateRegistry.getRegistry(IP_SERVIDOR, 1099);
        servico = (Servico) registry.lookup("Servico");

        JTabbedPane abas = new JTabbedPane();

        // ----- Aba Tutor -----
        JTextField[] t = new JTextField[2];
        JButton tCadastrar = new JButton("Cadastrar");
        JButton tListar = new JButton("Listar");
        JTextArea tSaida = new JTextArea(10, 30);
        abas.add("Tutor", montarAba(new String[]{"Nome", "Telefone"}, t, tCadastrar, tListar, tSaida));
        tCadastrar.addActionListener(e -> chamar(tSaida,
                () -> servico.cadastrarTutor(t[0].getText(), t[1].getText())));
        tListar.addActionListener(e -> chamar(tSaida, () -> servico.listarTutores()));

        // ----- Aba Animal -----
        JTextField[] a = new JTextField[3];
        JButton aCadastrar = new JButton("Cadastrar");
        JButton aListar = new JButton("Listar");
        JTextArea aSaida = new JTextArea(10, 30);
        abas.add("Animal", montarAba(new String[]{"Nome", "Espécie", "ID do Tutor"}, a, aCadastrar, aListar, aSaida));
        aCadastrar.addActionListener(e -> chamar(aSaida,
                () -> servico.cadastrarAnimal(a[0].getText(), a[1].getText(), a[2].getText())));
        aListar.addActionListener(e -> chamar(aSaida, () -> servico.listarAnimais()));

        // ----- Aba Veterinário -----
        JTextField[] v = new JTextField[2];
        JButton vCadastrar = new JButton("Cadastrar");
        JButton vListar = new JButton("Listar");
        JTextArea vSaida = new JTextArea(10, 30);
        abas.add("Veterinário", montarAba(new String[]{"Nome", "CRMV"}, v, vCadastrar, vListar, vSaida));
        vCadastrar.addActionListener(e -> chamar(vSaida,
                () -> servico.cadastrarVeterinario(v[0].getText(), v[1].getText())));
        vListar.addActionListener(e -> chamar(vSaida, () -> servico.listarVeterinarios()));

        // ----- Aba Consulta -----
        JTextField[] c = new JTextField[3];
        JButton cCadastrar = new JButton("Cadastrar");
        JButton cListar = new JButton("Listar");
        JTextArea cSaida = new JTextArea(10, 30);
        abas.add("Consulta", montarAba(new String[]{"Data (AAAA-MM-DD)", "ID do Animal", "ID do Veterinário"},
                c, cCadastrar, cListar, cSaida));
        cCadastrar.addActionListener(e -> chamar(cSaida,
                () -> servico.cadastrarConsulta(c[0].getText(), c[1].getText(), c[2].getText())));
        cListar.addActionListener(e -> chamar(cSaida, () -> servico.listarConsultas()));

        // ----- Aba Tratamento -----
        JTextField[] r = new JTextField[2];
        JButton rCadastrar = new JButton("Cadastrar");
        JButton rListar = new JButton("Listar");
        JTextArea rSaida = new JTextArea(10, 30);
        abas.add("Tratamento", montarAba(new String[]{"Descrição", "ID da Consulta"}, r, rCadastrar, rListar, rSaida));
        rCadastrar.addActionListener(e -> chamar(rSaida,
                () -> servico.cadastrarTratamento(r[0].getText(), r[1].getText())));
        rListar.addActionListener(e -> chamar(rSaida, () -> servico.listarTratamentos()));

        // monta a janela
        JFrame janela = new JFrame("Clínica Veterinária");
        janela.add(abas);
        janela.pack();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }
}
