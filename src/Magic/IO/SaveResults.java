package Magic.IO;


public class SaveResults
{	
	public static void saveRow(DataRep dtrunk,int index,String filename)
	{
        /*
		int count=0;
		int total=0;
		DecimalFormat format1=new DecimalFormat("#0.00");
		File outfile;
		FileOutputStream fout;
		BufferedOutputStream bout;
		DataOutputStream dout;
		
		try
		{			
			outfile=new File(filename);
			fout=new FileOutputStream(outfile);
			bout=new BufferedOutputStream(fout);
			dout=new DataOutputStream(bout);
	  	  	
			if(dtrunk.tracks[index].format.equals(KeyWords.SNPS)
			{
				HashSet<Integer> refer_site_set=new HashSet<Integer>();
				for(int i=0;i<dtrunk.tracks[index].reads.length;i++)
				{
					Read read=dtrunk.tracks[index].reads[i];
					for(int j=0;j<read.pieces.length;j++)
					{
						Piece piece=read.pieces[j];
						if(!piece.save) continue;
						refer_site_set.add(piece.geneticPiece.start);
					}
				}
				
				File infile;
				FileInputStream fin;
				BufferedInputStream bin;
				DataInputStream din;
				
				infile = new File(dtrunk.tracks[index].filename);
				fin = new FileInputStream(infile);
				bin = new BufferedInputStream(fin);
				din = new DataInputStream(bin);
				
				String line;
				StringTokenizer nizer;
				int refer_site=0;
				
				while(((line=din.readLine())!=null))
			  	{
					dout.writeBytes(line+"\n");
			  		if(line.length()==0) continue;
			  		if(line.startsWith("====")) break;
			  	}
			  	
			  	while(((line=din.readLine())!=null))
			  	{
			  		if(line.length()==0) continue;
			  		nizer=new StringTokenizer(line," \t|");
			  		if(nizer.countTokens()<4) continue;
			  					  		
			  		refer_site=Integer.valueOf(nizer.nextToken()).intValue();
			  		if(refer_site_set.contains(refer_site)) dout.writeBytes(line+"\n");
			  	}
			  	
				fin.close();
			  	bin.close();
			  	din.close();
			}
			else
			{
				HashMap<String,Integer> id2position=new HashMap<String,Integer>();
				for(int i=0;i<dtrunk.tracks[index].reads.length;i++)
				{
					Read read=dtrunk.tracks[index].reads[i];
					id2position.put(read.id,i);
				}
				
				File infile;
				FileInputStream fin;
				BufferedInputStream bin;
				DataInputStream din;
				
				infile = new File(dtrunk.tracks[index].filename);
				fin = new FileInputStream(infile);
				bin = new BufferedInputStream(fin);
				din = new DataInputStream(bin);
				
				String id=null;
				String line,token;
				StringTokenizer nizer;
				int current=0;
				String content="";
				boolean[] p_save=null;
				while(((line=din.readLine())!=null))
			  	{					
			  		if(line.length()==0)
			  		{
			  			dout.writeBytes(line+"\n");
			  			continue;
			  		}
			  		if(line.startsWith(">"))
			  		{
			  			if(id!=null && content.length()>0) dout.writeBytes(content);
			  			
			  			nizer=new StringTokenizer(line," \t");
			  			token=nizer.nextToken();
			  			if(token.length()>1) id=token.substring(1);
			  			else id="NUM "+(current+1);
			  			current++;
			  						  			
			  			Integer position=id2position.get(id);
			  			if(position==null)
			  			{
			  				//System.out.println(id+" not found");
			  				continue;
			  			}
			  			Read read=dtrunk.tracks[index].reads[position];
			  			
			  			content="Save: ";
			  			for(int i=0;i<read.pieces.length;i++)
			  			{
			  				if(read.pieces[i].save) content+="T";
		  					else content+="F";
			  			}
			  			content+="\n";
			  		}
			  		dout.writeBytes(line+"\n");
			  	}
				if(id!=null && content.length()>0) dout.writeBytes(content);
				
				fin.close();
			  	bin.close();
			  	din.close();
			}
		  	dout.close();
			bout.close();
			fout.close();			
	  	}
	  	catch (IOException e){//System.out.println("Exception in readContent()");}	  
	  	//System.out.println("Done. "+total+" reads id in content file");
	  	//System.out.println(count+" items'id has been written.");
        */
	}
	
