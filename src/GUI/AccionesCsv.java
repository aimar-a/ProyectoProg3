package GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AccionesCsv {

    private static final String CSV_CONTRASENAS = "src/CSV/usuarioContra.csv";
    private static final String CSV_DATOS = "src/CSV/usuarioDatos.csv";
    private static final String CSV_CARTERA = "src/CSV/cartera.csv";
    private static final String CSV_HISTORIAL = "src/CSV/historialMovimientos.csv";

    // IAG
    public static String obtenerContrasena(String usuario) {
        String contrasena = null;
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_CONTRASENAS))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(usuario)) {
                    contrasena = values[1];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contrasena;
    }

    // IAG
    public static boolean cambiarContrasena(String usuario, String nuevaContrasena) {
        List<String> lineas = new ArrayList<>();
        boolean actualizado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_CONTRASENAS))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals(usuario)) {
                    lineas.add(usuario + "," + nuevaContrasena);
                    actualizado = true;
                } else {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (actualizado) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_CONTRASENAS))) {
                for (String l : lineas) {
                    pw.println(l);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return actualizado;
    }

    // IAG
    public static String[] obtenerDatos(String usuario) {
        String[] datosUsuario = null;

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_DATOS))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals(usuario)) {
                    datosUsuario = new String[datos.length - 1];
                    System.arraycopy(datos, 1, datosUsuario, 0, datos.length - 1);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return datosUsuario;
    }

    // IAG
    public static boolean cambiarDatos(String usuario, String[] nuevosDatos) {
        if (nuevosDatos.length != 11) {
            return false;
        }
        List<String> lineas = new ArrayList<>();
        boolean actualizado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_DATOS))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals(usuario)) {
                    lineas.add(usuario + "," + String.join(",", nuevosDatos) + "," + datos[12]);
                    actualizado = true;
                } else {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (actualizado) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_DATOS))) {
                for (String l : lineas) {
                    pw.println(l);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return actualizado;
    }

    // IAG
    public static double obtenerSaldo(String usuario) {
        double saldo = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_CARTERA))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals(usuario)) {
                    saldo = Double.parseDouble(datos[1]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return saldo;
    }

    // IAG
    private static boolean cambiarSaldo(String usuario, double nuevoSaldo) {
        List<String> lineas = new ArrayList<>();
        boolean actualizado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_CARTERA))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals(usuario)) {
                    lineas.add(usuario + "," + String.format(Locale.US, "%.2f", nuevoSaldo));
                    actualizado = true;
                } else {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (actualizado) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_CARTERA))) {
                for (String l : lineas) {
                    pw.println(l);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return actualizado;
    }

    // IAG
    public static boolean agregarMovimiento(String usuario, double cantidad, String asunto) {
        double saldoActual = obtenerSaldo(usuario);
        double nuevoSaldo = saldoActual + cantidad;
        if (nuevoSaldo < 0) {
            return false;
        }
        if (!cambiarSaldo(usuario, nuevoSaldo)) {
            return false;
        }

        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String movimiento = String.format(Locale.US, "%s,%s,%s,%+.2f,%s,%.2f", fecha, hora, usuario, cantidad, asunto,
                nuevoSaldo);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_HISTORIAL, true))) {
            bw.write(movimiento);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // IAG
    public static List<String[]> obtenerHistorial(String usuario) {
        List<String[]> historial = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_HISTORIAL))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[2].equals(usuario)) {
                    historial.add(datos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historial;
    }

    // IAG
    public static boolean agregarUsuario(String usuario, String contrasena, String[] datos) {
        if (datos.length != 11 || usuarioExiste(usuario)) {
            return false;
        }
        List<String> lineasContrasenas = new ArrayList<>();
        List<String> lineasDatos = new ArrayList<>();
        List<String> lineasCartera = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_CONTRASENAS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineasContrasenas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_DATOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineasDatos.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_CARTERA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineasCartera.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        lineasContrasenas.add(usuario + "," + contrasena);
        lineasDatos.add(
                usuario + "," + String.join(",", datos) + "," + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        lineasCartera.add(usuario + ",0");

        lineasContrasenas.sort(String::compareTo);
        lineasDatos.sort(String::compareTo);
        lineasCartera.sort(String::compareTo);

        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_CONTRASENAS))) {
            for (String linea : lineasContrasenas) {
                pw.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_DATOS))) {
            for (String linea : lineasDatos) {
                pw.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_CARTERA))) {
            for (String linea : lineasCartera) {
                pw.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // IAG
    public static boolean usuarioExiste(String usuario) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_CONTRASENAS))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(usuario)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
