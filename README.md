# fa7-clientes-spring-boot
Especificação da API
A API Rest é capaz de cadastrar os clientes (CRUD) com os seguintes dados:
* nome (String)
* email (String)
* login (string)

A API também da suporte para ao cadastro dos endereços do cliente. Um cliente pode ter mais de um endereço.
Os atributos do endereço são:
* logradouro (string)
* complemento (string)
* cep (string)
API permite a navegação dos dados dessa forma:

/clientes
/clientes/:id/enderecos
/clientes/:id/enderecos/:eid
Ambos, cliente e endereço, pussuem ids gerados automaticamente.

A implementação foi feita com Spring, Spring Boot, Jersey, JPA e HSQLDb.
