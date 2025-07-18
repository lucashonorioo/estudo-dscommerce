<h1 align="center"> API E-Commerce </h1>

<h2> 📖 Descrição do projeto </h2>

DSCommerce é uma API REST desenvolvida em Java com Spring Boot, voltada para atender a operações de comércio eletrônico.

Durante a construção do back end do DSCommerce, foram implementadas as seguintes funcionalidades principais:

✅ Consulta de catálogo: Permite listar produtos, com possibilidade de filtros, para que usuários encontrem itens para adicionar ao carrinho de compras.

✅ Login: Autenticação via token JWT, onde o usuário informa suas credenciais e recebe um token de acesso com permissões baseadas nos seus perfis.

✅ Manter produtos: CRUD completo (Criar, Recuperar, Atualizar, Deletar) para gerenciar produtos. Esta funcionalidade é destinada à área administrativa.

✅ Registrar pedido: Registra pedidos no sistema a partir das informações do carrinho de compras do usuário.


<h2> 🛠️ Tecnologias e Ferramentas Utilizadas </h2>

 * Java 21

* Spring Boot

* IntelliJ IDEA Community

* Postman (para testes de requisição)

- Banco de Dados:

* H2 (perfil de test)

* PostgreSQL (perfil de dev)

<h2> 📌 Endpoints e Casos de Uso </h2>

**URL base:** `https://localhost:8080/`

| 📝 Caso de Uso          | 📄 Descrição                                                                 | 🔒 Acesso           |
|--------------------------|------------------------------------------------------------------------------|---------------------|
| **Manter Produtos**      | CRUD de produtos, podendo filtrar itens pelo nome                            | Somente Admin       |
| **Manter Categorias**    | CRUD de categorias, podendo filtrar itens pelo nome                          | Somente Admin       |
| **Manter Usuários**      | CRUD de usuários, podendo filtrar itens pelo nome                             | Somente Admin       |
| **Gerenciar Carrinho**   | Incluir, remover itens do carrinho e alterar quantidades de produtos          | Usuário Logado      |
| **Consultar Catálogo**   | Listar produtos disponíveis, podendo filtrar produtos pelo nome               | Público              |
| **Sign Up**              | Cadastro no sistema                                                          | Público              |
| **Login**                | Efetuar login e obter token de acesso JWT                                    | Público              |
| **Registrar Pedido**     | Salvar um pedido a partir do carrinho de compras                             | Usuário Logado      |
| **Atualizar Perfil**     | Atualizar o próprio cadastro e perfil                                        | Usuário Logado      |
| **Visualizar Pedidos**   | Visualizar todos os pedidos realizados pelo próprio usuário                  | Usuário Logado      |
| **Registrar Pagamento**  | Salvar os dados de pagamento de um pedido                                    | Usuário Logado      |
| **Reportar Pedidos**     | Relatório de pedidos, podendo filtrar por data                               | Somente Admin       |


<h2> 🚀 Funcionalidades Resumidas </h2>

* 🔐 Autenticação JWT com Spring Security

* 🛒 Carrinho de Compras e Registro de Pedidos

* 📦 Catálogo Público de Produtos

* 🖥️ Painel Administrativo para CRUD de Produtos, Categorias e Usuários

* 📊 Relatórios Administrativos de Pedidos

* 🧾 Integração com Banco H2 e PostgreSQL

  <h2> 👨‍🏫 Créditos </h2>

Este projeto foi desenvolvido para fins educacionais com base nas aulas do Professor Nélio Alves através da plataforma DevSuperior.
Link: https://devsuperior.com.br/
