/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Analysis.Merage;


import Magic.Analysis.Base.TrackRelatedWalk;
import Magic.Analysis.Merage.MergePara.MutimatchPolicy;
import Magic.Analysis.Merage.MergePara.SNPType;
//import Magic.Analysis.MergePara.SpeicesType;
import Magic.Units.File.FileFormat.PieceField;
import Magic.WinMain.MagicFrame;
import Magic.Units.File.Parameter.StringRep;
import Magic.Units.File.Parameter.Log;
import Magic.Units.Track.Track;
import Magic.Units.Piece.GELIPiece;
import Magic.Units.Piece.GFFPiece;
import Magic.Units.Piece.GeneticUnit.BasePiece;
import Magic.Units.Piece.GeneticUnit.CDSPiece;
import Magic.Units.Piece.GeneticUnit.GenePiece;
import Magic.Units.Piece.GeneticUnit.GeneticPiece;
import Magic.Units.Piece.GeneticUnit.IntronPiece;
import Magic.Units.Piece.Piece;
import Magic.Units.Piece.GeneticUnit.SNPPiece;
import Magic.Units.Piece.GeneticUnit.Utr3Piece;
import Magic.Units.Piece.GeneticUnit.Utr5Piece;

import Magic.Units.Piece.PTTPiece;
import Magic.Units.Track.IteratorPieces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.RNATools;
import org.biojava.bio.symbol.SymbolList;
import org.biojava.bio.symbol.SymbolListViews;
import org.biojava.bio.symbol.TranslationTable;
import utils.ForMagic;
import utils.ReportInfo;
import utils.SequanceUtil;

/**
 *
 * @author Huabin Hou
 */
public class MergeAnnoImpl extends TrackRelatedWalk{

    public static int utrLen = 200;
    public static int site;
    public MagicFrame parent;
    // public Fasta reference;
 
    public MergeAnnoImpl(MagicFrame parent) {
        this.parent = parent;
//        this.reference=reference;
    }
    //private String refFile;
    private static String[] candidateClusters = {"ID", "geneID", "name"};

 

    /* public class SelectPiece {

    public String file;
    public ArrayList<String> selectField;
    public Piece piece;
    }*/
    //每个piece
 

    public void prepare(ArrayList<Track> cdssList) {
       // parent.monitor.reSet("Prepare" + StringRep.START, false, TrackRelatedWalk.getTotalCdssListSize(cdssList));
        //  ArrayList<Track> geneList = new ArrayList<Track>();
        // parent.monitor.appendMessage("cluster cds to gene and get gene sequence...");

        //只有reference含有相应chrom才需要Prepare Prepare是为了根据注释信息坐标获得序列
        for (int i = 0; i < cdssList.size(); i++) {

            Iterator trackIt = cdssList.get(i).iteratorPieces.contigPieces.entrySet().iterator();
            while (trackIt.hasNext()) {
                Entry entry = (Entry) trackIt.next();
                String chrom = (String) entry.getKey();
                if (ForMagic.ifChromInProject(chrom)) {
                    ArrayList<Piece> ap = (ArrayList<Piece>) entry.getValue();
                    for (Piece piece : ap) {
                        piece.geneticPiece.mergePrepare();
                    }
                }
            }

        }
        //   return geneList;
    }


