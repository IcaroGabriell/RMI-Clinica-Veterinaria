import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Classe que inicia o servidor RMI
public class Servidor {

    // IP da máquina do servidor na rede
    private static final String IP_SERVIDOR = "127.0.0.1";

    /*
    IAG: Ferramenta: Claude (Anthropic)
    Prompt: "Faça comentários pontuais pelo código para facilitar a explicação do código depois. Verifique e corrija os erros que estão  dando."
    Validação humana: Após cada correção, compilei e executei o programa, rodei em uma máquina e o cliente em outra e conectou
     */
    public static void main(String[] args) {
        try {
            // necessário para o cliente da outra máquina conseguir chamar o servidor
            System.setProperty("java.rmi.server.hostname", IP_SERVIDOR);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Servico", new ServicoImpl());

            System.out.println("Servidor rodando...");
        } catch (Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}
