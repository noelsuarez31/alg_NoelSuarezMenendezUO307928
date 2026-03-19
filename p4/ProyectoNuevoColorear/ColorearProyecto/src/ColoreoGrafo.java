

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColoreoGrafo {

	static String[] colores = {"red", "blue", "green", "yellow", "orange", "purple", "cyan", "magenta", "lime"};

	public static Map<String, String> realizarVoraz (Map<String, List<String>> grafo)
	{
		// la entrada es por ejemplo un 1 y una lista, y los numeros que hay en esa lista son los vecinos
		Map<String, String> solucion = new HashMap<>();
		// recorrer nodos
		for (String nodo : grafo.keySet())
		{
			List<String> coloresVecinos = new ArrayList<>();
			// recorrer vecinos
			for(String vecino: grafo.get(nodo))
			{ 
				if(solucion.containsKey(vecino))
				{
					// aquí cojo el color de ese vecino
					coloresVecinos.add(solucion.get(vecino));
				}
			}
			for (String color : colores) 
			{
                if (!coloresVecinos.contains(color)) 
				{
                    solucion.put(nodo, color);
                    break;
                }
            }
		}

		return solucion;

	}
}
