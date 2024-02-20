/**
 * 
 */
package Magic.Algorithms.Extsort;

import java.io.IOException;
import java.util.Random;

/**
 * @author yovn
 *
 */
public class MemRecordStore implements RecordStore {

    private static class _Record implements Record {

        static final _Record NULL_RECORD = new _Record(null,null);
        String[] value;
        String[] type;

        _Record(int v) {
            //value = v;
            value=new String[1];
            value[1]=String.valueOf(v);
            type=new String[1];
            value[1]="Number";
        }
        _Record(String[] value,String[] type) {
            //value = v;
           this.value=value;
           this.type=type;
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
            for (int i = 0; i < value.length; i++) {
                if (type[i].equals("Number")) {
                    if (Double.valueOf(value[i]) == Double.valueOf(other.value[i])) {
                        continue;
                    } else {
                        return (int) (Double.valueOf(value[i]).compareTo(Double.valueOf(other.value[i])));
                    }


                }else if (type[i].equals("String"))
                {
                     if (value[i].equals(other.value[i])) {
                        continue;
                    }
                     else
                     {
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
            String s="";
            s.compareTo("");
            return String.valueOf(value);
        }
    }
    private _Record[] recs;
    int pos;
    int limit;

    public MemRecordStore() {
        this(100, false);
    }

    public void storeRecord(Record r) {
        if (pos >= recs.length) {
            int enlarge = recs.length / 2;
            _Record[] temp = new _Record[enlarge + recs.length];
            System.arraycopy(recs, 0, temp, 0, recs.length);
            recs = temp;
        }
        recs[pos++] = (_Record) r;
    }

    public void compact() {
        limit = pos;
        pos = 0;
    }

    public MemRecordStore(int num, boolean init) {
        recs = new _Record[num];
        if (init) {
            Random r = new Random();
            for (int i = 0; i < num; i++) {
                recs[i] = new _Record(r.nextInt(num));
            }
        }
        pos = 0;
        limit = num;
    }

    public MemRecordStore(int[] numbers) {
        recs = new _Record[numbers.length];
        Random r = new Random();
        for (int i = 0; i < numbers.length; i++) {
            recs[i] = new _Record(numbers[i]);
        }
        pos = 0;
        limit = numbers.length;
    }

    /* (non-Javadoc)
     * @see algorithms.extsort.RecordStore#readNextRecord()
     */
    @Override
    public Record readNextRecord() throws IOException {
        if (pos >= limit) {
            return _Record.NULL_RECORD;
        }
        return recs[pos++];
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
}
