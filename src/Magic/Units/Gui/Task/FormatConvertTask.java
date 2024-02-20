/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.Gui.Task;

import Magic.Units.File.Parameter.StringRep;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.StrandedFeature;
import org.biojava.bio.seq.io.SymbolTokenization;
import org.biojava.bio.symbol.Location;
import org.biojavax.Namespace;
import org.biojavax.RichObjectFactory;
import org.biojavax.SimpleNamespace;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.RichSequenceIterator;
import org.biojavax.bio.seq.io.EMBLFormat;
import org.biojavax.bio.seq.io.FastaFormat;
import org.biojavax.bio.seq.io.GenbankFormat;
import org.biojavax.bio.seq.io.RichSequenceBuilderFactory;
import org.biojavax.bio.seq.io.RichSequenceFormat;
import org.biojavax.bio.seq.io.RichStreamReader;
import org.biojavax.bio.seq.io.RichStreamWriter;
import org.biojavax.ontology.SimpleComparableTerm;
import utils.ReportInfo;

/**
 *
 * @author Administrator
 */
public class FormatConvertTask extends SimpleTask {
    //private ArrayList<String> filenames;
    //  private ArrayList<String> tasknames;

    private String in_type;
   // private String in_file;
    private String out_type;
    private String out_file;
    private FileReader fr;
    private BufferedReader br;
    private FileOutputStream fout;
    private BufferedOutputStream bout;
    private DataOutputStream dout;

