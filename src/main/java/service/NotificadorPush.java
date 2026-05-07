package service;

import model.Usuario;

/**
 * Interface responsável por definir o contrato de envio de notificações push.
 * Deve ser utilizada para alertar o usuário sobre novas recomendações.
 * @author Gabriel Câncio
 */
public interface NotificadorPush {

    /**
     * Envia uma mensagem de alerta para um usuário específico.
     * @param mensagem O texto que será exibido na notificação.
     * @param usuario  O destinatário que possui a flag de notificações ativa.
     */
    void enviarAviso(String mensagem, Usuario usuario);
}