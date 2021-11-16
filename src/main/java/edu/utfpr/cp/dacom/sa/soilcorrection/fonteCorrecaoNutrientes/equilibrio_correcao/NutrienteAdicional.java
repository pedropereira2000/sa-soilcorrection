package edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.equilibrio_correcao;

import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.equilibrio_correcao.NomeNutrienteAdicional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class NutrienteAdicional {

    private final NomeNutrienteAdicional nome;

    @Getter
    private final double teorNutriente;

    @Getter @Setter
    private double correcaoAdicional;
    
}
