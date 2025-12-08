Gerenciamento de Autores e Obras

Construa uma api para gerenciamento de autores e obras. Implemente um CRUD para
cadastro de obras relacionados aos autores e também para cadastro de autores.

Requisitos:

● Relacione Autores e Obras conforme as regras:

○ Um autor pode ter várias obras.

○ Uma obra pode ter vários autores.

Entidades:

1. Autor:
   
○ Nome (obrigatório)

○ Sexo

○ E-mail (validado, único)

○ Data de nascimento (validada)

○ País de origem (obrigatório)

○ CPF (obrigatório para autores do Brasil, único)

2. Obra:

○ Nome (obrigatório)

○ Descrição (máximo 240 caracteres)

○ Data de publicação ou data de exposição (uma é obrigatória).

Extras:

● Implemente autenticação com JWT.

● Desenvolva testes unitários para CRUD das entidades.
