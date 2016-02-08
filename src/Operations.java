
public class Operations {
	public int type; // 1 = PAINT_SQUARE; 2=PAINT_LINE (horizontal); 3=PAINT_LINE (vertical)
	public int i0, j0, i1, j1, sz;
	String command;
	public Operations(int ii0, int jj0, int sz1)
	{
		//must be square operation
		type = 1;
		i0 = ii0; j0 = jj0; sz = sz1;
		command = "PAINT_SQUARE " + i0 + " " + j0 + " " + sz;
	}
	
	public Operations(int ttype, int ii0, int jj0, int ii1, int jj1)
	{
		type = ttype;
		i0 = ii0; i1 = ii1; j0 = jj0; j1 = jj1;
		command = "PAINT_LINE " + i0 + " " + j0 + " " + i1 + " " + j1; 
	}
}
