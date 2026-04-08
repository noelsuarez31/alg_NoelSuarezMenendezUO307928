package Logica;

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

    public void run()
    {
        algorithm();
        printData();
        int max = getMaximumNumberOfVehicles();
        System.out.println("\nHan llegado un total de " + max + " vehículos (" + max + " viajarán).");
        printSolutionTable();
        printPossibleAssignation();
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

    public int getMaximumNumberOfVehicles() {
        for (int i = vehicles.size(); i >= 0; i--) {
            for (int l = 0; l <= L; l++) {
                if (DP[i][l]) {
                    return i;
                }
            }
        }
        return 0;
    }

    public void printSolutionTable() {
        System.out.println("\nTabla con los cálculos realizados:");
    
        System.out.print("V/L ");
        for (int l = 0; l <= L; l++) {
            System.out.print(l + " ");
        }
        System.out.println();

        for (int i = 0; i <= vehicles.size(); i++) {
            System.out.print(i + "   ");
            for (int l = 0; l <= L; l++) {
                System.out.print((DP[i][l] ? "T" : "F") + " ");
            }
            System.out.println();
        }
    }

    public void printPossibleAssignation() {
        int n = getMaximumNumberOfVehicles();

        // El criterio para dar la posible asignación es la que tenga menos babor ocupado
        int l = -1;
        for (int j = 0; j <= L; j++) {
            if (DP[n][j]) {
                l = j;
                break; // el primer válido es el de menor babor
            }
        }

        if (l == -1) {
            System.out.println("No hay solución.");
            return;
        }

        String[] asignacion = new String[n];

        for (int i = n; i > 0; i--) {
            int v = vehicles.get(i - 1);

            if (DP[i - 1][l] && (S[i] - l) <= L) {
                asignacion[i - 1] = "estribor";
            } else {
                asignacion[i - 1] = "babor";
                l = l - v;
            }
        }

        int babor = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("Vehículo " + (i + 1) + " (" + vehicles.get(i) + ") -> " + asignacion[i]);
            if (asignacion[i].equals("babor")) {
                babor += vehicles.get(i);
            }
        }

        int estribor = S[n] - babor;

        System.out.println("\nOcupación final:");
        System.out.println("Babor: " + babor + " / Estribor: " + estribor);
    }
}