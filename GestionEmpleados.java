import javax.swing.JOptionPane;

//clase Empleado (nodo del árbol)
class Empleado {
    int id;
    String nombre;
    Empleado izquierdo, derecho;

    public Empleado(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        izquierdo = derecho = null;
    }

    @Override
    public String toString() {
        return "ID: " + id + " — Nombre: " + nombre;
    }
}

//Árbol binario para empleados
class ArbolEmpleados {
    Empleado raiz;

    // INsertar empleado por ID
    public void insertar(int id, String nombre) {
        Empleado nuevo = new Empleado(id, nombre);
        if (raiz == null) {
            raiz = nuevo;
            return;
        }
        Empleado actual = raiz;
        while (true) {
            if (id < actual.id) {
                if (actual.izquierdo == null) {
                    actual.izquierdo = nuevo;
                    return;
                }
                actual = actual.izquierdo;
            } else {
                if (actual.derecho == null) {
                    actual.derecho = nuevo;
                    return;
                }
                actual = actual.derecho;
            }
        }
    }

    // Buscar empleado por ID
    public Empleado buscar(int id) {
        Empleado actual = raiz;
        while (actual != null) {
            if (id == actual.id) return actual;
            actual = (id < actual.id) ? actual.izquierdo : actual.derecho;
        }
        return null;
    }

    // Mostrar empleados ordenados por ID
    public void mostrarInorden() {
        inorden(raiz);
    }

    private void inorden(Empleado e) {
        if (e != null) {
            inorden(e.izquierdo);
            System.out.println(e);
            inorden(e.derecho);
        }
    }
}

//principal
public class GestionEmpleados {
    public static void main(String[] args) {
        ArbolEmpleados empleados = new ArbolEmpleados();

        int opcion;
        do {
            String input = JOptionPane.showInputDialog(
                null,
                "1. Agregar empleado\n"
              + "2. Buscar empleado por ID\n"
              + "3. Mostrar empleados ordenados\n"
              + "4. Comparar con búsqueda secuencial\n"
              + "5. Salir",
                "Gestión de Empleados", JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                opcion = 5;
            } else {
                opcion = Integer.parseInt(input);
            }

            switch (opcion) {
                case 1: {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(null, "ID del empleado:"));
                    String nombre = JOptionPane.showInputDialog(null, "Nombre del empleado:");
                    if (nombre == null) nombre = "(sin nombre)";
                    empleados.insertar(id, nombre);
                    JOptionPane.showMessageDialog(null, "Empleado agregado.");
                } break;

                case 2: {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(null, "ID a buscar:"));
                    Empleado e = empleados.buscar(id);
                    if (e != null) {
                        JOptionPane.showMessageDialog(null, "Encontrado:\n" + e);
                    } else {
                        JOptionPane.showMessageDialog(null, "Empleado no encontrado.");
                    }
                } break;

                case 3: {
                    JOptionPane.showMessageDialog(null, "Empleados ordenados por ID (ver consola):");
                    empleados.mostrarInorden();
                } break;

                case 4: {
                    // Simulación de búsqueda secuencial contra el árbol
                    int[] ids = {105, 203, 150, 98, 301, 120, 180};
                    String[] nombres = {"Ana", "Luis", "Carlos", "Sofía", "Marta", "Diego", "Elena"};

                    int buscarID = Integer.parseInt(JOptionPane.showInputDialog(null, "ID a buscar (simulación):"));

                    // Secuencial
                    long inicioSec = System.nanoTime();
                    String resultadoSec = null;
                    for (int i = 0; i < ids.length; i++) {
                        if (ids[i] == buscarID) {
                            resultadoSec = "ID: " + ids[i] + " — Nombre: " + nombres[i];
                            break;
                        }
                    }
                    long finSec = System.nanoTime();

                    // Árbol
                    ArbolEmpleados simulado = new ArbolEmpleados();
                    for (int i = 0; i < ids.length; i++) {
                        simulado.insertar(ids[i], nombres[i]);
                    }
                    long inicioBST = System.nanoTime();
                    Empleado resultadoBST = simulado.buscar(buscarID);
                    long finBST = System.nanoTime();

                    String mensaje = "Resultado secuencial: " + (resultadoSec != null ? resultadoSec : "No encontrado") +
                                     "\nTiempo: " + (finSec - inicioSec) + " ns" +
                                     "\n\nResultado árbol: " + (resultadoBST != null ? resultadoBST : "No encontrado") +
                                     "\nTiempo: " + (finBST - inicioBST) + " ns";

                    JOptionPane.showMessageDialog(null, mensaje);
                } break;

                case 5:
                    JOptionPane.showMessageDialog(null, "Programa finalizado.");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }

        } while (opcion != 5);
    }
}