	public static void saveMutationsInTable(DataRep dtrunk,int index,boolean is_snp,String filename)
	{
        /*
		File outfile;
		FileOutputStream fout;
		BufferedOutputStream bout;
		DataOutputStream dout;
		
		try
		{			
			outfile=new File(filename);
			fout=new FileOutputStream(outfile);
			bout=new BufferedOutputStream(fout);
			dout=new DataOutputStream(bout);
	  	  	
			
			String content="";
			for(int i=0;i<dtrunk.tracks[index].reads.length;i++)
			{
				Read read=dtrunk.tracks[index].reads[i];
				for(int j=0;j<read.pieces.length;j++)
				{
					Piece piece=read.pieces[j];
					if(!piece.save) continue;
						
					if(is_snp) dout.writeBytes(piece.content+"\n");
					else
					{
						if(piece.mutations==null) continue;
						for(int k=0;k<piece.mutations.length;k++)
						{
							content=piece.mutations[k].refer_site+" "+piece.mutations[k].refer_char+piece.mutations[k].query_char
									+" "+piece.mutations[k].query_site+" ";
							
							if(piece.mutations[k].refer_strand) content+="+";
							else content+="-";
							
							if(piece.mutations[k].query_strand) content+="+";
							else content+="-";
							
							content+="\n";
							dout.writeBytes(content);
						}						
					}					
				}
			}
			
		  	dout.close();
			bout.close();
			fout.close();			
	  	}
	  	catch (IOException e){//System.out.println("Exception in saveMutationsInTable()");}
        */
	}
	
	public static void saveCoordiantesInTable(DataRep dtrunk,int index,String filename)
	{
        /*
		File outfile;
		FileOutputStream fout;
		BufferedOutputStream bout;
		DataOutputStream dout;
		
		try
		{			
			outfile=new File(filename);
			fout=new FileOutputStream(outfile);
			bout=new BufferedOutputStream(fout);
			dout=new DataOutputStream(bout);
	  	  	
			
			String content="";
			for(int i=0;i<dtrunk.tracks[index].reads.length;i++)
			{
				Read read=dtrunk.tracks[index].reads[i];				
				for(int j=0;j<read.pieces.length;j++)
				{
					Piece piece=read.pieces[j];
					if(!piece.save) continue;
					
					dout.writeBytes(String.format("%-10d %d\n",piece.geneticPiece.start,piece.finish));										
				}
			}
			
		  	dout.close();
			bout.close();
			fout.close();			
	  	}
	  	catch (IOException e){//System.out.println("Exception in saveMutationsInTable()");}
        */
	}
	
	public static void saveReadsIDInTable(DataRep dtrunk,int index,String filename)
	{
        /*
		File outfile;
		FileOutputStream fout;
		BufferedOutputStream bout;
		DataOutputStream dout;
		
		try
		{			
			outfile=new File(filename);
			fout=new FileOutputStream(outfile);
			bout=new BufferedOutputStream(fout);
			dout=new DataOutputStream(bout);
	  	  	
			
			String content="";
			for(int i=0;i<dtrunk.tracks[index].reads.length;i++)
			{
				Read read=dtrunk.tracks[index].reads[i];
				
				boolean save=false;
				for(int j=0;j<read.pieces.length;j++)
				{
					Piece piece=read.pieces[j];
					if(piece.save)
					{
						save=true;
						break;
					}										
				}
				if(save) dout.writeBytes(String.format(">%s\n",read.id));
			}
			
		  	dout.close();
			bout.close();
			fout.close();			
	  	}
	  	catch (IOException e){//System.out.println("Exception in saveMutationsInTable()");}
        */
	}
	
