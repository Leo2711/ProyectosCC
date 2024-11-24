package cc.project.monsters_inc;

// Ejemplo Main Principal de la Empresa
public class MainEmpresa implements Runnable {
    
    String TIPOMONSTRUO = "";
    int i = 0;

    @Override
    public void run() {
        switch (TIPOMONSTRUO) {
            case ("ASUSTADOR  || COMEDIANTE"):
                while (i < 10) {
                    // lockers(ID HILO);
                    // banno(ID HILO);
                    if (this.equals("ASUSTADOR")) {
                        // CentroDeSustos(ID HILO);
                    } else {
                        // CentroDeRisas(ID HILO);
                    }
                }
                // Commer(ID HILO);
            break;
            case ("TANQUERO || PUERTERO"):
                while (i < 10) {
                    // lockers(ID HILO);
                    // banno(ID HILO);
                    if (this.equals("TANQUERO")) {
                        // FabricaTanques(ID HILO);
                    } else {
                        // FabricaDePuertas(ID HILO);
                    }
                    // Commer(ID HILO);
                }
            break;
            case ("AMARILLITO Y SU AMIGO"):
                // locker(ID HILO);
                // commer(ID HILO);
                // Banno(ID HILO);
                while (i < 30) {
                    // AlmacenTAnquesRecoger(ID HILO);
                    // Centros(ID HILO);
                }
            break;
        }
    }

    // System.out.println("TERMINANDO DIA");
}
