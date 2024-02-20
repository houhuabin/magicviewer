/**
 * 
 */
package Magic.Algorithms.Extsort;

import Magic.Algorithms.Extsort.AlgConstant;
import Magic.Units.File.Parameter.Log;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import utils.FileUtil;

/**
 * @author yovn
 *
 */
public class FileOneLineRecord implements RecordStore, ResultAcceptor {

    private static class _Record implements Record {

        static final _Record NULL_RECORD = new _Record(null, null, "");
        String[] value;
        String[] type;
        private String txt;

        _Record(String[] value, String[] type, String txt) {
            //value = v;
            this.value = value;
            this.type = type;
            this.txt = txt;
        }

        @Override
        public boolean isNull() {

            return this == NULL_RECORD;
        }

        @Override
        public int compareTo(Record o) {
            _Record other = (_Record) o;
            if (other == this) {
                return 0;
            }
            if (value == null && other.value == null) {
                return 0;
            } else if (value == null) {
                return 1;
            } else if (other.value == null) {
                return -1;
            }

            for (int i = 0; i < value.length; i++) {
                if (type[i].equals("Number")) {
                    if (Double.valueOf(value[i]) == Double.valueOf(other.value[i])) {
                        continue;
                    } else {
                        return (int) (Double.valueOf(value[i]).compareTo(Double.valueOf(other.value[i])));
                    }

                } else if (type[i].equals("String")) {
                    if (value[i].equals(other.value[i])) {
                        continue;
                    } else {
                        return value[i].compareTo(other.value[i]);
                    }
                }
            }

            return 0;
        }

        public String toString() {
            if (this == NULL_RECORD) {
                return "NULL_RECORD";
            }
            return txt;
        }
    }
    private String fileName;
    private BufferedReader reader;
    private PrintStream ps;
    //  private FormatBean formatBean;
    boolean eof;
    int count = 0;
    Record prev = null;

    public FileOneLineRecord(String name) {

        fileName = name;


    }

    public void storeRecord(Record r) throws IOException {
        if (ps == null) {
            OutputStream out = new FileOutputStream(fileName);
            ps = new PrintStream(new BufferedOutputStream(out, 12 * 1024));
        }
        ps.println(r.toString());
    }

    public void compact() {
        if (ps != null) {
            ps.flush();
            ps.close();
            ps = null;
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            reader = null;
        }

    }

    /* (non-Javadoc)
     * @see algorithms.extsort.RecordStore#readNextRecord()
     */
    @Override
    public Record readNextRecord() throws IOException {
        if (eof) {
            return _Record.NULL_RECORD;
        }
        if (reader == null) {
            InputStream in = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(in), 12 * 1024);
        }
        if (!reader.ready()) {
            eof = true;
            return _Record.NULL_RECORD;
        }
        String line = reader.readLine();
        // String head=line.substring(0,10).trim();
        String[] tempA = line.split("\t");

        String[] head = new String[AlgConstant.sortIndex.length];
        for (int i = 0; i < head.length; i++) {
            head[i] = tempA[AlgConstant.sortIndex[i]];

        }
        //  String head = tempA[AlgConstant.sortIndex];
        //  //System.out.println("===="+head);
        //int val = Integer.valueOf(head);
        _Record ret = new _Record(head, AlgConstant.sortType, line);
        return ret;

    }

    @Override
    public void acceptRecord(Record rec) throws IOException {
        count++;
        if (prev == null) {
            prev = rec;
        } else if (prev.compareTo(rec) > 0) {
            throw new IOException(" sorted error!!!");

        }
        ps.println(rec.toString());
        prev = rec;
    }

    @Override
    public void end() throws IOException {

        if (ps != null) {
            ps.flush();
            ps.close();
            ps = null;
        }
      //  FileUtil.deleteFile(fileName);
    }

    @Override
    public void start() throws IOException {
        if (ps == null) {
            OutputStream out = new FileOutputStream(fileName);
            ps = new PrintStream(new BufferedOutputStream(out, 12 * 1024));
        }

    }

    @Override
    public void destroy() {
        compact();
        File f = new File(fileName);
        f.delete();

    }
}
