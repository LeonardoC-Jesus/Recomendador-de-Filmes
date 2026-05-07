[CatalogoFilmesAPI]
Evita chamadas de rede lentas e garante um catálogo controlado para o teste.

[HistoricoUsuarioRepository]
Justificativa: Impede a escrita real em banco de dados ou disco durante a execução dos testes.

[NotificadorPush]
Justificativa: Garante que notificações não sejam enviadas de verdade aos usuários durante o desenvolvimento.

[GeradorAleatorio]
Justificativa: Torna o comportamento aleatório determinante, permitindo testar sorteios com previsibilidade.