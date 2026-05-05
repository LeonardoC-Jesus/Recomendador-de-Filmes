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

Bug 5:
Descrição -> Ao somar o bônus de Afinidade Histórica, o score final atingia 115.0. O sistema não estava limitando o resultado ao teto de 100%, o que causaria erros em barras de progresso ou rankings na interface do usuário.
Teste que revelou -> deve_testarLimiteDeScore_quando_estaAcimaDoMaximo (Teste 5).
Solução aplicada -> Implementação do método aplicarTrava(double valor), que utiliza uma estrutura condicional para verificar se o valor > 100.0 e, em caso positivo, força o retorno para exatamente 100.0.

Bug 6:
Descrição -> Quando um filme tinha uma duração muito acima do limite (ex: 500 min), o cálculo 100 - (diferenca * 2) resultava em um número negativo muito alto. Ao multiplicar pelo peso de 0.20, o score final do filme ficava negativo (ex: -30.0), o que é logicamente impossível para uma recomendação.
Teste que revelou -> deve_testarLimiteDeScore_quando_estaAbaixoDoMinimo (Teste 6).
Solução aplicada -> Adicionada uma trava interna no método calcularComponenteDuracao para retornar resultadoMinimo = 0.0 caso o cálculo da penalidade resulte em um valor menor que zero.

Bug 7:
Descrição -> No método calcularComponenteGenero, a operação soma / filme.getGeneros().size() causava uma ArithmeticException ou retornava NaN (Not a Number) quando a lista de gêneros estava vazia, pois não é possível dividir por zero. Isso travava a execução de toda a calculadora.
Teste que revelou -> Um teste de caso de borda com um filme sem gêneros cadastrados (Lista vazia).
Solução aplicada -> Inclusão de uma verificação defensiva no início do método: if (filme.getGeneros().isEmpty()) { return 0.0; }. Isso garante que o componente retorne zero imediatamente sem tentar realizar a divisão.

Bug 8:
Descrição -> No método recomendar, a exceção IndexOutOfBoundsException está sendo lançada porque o sistema tenta acessar um indice inexistente na lista.
Teste que revelou -> deve_OrdenarPorScoreDesc_Quando_ScoresSaoDiferentes() (Teste 2).
Solução aplicada -> a inserção de uma condição a mais na hora de acessar algum indice da lista.