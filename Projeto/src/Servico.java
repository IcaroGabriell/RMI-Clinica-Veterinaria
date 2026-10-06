import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * IAG: Ferramenta: Claude (Anthropic)
 * Prompt: "Corrija os erros"
 * Validação humana: conferi se todos os métodos lançam RemoteException
 */

// Interface remota: lista o que o cliente pode pedir para o servidor
public interface Servico extends Remote {

    String cadastrarTutor(String nome, String telefone) throws RemoteException;
    String listarTutores() throws RemoteException;

    String cadastrarAnimal(String nome, String especie, String idTutor) throws RemoteException;
    String listarAnimais() throws RemoteException;

    String cadastrarVeterinario(String nome, String crmv) throws RemoteException;
    String listarVeterinarios() throws RemoteException;

    String cadastrarConsulta(String data, String idAnimal, String idVeterinario) throws RemoteException;
    String listarConsultas() throws RemoteException;

    String cadastrarTratamento(String descricao, String idConsulta) throws RemoteException;
    String listarTratamentos() throws RemoteException;
}
