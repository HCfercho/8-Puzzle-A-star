import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * EightPuzzleState define un estado para el problema 8-Puzzle. El tablero esta siempre
 * representado por una matriz de una sola dimensión, intentamos proporcionar la ilusión
 * que la representación del estado es bidimensional y esto funciona muy bien. In
 * En términos de las fichas reales, '0' representa el "hueco" en el tablero, y 0 es
 * tratado especial al generar sucesores. No tratamos a '0' como una pieza
 * ensimisma, si hay un "hole" en el tablero (como lo referimos aquí)
 * 
 * 
 * 
 */
public class EightPuzzleState implements State
{

	private final int PUZZLE_SIZE = 9;
	private int outOfPlace = 0;

	private int manDist = 0;

	private final int[] GOAL = new int[]
	{ 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	private int[] curBoard;

	/**
	 * Constructor para EightPuzzleState
	 * 
	 * @param board
	 *            - la representación del tablero para el nuevo estado a ser cosntruido.
	 */
	public EightPuzzleState(int[] board)
	{
		curBoard = board;
		setOutOfPlace();
		setManDist();
	}

	/**
	 * Cuanto cuesta llegar a este estado.
	 */
	@Override
	public double findCost()
	{
		return 1;
	}

	/*
	 * Setea 'piezas fuera de lugar' distantes para el tablero actual
	 */
	private void setOutOfPlace()
	{
		for (int i = 0; i < curBoard.length; i++)
		{
			if (curBoard[i] != GOAL[i])
			{
				outOfPlace++;
			}
		}
	}

	/*
	 * Setea Distancia de Manhattan para el tablero actual
	 */
	private void setManDist()
	{
		// buscar linealmente la matriz independiente de las formas anidadas a continuación
		int index = -1;

		// simplemente realiza un seguimiento de dónde estamos en el tablero 
		// (relativamente, no puede usar 0 
		// por lo que estos valores deben ser desplazados al lugar correcto)
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				index++;

				// sub 1 para el val para obtener el indice de donde ese valor
				// debería estar
				int val = (curBoard[index] - 1);

				/*
				 * Si no estamos mirando el agujero. El agujero estara en la 
				 * posicion -1 ya que restamos 1 antes para convertir val en el índice
				 */
				if (val != -1)
				{
					// desplazamiento horizontal, mod el valor del mosaico por la dimensión 
					// horizontal
					int horiz = val % 3;
					// desplazamiento vertical, divide el valor del mosaico por la dimensión 
					// vertical
					int vert = val / 3;

					manDist += Math.abs(vert - (y)) + Math.abs(horiz - (x));
				}
				// Si estamos mirando el agujero, sáltelo
			}
		}
	}

	/*
	 * Intenta localizar el punto "0" en el tablero actual
	 * 
	 * @return el índice del "agujero" (o punto 0)
	 */
	private int getHole()
	{
		// Si devuelve -1, ha ocurrido un error. El "agujero" siempre debe existir 
		// en el tablero y siempre debe encontrarse en el siguiente lazo
		int holeIndex = -1;

		for (int i = 0; i < PUZZLE_SIZE; i++)
		{
			if (curBoard[i] == 0)
				holeIndex = i;
		}
		return holeIndex;
	}

	/**
	 * Getter para el atributo outOfPlace 
	 * 
	 * @return el valor h(n) outOfPlace
	 */
	public int getOutOfPlace()
	{
		return outOfPlace;
	}

	/**
	 * Getter para el valor Distancia de Manhattan Distance 
	 * 
	 * @return El valor de h(n) de la Distancia de Manhattan
	 */
	public int getManDist()
	{
		return manDist;
	}

	/*
	 * Hace una copia de la matriz que se le pasó
	 */
	private int[] copyBoard(int[] state)
	{
		int[] ret = new int[PUZZLE_SIZE];
		for (int i = 0; i < PUZZLE_SIZE; i++)
		{
			ret[i] = state[i];
		}
		return ret;
	}

	/**
	 * Se piensa en terminos de NO MAS DE 4 operaciones. Puede deslizar azulejos
	 * de 4 direcciones si el agujero está en el centro, dos direcciones si el agujero está en una
	 * esquina y tres direcciones si el agujero está en el medio de una fila
	 * 
	 * @return un ArrayList contiene todos los hijos para el estado.
	 */
	@Override
	public ArrayList<State> genSuccessors()
	{
		ArrayList<State> successors = new ArrayList<State>();
		int hole = getHole();

		// intenta generar un estado deslizando un azulejo hacia la izquierda en el agujero
		// si PODEMOS deslizarnos en el agujero
		if (hole != 0 && hole != 3 && hole != 6)
		{
			/*
			 * podemos deslizarnos hacia la izquierda en el agujero, para generar un nuevo estado para
			 * esta condición y tirarla a sucesores
			 */
			swapAndStore(hole - 1, hole, successors);
		}

		// Intenta generar un estado deslizando un azulejo por la parte superior en el agujero
		if (hole != 6 && hole != 7 && hole != 8)
		{
			swapAndStore(hole + 3, hole, successors);
		}

		// intenta generar un estado deslizando una ficha hacia abajo en el agujero
		if (hole != 0 && hole != 1 && hole != 2)
		{
			swapAndStore(hole - 3, hole, successors);
		}
		// intenta generar un estado deslizando un azulejo a la derecha en el agujero
		if (hole != 2 && hole != 5 && hole != 8)
		{
			swapAndStore(hole + 1, hole, successors);
		}

		return successors;
	}

	/*
	 * Cambia los datos en los índices d1 y d2, en una copia de la placa actual
	 * crea un nuevo estado basado en esta nueva placa y empuja en s.
	 */
	private void swapAndStore(int d1, int d2, ArrayList<State> s)
	{
		int[] cpy = copyBoard(curBoard);
		int temp = cpy[d1];
		cpy[d1] = curBoard[d2];
		cpy[d2] = temp;
		s.add((new EightPuzzleState(cpy)));
	}

	/**
	 * Verifique si el estado actual es el estado objetivo.
	 * 
	 * @return - verdadero o falso, dependiendo de si el estado actual coincide
	 *         con la meta
	 */
	@Override
	public boolean isGoal()
	{
		if (Arrays.equals(curBoard, GOAL))
		{
			return true;
		}
		return false;
	}

	/**
	 * Método para imprimir el estado actual. Imprime el tablero del rompecabezas.
	 */
	@Override
	public void printState()
	{
		System.out.println(curBoard[0] + " | " + curBoard[1] + " | "
				+ curBoard[2]);
		System.out.println("---------");
		System.out.println(curBoard[3] + " | " + curBoard[4] + " | "
				+ curBoard[5]);
		System.out.println("---------");
		System.out.println(curBoard[6] + " | " + curBoard[7] + " | "
				+ curBoard[8]);

	}

	/**
	 * La sobrecarga es igual al método para comparar dos estados.
	 * 
	 * @return verdadero o falso, dependiendo en donde sea el estado igual.
	 */
	@Override
	public boolean equals(State s)
	{
		if (Arrays.equals(curBoard, ((EightPuzzleState) s).getCurBoard()))
		{
			return true;
		}
		else
			return false;

	}

	/**
	 * Getter para devolver la matriz de placa actual
	 * 
	 * @return el curState(estado actual)
	 */
	public int[] getCurBoard()
	{
		return curBoard;
	}

}
