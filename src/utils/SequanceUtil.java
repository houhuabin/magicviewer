/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import Magic.Methods.iEggMethods;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.symbol.IllegalAlphabetException;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.biojava.bio.symbol.SymbolList;

/**
 *
 * @author Huabin Hou
 */
public class SequanceUtil {
    public static String reverseComplement(String sequence)    {

        if(sequence==null || sequence.length()==0) return null;
        StringBuffer complement = new StringBuffer();
        for (int i = sequence.length() - 1; i >= 0; i--) {
            switch (sequence.charAt(i)) {
                case 'A':
                    complement.append('T');
                    break;
                case 'T':
                    complement.append('A');
                    break;
                case 'C':
                    complement.append('G');
                    break;
                case 'G':
                    complement.append('C');
                    break;
                default:
                    complement.append(sequence.charAt(i));
                    break;
            }
        }
        return complement.toString();
    }

        public static String reverse(String quality) {
        StringBuffer qua = new StringBuffer(quality);
        return qua.reverse().toString();
    }

    
    
    public static String complement(String sequence) {
        if(sequence==null || sequence.length()==0) return null;
        StringBuffer complement = new StringBuffer();
        for (int i =0; i <  sequence.length() ; i++) {
            switch (sequence.charAt(i)) {
                case 'A':
                    complement.append('T');
                    break;
                case 'T':
                    complement.append('A');
                    break;
                case 'C':
                    complement.append('G');
                    break;
                case 'G':
                    complement.append('C');
                    break;
                default:
                    complement.append(sequence.charAt(i));
                    break;
            }
        }
        return complement.toString();
    }

    public static void main(String argvs[])
    {
        String read="ATGTTATTTTGGT";
        System.out.println(SequanceUtil.reverseComplement(read));

    }
   

}
