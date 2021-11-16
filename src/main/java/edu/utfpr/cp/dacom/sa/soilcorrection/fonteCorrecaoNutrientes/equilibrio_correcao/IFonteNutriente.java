package edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.equilibrio_correcao;


import java.util.Set;

public interface IFonteNutriente {
    
    public double getTeorFonte();
    public Set<NutrienteAdicional> getNutrientesAdicionais();
}
