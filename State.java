import java.util.ArrayList;

/**
 * 
 * Interfaz de estado de los cuales los estados problemáticos heredan. Defien un metodo para comprobar
 * si el estado actual es la meta, genera hijos, y encuentra el costo para
 * llegar al estado actual.
 * 
 * 
 * 
 */
public interface State
{
	// determina si el estado inicial es la meta.
	boolean isGoal();

	// genera hijos para el estado actual.
	ArrayList<State> genSuccessors();

	// determina el costo para el estado inicial de este estado.
	double findCost();

	// imprime el estado actual.
	public void printState();

	// compara los datos de estado reales
	public boolean equals(State s);
}
