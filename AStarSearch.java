import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Define una busqueda A* para ser realizado en un rompecabezas calificado.
 * 
 * 
 * 
 */
public class AStarSearch
{
	/**
	 * Función de inicialización para 8puzzle Búsqueda A* 
	 * 
	 * @param board
	 *            - El estado inicial, representado como un arreglo lineal de longitud
	 *            9 formando 3 meta-filas.
	 */
	public static void search(int[] board, boolean d, char heuristic)
	{
		SearchNode root = new SearchNode(new EightPuzzleState(board));
		Queue<SearchNode> q = new LinkedList<SearchNode>();
		q.add(root);

		int searchCount = 1; // contador de numero de iteraciones

		while (!q.isEmpty()) // mientras que la cola no está vacía
		{
			SearchNode tempNode = (SearchNode) q.poll();

			// si el tempNode no es el estado meta
			if (!tempNode.getCurState().isGoal())
			{
				// generar sucesores inmediatos de tempNode
				ArrayList<State> tempSuccessors = tempNode.getCurState()
						.genSuccessors();
				ArrayList<SearchNode> nodeSuccessors = new ArrayList<SearchNode>();

				/*
				 * Pasa por los sucesores, envuélvelos en un SearchNode, revisa
				 * si ya han sido evaluados, y si no, agréguelos a
				 * la cola
				 */
				for (int i = 0; i < tempSuccessors.size(); i++)
				{
					SearchNode checkedNode;
					// hacer el nodo
					if (heuristic == 'o')
					{
						/*
						 * Crear un SearchNode, con tempNode como padre,
						 * el costo de tempNode + el nuevo costo (1) para este estado,
						 * y el valor Out of Place h (n)
						 */
						checkedNode = new SearchNode(tempNode,
								tempSuccessors.get(i), tempNode.getCost()
										+ tempSuccessors.get(i).findCost(),
								((EightPuzzleState) tempSuccessors.get(i))
										.getOutOfPlace());
					}
					else
					{
						// Ver el comentario anterior
						checkedNode = new SearchNode(tempNode,
								tempSuccessors.get(i), tempNode.getCost()
										+ tempSuccessors.get(i).findCost(),
								((EightPuzzleState) tempSuccessors.get(i))
										.getManDist());
					}

					// Verifique las repeticiones antes de agregar el nuevo nodo
					if (!checkRepeats(checkedNode))
					{
						nodeSuccessors.add(checkedNode);
					}
				}

				// Verifique si nodeSuccessors está vacío. Si es así, continúa
				// el bucle desde la parte superior
				if (nodeSuccessors.size() == 0)
					continue;

				SearchNode lowestNode = nodeSuccessors.get(0);

				/*
				 * Este bucle encuentra la f (n) más baja en un nodo, y luego establece ese
				 * nodo como el más bajo.
				 */
				for (int i = 0; i < nodeSuccessors.size(); i++)
				{
					if (lowestNode.getFCost() > nodeSuccessors.get(i)
							.getFCost())
					{
						lowestNode = nodeSuccessors.get(i);
					}
				}

				int lowestValue = (int) lowestNode.getFCost();

				// Agrega cualquier nodo que tenga el mismo valor más bajo.
				for (int i = 0; i < nodeSuccessors.size(); i++)
				{
					if (nodeSuccessors.get(i).getFCost() == lowestValue)
					{
						q.add(nodeSuccessors.get(i));
					}
				}

				searchCount++;
			}
			else
			// El estado de la meta ha sido encontrado. Imprima el camino que tomó para llegar a
			// el.
			{
				// Use una pila para rastrear la ruta desde el estado inicial hasta el
				// estado objetivo
				Stack<SearchNode> solutionPath = new Stack<SearchNode>();
				solutionPath.push(tempNode);
				tempNode = tempNode.getParent();

				while (tempNode.getParent() != null)
				{
					solutionPath.push(tempNode);
					tempNode = tempNode.getParent();
				}
				solutionPath.push(tempNode);

				// El tamaño de la pila antes de recorrerla y vaciarla.
				int loopSize = solutionPath.size();

				for (int i = 0; i < loopSize; i++)
				{
					tempNode = solutionPath.pop();
					tempNode.getCurState().printState();
					System.out.println();
					System.out.println();
				}
				System.out.println("El costo fue: " + tempNode.getCost());
				if (d)
				{
					System.out.println("El numero de nodos examinados: "
							+ searchCount);
				}

				System.exit(0);
			}
		}

		// Esto no deberia pasar con nuestos rompecabezas actuales.
		System.out.println("Error! No se encontró solución!");

	}

	/*
	 * Método de ayuda para verificar si ya se ha evaluado un SearchNode.
	 * Devuelve verdadero si tiene, falso si no lo tiene.
	 */
	private static boolean checkRepeats(SearchNode n)
	{
		boolean retValue = false;
		SearchNode checkNode = n;

		// Mientras que el padre de n no es nulo, verifique si es igual al nodo
		// que estamos buscando.
		while (n.getParent() != null && !retValue)
		{
			if (n.getParent().getCurState().equals(checkNode.getCurState()))
			{
				retValue = true;
			}
			n = n.getParent();
		}

		return retValue;
	}

}
