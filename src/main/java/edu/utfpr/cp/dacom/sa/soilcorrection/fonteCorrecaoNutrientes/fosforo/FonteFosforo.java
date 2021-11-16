package edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.fosforo;

import java.util.Set;

import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.equilibrio_correcao.IFonteNutriente;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.equilibrio_correcao.NomeNutrienteAdicional;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.equilibrio_correcao.NutrienteAdicional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FonteFosforo
        implements IFonteNutriente {

    SUPERFOSFATO_SIMPLES(
        0.18, 
        Set.of(
            new NutrienteAdicional(
                NomeNutrienteAdicional.ENXOFRE, 0.1),
                new NutrienteAdicional(NomeNutrienteAdicional.CALCIO, 0.28))),
    SUPERFOSFATO_TRIPO(
        0.41, 
        Set.of(
            new NutrienteAdicional(NomeNutrienteAdicional.CALCIO, 0.2))),
    MAP(
        0.48, 
        Set.of(
            new NutrienteAdicional(NomeNutrienteAdicional.CALCIO, 0.09)));

    private final double teorFonte;
    private final Set<NutrienteAdicional> nutrientesAdicionais;

}
