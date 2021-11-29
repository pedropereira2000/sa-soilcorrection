/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utfpr.cp.dacom.sa.soilcorrection;
//import com.utfpr.pedro.gui.arquitetura.control_nutriente_fonte.fosforo.ControleFosforo;
//import com.utfpr.pedro.gui.arquitetura.control_nutriente_fonte.magnesio_calcio.ControleCalcioMagnesio;
//import com.utfpr.pedro.gui.arquitetura.control_nutriente_fonte.potassio.ControlePotassio;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.calcio_magnesio.CorrecaoCalcioMagnesio;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.calcio_magnesio.FonteCalcioMagnesio;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.conversao.ConverteCMolcDm3EmMgDm3;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.conversao.ConverteKgHaEmK2O;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.conversao.ConverteKgHaEmP2O5;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.conversao.ConverteMgDm3EmKgHa;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.equilibrio_correcao.NutrienteAdicional;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.fosforo.CorrecaoFosforo;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.fosforo.FonteFosforo;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.potassio.CorrecaoPotassio;
import edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes.potassio.FontePotassio;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author user
 */
public class GerenciarFontes extends javax.swing.JFrame {
    /*private ControlePotassio controlP = new ControlePotassio();
    private ControleFosforo controlF = new ControleFosforo();
    private ControleCalcioMagnesio controlCM = new ControleCalcioMagnesio();*/

    public static int opTpSolo = 0;
    public static Double potassioSolo = 0.0;
    public static Double hAlSolo = 0.0;
    public static Double calcioSolo = 0.0;
    public static Double magnesioSolo = 0.0;
    public static Double fosforoSolo = 0.0;
    public static Double enxofreSolo = 0.0;
    public static Double aluminioSolo = 0.0;
    public static Double magnesioPosCorr = 0.0;
    public static int fontUsadaPot = 0;
    public static int fontUsadaFof = 0;
    public static int fontUsadaCal = 0;
    
    
    /**
     * Creates new form GerenciarFontes
     */
    public GerenciarFontes() {
        initComponents();
        opTpSolo = GerenciarTeor.texturaSolo;
        potassioSolo= GerenciarTeor.potassio;
        hAlSolo=GerenciarTeor.hAl;
        calcioSolo=GerenciarTeor.calcio;
        magnesioSolo=GerenciarTeor.magnesio;
        fosforoSolo=GerenciarTeor.fosforo;
        enxofreSolo=GerenciarTeor.enxofre;
        aluminioSolo=GerenciarTeor.aluminio;
        magnesioPosCorr=GerenciarTeor.magnesioPosCorr;
        atualizaCampos();
    }

    public void calcFosforoCorrecao(){
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        if(fontUsadaFof == 1) {
            FonteFosforo fontF = Enum.valueOf(FonteFosforo.class, "SUPERFOSFATO_SIMPLES");
            CorrecaoFosforo corrF = new CorrecaoFosforo();
            ConverteMgDm3EmKgHa convertKgHa = new ConverteMgDm3EmKgHa();
            ConverteKgHaEmP2O5 convertP205 = new ConverteKgHaEmP2O5();
            Double nessAplica = 0.0;
            if(!txtTeorFont.getText().equals("")) nessAplica = Double.parseDouble(txtTeorFont.getText().replace("," , "."))-fosforoSolo;
            else nessAplica = 0.0;
            nessAplica = convertKgHa.converte(nessAplica);
            nessAplica = convertP205.converte(nessAplica);
            if(!txtFosfEfic.getText().equals("")) nessAplica = corrF.calculaEficienciaNutriente(nessAplica, Double.parseDouble(txtFosfEfic.getText())/100);
            else nessAplica = nessAplica;
            Double qtdAplicarFosforo = corrF.calculaQuantidadeAplicar(nessAplica, fontF);
            lblQtdAplicar.setText(String.valueOf(numberFormat.format(qtdAplicarFosforo)));
            Double custFont = 0.0;
            if(!txtFont1.getText().equals("")) {
                custFont = Double.parseDouble(txtFont1.getText().replace("," , "."));
                lblCustoFof.setText(String.valueOf(numberFormat.format(corrF.calculaCusto(custFont,qtdAplicarFosforo))));
            }else{
                custFont = 0.0;
                lblCustoFof.setText("0.00");
            }
            lblTipoFont.setText("Superfosfato Simple");
            lblTipoValFontAdicionalPot.setText("kg/ha de ");
            Set nutAd = fontF.getNutrientesAdicionais();
            Iterator<NutrienteAdicional> nutAdIt = nutAd.iterator();
            ArrayList<Double> teors= new ArrayList();
            while(nutAdIt.hasNext()){
                NutrienteAdicional nut = nutAdIt.next();
                teors.add(nut.getTeorNutriente());
            }
            lblQtdFonte.setText(String.valueOf(numberFormat.format(qtdAplicarFosforo*teors.get(0))));
            lblTipoFont2.setText("ENXOFRE");
            lblQtdFont2.setText(String.valueOf(numberFormat.format(qtdAplicarFosforo*teors.get(1))));
            lblTipoValFonteAdicional2.setText("kg/ha de ");
            lblAlet1.setText("Atenção para o teor de Magnésio no solo");
            lblTipoFont3.setText("CALCIO");
        }else if(fontUsadaFof == 2) {
            FonteFosforo fontF = Enum.valueOf(FonteFosforo.class, "SUPERFOSFATO_TRIPO");
            CorrecaoFosforo corrF = new CorrecaoFosforo();
            ConverteMgDm3EmKgHa convertKgHa = new ConverteMgDm3EmKgHa();
            ConverteKgHaEmP2O5 convertP205 = new ConverteKgHaEmP2O5();
            Double nessAplica = 0.0;
            if(!txtTeorFont.getText().equals("")) nessAplica = Double.parseDouble(txtTeorFont.getText().replace("," , "."))-fosforoSolo;
            else nessAplica = 0.0;
            nessAplica = convertKgHa.converte(nessAplica);
            nessAplica = convertP205.converte(nessAplica);
            if(!txtFosfEfic.getText().equals("")) nessAplica = corrF.calculaEficienciaNutriente(nessAplica, Double.parseDouble(txtFosfEfic.getText())/100);
            else nessAplica = nessAplica;
            Double qtdAplicarFosforo = corrF.calculaQuantidadeAplicar(nessAplica, fontF);
            lblQtdAplicar.setText(String.valueOf(numberFormat.format(qtdAplicarFosforo)));
            Double custFont = 0.0;
            if(!txtFont2.getText().equals("")) {
                custFont = Double.parseDouble(txtFont2.getText().replace("," , "."));
                lblCustoFof.setText(String.valueOf(numberFormat.format(corrF.calculaCusto(custFont,qtdAplicarFosforo))));
            }else{
                custFont = 0.0;
                lblCustoFof.setText("0.00");
            }
            lblTipoFont.setText("Superfosfato Triplo");
            lblTipoValFontAdicionalPot.setText("kg/ha de ");
            Set nutAd = fontF.getNutrientesAdicionais();
            Iterator<NutrienteAdicional> nutAdIt = nutAd.iterator();
            ArrayList<Double> teors= new ArrayList();
            while(nutAdIt.hasNext()){
                NutrienteAdicional nut = nutAdIt.next();
                teors.add(nut.getTeorNutriente());
            }
            lblQtdFonte.setText("0,0");
            lblTipoFont2.setText("");
            lblQtdFont2.setText(String.valueOf(numberFormat.format(qtdAplicarFosforo*teors.get(0))));
            lblTipoValFonteAdicional2.setText("kg/ha de ");
            lblTipoFont3.setText("CÁLCIO");
        }else if(fontUsadaFof == 3) {
            FonteFosforo fontF = Enum.valueOf(FonteFosforo.class, "MAP");
            CorrecaoFosforo corrF = new CorrecaoFosforo();
            ConverteMgDm3EmKgHa convertKgHa = new ConverteMgDm3EmKgHa();
            ConverteKgHaEmP2O5 convertP205 = new ConverteKgHaEmP2O5();
            Double nessAplica = 0.0;
            if(!txtTeorFont.getText().equals("")) nessAplica = Double.parseDouble(txtTeorFont.getText().replace("," , "."))-fosforoSolo;
            else nessAplica = 0.0;
            nessAplica = convertKgHa.converte(nessAplica);
            nessAplica = convertP205.converte(nessAplica);
            if(!txtFosfEfic.getText().equals("")) nessAplica = corrF.calculaEficienciaNutriente(nessAplica, Double.parseDouble(txtFosfEfic.getText())/100);
            else nessAplica = nessAplica;
            Double qtdAplicarFosforo = corrF.calculaQuantidadeAplicar(nessAplica, fontF);
            lblQtdAplicar.setText(String.valueOf(numberFormat.format(qtdAplicarFosforo)));
            Double custFont = 0.0;
            if(!txtFont3.getText().equals("")) {
                custFont = Double.parseDouble(txtFont3.getText().replace("," , "."));
                lblCustoFof.setText(String.valueOf(numberFormat.format(corrF.calculaCusto(custFont,qtdAplicarFosforo))));
            }else{
                custFont = 0.0;
                lblCustoFof.setText("0.00");
            }
            lblTipoFont.setText("MAP");
            lblTipoValFontAdicionalPot.setText("kg/ha de ");
            Set nutAd = fontF.getNutrientesAdicionais();
            Iterator<NutrienteAdicional> nutAdIt = nutAd.iterator();
            ArrayList<Double> teors= new ArrayList();
            while(nutAdIt.hasNext()){
                NutrienteAdicional nut = nutAdIt.next();
                teors.add(nut.getTeorNutriente());
            }
            lblQtdFonte.setText("0,0");
            lblTipoFont2.setText("");
            lblQtdFont2.setText(String.valueOf(numberFormat.format(qtdAplicarFosforo*teors.get(0))));
            lblTipoValFonteAdicional2.setText("kg/ha de ");
            lblTipoFont3.setText("NITROGÊNIO");
        }
        atualizaCampos();
    }

