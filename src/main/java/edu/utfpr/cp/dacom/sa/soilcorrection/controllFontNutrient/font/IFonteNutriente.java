package edu.utfpr.cp.dacom.sa.soilcorrection.controllFontNutrient.font;

import edu.utfpr.cp.dacom.sa.soilcorrection.controllFontNutrient.nutrient.*;

import java.util.Set;

public interface IFonteNutriente {
    
    public double getTeorFonte();
    public Set<NutrienteAdicional> getNutrientesAdicionais();
}
