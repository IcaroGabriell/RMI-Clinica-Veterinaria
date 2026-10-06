# RMI - Clínica Veterinária
Trabalho 1 - Sistemas Distribuídos (UEMG)

Projeto acadêmico de um sistema de gestão de clínica veterinária em Java com RMI e MySQL. O cliente tem interface gráfica e se comunica com o servidor, que acessa o banco de dados.

## Estrutura

- `src/Servico.java`: interface remota (compartilhada entre cliente e servidor)
- `src/ServicoImpl.java`: implementação do serviço e acesso ao banco (servidor)
- `src/Servidor.java`: inicia o servidor RMI
- `src/Cliente.java`: interface gráfica do cliente
- `lib/`: conector JDBC do MySQL
- `banco`: script de criação do banco de dados

## Como executar

1. Importe o script `banco` no MySQL.
2. Ajuste usuário e senha do banco em `ServicoImpl.java`.
3. Na máquina do servidor, execute `Servidor`.
4. Na máquina do cliente, ajuste o IP do servidor em `Cliente.java` e execute `Cliente`.