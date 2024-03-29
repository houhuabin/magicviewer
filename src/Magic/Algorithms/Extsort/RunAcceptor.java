/**
 * 
 */
package Magic.Algorithms.Extsort;

import java.io.IOException;

/**
 * @author yovn
 *
 */
public interface RunAcceptor {
	
	void startNewRun();
	void acceptRecord(Record recs)throws IOException;
	void closeRun();

	int getNumProductedStores();
	RecordStore getProductedStore(int index);
	RecordStore[] getProductedStores();
}
