# 8-Puzzle-A-star
===============
=== Leeme ===
===============



A* Search ('asm' o 'aso' dependiendo en la heuristica)

A* Search puede usar una de dos heuristicas:

Number Out of Place ('aso')
Manhattan Distance ('asm')
	

Ejecucion:
		
java -Xmx1024m ProblemSolver -d <aso/asm> [tablero inicial]
Ejemplo:
java -Xmx1024m ProblemSolver asm 5 6 8 4 0 1 2 3 7