package edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.conversao;

public interface Conversao<T, R> {

    public R converte(T valor);
}
