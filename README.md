# Mecanica
Um sistema de mecanica de veículos desenvolvido com JavaFx com o padrão de organização MVC.

## Arquitetura do Projeto
O projeto utiliza a arquitetura MVC e o modelo DAO para o acesso ao banco de dados. Sendo assim, o projeto está sendo estruturado através das seguintes pastas:
 - **mecanica/view**: Nesta pasta estão estruturados os arquivos *fxml* da aplicação. Estes arquivos são responsáveis pelas telas mostradas no sistema.
 - **mecanica/controller**: Nesta pasta estão estruturados os arqivos *java* que comunicam diretamente com a camada *view* e *model*.
 - **mecanica/model**: Nesta pasta estão as classes que estruturam as regras de negócio do sistema. Estas classes lidam diretamente com a persistência de dados da aplicação.
 - **mecanica/DAO**: Classes com funções que acessam o banco de dados diretamente.

Para uma melhor visualização da estrutura do projeto, considere a utilização do *NetBeans*.

## Dúvidas e Erros
Caso encontre algum erro ou possua alguma dúvida sobre o sistema, abra um tópico na aba *issues* a qual pode-se usar diversas ferramentas como imagens, pedaços de código e etc.

## Contribuir
Para quanquer contribuição com o projeto, abra um *Pull Request* com as suas modificações desejadas.