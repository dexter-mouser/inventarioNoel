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
    }
    }
