import java.util.Vector;

public class Transform {

	private int N, M;
	private boolean[][] image;
	public Vector<Operations> ops;

	public Transform(int n, int m, boolean[][] Image) {
		N = n;
		M = m;
		image = Image;
		ops = new Vector<Operations>();
	}

	public Vector<Operations> calculate() {
		// calculate rectangles
		for (int i0 = 0; i0 < N; i0++) {
			for (int j0 = 0; j0 < M; j0++) {
				if(image[i0][j0]) //if it is '#'
				{
					// uradi pametno uradi pametno uradi pametno
					int sz = 1;
					boolean expand = true;
					while(expand) {
						for(int i=i0-sz, j=j0-sz; j<j0+sz+1; j++)
							if(!checkCoord(i,j, true))
								expand = false;
						for(int i=i0+sz, j=j0-sz; j<j0+sz+1; j++)
							if(!checkCoord(i,j,true))
								expand = false;
						for(int i=i0-sz, j=j0-sz; i<i0+sz+1; i++)
							if(!checkCoord(i,j,true))
								expand = false;
						for(int i=i0-sz, j=j0+sz; i<i0+sz+1; i++)
							if(!checkCoord(i,j,true))
								expand = false;
						
						if(expand)
							sz++;
					}
					//dodaj operaciju SQUARE i0, j0, sz
//					Operations tmp = new Operations(i0, j0, sz);
//					System.out.println(tmp.command);
					ops.add(new Operations(i0, j0, sz));
					// horizontal line
					sz = 1;
					expand = true;
					while(expand)
					{
						if(!checkCoord(i0, j0+sz, true))
							expand = false;
						else
							sz++;
					}
					//add operation PAINT_LINE i0, j0-sz+1, i0, j0+sz-1
//					tmp = new Operations(2, i0, j0-sz+1, i0, j0+sz-1);
//					System.out.println(tmp.command);
					ops.add(new Operations(2, i0, j0, i0, j0+sz-1));
					//vertical line
					sz = 1;
					expand = true;
					while(expand)
					{
						if(!checkCoord(i0+sz, j0, true))
							expand = false;
						else
							sz++;
					}
//					tmp = new Operations(3, i0-sz+1, j0, i0+sz-1, j0);
//					System.out.println(tmp.command);
					ops.add(new Operations(3, i0, j0, i0+sz-1, j0));
				}
			}
		}
		return ops;
	}
	
	public Vector<Vector<coord> > calculateSets(Vector<Operations> operation)
	{
		Vector<Vector<coord> >S = new Vector<Vector<coord> >();
		for(int i=0; i<ops.size(); i++)
		{
			Vector<coord> coords = new Vector<coord>();
			if(operation.elementAt(i).type == 1)
			{
				int i0 = operation.elementAt(i).i0;
				int j0 = operation.elementAt(i).j0;
				int sz = operation.elementAt(i).sz;
				for(int y=i0-sz+1; y<i0+sz;y++)
					for(int x=j0-sz+1; x<j0+sz;x++)
						coords.add(new coord(y,x));
			}
			else if(operation.elementAt(i).type == 2)
			{
				int i0 = operation.elementAt(i).i0;
				int j0 = operation.elementAt(i).j0;
				int j1 = operation.elementAt(i).j1;
				for(int x = j0; x<=j1;x++)
					coords.add(new coord(i0,x));
			}
			else if(operation.elementAt(i).type == 3)
			{
				int i0 = operation.elementAt(i).i0;
				int j0 = operation.elementAt(i).j0;
				int i1 = operation.elementAt(i).i1;
				for(int y = i0; y <= i1; y++)
					coords.add(new coord(y,j0));
			}
			if(coords.size() > 0)
				S.add(coords);
		}
		return S;
	}
	public Vector<coord> getUniversalSet()
	{
		Vector<coord> U = new Vector<coord>();
		for(int i=0; i<N; i++)
			for(int j=0; j<M; j++)
			{
				if(image[i][j])
					U.add(new coord(i,j));
			}
		return U;
	}

	private boolean checkCoord(int x, int y, boolean hashtag) {
		if (x >= 0 && y >= 0 && x < N && y < M)
			if (hashtag && image[x][y])
				return true;
			else
				return false;
		else
			return false;
	}

}
