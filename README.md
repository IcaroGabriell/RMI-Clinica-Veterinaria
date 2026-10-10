# Sistema de Gestão de Clínica Veterinária

Projeto desenvolvido para a disciplina de **Sistemas Distribuídos**, do curso de Engenharia de Computação da UEMG – Divinópolis.

O sistema é uma aplicação distribuída em Java, em que um cliente com interface gráfica se comunica com um servidor por meio de **RMI**, e o servidor armazena os dados em um banco **MySQL**.

## Tecnologias

- Java
- RMI
- MySQL
- JDBC

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

## Autores

**Ícaro Gabriel dos Santos**  
**Raphael Muro Pimentel**

Engenharia de Computação — UEMG Divinópolis  
2026
