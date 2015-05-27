
public class Dispatcher {

    /**
     * @brief Main del programa.
     * @param argv[0] Nombre del archivo xml donde se encuentra especificados los procesos.
     * @param argv[1] El intervalo de interrupción del timer.
     */
    public static void main(String argv[]) {
        
        String file = argv[0];
        int interruptInterval = Integer.parseInt(argv[1]);
        ProcessAppletTable table = new ProcessAppletTable();
        table.init(file, interruptInterval);
    }
}