    /*  public void processTrack(Piece snp, Track genesList, String contigName, MergePiece mp) {
    HashMap<String, ArrayList<Piece>> contigItems = genesList.contigItems;
    ArrayList<Piece> genes = contigItems.get(contigName);
    processContig(snp, genes, mp);
    }*/
    /*  public void processTrack(Piece snp, Track genesList, MergePiece mp) {
    HashMap<String, ArrayList<Piece>> contigItems = genesList.iteratorPieces.contigPieces;
    ArrayList<Piece> genes = contigItems.get(snp.geneticPiece.chrom);
    processContig(snp, genes, mp);
    }*/
    @Override
    public void processContig(Piece snp, ArrayList<Piece> genes, MergePiece mergePiece) {

        //  System.out.println(snp.geneticPiece.start+"  =======snp start");
        if (genes == null) {
            return;
        }
        if (snp.geneticPiece instanceof SNPPiece) {
            ((SNPPiece) snp.geneticPiece).snpType = SNPType.intergenic;
        }

        Iterator<Piece> iter = genes.iterator();
        while (iter.hasNext()) {
            Piece gene = iter.next();

            if (GeneticPiece.isInPieceLocation(snp, gene)) {
                // Piece correlatedPiece = new Piece();

                Piece correlatedPiece = new Piece(gene);
                // Piece correlatedPiece = gene;


                //获得snp type
                SNPType currentSNTType = null;
                if (snp.geneticPiece instanceof SNPPiece) {
                    currentSNTType = gene.geneticPiece.getSNPType(snp, correlatedPiece);
                    // System.out.println(((SNPPiece) snp.geneticPiece).snpType+"  "+snp.geneticPiece.start);
                }
                if (MergePara.currentMutimatchPolicy == MutimatchPolicy.Priority) {
                    if (snp.geneticPiece instanceof SNPPiece) {
                        setPriority(mergePiece, correlatedPiece, currentSNTType);
                    }
                    continue;
                } else if (MergePara.currentMutimatchPolicy == MutimatchPolicy.All) {
                    mergePiece.correlated.add(correlatedPiece);
                }
                //MutimatchPolicy 是针对一个track来说的
                if (MergePara.currentMutimatchPolicy == MutimatchPolicy.Random) {
                    break;
                }
            }
        }
    }

    public void setPriority(MergePiece mp, Piece correlatedPiece, SNPType currentSNTType) {
        if (mp.main.geneticPiece instanceof SNPPiece) {
            SNPType oldsnpType = ((SNPPiece) mp.main.geneticPiece).snpType;
            if (oldsnpType == null || isMorePrioSNPType(currentSNTType, oldsnpType)) {
                // System.out.println(oldsnpType+" "+currentSNTType+" "+mp.main.geneticPiece.start);
                mp.correlated = new ArrayList<Piece>();
                mp.correlated.add(correlatedPiece);
                ((SNPPiece) mp.main.geneticPiece).snpType = currentSNTType;
            }
        }
    }

    public static boolean isMorePrioSNPType(SNPType currentSNTType, SNPType oldsnpType) {
        //   System.out.println(currentSNTType+"---------------"+oldsnpType);
        if (currentSNTType == null) {
            return false;
        }
        if (oldsnpType == null) {
            return true;
        }
        if (oldsnpType.compareTo(currentSNTType) > 0) {
            return true;
        }
        return false;
    }

    public static void main(String argv[]) {
        System.out.println(isMorePrioSNPType(SNPType.missense, SNPType.UTR3));
    }

    /* public void process(Piece snp, ArrayList<Track> genesList, MergePiece mp) {

    for (Track track : genesList) {
    processTrack(snp, track, mp);
    }

    }*/

    /*  public int getCodonStart(Piece snp, Piece cds) {
    if (snp.geneticPiece.strand) {
    return snp.geneticPiece.start - (snp.geneticPiece.start - cds.geneticPiece.start) % 3;
    } else {
    return snp.geneticPiece.start - (3 - (cds.geneticPiece.end - snp.geneticPiece.start + 1) % 3);
    }
    }*/
   

    public boolean isReadThrough(String protean) {
        if (protean.equals("*")) {
            return true;
        }
        return false;
    }

    public static String translateCodon(String codon1) {
        SymbolList aa1 = null;
        TranslationTable eup = RNATools.getGeneticCode(MergePara.translationTable);
        try {
            //  System.out.println(codon1 + "  codon-------------");
            SymbolList seq1 = SymbolListViews.windowedSymbolList(DNATools.toRNA(DNATools.createDNA(codon1)), 3);
            //  //System.out.println(codon1+"---------codon1---------"+seq1.seqString());
            aa1 = SymbolListViews.translate(seq1, eup);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return aa1.seqString();

    }
}