    //  private int oldTrackNum;
    public FormatConvertTask(String in_type, String in_file, String out_type, String out_file) {
        try {
         //   this.in_file = in_file;
            this.out_file = out_file;
            this.in_type = in_type;
            this.out_type = out_type;
            fr = new FileReader(in_file);
            br = new BufferedReader(fr);

            fout = new FileOutputStream(out_file);
            bout = new BufferedOutputStream(fout);
            dout = new DataOutputStream(bout);
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
        // this.tasknames=tasknames;

    }

    public void runTask(int paramInt) throws Exception {
        try {

            SymbolTokenization dna = DNATools.getDNA().getTokenization("token");
            RichSequenceBuilderFactory factory = RichSequenceBuilderFactory.THRESHOLD;
            Namespace bloggsns = (Namespace) RichObjectFactory.getObject(SimpleNamespace.class, new Object[]{"bloggs"});

            RichSequenceFormat in_rsf = null;
            RichSequenceFormat out_rsf = null;


            if (out_type.equals(StringRep.GBK) || out_type.equals(StringRep.EMBL) || out_type.equals(StringRep.GENOME)) {
                if (in_type.equals(StringRep.GBK)) {
                    in_rsf = new GenbankFormat();
                } else if (in_type.equals(StringRep.EMBL)) {
                    in_rsf = new EMBLFormat();
                } else {
                    //System.out.println("Wrong format: " + in_type);
                    return;
                }

                if (out_type.equals(StringRep.GBK)) {
                    out_rsf = new GenbankFormat();
                } else if (out_type.equals(StringRep.EMBL)) {
                    out_rsf = new EMBLFormat();
                } else if (out_type.equals(StringRep.GENOME)) {
                    out_rsf = new FastaFormat();
                } else {
                    //System.out.println("Wrong format: " + out_type);
                    return;
                }

                RichStreamReader seqs_in = new RichStreamReader(br, in_rsf, dna, factory, bloggsns);
                RichStreamWriter seqs_out = new RichStreamWriter(dout, out_rsf);
                seqs_out.writeStream(seqs_in, bloggsns);
            } else {
                if (out_type.equals(StringRep.GFF) || out_type.equals(StringRep.PTT)) {
                    Date date = new Date();
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                    StringBuffer title = new StringBuffer();
                    if (out_type.equals(StringRep.GFF)) {
                        title.append("##gff-version 3\n");
                        title.append("##source-version inGAP1.0\n");
                        title.append("##date " + dateformat.format(date) + "\n");
                        title.append("##Type DNA " + StringRep.NULL + "\n");
                    } else if (out_type.equals(StringRep.PTT)) {
                        title.append(out_file + "\n");
                        title.append(dateformat.format(date) + "\n");
                        title.append("Location	Strand	Length	PID	Gene	Synonym	Code	COG	Product\n");
                    }
                    dout.writeBytes(title.toString());
                }

                RichSequenceIterator seqs = RichSequence.IOTools.readGenbank(br, dna, factory, bloggsns);
                while (seqs.hasNext()) {
                    if (!isOK()) {
                        close();
                        return;
                    }

                    RichSequence rs = seqs.nextRichSequence();
                    String seq_name = rs.getName();
                    int count = 1;
                    for (Iterator it = rs.features(); it.hasNext();) {
                        StrandedFeature f = (StrandedFeature) it.next();

                        if (out_type.equals(StringRep.CDS) || out_type.equals(StringRep.PROTEIN)) {
                            if (!f.getType().equals("CDS")) {
                                continue;
                            }
                        } else if (out_type.equals(StringRep.GFF) || out_type.equals(StringRep.PTT)) {
                            //GFF and PTT is only for coding region
                            if (!f.getType().equals("CDS")) {
                                continue;
                            }
                        }

                        char strand = '+';
                        if (f.getStrand().equals(StrandedFeature.NEGATIVE)) {
                            strand = '-';
                        }
                        Location location = f.getLocation();

                        String gi = "";
                        String gene = "";
                        String product = "";
                        String translation = "";
                        String codon_start = "0";

                        Iterator it1 = f.getAnnotation().keys().iterator();
                        while (it1.hasNext()) {
                            ////System.out.println(it1.next().getClass());
                            String key = ((SimpleComparableTerm) it1.next()).getName();
                            if (key.equals("db_xref")) {
                                String s = f.getAnnotation().getProperty(key).toString();
                                StringTokenizer nizer1 = new StringTokenizer(s, ",[]");
                                while (nizer1.hasMoreTokens()) {
                                    if (gi.length() > 0) {
                                        gi += "|";
                                    }
                                    gi += nizer1.nextToken().trim().replace(':', '|');
                                }
                            } else if (key.equals("gene")) {
                                gene = f.getAnnotation().getProperty(key).toString();
                            } else if (key.equals("product")) {
                                product = f.getAnnotation().getProperty(key).toString();
                            } else if (key.equals("translation")) {
                                translation = f.getAnnotation().getProperty(key).toString();
                            } else if (key.equals("codon_start")) {
                                codon_start = f.getAnnotation().getProperty(key).toString();
                            }
                        }

                        StringBuffer content = new StringBuffer();
                        if (out_type.equals(StringRep.CDS) || out_type.equals(StringRep.PROTEIN)) {
                            content.append(">");
                            if (gi.length() > 0) {
                                content.append(gi);
                            } else {
                                content.append(f.getType() + count);
                            }
                            if (gene.length() > 0) {
                                content.append(" " + gene);
                            }
                            if (product.length() > 0) {
                                content.append(", " + product);
                            }
                            content.append("\n");

                            if (out_type.equals(StringRep.CDS)) {
                                String seq_string = f.getSymbols().seqString();
                                for (int a = 0; a < seq_string.length(); a++) {
                                    if (a != 0 && a % 60 == 0) {
                                        content.append("\n");
                                    }
                                    content.append(seq_string.charAt(a));
                                }
                            } else if (out_type.equals(StringRep.PROTEIN)) {
                                if (translation.length() > 0) {
                                    for (int a = 0; a < translation.length(); a++) {
                                        if (a != 0 && a % 60 == 0) {
                                            content.append("\n");
                                        }
                                        content.append(translation.charAt(a));
                                    }
                                }
                            }
                            content.append("\n");
                        }
                        if (out_type.equals(StringRep.GFF) || out_type.equals(StringRep.PTT)) {
                            if (out_type.equals(StringRep.GFF)) {
                                if (seq_name.length() > 0) {
                                    content.append(seq_name); //for seqname
                                } else {
                                    content.append("\t.");
                                }

                                content.append("\tMagicViewer");  //for source
                                content.append("\t" + f.getType());
                                content.append("\t" + location.getMin());
                                content.append("\t" + location.getMax());
                                content.append("\t" + ".");
                                content.append("\t" + strand);
                                content.append("\t" + codon_start);
                                content.append("\t.");

                            } else if (out_type.equals(StringRep.PTT)) {
                                content.append(location.getMin() + ".." + location.getMax());
                                content.append("\t" + strand);
                                content.append("\t" + (location.getMax() - location.getMin()));

                                if (gi.length() > 0) {
                                    content.append("\t" + gi);
                                } else {
                                    content.append("\t.");
                                }

                                if (gene.length() > 0) {
                                    content.append("\t" + gene);
                                } else {
                                    content.append("\t.");
                                }

                                content.append("\t" + f.getType() + count);
                                content.append("\t.");
                                content.append("\t.");

                                if (gene.length() > 0) {
                                    content.append("\t" + product);
                                } else {
                                    content.append("\t.");
                                }
                            }
                            content.append("\n");
                        }

                        dout.writeBytes(content.toString());
                        count++;
                    }
                }
            }
            close();

        } catch (Exception e) {
           ReportInfo.reportError("", e);
        }
    }

    private void close() {
        try {
            fr.close();
            br.close();

            dout.close();
            bout.close();
            fout.close();
        } catch (Exception e) {
            ReportInfo.reportError("", e);
        }
    }

    public ArrayList<String> getNames() {
        ArrayList<String> tasknames = new ArrayList<String>();
        tasknames.add("Convert " + in_type + " file to" + out_type + " file.");
        return tasknames;
    }
}