    public void calcPotassioCorrecao(){
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        if(fontUsadaPot == 1) {
            FontePotassio fontP = Enum.valueOf(FontePotassio.class, "CLORETO_POTASSIO");
            CorrecaoPotassio corrP = new CorrecaoPotassio();
            ConverteCMolcDm3EmMgDm3 converteMgDm3 = new ConverteCMolcDm3EmMgDm3();
            ConverteMgDm3EmKgHa convertKgHa = new ConverteMgDm3EmKgHa();
            ConverteKgHaEmK2O convertK20 = new ConverteKgHaEmK2O();
            Double nessAplica = 0.0;
            if(!txtPotCtcSolo.getText().equals("")&&!txtPotCtcDesejada.getText().equals("")) nessAplica = corrP.calculaNecessidadeAdicionarCMolcDm3(potassioSolo,Double.parseDouble(txtPotCtcSolo.getText().replace("," , ".")),Double.parseDouble(txtPotCtcDesejada.getText().replace("," , ".")));
            else nessAplica = corrP.calculaNecessidadeAdicionarCMolcDm3(potassioSolo,0.0, 0.0);
            nessAplica = converteMgDm3.converte(nessAplica);
            nessAplica = convertKgHa.converte(nessAplica);
            nessAplica = convertK20.converte(nessAplica);
            nessAplica = corrP.calculaEficienciaNutriente(nessAplica, 0.85);
            Double qtdAplicar = corrP.calculaQuantidadeAplicar(nessAplica, fontP);
            lblQtdAplicarPot.setText(String.valueOf(numberFormat.format(qtdAplicar)));
            Double custFont = 0.0;
            if(!txtFontPot1.getText().equals("")) {
                custFont = Double.parseDouble(txtFontPot1.getText().replace("," , "."));
                lblCustoPot.setText(String.valueOf(numberFormat.format(corrP.calculaCusto(custFont,qtdAplicar))));
            }else{
                custFont = 0.0;
                lblCustoPot.setText("0.00");
            }
            lblTipoFontePot.setText("Cloreto de Potássio");
            lblFontAdicional.setText("");
            lblValFontAdicionalPot.setText("");
            lblTipoValFontAdicionalPot.setText("");
            lblFontAdicionalPot2.setText("");
            lblValFontAdicionarPot2.setText("");
            lblTipoValFonteAdicional2.setText("");
            lblAlet1.setText("");
            lblFonteAdicionalPot3.setText("");
        }
        else if(fontUsadaPot == 2) {
            FontePotassio fontP = Enum.valueOf(FontePotassio.class, "SULFATO_POTASSIO");
            CorrecaoPotassio corrP = new CorrecaoPotassio();
            ConverteCMolcDm3EmMgDm3 converteMgDm3 = new ConverteCMolcDm3EmMgDm3();
            ConverteMgDm3EmKgHa convertKgHa = new ConverteMgDm3EmKgHa();
            ConverteKgHaEmK2O convertK20 = new ConverteKgHaEmK2O();
            Double nessAplica = 0.0;
            if(!txtPotCtcSolo.getText().equals("")&&!txtPotCtcDesejada.getText().equals("")) nessAplica = corrP.calculaNecessidadeAdicionarCMolcDm3(potassioSolo,Double.parseDouble(txtPotCtcSolo.getText().replace("," , ".")),Double.parseDouble(txtPotCtcDesejada.getText().replace("," , ".")));
            else nessAplica = corrP.calculaNecessidadeAdicionarCMolcDm3(potassioSolo,0.0, 0.0);
            nessAplica = converteMgDm3.converte(nessAplica);
            nessAplica = convertKgHa.converte(nessAplica);
            nessAplica = convertK20.converte(nessAplica);
            nessAplica = corrP.calculaEficienciaNutriente(nessAplica, 0.85);
            Double qtdAplicar = corrP.calculaQuantidadeAplicar(nessAplica, fontP);
            lblQtdAplicarPot.setText(String.valueOf(numberFormat.format(qtdAplicar)));
            Double custFont = 0.0;
            if(!txtFontPot2.getText().equals("")) {
                custFont = Double.parseDouble(txtFontPot2.getText().replace("," , "."));
                lblCustoPot.setText(String.valueOf(numberFormat.format(corrP.calculaCusto(custFont,qtdAplicar))));
            }else{
                custFont = 0.0;
                lblCustoPot.setText("0.00");
            }
            lblTipoFontePot.setText("Sulfato de Potássio");
            lblFontAdicional.setText("Essa correção de Potássio fornecerá também:");
            //lblValFontAdicionalPot.setText(String.valueOf(corrP.calculaQuantidadeAplicar(Double.parseDouble(lblQtdAplicarPot.getText().replace("," , ".")),fontP)));
            lblTipoValFontAdicionalPot.setText("kg/ha de ");
            Set nutAd = fontP.getNutrientesAdicionais();
            Iterator<NutrienteAdicional> nutAdIt = nutAd.iterator();
            Double teorAd = 0.0;
            while(nutAdIt.hasNext()){
                NutrienteAdicional nut = nutAdIt.next();
                teorAd=nut.getTeorNutriente();
            }
            lblValFontAdicionalPot.setText(String.valueOf(qtdAplicar*teorAd));
            lblFontAdicionalPot2.setText("ENXOFRE");
            lblValFontAdicionarPot2.setText("");
            lblTipoValFonteAdicional2.setText("");
            lblAlet1.setText("");
            lblFonteAdicionalPot3.setText("");
        }
        else if(fontUsadaPot == 3){
            FontePotassio fontP = Enum.valueOf(FontePotassio.class, "SULFATO_POTASSIO_MAGNESIO");
            CorrecaoPotassio corrP = new CorrecaoPotassio();
            ConverteCMolcDm3EmMgDm3 converteMgDm3 = new ConverteCMolcDm3EmMgDm3();
            ConverteMgDm3EmKgHa convertKgHa = new ConverteMgDm3EmKgHa();
            ConverteKgHaEmK2O convertK20 = new ConverteKgHaEmK2O();
            Double nessAplica = 0.0;
            if(!txtPotCtcSolo.getText().equals("")&&!txtPotCtcDesejada.getText().equals("")) nessAplica = corrP.calculaNecessidadeAdicionarCMolcDm3(potassioSolo,Double.parseDouble(txtPotCtcSolo.getText().replace("," , ".")),Double.parseDouble(txtPotCtcDesejada.getText().replace("," , ".")));
            else nessAplica = corrP.calculaNecessidadeAdicionarCMolcDm3(potassioSolo,0.0, 0.0);
            nessAplica = converteMgDm3.converte(nessAplica);
            nessAplica = convertKgHa.converte(nessAplica);
            nessAplica = convertK20.converte(nessAplica);
            nessAplica = corrP.calculaEficienciaNutriente(nessAplica, 0.85);
            Double qtdAplicar = corrP.calculaQuantidadeAplicar(nessAplica, fontP);
            lblQtdAplicarPot.setText(String.valueOf(numberFormat.format(qtdAplicar)));
            Double custFont = 0.0;
            if(!txtFontPot3.getText().equals("")) {
                custFont = Double.parseDouble(txtFontPot3.getText().replace("," , "."));
                lblCustoPot.setText(String.valueOf(numberFormat.format(corrP.calculaCusto(custFont,qtdAplicar))));
            }else{
                custFont = 0.0;
                lblCustoPot.setText("0.00");
            }
            lblTipoFontePot.setText("Sulfato de Potássio e Magnésio");
            lblFontAdicional.setText("Essa correção de Potássio fornecerá também:");
            lblTipoValFontAdicionalPot.setText("kg/ha de ");
            Set nutAd = fontP.getNutrientesAdicionais();
            Iterator<NutrienteAdicional> nutAdIt = nutAd.iterator();
            ArrayList<Double> teors= new ArrayList();
            while(nutAdIt.hasNext()){
                NutrienteAdicional nut = nutAdIt.next();
                teors.add(nut.getTeorNutriente());
            }
            lblValFontAdicionalPot.setText(String.valueOf(numberFormat.format(qtdAplicar*teors.get(0))));
            lblFontAdicionalPot2.setText("ENXOFRE");
            lblValFontAdicionarPot2.setText(String.valueOf(numberFormat.format(qtdAplicar*teors.get(1))));
            lblTipoValFonteAdicional2.setText("kg/ha de ");
            lblAlet1.setText("Atenção para o teor de Magnésio no solo");
            lblFonteAdicionalPot3.setText("MAGNÉSIO");
        }
        else{
            lblTipoFont.setText("");
            lblFontAdicional.setText("");
            lblValFontAdicionalPot.setText("");
            lblTipoValFontAdicionalPot.setText("");
            lblFontAdicionalPot2.setText("");
            lblValFontAdicionarPot2.setText("");
            lblTipoValFonteAdicional2.setText("");
            lblAlet1.setText("");
            lblFonteAdicionalPot3.setText("");
        }
        atualizaCampos();
    }

