package edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.conversao;

import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.conversao.Conversao;
import lombok.NonNull;

public class ConverteMgDm3EmKgHa implements Conversao<Double, Double> {

    @Override
    public Double converte(@NonNull Double valor) {

        if (valor <= 0) {
            throw new IllegalArgumentException();
        }

        return valor * 2;
    }
}