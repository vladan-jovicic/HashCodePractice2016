import ilog.concert.IloException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */

	static boolean[][] image;

	public static void main(String[] args) throws IOException, IloException {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("logo.in"));
		String tmp = in.readLine();
		String[] tmps = tmp.split(" ");
		int n = Integer.parseInt(tmps[0]), m = Integer.parseInt(tmps[1]);
		image = new boolean[n][m];
		for (int i = 0; i < n; i++) {
			tmp = in.readLine();
			for (int j = 0; j < m; j++) {
				if (tmp.charAt(j) == '#')
					image[i][j] = true;
				else
					image[i][j] = false;
			}
		}
		in.close();
		Transform t = new Transform(n, m, image);
		Vector<Operations> ops = t.calculate();
		Vector<Vector<coord> > S = t.calculateSets(ops);
		Vector<coord> U = t.getUniversalSet();
//		for(int i=0; i<S.size(); i++)
//		{
//			Vector<coord> tv = S.elementAt(i);
//			System.out.println(ops.elementAt(i).command);
//			for(int j=0; j<tv.size(); j++)
//				System.out.print(tv.elementAt(j).i + " " + tv.elementAt(j).j + " ");
//			System.out.println();
//		}
		SolveILP solver = new SolveILP(U,S);
		Vector<Integer> sol = solver.solve();
//		if(sol.size() == S.size())
//			System.out.println(sol.size());
		int cnt = 0;
		for(int i=0; i<sol.size(); i++)
			if(sol.elementAt(i) == 1)
			{
				cnt++;
				System.out.println(ops.elementAt(i).command);
			}
		System.out.println(cnt);
		boolean [][] output = new boolean[n][m];
		for(int i=0; i<sol.size();i++)
		{
			if(sol.elementAt(i) == 1)
			{
				Vector<coord> temp = S.elementAt(i);
				for(int j=0; j<temp.size(); j++)
				{
					output[temp.elementAt(j).i][temp.elementAt(j).j] = true;
				}
			}
		}
//		for(int i=0; i<n;i++)
//		{
//			for(int j=0; j<m; j++)
//			{
//				if(output[i][j])
//					System.out.print('#');
//				else
//					System.out.print('.');
//			}
//			System.out.println();
//		}
//		for(int i=0; i<sol.size(); i++)
//		{
//			System.out.println(sol.elementAt(i));
//		}
	}

}
