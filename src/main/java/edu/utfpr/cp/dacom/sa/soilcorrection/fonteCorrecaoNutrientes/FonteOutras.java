/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.utfpr.cp.dacom.sa.soilcorrection.fonteCorrecaoNutrientes;

/**
 *
 * @author pedropereira
 */
public interface FonteOutras {
    public double calcSCmol(int item);
    public double calcCrcCmol(int item);
    public double calcVAtual(double item);
    public double calcGdm3Decimal(double val2);
    public String calGdm4Decimal(double val3);
}
