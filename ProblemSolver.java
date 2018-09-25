public class ProblemSolver
{

	/*
	 * Se esperan argumentos en la forma:
	 * 
	 * ./ProblemSolver <-d> aso/asm <estado inicial> <parametro opcional>
	 * 
	 * Ejemplo: ./ProblemSolver aso 0 1 2 3 4 5 6 7 8
	 * 
	 * 
	 */
	public static void main(String[] args)
	{
		// Números que se ajustarán si la palanca de depuración está presente, asi como los componentes
		// de args estaran en diferentes lugares.
		int searchTypeDebug = 0;
		int eightPuzzleDebug = 1;
		boolean debug = false;

		// Imprimir el uso correcto y cerrar el programa en caso de no existir
		// parametros
		if (args.length < 1)
		{
			printUsage();
		}

		// Chequear la palanca de depuración
		if (args[0].equals("-d"))
		{
			searchTypeDebug = 1;
			eightPuzzleDebug = 2;
			debug = true;
			System.out.println("Tipo de busqueda: "
					+ args[searchTypeDebug].toLowerCase());
		}

		String searchType = args[searchTypeDebug].toLowerCase();

		if (args.length > 2) // Corremos con 8puzzle
		{
			int[] startingStateBoard = dispatchEightPuzzle(args,
					eightPuzzleDebug);

			// Usar AStarSearch.java con el numero fuera de lugar
			if (searchType.equals("aso"))
			{
				AStarSearch.search(startingStateBoard, debug, 'o');
			}
			// Usar AStarSearch.java con Distancia de Manhattan 
			else if (searchType.equals("asm"))
			{
				AStarSearch.search(startingStateBoard, debug, 'm');
			}
			// Un tipo de busqueda inválido ha sido puesto. Imprimir el uso correcto y
			// terminar el programa.
			else
			{
				printUsage();
			}
		}

		else
		// We will run with fwgc
		{
			printUsage();
		}
	}

	// Método de ayuda que permite desplegar como usar el programa
	private static void printUsage()
	{
		System.out.println("Uso: ./Main <tipo de busqueda> [Estado inicial del puzzle]");
		System.exit(-1);
	}
	// Método de ayuda para construir nuestro estado inicial pasado en argumentos
	private static int[] dispatchEightPuzzle(String[] a, int d)
	{
		int[] initState = new int[9];
		// i -> loop counter
		for (int i = d; i < a.length; i++)
		{
			initState[i - d] = Integer.parseInt(a[i]);
		}
		return initState;
	}
}
