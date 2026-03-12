# Sistema de Gerenciamento de Estoque e Ativos Patrimoniais — SENAI-SP

## Descrição
Sistema web desenvolvido com Spring Boot e Thymeleaf para gerenciar
materiais, movimentações de estoque e ativos patrimoniais de uma
unidade escolar do SENAI-SP.

---

## Requisitos Funcionais (RF)

| Código | Descrição |
|--------|-----------|
| RF01 | O sistema deve permitir o cadastro de funcionários mediante NIF autorizado |
| RF02 | O sistema deve permitir o login com NIF e senha |
| RF03 | O sistema deve permitir o logout |
| RF04 | O sistema deve permitir cadastrar, listar, editar e excluir categorias de materiais |
| RF05 | O sistema deve permitir cadastrar, listar, editar e excluir materiais |
| RF06 | Cada material deve estar vinculado a uma categoria |
| RF07 | O sistema deve permitir registrar movimentações de entrada e saída de estoque |
| RF08 | O sistema deve atualizar automaticamente a quantidade do material ao registrar uma movimentação |
| RF09 | O sistema deve impedir saída de estoque com quantidade maior do que a disponível |
| RF10 | O sistema deve permitir cadastrar, listar, editar e excluir ativos patrimoniais |
| RF11 | Somente usuários logados podem acessar a área interna do sistema |

---

## Requisitos Não Funcionais (RNF)

| Código | Descrição |
|--------|-----------|
| RNF01 | O sistema deve ser desenvolvido com Java 21 e Spring Boot 4 |
| RNF02 | O banco de dados utilizado é o PostgreSQL |
| RNF03 | A interface deve seguir a identidade visual do SENAI-SP |
| RNF04 | O sistema deve ser responsivo |
| RNF05 | As senhas devem ter no mínimo 4 caracteres |
| RNF06 | O acesso às páginas internas deve ser protegido por sessão |
| RNF07 | A arquitetura deve seguir o padrão MVC com camadas Model, Repository, Service e Controller |

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot 4
- Spring Data JPA
- Thymeleaf
- PostgreSQL
- Maven

---

## Como Rodar o Projeto

### Pré-requisitos
- Java JDK 21+
- PostgreSQL instalado e rodando
- VS Code com Extension Pack for Java e Spring Boot Extension Pack

### Configuração do Banco

1. Crie o banco de dados:
```sql
CREATE DATABASE estoque;
```

2. Insira um funcionário autorizado para poder se cadastrar:
```sql
INSERT INTO funcionarios_autenticados (nome, nif, ativo)
VALUES ('Seu Nome', 'SeuNIF', true);
```

### Configuração do `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/estoque
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
spring.thymeleaf.cache=false
```

### Rodando

1. Abra o projeto no VS Code
2. Rode a classe `GerenciamentoEstoqueApplication.java`
3. Acesse: http://localhost:8080

---

## Estrutura do Projeto
```
src/main/java/com/example/gerenciamento_estoque/
├── controller/
│   ├── AppController.java
│   ├── AtivoController.java
│   ├── AuthController.java
│   ├── CategoriaController.java
│   ├── HomeController.java
│   ├── MaterialController.java
│   └── MovimentacaoController.java
├── model/
│   ├── Ativo.java
│   ├── Categoria.java
│   ├── Funcionario.java
│   ├── FuncionarioAutenticado.java
│   ├── Material.java
│   └── Movimentacao.java
├── repository/
│   ├── AtivoRepository.java
│   ├── CategoriaRepository.java
│   ├── FuncionarioAutenticadoRepository.java
│   ├── FuncionarioRepository.java
│   ├── MaterialRepository.java
│   └── MovimentacaoRepository.java
└── service/
    ├── AtivoService.java
    ├── CategoriaService.java
    ├── FuncionarioService.java
    ├── MaterialService.java
    └── MovimentacaoService.java
```

---

## Schema do Banco de Dados
```
funcionarios_autenticados
├── id (PK)
├── nome
├── nif
└── ativo

funcionarios
├── id (PK)
├── nome
├── nif (único)
├── senha
└── ativo

categorias
├── id (PK)
├── nome (único)
└── descricao

materiais
├── id (PK)
├── nome
├── descricao
├── quantidade
└── categoria_id (FK → categorias)

movimentacoes
├── id (PK)
├── tipo (ENTRADA ou SAIDA)
├── quantidade
├── observacao
├── data_hora
└── material_id (FK → materiais)

ativos
├── id (PK)
├── nome
├── descricao
├── numero_patrimonio (único)
├── localizacao
└── estado (BOM, REGULAR, RUIM, INATIVO)
```

---

## Fluxo de Uso

1. Acesse **http://localhost:8080**
2. Clique em **Criar conta** e preencha com NIF autorizado
3. Faça **Login** com NIF e senha
4. Na área interna acesse os módulos:
   - **Categorias** → cadastre as categorias dos materiais
   - **Materiais** → cadastre os materiais vinculados às categorias
   - **Movimentações** → registre entradas e saídas de estoque
   - **Ativos Patrimoniais** → gerencie os ativos da instituição
5. Clique em **Logout** para sair do sistema