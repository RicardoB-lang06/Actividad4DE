import javax.swing.JOptionPane;

// ----- Nodo -----
class Nodo {
    int dato;//valor del nodo
    String nombre;//nombre del nodo
    Nodo hijoizquierdo, hijoderecho;//referencia para hijos

    public Nodo(int d, String nom) {
        this.dato = d;//guarda el numero en el nodo
        this.nombre = nom;//guarda el nombre de
        this.hijoizquierdo = null;//se inicializa vacio
        this.hijoderecho = null;//este tambien empieza vacio o null
    }

    @Override
    public String toString() {
        return nombre + " — su dato es " + dato;//muestra el nombre y el valor que hay en el nodo
    }
}

// ----- Árbol Binario -----
class ArbolBinario {
    Nodo raiz;//raiz del arbol

    public ArbolBinario() {
        raiz = null;//el arbol comoienza vacio
    }

    // Insertar nodo en el árbol
    public void agregarNodo(int d, String nom) {
        Nodo nuevo = new Nodo(d, nom);//crea un nodo
        if (raiz == null) {
            raiz = nuevo;//si está vacío, lo pone como raiz
            return;
        }
        Nodo auxiliar = raiz; // empieza desde la raiz
        Nodo padre;
        while (true) {
            padre = auxiliar;
            if (d < auxiliar.dato) {
                auxiliar = auxiliar.hijoizquierdo;//si es menor va a la izquierda
                if (auxiliar == null) {
                    padre.hijoizquierdo = nuevo;//y lo coloca ahí
                    return;
                }
            } else {
                auxiliar = auxiliar.hijoderecho;//si es mayor va a la derecha
                if (auxiliar == null) {
                    padre.hijoderecho = nuevo;//lo coloca ahí
                    return;
                }
            }
        }
    }
/*Reglas de eliminación en un arbol binario de búsquedaa

Nodo sin hijos (hoja):

Simplemente se elimina.

Nodo con un hijo:

Se elimina y su hijo ocupa su lugar.

Nodo con dos hijos:

Se busca el sucesor inorden (el nodo más pequeño del subárbol derecho) o el predecesor inorden (el mayor del subárbol izquierdo).

Se reemplaza el valor del nodo a eliminar con el del sucesor/predecesor.

Luego se elimina ese sucesor/predecesor. */
    public Nodo eliminarNodo(Nodo raiz, int d) {
        if (raiz == null) {
            System.out.println("El árbol o el nodo está vacío, no se puede eliminar.");
            return null;
        }
        if(d<raiz.dato){
            raiz.hijoizquierdo=eliminarNodo(raiz.hijoizquierdo,d);//buscar a la izquierda
        }else if(d>raiz.dato){
            raiz.hijoderecho=eliminarNodo(raiz.hijoderecho,d);//buscar a la derecha
        }else{
            //Buscar el nodo para borrarlo
            if(raiz.hijoizquierdo==null && raiz.hijoderecho==null){
                //caso1
                return null;//sin hihjos, se borra
            }else if(raiz.hijoizquierdo==null){
                //caso2
                return raiz.hijoderecho;//solo hijo derecho
            }else if(raiz.hijoderecho==null){
                //caso2
                return raiz.hijoizquierdo;//solo hijo izquierdo
            }else{
                //caso3
                //tiene dos hijos
                Nodo sucesor = encontrarMinimo(raiz.hijoderecho);//busca el menor del subarbol derecho
                raiz.dato = sucesor.dato;//copia el dato del sucesor
                raiz.nombre = sucesor.nombre;//copia el nombre del sucesor
                raiz.hijoderecho = eliminarNodo(raiz.hijoderecho,sucesor.dato);//borra ekl sucesor
                
            }
        }
                    return raiz;
    }
    //nodo minimo para eliminarlo
    public Nodo encontrarMinimo(Nodo raiz){
        if(raiz==null){
            return null;
        }else if(raiz.hijoizquierdo==null){
            return raiz;
        }
        return encontrarMinimo(raiz.hijoizquierdo);//recorre por la izquierda
    }

    public boolean estaVacio() {
        return raiz == null;//verifica si la raiz está vacía
    }
    // Buscar nodo por dato
public Nodo buscarNodo(int d) {
    Nodo actual = raiz;
    while (actual != null) {
        if (d == actual.dato) {
            return actual;//encuentra el nodo
        } else if (d < actual.dato) {
            actual = actual.hijoizquierdo;//va a la izquierda
        } else {
            actual = actual.hijoderecho;//va a la derecha
        }
    }
    return null;
}

// preorden
public void preorden() {
    preorden(raiz);
}
private void preorden(Nodo r) {
    if (r != null) {
        System.out.println(r);//visita el nodo
        preorden(r.hijoizquierdo);//Va a la izquierda
        preorden(r.hijoderecho);//va a ña derecha
    }
}

// postorden
public void postorden() {
    postorden(raiz);
}
private void postorden(Nodo r) {
    if (r != null) {
        postorden(r.hijoizquierdo);//izquierda
        postorden(r.hijoderecho);//derecha
        System.out.println(r);//visita el final
    }
}

