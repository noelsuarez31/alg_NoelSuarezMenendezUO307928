import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class Ferry
{
    private int L; // Longitud de los carriles del barco
    private List<Integer> vehicles; // Lista de vehículos
    private boolean[][] DP; // Matriz con las posibles soluciones
    private int[] S; // Suma acumulada de las longitudes de los vehículos
    // Missing parameter

    public Ferry (int L, List<Integer> vehicles)
    {
        this.L = L;
        this.vehicles = vehicles;

        this.DP = new boolean[vehicles.size()+1][L+1];

        this.S = new int[vehicles.size()+1];
        createS();
    }

    private void createS()
    {
        S[0] = 0;
        for(int i = 1; i < S.length; i++)
        {
            S[i] = S[i-1] + this.vehicles.get(i-1);
        }
    }

    public void Run()
    {
        
        //loadData();
        algorithm();
        printData();

    }

    private void algorithm()
    {
        initDP();

        for(int i = 1; i < vehicles.size()+1; i++)
        {
            for(int l = L; l >= 0; l--)
            {
                if(!DP[i-1][l])
                {
                    continue;
                }

                int v = vehicles.get(i-1);

                // Meter el coche en babor
                if(l+v <= L)
                {
                    DP[i][l+v] = true;
                }

                // Meter el coche en estribor
                if(S[i]-l <= L)
                {
                    DP[i][l] = true;
                }
            }
            
        }
    }

    private void initDP()
    {
        // Caso BASE
        DP[0][0] = true;
    }

    public void printData()
    {
        System.out.printf("Longitud de los carriles: %d\n", L);
        System.out.println("Longitud de los vehículos:\n");
        for(int i = 0; i<vehicles.size(); i++)
        {
            System.out.printf("\tVehículo %d: %d metros.", i+1, vehicles.get(i));
        }
    }
}