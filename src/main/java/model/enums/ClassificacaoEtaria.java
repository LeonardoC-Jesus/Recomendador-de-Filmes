package model.enums;

import exception.ClassificacaoInvalidaException;

public enum ClassificacaoEtaria {
    LIVRE(0),
    DEZ(10),
    DOZE(12),
    QUATORZE(14),
    DEZESSEIS(16),
    DEZOITO(18);

    private int CLASSIFICACAO;

    ClassificacaoEtaria (int classificacao) {
        this.CLASSIFICACAO = classificacao;
    }

    public int getCLASSIFICACAO() {
        return CLASSIFICACAO;
    }

    public static ClassificacaoEtaria pegarPeloValor(int valor) {
        for (ClassificacaoEtaria classificacaoEtaria: ClassificacaoEtaria.values()) {
            if (classificacaoEtaria.getCLASSIFICACAO() == valor) {
                return classificacaoEtaria;
            }
        }
        throw new ClassificacaoInvalidaException();
    }
}
