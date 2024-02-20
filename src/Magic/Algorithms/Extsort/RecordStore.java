/**
 * 
 */
package Magic.Algorithms.Extsort;

import java.io.IOException;

/**
 * Should provide buffer  mechanism 
 * @author yovn
 *
 */
public interface RecordStore {
	Record readNextRecord()throws IOException;
	void destroy();

}
