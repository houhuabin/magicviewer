package Magic.Units.Main;

import java.awt.Component;
import java.awt.Point;

import java.io.File;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class SeeEggMethods {

    public static String transfer(String kstring) {
        String complement = "";
        for (int i = kstring.length() - 1; i >= 0; i--) {
            switch (kstring.charAt(i)) {
                case 'A':
                    complement += 'T';
                    break;
                case 'T':
                    complement += 'A';
                    break;
                case 'C':
                    complement += 'G';
                    break;
                case 'G':
                    complement += 'C';
                    break;
                case '[':
                    complement += ']';
                    break;
                case ']':
                    complement += '[';
                    break;
                default:
                    complement += kstring.charAt(i);
                    break;
            }
        }
        return complement;
    }

    public static String getSubString(Vector vector, int start, int end) {
        String s = "";
        for (int i = start; i < end; i++) {
            s += (Character) (vector.elementAt(i));
        }
        return s;
    }

    public static String getSubString(char[] seq, int start, int end) {
        String s = "";
        for (int i = start; i < end; i++) {
            s += seq[i];
        }
        return s;
    }

    public static Vector indexOf(Vector vector, String kstring, int index) {
        Vector results = null;
        if (vector == null || vector.size() == 0 || kstring == null || kstring.length() == 0) {
            return results;
        }
        int length = kstring.length();
        int size = vector.size();
        if (size - index < length) {
            return results;
        }
        for (int i = index; i <= size - length; i++) {
            boolean match = true;
            for (int j = 0; j < length; j++) {
                if (kstring.charAt(j) != ((Character) (vector.elementAt(i + j))).charValue()) {
                    match = false;
                    break;
                }
            }
            if (match) {
                if (results == null) {
                    results = new Vector();
                }
                results.add(i);
            }
        }
        return results;
    }

    public static Vector indexOf(char[] seq, String kstring, int index) {
        Vector results = null;
        if (seq == null || seq.length == 0 || kstring == null || kstring.length() == 0) {
            return results;
        }
        int length = kstring.length();
        int size = seq.length;
        if (size - index < length) {
            return results;
        }
        for (int i = index; i <= size - length; i++) {
            boolean match = true;
            for (int j = 0; j < length; j++) {
                if (kstring.charAt(j) != seq[i + j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                if (results == null) {
                    results = new Vector();
                }
                results.add(i);
            }
        }
        return results;
    }

    public static Vector findOligoNucleotide(char[] seq, String kstring) {
        Vector results_plus = null;
        Vector results_minus = null;
        results_plus = indexOf(seq, kstring, 0);
        results_minus = indexOf(seq, transfer(kstring), 0);

        Vector vector = new Vector();
        if (results_plus != null) {
            for (int i = 0; i < results_plus.size(); i++) {
                vector.add("+" + ((Integer) results_plus.elementAt(i) + 1));
            }
        }
        if (results_minus != null) {
            for (int i = 0; i < results_minus.size(); i++) {
                vector.add("-" + ((Integer) results_minus.elementAt(i) + 1));
            }
        }
        return vector;
    }

    public static Vector<Point> findPeaks(boolean[] value) {
        Vector<Point> vector = new Vector<Point>();

        int start = 0;
        int end = 0;
        boolean in_region = false;

        for (int i = 0; i < value.length; i++) {
            if (value[i]) {
                if (!in_region) {
                    start = i;
                    in_region = true;
                }
            } else {
                if (in_region && i > 0) {
                    end = i - 1;
                    in_region = false;

                    Point p = new Point(start, end);
                    vector.add(p);
                }
            }
        }
        if (in_region) {
            Point p = new Point(start, value.length - 1);
            vector.add(p);
        }

        return vector;
    }

    public static Vector<Point> findPeaks(int[] value) {
        Vector<Point> vector = new Vector<Point>();

        int start = 0;
        int end = 0;
        boolean in_region = false;

        for (int i = 0; i < value.length; i++) {
            if (value[i]>0) {
                if (!in_region) {
                    start = i;
                    in_region = true;
                }
            } else {
                if (in_region && i > 0) {
                    end = i - 1;
                    in_region = false;

                    Point p = new Point(start, end);
                    vector.add(p);
                }
            }
        }
        if (in_region) {
            Point p = new Point(start, value.length - 1);
            vector.add(p);
        }

        return vector;
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void quickSort(Vector<Integer> key, Vector value, int first, int last, boolean increase) {
        int low = first;
        int high = last;
        if (first >= last) {
            return;
        }
        int mid = (key.elementAt((first + last) / 2)).intValue();
        do {
            if (increase) {
                while ((key.elementAt(low)).intValue() < mid) {
                    low++;
                }
                while ((key.elementAt(high)).intValue() > mid) {
                    high--;
                }
            } else {
                while ((key.elementAt(low)).intValue() > mid) {
                    low++;
                }
                while ((key.elementAt(high)).intValue() < mid) {
                    high--;
                }
            }
            if (low <= high) {
                int temp_key = key.elementAt(low);
                Object temp_value = value.elementAt(low);

                key.setElementAt(key.elementAt(high), low);
                value.setElementAt(value.elementAt(high), low);

                key.setElementAt(temp_key, high);
                value.setElementAt(temp_value, high);

                low++;
                high--;
            }
        } while (low <= high);
        quickSort(key, value, first, high, increase);
        quickSort(key, value, low, last, increase);
    }

    public static void quickSort(String[] key, Vector value, int first, int last, boolean increase) {
        int low = first;
        int high = last;
        ////System.out.println(first+","+last);
        if (first >= last) {
            return;
        }
        String mid = key[(first + last) / 2];
        do {
            if (increase) {
                while (key[low].compareTo(mid) < 0) {
                    low++;
                }
                while (key[high].compareTo(mid) > 0) {
                    high--;
                }
            } else {
                while (key[low].compareTo(mid) > 0) {
                    low++;
                }
                while (key[high].compareTo(mid) < 0) {
                    high--;
                }
            }
            if (low <= high) {
                String temp_key = key[low];
                Object temp_value = value.elementAt(low);

                key[low] = key[high];
                value.setElementAt(value.elementAt(high), low);

                key[high] = temp_key;
                value.setElementAt(temp_value, high);

                low++;
                high--;
            }
        } while (low <= high);
        quickSort(key, value, first, high, increase);
        quickSort(key, value, low, last, increase);
    }

    public static boolean checkFileExist(String filename) {
        try {
            File infile = new File(filename);
            ////System.out.println("Checking "+infile.getPath()+" "+infile.exists());
            if (infile.exists()) {
                return true;
            }
        } catch (Exception e) {
            //System.out.println("Exception in checkFileExist()");
        }
        return false;
    }

    public static String getNonExistedFile(String filename, Component parent) {
        String name = filename;

        while (checkFileExist(name)) {
            int overlap = JOptionPane.showConfirmDialog(parent, filename + " is already existed, replace?");
            switch (overlap) {
                case JOptionPane.YES_OPTION:
                    return name;
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return null;
                case JOptionPane.CLOSED_OPTION:
                    return null;
            }

            JFileChooser filechooser = new JFileChooser();
            filechooser.setCurrentDirectory(new File(name));
            filechooser.showSaveDialog(parent);
            File file = filechooser.getSelectedFile();
            name = file.getAbsolutePath();
            if (file == null) {
                continue;
            }
        }

        return name;
    }

  
}