    public Double calcTeorCaOAdicionar(){
        Double part1 = ((calcioSolo*Double.parseDouble(txtPartiDesejadaCalcio.getText().replace(",", "."))/Double.parseDouble(lblParticCtcSoloCalcio.getText().replace(",","."))));
        Double part2 = 0.0;
        if(fontUsadaFof==1) part2 = 0.49924;
        else if(fontUsadaFof==2) part2 = 0.33877;
        else if(fontUsadaFof==3) part2 = 0.0;
        else if(fontUsadaFof==4) part2 = 0.0;
        else if(fontUsadaFof==5) part2 = 0.49924;
        else if(fontUsadaFof==6) part2 = 0.92716;
        else if(fontUsadaFof>=7) part2 = 0.92716;
        else if(fontUsadaFof>=8) part2 = 0.80235;
        else if(fontUsadaFof>=9) part2 = 0.49924;
        else if(fontUsadaFof>=10) part2 = 0.795218;
        else if(fontUsadaFof>=11) part2 = 0.0;
        else if(fontUsadaFof>=12) part2 = 0.0;
        return part1-calcioSolo-part2;
    }

    public void calcCalcioMagCorrecao() {
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        if (fontUsadaCal == 1) {
            FonteCalcioMagnesio fontCM = Enum.valueOf(FonteCalcioMagnesio.class, "CALCARIO_DOLOMITICO");
            CorrecaoCalcioMagnesio corrCM = new CorrecaoCalcioMagnesio();
            ConverteCMolcDm3EmMgDm3 converteMgDm3 = new ConverteCMolcDm3EmMgDm3();
            ConverteMgDm3EmKgHa convertKgHa = new ConverteMgDm3EmKgHa();
            ConverteKgHaEmK2O convertK20 = new ConverteKgHaEmK2O();
            //SE(D23=1;"0,49924";SE(D23=2;"0,33877";SE(D23=3;"0,0";SE(D23=4;"0,0";SE(D23=5;"0,49924";SE(D23=6;"0,92716";SE(D23>=7;AM41;"")))))))
            Double nessAplica = calcTeorCaOAdicionar();
            System.out.println(nessAplica);
            /*nessAplica = converteMgDm3.converte(nessAplica);
            nessAplica = convertKgHa.converte(nessAplica);
            nessAplica = convertK20.converte(nessAplica);
            nessAplica = corrP.calculaEficienciaNutriente(nessAplica, 0.85);
            Double qtdAplicar = corrP.calculaQuantidadeAplicar(nessAplica, fontP);
            lblQtdAplicarPot.setText(String.valueOf(numberFormat.format(qtdAplicar)));
            Double custFont = 0.0;
            if(!txtFontPot1.getText().equals("")) {
                custFont = Double.parseDouble(txtFontPot1.getText().replace("," , "."));
                lblCustoPot.setText(String.valueOf(numberFormat.format(corrP.calculaCusto(custFont,qtdAplicar))));
            }else{
                custFont = 0.0;
                lblCustoPot.setText("0.00");
            }
            lblTipoFontePot.setText("Cloreto de Potássio");
            lblFontAdicional.setText("");
            lblValFontAdicionalPot.setText("");
            lblTipoValFontAdicionalPot.setText("");
            lblFontAdicionalPot2.setText("");
            lblValFontAdicionarPot2.setText("");
            lblTipoValFonteAdicional2.setText("");
            lblAlet1.setText("");
            lblFonteAdicionalPot3.setText("");*/
        }
    }

