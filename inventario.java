import java.util.Scanner;

public class inventario {
    public static void main(String[] args) {
        String[] inventario = {"ducales", "festival", "tosh", null, null};
        //
        Scanner leer = new Scanner(System.in);
        //Variables
        int opcion = 0;
        String nombreBuscado = "";
        String nuevoProducto = "";
        boolean encontrado = false;
        boolean agregado = false;
        //
        while (opcion != 4) {
            System.out.println("===================");
            System.out.println("1. Listar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Agregar producto");
            System.out.println("4. cerrar Menu");
            System.out.println("===================");
            //
            opcion = Integer.parseInt(leer.nextLine());
            //
            switch (opcion) {
                case 1:
                    System.out.println("1. Listar Producto");
                    for (String producto : inventario) {
                        if (producto != null) {
                            System.out.println(producto);
                        }
                    }
                    break;
                case 2:
                    System.out.println("Nombre del producto a buscar:  ");
                    System.out.println("(Todo en minuscula y sin caracteres especiales)");
                    nombreBuscado = leer.nextLine();
                    //
                    encontrado = buscarProducto(inventario, nombreBuscado);
                    if (encontrado) {
                        System.out.println("Producto encontrado: " + nombreBuscado);
                    } else {
                        System.out.println("Producto no encontrado: " + nombreBuscado);
                    }
                    break;
                case 3:
                    System.out.print("Nombre del nuevo producto:  ");
                    System.out.println("(Todo en minuscula y sin caracteres especiales)");
                    nuevoProducto = leer.nextLine();
                    //
                    agregado = false;
                    for (int i = 0; i < inventario.length; i++) {
                        if (inventario[i] == null) {
                            inventario[i] = nuevoProducto;
                            agregado = true;
                            break;
                        }
                    }
                    if (agregado) {
                        System.out.println("Producto agregado");
                    } else {
                        System.out.println("Sin espacio, Error al agregar");
                    }
                    break;
                case 4:
                    System.out.println("Menu Cerrado");
                    break;
                default:
                    System.out.println("opcion invalida");
            }
        }
        leer.close();
    }
// ??
    public static boolean buscarProducto(String[] inventario, String nombreBuscado) {
        for (String producto : inventario) {
            if (producto != null && producto.equals(nombreBuscado)) {
                return true;
            }
        }
        return false;
    }
}