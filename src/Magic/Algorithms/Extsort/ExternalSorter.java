/**
 * 
 */
package Magic.Algorithms.Extsort;

import Magic.Algorithms.Extsort.AlgConstant;
import java.io.IOException;

import Magic.Algorithms.Extsort.MinHeap;


/**
 * @author yovn
 *
 */
public class ExternalSorter {
	
		
	
	public void sort(int heapSize,RecordStore source,RunAcceptor mediator ,ResultAcceptor ra)throws IOException
	{
		MinHeap<Record> heap=new MinHeap<Record>(Record.class,heapSize);
		for(int i=0;i<heapSize;i++)
		{
			Record r=source.readNextRecord();
			if(r.isNull())break;
			heap.insert(r);
		}
		
		Record readR=source.readNextRecord();
		while(!readR.isNull()||!heap.isEmpty())
		{
		
			Record curR=null;
           
			//begin output one run
			mediator.startNewRun();
			while(!heap.isEmpty())
			{
				curR=heap.removeMin();
			
				mediator.acceptRecord(curR);
				
				if (!readR.isNull()) {
					if (readR.compareTo(curR) < 0) {
						heap.addToTail(readR);
					} else
						heap.insert(readR);
				}
				readR=source.readNextRecord();
				
			}
			//done one run
			mediator.closeRun();
			
			//prepare for next run
			heap.reverse();
			while(!heap.isFull()&&!readR.isNull())
			{
				// j++;   //System.out.print("=============="+j);
				heap.insert(readR);
				readR=source.readNextRecord();
				
			}
			
			
		}
     
		RecordStore[] stores=mediator.getProductedStores();
		//LoserTree  lt=new LoserTree(stores);
        /*
		WinnerTree  lt=new WinnerTree(stores);
		
		Record least=lt.nextLeastRecord();
		ra.start();
		while(!least.isNull())
		{
			ra.acceptRecord(least);
			least=lt.nextLeastRecord();
		}
		ra.end();*/
         if (stores.length > 1) {
            WinnerTree lt = new WinnerTree(stores);

            Record least = lt.nextLeastRecord();
            ra.start();
            while (!least.isNull()) {
                ra.acceptRecord(least);
                least = lt.nextLeastRecord();
            }
            ra.end();
        } else {
            Record least = stores[0].readNextRecord();
            ra.start();
            while (!least.isNull()) {
                ra.acceptRecord(least);
                least = stores[0].readNextRecord();
            }
            ra.end();
        }
		
		for(int i=0;i<stores.length;i++)
		{
			stores[i].destroy();
		}
	}
	
    public  void doSort(String file,int sortIndex) throws IOException
	{
//		RecordStore store=new MemRecordStore(60004,true);
//		RunAcceptor mediator=new MemRunAcceptor();
//		ResultAcceptor ra=new MemResultAcceptor();
                 AlgConstant.sortIndex=new int[1];
                AlgConstant.sortIndex[1]=sortIndex;
                AlgConstant.sortType=new String[1];
                AlgConstant.sortType[1]="Number";
		ExternalSorter sorter=new ExternalSorter();

		RecordStore store=new FileOneLineRecord(file);
		RunAcceptor mediator=new FileRunAcceptor(file+"_temp");
		ResultAcceptor ra=new FileOneLineRecord(file+"_sorted");
		sorter.sort(70000, store, mediator, ra);
              
	}
     public  void doFormatSort(String file,String outFilePre,int sortIndex) throws IOException
	{


                AlgConstant.sortIndex=new int[1];
                AlgConstant.sortIndex[1]=sortIndex;
                AlgConstant.sortType=new String[1];
                AlgConstant.sortType[1]="Number";
		ExternalSorter sorter=new ExternalSorter();

		RecordStore store=new FileOneLineRecord(file);
		RunAcceptor mediator=new FileRunAcceptor(outFilePre+"_temp");
		ResultAcceptor ra=new FileOneLineRecord(outFilePre+"_sorted");
		sorter.sort(70000, store, mediator, ra);
	}

        public  void doFormatSort(String file,String outFilePre,int sortIndex[],String[] sortType) throws IOException
	{


                AlgConstant.sortIndex=sortIndex;
               // AlgConstant.sortIndex[1]=sortIndex;
                AlgConstant.sortType=sortType;
              //  AlgConstant.sortType[1]="Number";
		ExternalSorter sorter=new ExternalSorter();

		RecordStore store=new FileOneLineRecord(file);
		RunAcceptor mediator=new FileRunAcceptor(outFilePre+"_temp");
		ResultAcceptor result=new FileOneLineRecord(outFilePre);
		sorter.sort(70000, store, mediator, result);
	}




	public static void main(String[] args) throws IOException
	{

            
		ExternalSorter sorter=new ExternalSorter();
                int[] soap2SortIndex={0,3};
                String[] soap2SortType={"String","Number"};

                sorter.doFormatSort("E:\\refGene.gff", "E:\\output.sort", soap2SortIndex,soap2SortType);

	}

	
}
