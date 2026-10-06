import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/*
 * IAG: Ferramenta: Claude (Anthropic)
 * Usos nesta classe:
 *   1 - Correção de erros
 *   2 - Geração de alguns trechos em que tive dificuldade.
 *   3 - Criação dos comentários explicativos ao longo do código.
 * Prompts:
 *   1 "Verifique e corrija os erros que estão dando."
 *   2 "Crie um método que execute um INSERT no MySQL"
 *   3 "Comente o código para facilitar a explicação depois."
 *   4 "Erro No suitable driver found for jdbc:mysql... como resolver no VS Code?"
 * Validação humana: Executei e testei as funções pela tela e conferi os dados no MySQL. 
 * Li os comentários e conferi se descrevem o que o código realmente faz.
 * Segui a orientação, adicionei o .jar ao projeto e conferi que a conexão com o banco passou a funcionar.
 */




// Classe que roda no servidor: recebe os pedidos do cliente e acessa o banco MySQL
public class ServicoImpl extends UnicastRemoteObject implements Servico {

    // Dados de conexão com o banco (ajustar usuário e senha do MySQL)
    private static final String URL = "jdbc:mysql://localhost:3306/clinica_vet";
    private static final String USUARIO = "root";
    private static final String SENHA = "senha";

    
    public ServicoImpl() throws RemoteException {
        super(1099); // usa a mesma porta do registry (facilita o firewall)
    }

    /*
     * IAG: Ferramenta: Claude (Anthropic)
     * Prompt: "Crie um método que execute um INSERT no MySQL com PreparedStatement."
     * Validação humana: Executei e testei as funções pela tela e conferi os dados no MySQL
     */
    private String inserir(String sql, String... valores) {
        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement ps = con.prepareStatement(sql)) {

            // coloca cada valor no lugar de uma interrogação do SQL
            for (int i = 0; i < valores.length; i++) {
                ps.setString(i + 1, valores[i]);
            }
            ps.executeUpdate();
            return "Cadastrado com sucesso!";

        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
    }

    /*método que execute um SELECT e devolva o resultado como texto*/
    private String listar(String sql) {
        StringBuilder texto = new StringBuilder();
        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            int colunas = rs.getMetaData().getColumnCount();

            // monta uma linha de texto para cada registro
            while (rs.next()) {
                for (int i = 1; i <= colunas; i++) {
                    texto.append(rs.getString(i)).append(" | ");
                }
                texto.append("\n");
            }

        } catch (SQLException e) {
            return "Erro: " + e.getMessage();
        }
        return texto.toString();
    }

    /*método que cadastra um tutor chamando o método inserir*/
    public String cadastrarTutor(String nome, String telefone) {
        return inserir("INSERT INTO tutor (nome, telefone) VALUES (?, ?)", nome, telefone);
    }

    /*método que lista os tutores chamando o método listar*/
    public String listarTutores() {
        return listar("SELECT id, nome, telefone FROM tutor");
    }

    /*método que cadastra um animal ligado a um tutor*/
    public String cadastrarAnimal(String nome, String especie, String idTutor) {
        return inserir("INSERT INTO animal (nome, especie, id_tutor) VALUES (?, ?, ?)",
                nome, especie, idTutor);
    }

    /*método que lista os animais*/
    public String listarAnimais() {
        return listar("SELECT id, nome, especie, id_tutor FROM animal");
    }

    /*método que cadastra um veterinário*/
    public String cadastrarVeterinario(String nome, String crmv) {
        return inserir("INSERT INTO veterinario (nome, crmv) VALUES (?, ?)", nome, crmv);
    }

    /*método que lista os veterinários*/
    public String listarVeterinarios() {
        return listar("SELECT id, nome, crmv FROM veterinario");
    }

    /*método que cadastra uma consulta ligada a um animal e a um veterinário*/
    public String cadastrarConsulta(String data, String idAnimal, String idVeterinario) {
        return inserir("INSERT INTO consulta (data_consulta, id_animal, id_veterinario) VALUES (?, ?, ?)",
                data, idAnimal, idVeterinario);
    }

    /*método que lista as consultas.*/
    public String listarConsultas() {
        return listar("SELECT id, data_consulta, id_animal, id_veterinario FROM consulta");
    }

    /*método que cadastra um tratamento ligado a uma consulta.*/
    public String cadastrarTratamento(String descricao, String idConsulta) {
        return inserir("INSERT INTO tratamento (descricao, id_consulta) VALUES (?, ?)",
                descricao, idConsulta);
    }

    /* método que lista os tratamentos*/
    public String listarTratamentos() {
        return listar("SELECT id, descricao, id_consulta FROM tratamento");
    }
}
