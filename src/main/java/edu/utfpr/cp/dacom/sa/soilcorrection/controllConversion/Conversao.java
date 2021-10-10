package edu.utfpr.cp.dacom.sa.soilcorrection.controllConversion;

public interface Conversao<T, R> {

    public R converte(T valor);
}
