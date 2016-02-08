import ilog.concert.*;
import ilog.cplex.*;

import java.util.Vector;


public class SolveILP {
	IloCplex cplex;
	
	Vector<coord>U; 
	Vector<Vector<coord> >S;
	
	public SolveILP(Vector<coord>UU, Vector<Vector<coord> >SS) throws IloException
	{
		U = UU; S = SS;
		cplex = new IloCplex();
	}
	
	public Vector<Integer> solve() throws IloException
	{
		IloIntVar [] x = new IloIntVar[S.size()];
		
		for(int i=0; i<S.size(); i++)
		{
			x[i] = cplex.boolVar("x " + i);
		}
		IloLinearNumExpr objective = cplex.linearNumExpr();
		for(int i=0; i<S.size(); i++)
		{
			objective.addTerm(1, x[i]);
		}
		cplex.addMinimize(objective);
		
		//define constarints
		//IloLinearNumExpr lhs = cplex.linearNumExpr();
		for(int i=0; i<U.size(); i++)
		{
			IloLinearNumExpr lhs = cplex.linearNumExpr();
			for(int j=0; j<S.size(); j++)
			{
				if(contains(S.elementAt(j), U.elementAt(i)))
					lhs.addTerm(1, x[j]);
			}
			cplex.addGe(lhs, 1);
		}
		Vector<Integer> solution = new Vector<Integer>();
		//cplex.getValue(x[0]);
		if(cplex.solve())
			for(int i=0;i<S.size(); i++)
			{
				solution.add((int)cplex.getValue(x[i]));
			}
		else
			System.out.println("Not Solved");
		return solution;
	}
	
	private boolean contains(Vector<coord> tmp, coord pos)
	{
		for(int i=0; i<tmp.size(); i++)
		{
			if(tmp.elementAt(i).i == pos.i && tmp.elementAt(i).j == pos.j)
				return true;
		}
		return false;
	}
}