    public Double calcMagnesioCtcSolo(){
        return (magnesioSolo/(potassioSolo+magnesioSolo+calcioSolo+hAlSolo))*100;
    }
    
    public Double calcCalcioCtcSolo(){
        return (calcioSolo/(potassioSolo+magnesioSolo+calcioSolo+hAlSolo))*100;
    }

    public Double calcPotassioCtcSolo(){
        return (potassioSolo/(potassioSolo+magnesioSolo+calcioSolo+hAlSolo))*100;
    }

    public void atualizaCampos(){
        DecimalFormat numberFormat = new DecimalFormat("##.##");
        txtPotCtcSolo.setText(String.valueOf(numberFormat.format(calcPotassioCtcSolo())));
        lblParticCtcSoloCalcio.setText(String.valueOf(numberFormat.format(calcCalcioCtcSolo())));
        lblPartiCtcSoloMag.setText(String.valueOf(numberFormat.format(calcMagnesioCtcSolo())));
        lblPosCorrecaoMag.setText(String.valueOf((magnesioPosCorr/(potassioSolo+magnesioSolo+calcioSolo+hAlSolo))*100));
        lblVPercentAtual.setText(String.valueOf(100*((potassioSolo+calcioSolo+magnesioSolo)/(potassioSolo+calcioSolo+magnesioSolo+hAlSolo))));
        if(!txtPotCtcPosCorrecao.getText().equals("")&&!lblPosCorrecaoCalcio.getText().equals("")&&!lblPosCorrecaoMag.getText().equals("")) lblVPercentPosCorrecao.setText(String.valueOf(Double.parseDouble(txtPotCtcPosCorrecao.getText().replace(",","."))+Double.parseDouble(lblPosCorrecaoCalcio.getText().replace(",","."))+Double.parseDouble(lblPosCorrecaoMag.getText().replace(",","."))));
        else lblVPercentPosCorrecao.setText("0,0");
        if(opTpSolo==1) {lblIdealCalcio.setText("45 a 55");lblPartiIdealMag.setText("10 a 15"); lblVPercentIdeal.setText("60 a 70");;}
        else if(opTpSolo==2) {lblIdealCalcio.setText("35 a 40");lblPartiIdealMag.setText("8 a 12"); lblVPercentIdeal.setText("50");}
        else {lblIdealCalcio.setText("");lblPartiIdealMag.setText("");lblVPercentIdeal.setText("");}
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTeorFont = new javax.swing.JTextField();
        txtFontUti = new javax.swing.JTextField();
        txtFosfEfic = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lblTipoFont = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblQtdFonte = new javax.swing.JLabel();
        lblQtdAplicar = new javax.swing.JLabel();
        lblQtdFont2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblCustoFof = new javax.swing.JLabel();
        lblTipoFont2 = new javax.swing.JLabel();
        lblTipoFont3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtFont1 = new javax.swing.JTextField();
        txtFont4 = new javax.swing.JTextField();
        txtFont7 = new javax.swing.JTextField();
        txtFont10 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel33 = new javax.swing.JLabel();
        txtFont2 = new javax.swing.JTextField();
        txtFont5 = new javax.swing.JTextField();
        txtFont8 = new javax.swing.JTextField();
        txtFont11 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtFont3 = new javax.swing.JTextField();
        txtFont6 = new javax.swing.JTextField();
        txtFont9 = new javax.swing.JTextField();
        txtFont12 = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtPotCtcSolo = new javax.swing.JTextField();
        txtPotCtcDesejada = new javax.swing.JTextField();
        txtPotCtcPosCorrecao = new javax.swing.JTextField();
        txtFonteUsada = new javax.swing.JTextField();
        lblQtdAplicarPot = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        lblPartPotCtcPorcent = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        lblCustoPot = new javax.swing.JLabel();
        lblAlet1 = new javax.swing.JLabel();
        lblTipoFontePot = new javax.swing.JLabel();
        lblFontAdicional = new javax.swing.JLabel();
        lblValFontAdicionalPot = new javax.swing.JLabel();
        lblTipoValFontAdicionalPot = new javax.swing.JLabel();
        lblFontAdicionalPot2 = new javax.swing.JLabel();
        lblTipoValFonteAdicional2 = new javax.swing.JLabel();
        lblFonteAdicionalPot3 = new javax.swing.JLabel();
        lblValFontAdicionarPot2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        txtFontPot1 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        txtFontPot2 = new javax.swing.JTextField();
        txtFontPot3 = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        txtPartiDesejadaCalcio = new javax.swing.JTextField();
        lblPosCorrecaoCalcio = new javax.swing.JLabel();
        lblIdealCalcio = new javax.swing.JLabel();
        lblParticCtcSoloCalcio = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        lblPartiCtcSoloMag = new javax.swing.JLabel();
        lblPartiIdealMag = new javax.swing.JLabel();
        lblPosCorrecaoMag = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        txtFontMag2 = new javax.swing.JTextField();
        txtFontMag3 = new javax.swing.JTextField();
        txtFontMag4 = new javax.swing.JTextField();
        txtFontMag5 = new javax.swing.JTextField();
        txtFontMag6 = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel82 = new javax.swing.JLabel();
        txtFontUsadaCalcioMag = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        lblPrintFont = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        txtTeorCaoCorretivo = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        lblQtdAplicarCalcioMag = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator19 = new javax.swing.JSeparator();
        jPanel16 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        lblVPercentAtual = new javax.swing.JLabel();
        jSeparator20 = new javax.swing.JSeparator();
        jPanel19 = new javax.swing.JPanel();
        jLabel95 = new javax.swing.JLabel();
        lblVPercentIdeal = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        lblVPercentPosCorrecao = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(30000, 30000));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(900, 450));

        jPanel1.setAutoscrolls(true);
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 1500));

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setText("Teor de Fósforo a atingir:");
        jPanel9.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 24, -1, -1));

        jLabel8.setText("Fonte de Fósforo a utilizar:");
        jPanel9.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 62, -1, -1));

        jLabel9.setText("Quantidade a aplicar:");
        jPanel9.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 98, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Eficiência do Fósforo %:");
        jPanel9.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 132, -1, -1));

        txtTeorFont.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTeorFontFocusLost(evt);
            }
        });
        jPanel9.add(txtTeorFont, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 21, 60, -1));

        txtFontUti.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontUtiFocusLost(evt);
            }
        });
        jPanel9.add(txtFontUti, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 59, 60, -1));

        txtFosfEfic.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFosfEfic.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFosfEficFocusLost(evt);
            }
        });
        jPanel9.add(txtFosfEfic, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 130, 60, -1));

        jLabel11.setText("mg.dm3");
        jPanel9.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 24, -1, -1));

        lblTipoFont.setText("_____");
        jPanel9.add(lblTipoFont, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 62, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("kg/hectare");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 97, -1, -1));

        jLabel14.setText("OBS: Sugerimos uma eficiência entre 70% e 90%");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(277, 133, -1, -1));

        jLabel15.setText("Essa correção de FÓSFORO, fornecerá também (kg/ha):");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(397, 24, -1, -1));

        lblQtdFonte.setText("0,0");
        jPanel9.add(lblQtdFonte, new org.netbeans.lib.awtextra.AbsoluteConstraints(397, 62, -1, -1));
        jPanel9.add(lblQtdAplicar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 94, 60, 20));

        lblQtdFont2.setText("0,0");
        jPanel9.add(lblQtdFont2, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 62, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Custo - R$/ha:");
        jPanel9.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(397, 97, -1, -1));

        lblCustoFof.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCustoFof.setText("0,00");
        jPanel9.add(lblCustoFof, new org.netbeans.lib.awtextra.AbsoluteConstraints(507, 97, -1, -1));

        lblTipoFont2.setText("_____");
        jPanel9.add(lblTipoFont2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, -1, -1));

        lblTipoFont3.setText("______");
        jPanel9.add(lblTipoFont3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 60, -1, 20));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setText("Fonte");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 11, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 10, 188));

        jLabel23.setText("1 - Superfosfato Simples");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 20));

        jLabel24.setText("4 - DAP");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 20));

        jLabel25.setText("7 - Fosfato Gafsa");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 20));

        jLabel26.setText("10 - Escória de Thomas");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 20));

        jLabel27.setText("Valor/Ton. (R$)");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 84, -1));

        txtFont1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont1FocusLost(evt);
            }
        });
        jPanel2.add(txtFont1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 60, -1));

        txtFont4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont4FocusLost(evt);
            }
        });
        jPanel2.add(txtFont4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 60, -1));

        txtFont7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont7FocusLost(evt);
            }
        });
        jPanel2.add(txtFont7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 60, -1));

        txtFont10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont10FocusLost(evt);
            }
        });
        jPanel2.add(txtFont10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 60, -1));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 10, 188));

        jLabel28.setText("Fonte");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        jLabel29.setText("2 - Superfosfato Triplo");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, -1, 20));

        jLabel30.setText("5 - Yoorin");
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, -1, 20));

        jLabel31.setText("8 - Fosfato Daoui");
        jPanel2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, -1, 20));

        jLabel32.setText("11 - Ácido Fosfórico");
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, -1, 20));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 10, 188));

        jLabel33.setText("Valor/Ton. (R$)");
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 84, -1));

        txtFont2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont2FocusLost(evt);
            }
        });
        jPanel2.add(txtFont2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 60, -1));

        txtFont5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont5FocusLost(evt);
            }
        });
        jPanel2.add(txtFont5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 60, -1));

        txtFont8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont8FocusLost(evt);
            }
        });
        txtFont8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFont8ActionPerformed(evt);
            }
        });
        jPanel2.add(txtFont8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, 60, -1));

        txtFont11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont11FocusLost(evt);
            }
        });
        jPanel2.add(txtFont11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 60, -1));

        jLabel34.setText("Fonte");
        jPanel2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, -1, -1));

        jLabel35.setText("3 - MAP");
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, -1, 20));

        jLabel36.setText("6 - Fosfato Arad");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, -1, 20));

        jLabel37.setText("9 - Fosf. Patos Minasi");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 110, -1, 20));

        jLabel38.setText("12 - Multif. Magnesiano");
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, -1, 20));

        jLabel39.setText("Valor/Ton. (R$)");
        jPanel2.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 84, -1));

        txtFont3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont3FocusLost(evt);
            }
        });
        txtFont3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFont3ActionPerformed(evt);
            }
        });
        jPanel2.add(txtFont3, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 70, -1));

        txtFont6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont6FocusLost(evt);
            }
        });
        jPanel2.add(txtFont6, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 80, 70, -1));

        txtFont9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont9FocusLost(evt);
            }
        });
        jPanel2.add(txtFont9, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 110, 70, -1));

        txtFont12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFont12FocusLost(evt);
            }
        });
        jPanel2.add(txtFont12, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 140, 70, -1));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(738, 0, 10, 188));

        jSeparator18.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator18.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator18.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 10, 188));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel40.setText("Participação atual do POTÁSSIO na CTC do solo:");
        jPanel3.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 14, -1, -1));

        jLabel41.setText("Participação do Potássio na CTC, desejada: ");
        jPanel3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 49, -1, -1));

        jLabel42.setText("Participação do POTÁSSIO na CTC, após correção:");
        jPanel3.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 84, -1, -1));

        jLabel43.setText("Fonte de Potássio a usar:");
        jPanel3.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 20));

        jLabel44.setText("Quantidade a aplicar:");
        jPanel3.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        txtPotCtcSolo.setEditable(false);
        txtPotCtcSolo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtPotCtcSolo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPotCtcSolo.setText("1,2");
        txtPotCtcSolo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPotCtcSoloFocusLost(evt);
            }
        });
        jPanel3.add(txtPotCtcSolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 56, -1));

        txtPotCtcDesejada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPotCtcDesejada.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPotCtcDesejadaFocusLost(evt);
            }
        });
        jPanel3.add(txtPotCtcDesejada, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 56, -1));

        txtPotCtcPosCorrecao.setEditable(false);
        txtPotCtcPosCorrecao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPotCtcPosCorrecao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPotCtcPosCorrecaoFocusLost(evt);
            }
        });
        txtPotCtcPosCorrecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPotCtcPosCorrecaoActionPerformed(evt);
            }
        });
        jPanel3.add(txtPotCtcPosCorrecao, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 56, -1));

        txtFonteUsada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFonteUsada.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFonteUsadaFocusLost(evt);
            }
        });
        jPanel3.add(txtFonteUsada, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 53, -1));

        lblQtdAplicarPot.setText("0,00");
        jPanel3.add(lblQtdAplicarPot, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, -1, 20));

        jLabel46.setText("Participação ideal do Potássio na CTC:");
        jPanel3.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, -1, 20));

        lblPartPotCtcPorcent.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPartPotCtcPorcent.setText("3,0%");
        jPanel3.add(lblPartPotCtcPorcent, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, -1, 20));

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel48.setText("kg/hectare");
        jPanel3.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, -1, 20));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel49.setText("Custo - R$/ha:");
        jPanel3.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, -1, 20));

        lblCustoPot.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCustoPot.setText("0,00");
        jPanel3.add(lblCustoPot, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, -1, 20));
        jPanel3.add(lblAlet1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 290, 20));
        jPanel3.add(lblTipoFontePot, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 180, 20));
        jPanel3.add(lblFontAdicional, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 170, 20));
        jPanel3.add(lblValFontAdicionalPot, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 60, 20));
        jPanel3.add(lblTipoValFontAdicionalPot, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 70, 20));
        jPanel3.add(lblFontAdicionalPot2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, 70, 20));
        jPanel3.add(lblTipoValFonteAdicional2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 140, 50, 20));
        jPanel3.add(lblFonteAdicionalPot3, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, 110, 20));
        jPanel3.add(lblValFontAdicionarPot2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 80, 20));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setText("Fonte");
        jPanel4.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 11, -1, -1));

        jLabel52.setText("Valor/Ton. (R$)");
        jPanel4.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, 20));

        jLabel53.setText("Fonte");
        jPanel4.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, -1, 20));

        jLabel54.setText("Valor/Ton. (R$)");
        jPanel4.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, 20));

        jLabel55.setText("Fonte");
        jPanel4.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, 20));

        jLabel56.setText("Valor/Ton. (R$)");
        jPanel4.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, -1, 20));

        jLabel57.setText("1 - Cloreto de Potássio");
        jPanel4.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 43, -1, -1));

        txtFontPot1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontPot1FocusLost(evt);
            }
        });
        txtFontPot1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFontPot1ActionPerformed(evt);
            }
        });
        jPanel4.add(txtFontPot1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 70, -1));

        jLabel58.setText("2 - Sulfato de Potássio");
        jPanel4.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, 20));

        jLabel59.setText("3 - Sulf. Potássio/Mag.");
        jPanel4.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, -1, 20));

        txtFontPot2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontPot2FocusLost(evt);
            }
        });
        jPanel4.add(txtFontPot2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 70, -1));

        txtFontPot3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontPot3FocusLost(evt);
            }
        });
        jPanel4.add(txtFontPot3, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, 70, -1));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 0, 10, 96));

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 0, 10, 96));

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(431, 0, 10, 96));

        jSeparator9.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 0, 10, 96));

        jSeparator10.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 0, 10, 96));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel60.setText("Cálcio");
        jPanel5.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 22, -1, -1));

        jLabel61.setText("Participação atual na CTC do solo:");
        jPanel5.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, 20));

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel62.setText("ideal:");
        jPanel5.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, 20));

        jLabel63.setText("Após as correções:");
        jPanel5.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, -1, 20));

        jLabel64.setText("% de participação do CÁLCIO na CTC, desejada:");
        jPanel5.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 240, 20));

        txtPartiDesejadaCalcio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPartiDesejadaCalcioFocusLost(evt);
            }
        });
        jPanel5.add(txtPartiDesejadaCalcio, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 60, -1));
        jPanel5.add(lblPosCorrecaoCalcio, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 50, 20));

        lblIdealCalcio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIdealCalcio.setText("45 a 55 %");
        jPanel5.add(lblIdealCalcio, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, 20));

        lblParticCtcSoloCalcio.setText("44,7 %");
        jPanel5.add(lblParticCtcSoloCalcio, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, -1, 20));

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel5.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(441, 0, 10, 159));

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel68.setText("Magnésio");
        jPanel5.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, -1, -1));

        jLabel69.setText("Participação atual na CTC do solo:");
        jPanel5.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, -1, 20));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel70.setText("ideal:");
        jPanel5.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, -1, 20));

        jLabel71.setText("Após as correções:");
        jPanel5.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, -1, 20));

        lblPartiCtcSoloMag.setText("12,6 %");
        jPanel5.add(lblPartiCtcSoloMag, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 50, -1, 20));

        lblPartiIdealMag.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPartiIdealMag.setText("10 a 15 %");
        jPanel5.add(lblPartiIdealMag, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 80, -1, 20));

        lblPosCorrecaoMag.setText("12,6 %");
        jPanel5.add(lblPosCorrecaoMag, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, -1, 20));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel75.setText("Fontes");
        jPanel6.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 72, -1, -1));

        jLabel76.setText("1 - Calcário Dolomítico");
        jPanel6.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 20));

        jLabel77.setText("2 - Calcário Calcítico");
        jPanel6.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 20));

        jLabel78.setText("3 - Calcário de Concha");
        jPanel6.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        jLabel79.setText("4 - Gesso Agrícola");
        jPanel6.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 20));

        jLabel80.setText("5 - Hidróxido de cálcio");
        jPanel6.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 20));

        jLabel81.setText("6 - Calcário Magnesiano");
        jPanel6.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, 20));

        jTextField24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField24FocusLost(evt);
            }
        });
        jPanel6.add(jTextField24, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 60, -1));

        txtFontMag2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontMag2FocusLost(evt);
            }
        });
        jPanel6.add(txtFontMag2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 60, -1));

        txtFontMag3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontMag3FocusLost(evt);
            }
        });
        jPanel6.add(txtFontMag3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 60, -1));

        txtFontMag4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontMag4FocusLost(evt);
            }
        });
        jPanel6.add(txtFontMag4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 60, -1));

        txtFontMag5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontMag5FocusLost(evt);
            }
        });
        txtFontMag5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFontMag5ActionPerformed(evt);
            }
        });
        jPanel6.add(txtFontMag5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 60, -1));

        txtFontMag6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontMag6FocusLost(evt);
            }
        });
        jPanel6.add(txtFontMag6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 60, -1));

        jSeparator12.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator12.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel6.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 64, -1, 246));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 47, -1, 11));

        jPanel15.setBackground(new java.awt.Color(102, 255, 0));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Fonte de CÁLCIO e MAGNÉSIO");
        jPanel15.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 15, -1, -1));

        jPanel6.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 358, 40));

        jLabel92.setText("Valor/Ton. (R$)");
        jPanel6.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 69, -1, -1));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(507, 165, -1, 310));

        jSeparator13.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel5.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(499, 165, -1, 310));

        jSeparator14.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));
        jPanel5.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 165, 483, -1));

        jLabel82.setText("Fonte de Corretivo a usar: ");
        jPanel5.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 188, -1, 20));

        txtFontUsadaCalcioMag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontUsadaCalcioMagFocusLost(evt);
            }
        });
        jPanel5.add(txtFontUsadaCalcioMag, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, 70, 30));

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel83.setText("PRINT:");
        jPanel5.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 225, -1, 20));

        lblPrintFont.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                lblPrintFontFocusLost(evt);
            }
        });
        jPanel5.add(lblPrintFont, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 70, 30));

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel84.setText("Teor de CaO do corretivo:");
        jPanel5.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 263, -1, 20));

        txtTeorCaoCorretivo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTeorCaoCorretivoFocusLost(evt);
            }
        });
        jPanel5.add(txtTeorCaoCorretivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 260, 70, 30));

        jLabel85.setText("Quantidade a aplicar:");
        jPanel5.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 300, -1, 20));
        jPanel5.add(lblQtdAplicarCalcioMag, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, 60, 20));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator15.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator15.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel7.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 0, -1, 77));

        jSeparator16.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 41, 70, -1));

        jSeparator17.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator17.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel7.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 0, -1, 70));

        jSeparator19.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator19.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator19.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel7.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(287, 0, -1, 77));

        jPanel16.setBackground(new java.awt.Color(0, 153, 255));

        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("V%");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel90)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel90)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 77));

        jPanel17.setBackground(new java.awt.Color(102, 204, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(99, 35));

        jLabel91.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel91.setText("Atual:");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel91)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel91)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 0, 130, -1));

        jPanel18.setBackground(new java.awt.Color(102, 204, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(70, 35));

        lblVPercentAtual.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblVPercentAtual.setText("58,49");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(lblVPercentAtual)
                .addGap(16, 16, 16))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblVPercentAtual)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 0, -1, -1));

        jSeparator20.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator20.setForeground(new java.awt.Color(0, 0, 0));
        jPanel7.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 41, 130, 0));

        jPanel19.setBackground(new java.awt.Color(0, 102, 102));

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setText("Ideal:");

        lblVPercentIdeal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblVPercentIdeal.setForeground(new java.awt.Color(255, 255, 255));
        lblVPercentIdeal.setText("60 a 70%");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel95)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(lblVPercentIdeal)
                .addGap(27, 27, 27))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel95)
                    .addComponent(lblVPercentIdeal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 0, -1, 77));

        jPanel20.setBackground(new java.awt.Color(51, 51, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(100, 35));

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("Após as correções");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel97)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel97)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 40, 130, 37));

        jPanel21.setBackground(new java.awt.Color(51, 51, 255));
        jPanel21.setPreferredSize(new java.awt.Dimension(70, 35));

        lblVPercentPosCorrecao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblVPercentPosCorrecao.setForeground(new java.awt.Color(255, 255, 255));
        lblVPercentPosCorrecao.setText("58,49");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(lblVPercentPosCorrecao)
                .addGap(16, 16, 16))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblVPercentPosCorrecao)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 42, -1, -1));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 374, 455, 79));

        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel88.setText("Custo - R$/ha:");
        jPanel5.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, -1, 20));

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel87.setText("0,00");
        jPanel5.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, -1, 20));

        jLabel89.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel89.setText("Ton./ha");
        jPanel5.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, -1, 20));

        jPanel8.setBackground(new java.awt.Color(0, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(470, 37));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Correção/Recuperação de Fósforo");
        jPanel8.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 11, -1, -1));

        jPanel10.setBackground(new java.awt.Color(51, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Fontes de FÓSFORO");
        jPanel10.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 11, -1, -1));

        jPanel11.setBackground(new java.awt.Color(255, 255, 51));
        jPanel11.setPreferredSize(new java.awt.Dimension(870, 40));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Correção/Recuperação de Potássio");
        jPanel11.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(339, 14, -1, -1));

        jPanel12.setBackground(new java.awt.Color(255, 255, 51));
        jPanel12.setPreferredSize(new java.awt.Dimension(870, 40));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Fontes de POTÁSSIO");
        jPanel12.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 14, -1, -1));

        jPanel13.setBackground(new java.awt.Color(102, 255, 0));
        jPanel13.setPreferredSize(new java.awt.Dimension(870, 40));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Correção/Recuperação de Cálcio e Magnésio");
        jPanel13.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(272, 14, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(246, 246, 246))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFont3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFont3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont3ActionPerformed

    private void txtFont8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFont8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont8ActionPerformed

    private void txtPotCtcDesejadaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPotCtcDesejadaFocusLost
        if(Double.parseDouble(txtPotCtcDesejada.getText())>0.0&&!txtPotCtcDesejada.getText().equals("")) txtPotCtcPosCorrecao.setText(txtPotCtcDesejada.getText());
        else txtPotCtcPosCorrecao.setText("0.0");
    }//GEN-LAST:event_txtPotCtcDesejadaFocusLost

    private void txtPotCtcPosCorrecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPotCtcPosCorrecaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPotCtcPosCorrecaoActionPerformed

    private void txtFontMag5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFontMag5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFontMag5ActionPerformed

    private void txtFontPot1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFontPot1ActionPerformed
    }//GEN-LAST:event_txtFontPot1ActionPerformed

    private void txtFontMag6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontMag6FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFontMag6FocusLost

    private void txtFontMag5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontMag5FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFontMag5FocusLost

    private void txtFontMag4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontMag4FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFontMag4FocusLost

    private void txtFontMag3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontMag3FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFontMag3FocusLost

    private void txtFontMag2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontMag2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFontMag2FocusLost

    private void jTextField24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField24FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField24FocusLost

    private void txtTeorCaoCorretivoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTeorCaoCorretivoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeorCaoCorretivoFocusLost

    private void lblPrintFontFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lblPrintFontFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPrintFontFocusLost

    private void txtFontUsadaCalcioMagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontUsadaCalcioMagFocusLost
       if(!txtFontUsadaCalcioMag.getText().equals("")) {
           if (txtFontUsadaCalcioMag.getText().equals("1")) {
               fontUsadaCal = 1;
               calcCalcioMagCorrecao();
           } else if (txtFontUsadaCalcioMag.getText().equals("2")) {
               fontUsadaCal = 1;
               calcCalcioMagCorrecao();
           } else if (txtFontUsadaCalcioMag.getText().equals("3")) {
               fontUsadaCal = 1;
               calcCalcioMagCorrecao();
           } else if (txtFontUsadaCalcioMag.getText().equals("4")) {
               fontUsadaCal = 1;
               calcCalcioMagCorrecao();
           } else if (txtFontUsadaCalcioMag.getText().equals("5")) {
               fontUsadaCal = 1;
               calcCalcioMagCorrecao();
           } else if (txtFontUsadaCalcioMag.getText().equals("6")) {
               fontUsadaCal = 1;
               calcCalcioMagCorrecao();
           } else {
               fontUsadaCal = 1;
               calcCalcioMagCorrecao();
           }
       }
       atualizaCampos();
    }//GEN-LAST:event_txtFontUsadaCalcioMagFocusLost

    private void txtPartiDesejadaCalcioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPartiDesejadaCalcioFocusLost
        if(!txtPartiDesejadaCalcio.getText().equals("")){
            Double posCorrecao = Double.parseDouble(txtPartiDesejadaCalcio.getText().replace("," , "."));
            if(posCorrecao>45.0)
                lblPosCorrecaoCalcio.setText(String.valueOf(posCorrecao));
            else lblPosCorrecaoCalcio.setText(String.valueOf("46,1"));
        }
    }//GEN-LAST:event_txtPartiDesejadaCalcioFocusLost

    private void txtFontPot3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontPot3FocusLost
        calcPotassioCorrecao();
    }//GEN-LAST:event_txtFontPot3FocusLost

    private void txtFontPot2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontPot2FocusLost
        calcPotassioCorrecao();
    }//GEN-LAST:event_txtFontPot2FocusLost

    private void txtFontPot1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontPot1FocusLost
        calcPotassioCorrecao();
    }//GEN-LAST:event_txtFontPot1FocusLost

    private void txtFonteUsadaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFonteUsadaFocusLost
         if(!txtFonteUsada.getText().equals("")) {
             if (txtFonteUsada.getText().equals("1")) {
                 fontUsadaPot = 1;
                 calcPotassioCorrecao();
             } else if (txtFonteUsada.getText().equals("2")) {
                 fontUsadaPot = 2;
                 calcPotassioCorrecao();
             } else if (txtFonteUsada.getText().equals("3")) {
                 fontUsadaPot = 3;
                 calcPotassioCorrecao();
             } else {
                 fontUsadaPot = 0;
                 calcPotassioCorrecao();
             }
         }
    }//GEN-LAST:event_txtFonteUsadaFocusLost

    private void txtPotCtcPosCorrecaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPotCtcPosCorrecaoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPotCtcPosCorrecaoFocusLost

    private void txtPotCtcSoloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPotCtcSoloFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPotCtcSoloFocusLost

    private void txtFont12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont12FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont12FocusLost

    private void txtFont9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont9FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont9FocusLost

    private void txtFont6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont6FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont6FocusLost

    private void txtFont3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont3FocusLost
        calcFosforoCorrecao();
    }//GEN-LAST:event_txtFont3FocusLost

    private void txtFont11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont11FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont11FocusLost

    private void txtFont8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont8FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont8FocusLost

    private void txtFont5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont5FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont5FocusLost

    private void txtFont2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont2FocusLost

    private void txtFont10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont10FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont10FocusLost

    private void txtFont7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont7FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont7FocusLost

    private void txtFont4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont4FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFont4FocusLost

    private void txtFont1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFont1FocusLost
        calcFosforoCorrecao();
    }//GEN-LAST:event_txtFont1FocusLost

    private void txtFosfEficFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFosfEficFocusLost
        calcFosforoCorrecao();
    }//GEN-LAST:event_txtFosfEficFocusLost

    private void txtFontUtiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontUtiFocusLost
        if(!txtFontUti.getText().equals("")) {
            if (txtFontUti.getText().equals("1")) {
                fontUsadaFof = 1;
                calcFosforoCorrecao();
            } else if (txtFontUti.getText().equals("2")) {
                fontUsadaFof = 2;
                calcFosforoCorrecao();
            } else if (txtFontUti.getText().equals("3")) {
                fontUsadaFof = 3;
                calcFosforoCorrecao();
            } else {
                fontUsadaFof = 0;
                calcFosforoCorrecao();
            }
        }
    }//GEN-LAST:event_txtFontUtiFocusLost

    private void txtTeorFontFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTeorFontFocusLost
        calcFosforoCorrecao();
    }//GEN-LAST:event_txtTeorFontFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GerenciarFontes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarFontes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarFontes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarFontes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GerenciarFontes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JLabel lblAlet1;
    private javax.swing.JLabel lblCustoFof;
    private javax.swing.JLabel lblCustoPot;
    private javax.swing.JLabel lblFontAdicional;
    private javax.swing.JLabel lblFontAdicionalPot2;
    private javax.swing.JLabel lblFonteAdicionalPot3;
    private javax.swing.JLabel lblIdealCalcio;
    private javax.swing.JLabel lblPartPotCtcPorcent;
    private javax.swing.JLabel lblPartiCtcSoloMag;
    private javax.swing.JLabel lblPartiIdealMag;
    private javax.swing.JLabel lblParticCtcSoloCalcio;
    private javax.swing.JLabel lblPosCorrecaoCalcio;
    private javax.swing.JLabel lblPosCorrecaoMag;
    private javax.swing.JTextField lblPrintFont;
    private javax.swing.JLabel lblQtdAplicar;
    private javax.swing.JLabel lblQtdAplicarCalcioMag;
    private javax.swing.JLabel lblQtdAplicarPot;
    private javax.swing.JLabel lblQtdFont2;
    private javax.swing.JLabel lblQtdFonte;
    private javax.swing.JLabel lblTipoFont;
    private javax.swing.JLabel lblTipoFont2;
    private javax.swing.JLabel lblTipoFont3;
    private javax.swing.JLabel lblTipoFontePot;
    private javax.swing.JLabel lblTipoValFontAdicionalPot;
    private javax.swing.JLabel lblTipoValFonteAdicional2;
    private javax.swing.JLabel lblVPercentAtual;
    private javax.swing.JLabel lblVPercentIdeal;
    private javax.swing.JLabel lblVPercentPosCorrecao;
    private javax.swing.JLabel lblValFontAdicionalPot;
    private javax.swing.JLabel lblValFontAdicionarPot2;
    private javax.swing.JTextField txtFont1;
    private javax.swing.JTextField txtFont10;
    private javax.swing.JTextField txtFont11;
    private javax.swing.JTextField txtFont12;
    private javax.swing.JTextField txtFont2;
    private javax.swing.JTextField txtFont3;
    private javax.swing.JTextField txtFont4;
    private javax.swing.JTextField txtFont5;
    private javax.swing.JTextField txtFont6;
    private javax.swing.JTextField txtFont7;
    private javax.swing.JTextField txtFont8;
    private javax.swing.JTextField txtFont9;
    private javax.swing.JTextField txtFontMag2;
    private javax.swing.JTextField txtFontMag3;
    private javax.swing.JTextField txtFontMag4;
    private javax.swing.JTextField txtFontMag5;
    private javax.swing.JTextField txtFontMag6;
    private javax.swing.JTextField txtFontPot1;
    private javax.swing.JTextField txtFontPot2;
    private javax.swing.JTextField txtFontPot3;
    private javax.swing.JTextField txtFontUsadaCalcioMag;
    private javax.swing.JTextField txtFontUti;
    private javax.swing.JTextField txtFonteUsada;
    private javax.swing.JTextField txtFosfEfic;
    private javax.swing.JTextField txtPartiDesejadaCalcio;
    private javax.swing.JTextField txtPotCtcDesejada;
    private javax.swing.JTextField txtPotCtcPosCorrecao;
    private javax.swing.JTextField txtPotCtcSolo;
    private javax.swing.JTextField txtTeorCaoCorretivo;
    private javax.swing.JTextField txtTeorFont;
    // End of variables declaration//GEN-END:variables
}
