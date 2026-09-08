import java.util.Scanner;

public class InventarioNoel {
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
            opcion = leer.nextInt();
            //
        }
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
                    
                case 4:
            }
    }
    }