	public static void saveNewGenomeByAcceptSNPs(DataRep dtrunk,int index,String filename)
	{
        /*
		Vector key=new Vector();
		Vector snps_vec=new Vector();
		String token;
		StringTokenizer nizer;
		
		int insertion=0;
		int deletion=0;
		int substitution=0;
		for(int i=0;i<dtrunk.tracks[index].reads.length;i++)
		{
			Read read=dtrunk.tracks[index].reads[i];				
			for(int j=0;j<read.pieces.length;j++)
			{
				Piece piece=read.pieces[j];
				String content=piece.content;
				nizer=new StringTokenizer(content," \t");
				Mutation snp=new Mutation();
				snp.refer_site=Integer.valueOf(nizer.nextToken()).intValue();
				token=nizer.nextToken();
				snp.refer_char=token.charAt(0);
				snp.query_char=token.charAt(1);
				snp.query_site=Integer.valueOf(nizer.nextToken()).intValue();
				token=nizer.nextToken();
				if(token.charAt(0)=='+') snp.refer_strand=true;
				else snp.refer_strand=false;
				if(token.charAt(1)=='+') snp.query_strand=true;
				else snp.query_strand=false;
				
				key.add(snp.refer_site);
				snps_vec.add(snp);
				
				if(snp.refer_char=='.')	insertion++;			
				else if(snp.query_char=='.') deletion++;
				else substitution++;
			}
		}
		////System.out.println(insertion+" "+deletion);
		char[] genome=new char[dtrunk.genome.length+insertion-deletion];
		SeeEggMethods.quickSort(key,snps_vec,0,snps_vec.size()-1,true);
		int start=0;
		int end=0;
		int next_p=0;
		int current=0;
		for(int i=0;i<snps_vec.size();i++)
		{
			Mutation snp=(Mutation)snps_vec.elementAt(i);			
			end=snp.refer_site-1;
			////System.out.println(">"+snp.refer_site+" "+snp.refer_char+" "+snp.query_char+" "+start+" "+end);
			for(int j=start;j<=end;j++)
			{
				genome[current]=dtrunk.genome[j];
				////System.out.print(genome[current]);
				current++;
			}
			
			if(snp.refer_char=='.')
			{				
				genome[current]=snp.query_char;
				////System.out.print(genome[current]);
				current++;
			}
			else if(snp.query_char=='.')
			{
				current--;
			}
			else
			{
				if(genome[current-1]!=snp.refer_char) //System.out.println("error:"+snp.refer_site);
				genome[current-1]=snp.query_char;
				////System.out.print(genome[current-1]);
			}
			start=end+1;
			////System.out.println();
		}
		
		if(end<dtrunk.genome.length-1)
		{
			start=end+1;
			end=dtrunk.genome.length-1;
			for(int j=start;j<=end;j++)
			{
				genome[current]=dtrunk.genome[j];
				current++;
			}
		}
		
		//for(int i=0;i<genome.length;i++) //System.out.print(genome[i]);
		File outfile;
		FileOutputStream fout;
		BufferedOutputStream bout;
		DataOutputStream dout;

		try
		{
			outfile=new File(filename);
			fout=new FileOutputStream(outfile);
			bout=new BufferedOutputStream(fout);
			dout=new DataOutputStream(bout);
			
			Date today=new Date();
			String title=">[New "+today.toLocaleString()+"] [From "+dtrunk.sequence_name+"]\n";
			dout.writeBytes(title);
			for(int i=0;i<genome.length;i++) dout.writeByte(genome[i]);
					
			dout.close();
			bout.close();
			fout.close();
		}
		catch(IOException e) {//System.out.println("Exception when saving "+filename);}
         */
	}
	
	public static void saveReads(DataRep dtrunk,String filename)
	{
        /*
		//System.out.println("save reads...");
		File outfile;
		FileOutputStream fout;
		BufferedOutputStream bout;
		DataOutputStream dout;

		try
		{
			outfile=new File(filename);
			fout=new FileOutputStream(outfile);
			bout=new BufferedOutputStream(fout);
			dout=new DataOutputStream(bout);
			
			String s;
			for(int j=0;j<dtrunk.tracks[0].reads.length;j++)
			{	
				s=dtrunk.tracks[0].reads[j].content;
				s=">"+s.substring(0,s.length()-1);
				for(int k=0;k<dtrunk.tracks[0].reads[j].pieces.length;k++)
				{
					if(dtrunk.tracks[0].reads[j].pieces[k].save==false) continue;
					s+=" ";
					if(dtrunk.tracks[0].reads[j].pieces[k].rep_sign=='c') s+="c";
					s+=dtrunk.tracks[0].reads[j].pieces[k].start+".."+dtrunk.tracks[0].reads[j].pieces[k].finish;
				}
				s+="\n";
				dout.writeBytes(s);
				////System.out.println(s);
				//dout.writeChars(dtrunk.dsheet[0].reads[j].content);
			}
			
			//dout.writeChars("hello world");			
			dout.close();
			bout.close();
			fout.close();
		}
		catch(IOException e) {//System.out.println("Exception when saving "+filename);}
         */
	}
}
