/**
 * 
 */
package Magic.Algorithms.Extsort;

import java.io.IOException;

/**
 * @author yovn
 *
 */
public interface ResultAcceptor {
	
	
	void start()throws IOException;
	void end()throws IOException;
	void acceptRecord(Record rec)throws IOException;

}
