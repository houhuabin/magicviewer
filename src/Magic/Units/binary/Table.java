/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.Units.binary;

/**
 *
 * @author Huabin Hou
 */
public class Table {

    public static String[] binaryByeTable = {"AAAA", "AAAC", "AAAG", "AAAT", "AACA", "AACC", "AACG", "AACT", "AAGA", "AAGC",
        "AAGG", "AAGT", "AATA", "AATC", "AATG", "AATT", "ACAA", "ACAC", "ACAG", "ACAT",
        "ACCA", "ACCC", "ACCG", "ACCT", "ACGA", "ACGC", "ACGG", "ACGT", "ACTA", "ACTC",
        "ACTG", "ACTT", "AGAA", "AGAC", "AGAG", "AGAT", "AGCA", "AGCC", "AGCG", "AGCT",
        "AGGA", "AGGC", "AGGG", "AGGT", "AGTA", "AGTC", "AGTG", "AGTT", "ATAA", "ATAC",
        "ATAG", "ATAT", "ATCA", "ATCC", "ATCG", "ATCT", "ATGA", "ATGC", "ATGG", "ATGT",
        "ATTA", "ATTC", "ATTG", "ATTT", "CAAA", "CAAC", "CAAG", "CAAT", "CACA", "CACC",
        "CACG", "CACT", "CAGA", "CAGC", "CAGG", "CAGT", "CATA", "CATC", "CATG", "CATT",
        "CCAA", "CCAC", "CCAG", "CCAT", "CCCA", "CCCC", "CCCG", "CCCT", "CCGA", "CCGC",
        "CCGG", "CCGT", "CCTA", "CCTC", "CCTG", "CCTT", "CGAA", "CGAC", "CGAG", "CGAT",
        "CGCA", "CGCC", "CGCG", "CGCT", "CGGA", "CGGC", "CGGG", "CGGT", "CGTA", "CGTC",
        "CGTG", "CGTT", "CTAA", "CTAC", "CTAG", "CTAT", "CTCA", "CTCC", "CTCG", "CTCT",
        "CTGA", "CTGC", "CTGG", "CTGT", "CTTA", "CTTC", "CTTG", "CTTT", "GAAA", "GAAC",
        "GAAG", "GAAT", "GACA", "GACC", "GACG", "GACT", "GAGA", "GAGC", "GAGG", "GAGT",
        "GATA", "GATC", "GATG", "GATT", "GCAA", "GCAC", "GCAG", "GCAT", "GCCA", "GCCC",
        "GCCG", "GCCT", "GCGA", "GCGC", "GCGG", "GCGT", "GCTA", "GCTC", "GCTG", "GCTT",
        "GGAA", "GGAC", "GGAG", "GGAT", "GGCA", "GGCC", "GGCG", "GGCT", "GGGA", "GGGC",
        "GGGG", "GGGT", "GGTA", "GGTC", "GGTG", "GGTT", "GTAA", "GTAC", "GTAG", "GTAT",
        "GTCA", "GTCC", "GTCG", "GTCT", "GTGA", "GTGC", "GTGG", "GTGT", "GTTA", "GTTC",
        "GTTG", "GTTT", "TAAA", "TAAC", "TAAG", "TAAT", "TACA", "TACC", "TACG", "TACT",
        "TAGA", "TAGC", "TAGG", "TAGT", "TATA", "TATC", "TATG", "TATT", "TCAA", "TCAC",
        "TCAG", "TCAT", "TCCA", "TCCC", "TCCG", "TCCT", "TCGA", "TCGC", "TCGG", "TCGT",
        "TCTA", "TCTC", "TCTG", "TCTT", "TGAA", "TGAC", "TGAG", "TGAT", "TGCA", "TGCC",
        "TGCG", "TGCT", "TGGA", "TGGC", "TGGG", "TGGT", "TGTA", "TGTC", "TGTG", "TGTT",
        "TTAA", "TTAC", "TTAG", "TTAT", "TTCA", "TTCC", "TTCG", "TTCT", "TTGA", "TTGC",
        "TTGG", "TTGT", "TTTA", "TTTC", "TTTG", "TTTT"};
    public static byte[] nst_nt4_table = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //16
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //32
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5 /*'-'*/, 4, 4,//48
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //64

        // a     c           g                  //n
        4, 0, 4, 1, 4, 4, 4, 2, 4, 4, 4, 4, 4, 4, 4, 4, //80
                                               
        4, 4, 4, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //96
        //  r  s   t
        4, 0, 4, 1, 4, 4, 4, 2, 4, 4, 4, 4, 4, 4, 4, 4, //112

