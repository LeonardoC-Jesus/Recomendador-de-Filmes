[RecomendadorServiceTest]

Teste 1:

Id: CT-01
Cenário: Limite de Recomendações
Entrada: Usuario maria, topN 2
Resultado Esperado: Retorna lista com exatamente 2 recomendações
Status: [ ]

Teste 2:

Id: CT-02
Cenário: Ordenação por Relevância
Entrada: Lista de filmes com scores variados
Resultado Esperado: Retorna lista com 2 recomendações ordenadas decrescentemente
Status: [ ]

Teste 3:

Id: CT-03
Cenário: Resiliência de API
Entrada: API de Filmes indisponível (RuntimeException)
Resultado Esperado: Retorna uma lista vazia e não dispara erro para o usuário
Status: [ ]

Teste 4:

Id: CT-04
Cenário: Push Notification Quando Habilitado
Entrada: Usuário com notificações ligadas
Resultado Esperado: Retorna uma notificação de nova recomendação disponível
Status: [ ]

Teste 5:

Id: CT-05
Cenário: Push Notification Quando Não Habilitado
Entrada: Usuário com notificações desligadas
Resultado Esperado: Não retorna uma notificação de nova recomendação disponível
Status: [ ]

Teste 6:

Id: CT-06
Cenário: Integridade da Persistência
Entrada: Usuario maria, Filme ("Filme"), topN 1
Resultado Esperado: Garantir que o título salvo no repositório é idêntico ao processado
Status: [ ]

Teste 7:

Id: CT-07
Cenário: Sorteio Aleatório
Entrada: Lista de filmes [Filme1, Filme2], GeradorAleatorio retornando índice 0
Resultado Esperado: Um objeto Optional contendo o filme sorteado
Status: [ ]

[FilmeTest]

Teste 8:

Id: CT-08
Cenário: Preenchimento de Atributos
Entrada: Objeto Filme com dados completos
Resultado Esperado: Todos os atributos de filme são preenchidos corretamente
Status: [ ]

Teste 9:

Id: CT-09
Cenário: Consistência de Identidade
Entrada: Dois objetos Filme diferentes com o mesmo ID
Resultado Esperado: Filmes com o mesmo ID devem ser considerados iguais pelo equals e gerar o mesmo hashCode
Status: [ ]

[CalculadoraScoreTest]

Teste 10:

Id: CT-10
Cenário: Peso Máximo por Gênero
Entrada: Filme com Gêneros de Peso 1.0
Resultado Esperado: Gêneros com peso 1.0 devem somar 50 pontos no score final
Status: [ ]

Teste 11:

Id: CT-11
Cenário: Peso Mínimo por Gênero
Entrada: Filme com Gêneros de Peso 0.0
Resultado Esperado: Gêneros com peso 0.0 devem somar 0 pontos no score final
Status: [ ]

Teste 12:

Id: CT-12
Cenário: Duração Ideal
Entrada: Filme com duração = Limite Perfil
Resultado Esperado: Componente de duração deve somar pontuação máxima (20 pts)
Status: [ ]

Teste 13:

Id: CT-13
Cenário: Penalização por Duração
Entrada: Filme com duração > Limite Perfil
Resultado Esperado: O score deve ser reduzido proporcionalmente ao excesso de tempo
Status: [ ]

Teste 14:

Id: CT-14
Cenário: Teto do Score Global
Entrada: Filme perfeito (Popularidade 100, Peso 1.0, Tempo Ideal)
Resultado Esperado: Filme não deve passar de 100 de score
Status: [ ]

Teste 15:

Id: CT-15
Cenário: Piso do Score Global
Entrada: Filme incompatível (Popularidade 0, Peso 0.0, Tempo Excessivo)
Resultado Esperado: Filme não deve ser menor que 0 de score
Status: [ ]

[FiltroFilmesTest]

Teste 16:

Id: CT-16
Cenário: Remoção de Filme Assistido
Entrada: Filme já assistido
Resultado Esperado: O sistema deve remover o filme da lista se já foi assistido
Status: [ ]

Teste 17:

Id: CT-17
Cenário: Regra de Classificação Etária
Entrada: Filme com idade > limite do Perfil
Resultado Esperado: Filmes impróprios para a idade do perfil devem ser barrados
Status: [ ]

Teste 18:

Id: CT-18
Cenário: Filtro de Idioma
Entrada: Filme com Idioma diferente do Perfil Cinéfilo
Resultado Esperado: Filmes em idiomas não compreendidos devem ser removidos
Status: [ ]

Teste 19:

Id: CT-19
Cenário: Bloqueio por Gênero
Entrada: Uma lista de Gêneros com seu peso
Resultado Esperado: O sistema deve remover o filme da lista se possui um peso igual a zero
Status: [ ]

Teste 20:

Id: CT-20
Cenário: Catálogo Inexistente
Entrada: Lista de filmes vazia vinda da API
Resultado Esperado: O sistema deve retornar uma lista vazia sem gerar erros
Status: [ ]