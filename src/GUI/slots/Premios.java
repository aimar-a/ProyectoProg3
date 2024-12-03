package GUI.slots;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Premios {
    private List<List<Integer>> combinaciones = new ArrayList<>();
    private List<Integer> premios = new ArrayList<>();
    private List<String> imagenes = new ArrayList<>();

    public Premios() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/GUI/slots/ListaPremios.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int numImg1 = Integer.parseInt(values[0]);
                int numImg2 = Integer.parseInt(values[1]);
                int numImg3 = Integer.parseInt(values[2]);
                int premio = Integer.parseInt(values[3]);
                String imagen = values[4];

                List<Integer> combinacion = new ArrayList<>();
                combinacion.add(numImg1);
                combinacion.add(numImg2);
                combinacion.add(numImg3);
                combinaciones.add(combinacion);
                premios.add(premio);
                imagenes.add(imagen);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int comprobarResultado(int[][] numsImg) {
        // Obtener las combinaciones con posibles premios
        List<List<Integer>> payLines = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Integer> payLine = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                payLine.add(numsImg[j][i]);
            }
            payLines.add(payLine);
        }
        payLines.add(List.of(numsImg[0][0], numsImg[1][1], numsImg[2][2]));
        payLines.add(List.of(numsImg[0][2], numsImg[1][1], numsImg[2][0]));

        // Comprobar si hay premio
        int premio = 0;
        for (int i = 0; i < combinaciones.size(); i++) {
            if (payLines.contains(combinaciones.get(i))) {
                premio += premios.get(i);
            }
        }
        return premio;
    }
}
