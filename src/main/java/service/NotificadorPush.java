package service;

import model.Usuario;

public interface NotificadorPush {

    void enviarAviso(String messagem, Usuario usuario);
}