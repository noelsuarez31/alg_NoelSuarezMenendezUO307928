package algstudent.s4;

import java.util.List;
import java.util.Map;

public class ColoreoGrafo {

	String[] colores = {"red", "blue", "green", "yellow", "orange", "purple", "cyan", "magenta", "lime"};

	public static Map<String, String> realizarVoraz (Map<String, List<String>> grafo)
	{
		// la entrada es por ejemplo un 1 y una lista, y los numeros que hay en esa lista son los vecinos
		Map<String, String> solucion = new Map<String, String>();
		// recorrer nodos
		for (String nodo : grafo.keySet())
		{
			int counter  = 0;
			for(String vecino: grafo.get(nodo))
			{ 
				if(solucion.containsKey(vecino))
				{
					// si ya tiene un color asignado en el map de la solucion ya no se puede usar y paso al siguiente
					counter++;
				}
			} 
		}
		para cadauno de los nodos recorrer vecinos
		si ese vecino ya tiene un color asignado en el map de la solucion ya no se puede usar y paso al siguiente

	}
}