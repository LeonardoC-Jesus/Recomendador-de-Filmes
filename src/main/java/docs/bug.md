Bug 1:
Descrição - > O atributo filtroFilmes inicia com null dependendo de qual metodo de filtro for chamado primeiro;
Teste que revelou -> foi deve_RemoverFilme_Quando_JaFoiAssistido();
Correção aplicada -> iniciar o filtroFilmes direto com o catalogo no construtor

Bug 2:
Descrição -> O java não permite remoção de itens da lista durante iteração;
Teste que revelou -> deve_RemoverFilme_Quando_UltrapassarClassificacaoEtaria
Correção aplicada -> utilização de uma lista de apoio, e ao final do filtro, a lista principal é atualizada com essa lista de apoio;

Bug 3:
Descrição -> O filtro por filmes assistidos não funciona, pois está sendo feita a comparação de 2 Objetos, que em java apesar de terem os mesmos valores, são diferentes;
Teste que revelou -> deve_RemoverFilme_Quando_JaFoiAssistido();
Correção aplicada -> a sobrescrita dos métodos equals e hashCode da classe Filme, para que considere filmes com o mesmo id valores iguais.

Bug 4:
Descrição -> Ao filtrar um filme por peso de cada gênero, nenhum filme é filtrado realmente, e mesmo que tenho peso 0, ele continua na lista;
Teste que revelou -> deve_RemoverFilme_Quando_FilmeComPesoZero
Correção aplicada -> trocando a lógica de adicionar em uma lista alternativa, para remover o filme na lista principal quando atender a condição de peso igual a zero e o filme tiver esse gênero;