        4, 4, 4, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //128
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //144
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //160
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //176
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //192
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //208
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //224
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, //240
        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 //256
    };

    //a 8  t 4  c 2  g 1
    // a0   c1   g2   t3
     public static byte[] bToFTable = {8,2,1,4};

 public static byte[][] fullToBiTable={{0},{2},{1},{2,1},{3},{3,2},{3,1},{3,1,2},{0},{0,2},{0,1},{0,1,2},{0,3},{0,3,2},{0,3,1},{0,3,1,2}};
     public static String[] full_table = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", //16
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", //32
        "", "", "", "", "", "", "", "", "", "", "", "", "", "" /*'-'*/, "", "",//""8
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", //6""
        "", "A", "CGT", "C", "AGT", "", "", "G", "ACT", "", "", "GT", "", "AC", "ACGT", "", //80
        "", "", "ag", "gc", "T", "", "ACG", "AT", "CT", "", "", "", "", "", "", "", //96
        "", "a", "cgt", "c", "agt", "", "", "g", "act", "", "", "gt", "", "ac", "acgt", "", //112
        "", "", "ag", "gc", "t", "", "acg", "at", "ct", "", "", "", "", "", "", "", //128
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", //1""""
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", //160
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", //176
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", //192
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", //208
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", //22""
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", //2""0
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" //256
    };


/*M=A/C, K=G/T, Y=C/T, R=A/G, W=A/T, S=G/C,
D=A/G/T, B=C/G/T, H=A/C/T, V=A/C/G,
N=A/C/G/T
*/
  public static String[] binaryFullTable = { "  ", "G ","C ","S ","T ","K ","X ","B ","A ","R ","M ","V ","W ","D ","H ","N ","G ",
 "GG","GC","GS","GT","GK","GX","GB","GA","GR","GM","GV","GW","GD","GH","GN","C ",
 "CG","CC","CS","CT","CK","CX","CB","CA","CR","CM","CV","CW","CD","CH","CN","S ",
  "SG","SC","SS","ST","SK","SX","SB","SA","SR","SM","SV","SW","SD","SH","SN","T ",
"TG","TC","TS","TT","TK","TX","TB","TA","TR","TM","TV","TW","TD","TH","TN","K ",
  "KG","KC","KS","KT","KK","KX","KB","KA","KR","KM","KV","KW","KD","KH","KN","X ",
  "XG","XC","XS","XT","XK","XX","XB","XA","XR","XM","XV","XW","XD","XH","XN","B ",
"BG","BC","BS","BT","BK","BX","BB","BA","BR","BM","BV","BW","BD","BH","BN","A ",
"AG","AC","AS","AT","AK","AX","AB","AA","AR","AM","AV","AW","AD","AH","AN","R ",
"RG","RC","RS","RT","RK","RX","RB","RA","RR","RM","RV","RW","RD","RH","RN","M ",
"MG","MC","MS","MT","MK","MX","MB","MA","MR","MM","MV","MW","MD","MH","MN","V ",
"VG","VC","VS","VT","VK","VX","VB","VA","VR","VM","VV","VW","VD","VH","VN","W ",
"WG","WC","WS","WT","WK","WX","WB","WA","WR","WM","WV","WW","WD","WH","WN","D ",
"DG","DC","DS","DT","DK","DX","DB","DA","DR","DM","DV","DW","DD","DH","DN","H ",
"HG","HC","HS","HT","HK","HX","HB","HA","HR","HM","HV","HW","HD","HH","HN","N ",
"NG","NC","NS","NT","NK","NX","NB","NA","NR","NM","NV","NW","ND","NH","NN"};

    //a 8  t 4  c 2  g 1   z代表全部 n代表全非
    public static byte[] full_alpha_table = {16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, //16
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, //32
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 5 /*'-'*/, 16, 16,//48
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, //64
        //  a  b  c  d           g   h          k      m   n
        16, 8, 7, 2, 13, 16, 16, 1, 14, 16, 16, 5, 16, 10, 0, 16, //80
        //      r  s  t      v   w       y      z
        16, 16, 9, 3, 4, 16, 11, 12, 16, 6, 16, 15, 16, 16, 16, 16, //96
        16, 8, 7, 2, 13, 16, 16, 1, 14, 16, 16, 5, 16, 10, 0, 16, //112

        16, 16, 9, 3, 4, 16, 11, 12, 16, 6, 16, 15, 16, 16, 16, 16, //128
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, //144
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, //160
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, //176
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, //192
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, //208
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, //224
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, //240
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16 //256
    };
}
