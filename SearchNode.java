/**
 * 
 * Clase que representa un nodo de busqueda. Esto será un envoltorio para el estado, y
 * rastrear el costo para llegar a ese estado y el nodo padre del estado.
 * 
 * 
 * 
 */
public class SearchNode
{

	private State curState;  // estado actual
	private SearchNode parent; 
	private double cost; // costo para llegar a este estado
	private double hCost; // costo de heuristica
	private double fCost; // costo f(n) 

	/**
	 * Constructor para el nodo de busqueda raíz
	 * 
	 * @param s
	 *            el estado pasa en
	 */
	public SearchNode(State s)
	{
		curState = s;
		parent = null;
		cost = 0;
		hCost = 0;
		fCost = 0;
	}

	/**
	 * Constructor para todos los nodos de busqueda
	 * 
	 * @param prev
	 *            el nodo padre
	 * @param s
	 *            el estado
	 * @param c
	 *            el costo g(n) para llegar a ese nodo
	 * @param h
	 *            el costo h(n) para llegar a ese nodo
	 */
	public SearchNode(SearchNode prev, State s, double c, double h)
	{
		parent = prev;
		curState = s;
		cost = c;
		hCost = h;
		fCost = cost + hCost; // aqui es donde se aplica la funcion de la heuristica
	}

	/**
	 * @return el curState(estado actual)
	 */
	public State getCurState()
	{
		return curState;
	}

	/**
	 * @return el padre
	 */
	public SearchNode getParent()
	{
		return parent;
	}

	/**
	 * @return el costo
	 */
	public double getCost()
	{
		return cost;
	}

	/**
	 * 
	 * @return el costo de heuristica
	 */
	public double getHCost()
	{
		return hCost;
	}

	/**
	 * 
	 * @return el costo f(n) para A*
	 */
	public double getFCost()
	{
		return fCost;
	}
}
