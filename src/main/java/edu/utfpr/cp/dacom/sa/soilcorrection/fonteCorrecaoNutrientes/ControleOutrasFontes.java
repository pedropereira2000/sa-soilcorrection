/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes;
//import com.utfpr.pedro.gui.arquitetura.views.FonteOutras;
import java.util.ArrayList;

/**
 *
 * @author pedropereira
 */
public class ControleOutrasFontes {
    private FonteOutras fontOther;
    
    public void setFonte(FonteOutras fontOther){
        this.fontOther = fontOther;
    }
    
    public double enxofreIdeal(int textSolo){
        if(textSolo==1)return 9.0;
        else if(textSolo==2)return 6.0;
        else return 0.0;
    }
    
    public ArrayList<String> tipoSolo(int textSolo){
        ArrayList itens = new ArrayList();
        if(textSolo==1){
            itens.add("Argiloso");
            itens.add("+ 40% de argila");
        }
        else{
            itens.add("Text. Média");
            itens.add("25 a 40% de argila");
        }
        return itens;
    }
    
    public String tipoPlantio(int sistemaCultivo){
        if(sistemaCultivo==1)return "Plantio Direto";
        else return "Convencional";
    }
    
    public double calcSCmol(double pot, double calci, double mag){
        if(pot+calci+mag>0.01)return pot+calci+mag;
        else return 0;
    }
    
    public double calcCtcCmol(double sCmol, double hAl){
        if(sCmol+hAl > 0.01)return sCmol+hAl;
        else return 0;
    }
    
    public double calcVAtual(double item){
        return fontOther.calcVAtual(item);
    }
    
    public String controleMOgdm3(double mogdm3){
        if(mogdm3 > 0.01)return "";
        else return "M.O. %";
    }
    
    public double calcMOPercent(double mogdm3){
        if(mogdm3/10>0.01)return mogdm3/10;
        else return 0;           
    }
    
    public String calcTipoMOPercent(double mogdm3Percent){
        if(mogdm3Percent/1.72*10>0.01)return "M.O. %";
        else return "";
    }
    
    //Revisar Nome e variável
    public double calcVal3MOgdm3(double val1){
        return val1*10;
    }
    
    //Revisar Nome e variável
    public double calcVal3Decimal(double val2){
        return fontOther.calcGdm3Decimal(val2);
    }
    
    //Revisar Nome e variável
    public String calcVal2MOgdm3(double val1){
        if(val1 < 0.01)return "";
        else return "M.O. (g.dm3)";
    }
    
    //Revisar Nome e variável
    public String calcVal4MOgdm3(double val3){
        return fontOther.calGdm4Decimal(val3);
    }
    
    //Revisar Nome e variável
    public String calcValK17(double k17){
        if(k17>0.01)return "M.O. %";
        else return "";
    }
    
    public double calcP18(double k17){
        if(k17/1.72*10>0.01) return k17/1.72*10;
        else return 0;
    }
    
    public double calcCarbono(double g18){
        if(g18/1.72*10>0.01)return g18/1.72*10;
        else return 0;
    }
}
