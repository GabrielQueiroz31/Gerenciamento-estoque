# Sistema de Gerenciamento de Estoque e Ativos Patrimoniais — SENAI-SP

## Descrição

Sistema web desenvolvido com Spring Boot e Thymeleaf para gerenciar materiais,
movimentações de estoque e ativos patrimoniais de uma unidade escolar do SENAI-SP.
O sistema segue a identidade visual oficial do SENAI-SP e adota arquitetura MVC
com camadas bem definidas.

---

## Requisitos Funcionais (RF)

| Código | Descrição |
|--------|-----------|
| RF01 | Permitir o cadastro de funcionários mediante NIF autorizado |
| RF02 | Permitir o login com NIF e senha |
| RF03 | Permitir o logout |
| RF04 | Cadastrar, listar, editar e excluir categorias de materiais |
| RF05 | Cadastrar, listar, editar e excluir materiais |
| RF06 | Vincular cada material a uma categoria |
| RF07 | Registrar movimentações de entrada e saída de estoque |
| RF08 | Atualizar automaticamente a quantidade do material ao registrar movimentação |
| RF09 | Impedir saída de estoque com quantidade maior do que a disponível |
| RF10 | Cadastrar, listar, editar e excluir ativos patrimoniais |
| RF11 | Proteger a área interna — somente usuários logados podem acessar |

---

## Requisitos Não Funcionais (RNF)

| Código | Descrição |
|--------|-----------|
| RNF01 | Desenvolvido com Java 21 e Spring Boot 4 |
| RNF02 | Banco de dados PostgreSQL |
| RNF03 | Interface seguindo a identidade visual do SENAI-SP (cores, fonte Montserrat) |
| RNF04 | Interface responsiva |
| RNF05 | Senhas com no mínimo 4 caracteres |
| RNF06 | Acesso às páginas internas protegido por sessão HTTP |
| RNF07 | Arquitetura MVC com camadas Model, Repository, Service e Controller |

---

## Tecnologias Utilizadas

| Tecnologia | Versão |
|------------|--------|
| Java | 21 |
| Spring Boot | 4 |
| Spring Data JPA | — |
| Thymeleaf | — |
| PostgreSQL | — |
| Maven | — |

---

## Como Rodar o Projeto

### Pré-requisitos

- Java JDK 21+
- PostgreSQL instalado e rodando
- VS Code com as extensões:
  - Extension Pack for Java (Microsoft)
  - Spring Boot Extension Pack (VMware)

### 1. Configurar o Banco de Dados

Crie o banco:
```sql
CREATE DATABASE estoque;
```

Insira um funcionário autorizado para poder se cadastrar:
```sql
INSERT INTO funcionarios_autenticados (nome, nif, ativo)
VALUES ('Seu Nome Completo', 'SeuNIF', true);
```

### 2. Configurar o `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/estoque
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.thymeleaf.cache=false
```

### 3. Rodar

1. Abra o projeto no VS Code
2. Rode a classe `GerenciamentoEstoqueApplication.java`
3. Acesse: [http://localhost:8080](http://localhost:8080)

---

## Estrutura do Projeto
```
src/main/java/com/example/gerenciamento_estoque/
│
├── controller/
│   ├── AppController.java           → Área interna
│   ├── AtivoController.java         → CRUD de Ativos
│   ├── AuthController.java          → Login, Cadastro, Logout
│   ├── CategoriaController.java     → CRUD de Categorias
│   ├── HomeController.java          → Página inicial
│   ├── MaterialController.java      → CRUD de Materiais
│   └── MovimentacaoController.java  → Movimentações de Estoque
│
├── model/
│   ├── Ativo.java
│   ├── Categoria.java
│   ├── Funcionario.java
│   ├── FuncionarioAutenticado.java
│   ├── Material.java
│   └── Movimentacao.java
│
├── repository/
│   ├── AtivoRepository.java
│   ├── CategoriaRepository.java
│   ├── FuncionarioAutenticadoRepository.java
│   ├── FuncionarioRepository.java
│   ├── MaterialRepository.java
│   └── MovimentacaoRepository.java
│
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
funcionarios_autenticados        funcionarios
├── id (PK)                      ├── id (PK)
├── nome                         ├── nome
├── nif                          ├── nif (único)
└── ativo                        ├── senha
                                 └── ativo

categorias                       materiais
├── id (PK)                      ├── id (PK)
├── nome (único)                 ├── nome
└── descricao                    ├── descricao
                                 ├── quantidade
                                 └── categoria_id (FK → categorias)

movimentacoes                    ativos
├── id (PK)                      ├── id (PK)
├── tipo (ENTRADA ou SAIDA)      ├── nome
├── quantidade                   ├── descricao
├── observacao                   ├── numero_patrimonio (único)
├── data_hora                    ├── localizacao
└── material_id (FK → materiais) └── estado (BOM, REGULAR, RUIM, INATIVO)
```

---

## Fluxo de Uso
```
[Página Inicial]
      ↓
[Criar Conta] → valida NIF na lista branca → [Login]
      ↓
[Área Interna]
  ├── [Categorias]    → Cadastrar / Editar / Excluir
  ├── [Materiais]     → Cadastrar / Editar / Excluir (vinculado à Categoria)
  ├── [Movimentações] → Registrar Entrada ou Saída (atualiza estoque)
  └── [Ativos]        → Cadastrar / Editar / Excluir
      ↓
[Logout] → [Página Inicial]
```

---