    // Wrapper público
    public void inorden() {
        inorden(raiz);
    }

    // Recorrido inorden (privado)
    private void inorden(Nodo r) {
        if (r != null) {
            inorden(r.hijoizquierdo);//izquierda
            System.out.println(r.dato + " -> " + r.nombre);//nodo
            inorden(r.hijoderecho);//derecha
        }
    }
}

// ----- Principal (única clase pública) -----
public class Principal {
    public static void main(String[] args) {
        int opcion = 0;
        ArbolBinario arbolito = new ArbolBinario();//creacion del arbol

        do {
            try {
                String input = JOptionPane.showInputDialog(
                    null,
                    "1. Agregar un Nodo\n"
                    + "2. Recorrer el árbol en inorden\n"
                    + "3. Eliminar un nodo\n"
                    + "4. Buscar un nodo\n"
                    + "5. Recorrer el árbol en preorden\n"
                    + "6. Recorrer el árbol en postorden\n"
                    + "7. Salir\n\n",
                    JOptionPane.QUESTION_MESSAGE
                );

                if (input == null) {
                    opcion = 7; // Cancelar = salir
                } else {
                    opcion = Integer.parseInt(input);//convierte la opcion a entero
                }
                //si la opcion es menor a 1 o mayor a 7 o sea invalida
                if (opcion < 1 || opcion > 7) {
                    JOptionPane.showMessageDialog(
                        null, "Opción inválida",
                        "Cuidado", JOptionPane.INFORMATION_MESSAGE
                    );
                    continue;
                }

                switch (opcion) {
                    case 1: {
                        int elemento = Integer.parseInt(
                            JOptionPane.showInputDialog(
                                null, "Ingresa el número del nodo...",
                                "Agregando Nodo", JOptionPane.QUESTION_MESSAGE
                            )
                        );
                        String nombre = JOptionPane.showInputDialog(
                            null, "Ingresa el nombre del nodo",
                            "Agregando Nodo", JOptionPane.QUESTION_MESSAGE
                        );
                        if (nombre == null) nombre = "(sin nombre)";
                        arbolito.agregarNodo(elemento, nombre);
                    } break;

                    case 2: {
                        if (!arbolito.estaVacio()) {
                            arbolito.inorden();
                        } else {
                            JOptionPane.showMessageDialog(
                                null, "El árbol está vacío",
                                "Cuidado", JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    } break;

                    case 3: {
                        if (!arbolito.estaVacio()) {
                            int elemento = Integer.parseInt(JOptionPane.showInputDialog(
                                null, "Ingresa el número del valor del nodo a eliminar...",
                                "Eliminar Nodo", JOptionPane.QUESTION_MESSAGE
                            ));
                            arbolito.raiz = arbolito.eliminarNodo(arbolito.raiz, elemento);
                            JOptionPane.showMessageDialog(null, "Nodo eliminado (si existía)");
                        } else {
                            JOptionPane.showMessageDialog(
                                null, "El árbol está vacío",
                                "Cuidado", JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    } break;

                    case 4: {
                        int elemento = Integer.parseInt(JOptionPane.showInputDialog(
                            null, "Ingresa el número del nodo a buscar...",
                            "Buscar Nodo", JOptionPane.QUESTION_MESSAGE
                        ));
                        Nodo resultado = arbolito.buscarNodo(elemento);
                        if (resultado != null) {
                            JOptionPane.showMessageDialog(null, "Nodo encontrado:\n" + resultado);
                        } else {
                            JOptionPane.showMessageDialog(null, "Nodo no encontrado.");
                        }
                    } break;

                    case 5:
                        arbolito.preorden();
                        break;

                    case 6:
                        arbolito.postorden();
                        break;

                    case 7:
                        JOptionPane.showMessageDialog(null, "Aplicación finalizada", "F I N", JOptionPane.INFORMATION_MESSAGE);
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida", "Cuidado", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }

            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Error: " + n.getMessage());
            } finally {
                System.out.println("Ciclo de menú ejecutado."); // Este mensaje aparece siempre
            }

        } while (opcion != 7);
    }
}