package Modelo.Form;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaForm {

    private int usuario;
    private int juego;
    private LocalDate fechaAdquisicion;

    public BibliotecaForm(int usuario, int juego, LocalDate fechaAdquisicion) {
        this.usuario = usuario;
        this.juego = juego;
        this.fechaAdquisicion = fechaAdquisicion;
    }
    public List<ErrorDto> validate() {

        List<ErrorDto> errores = new ArrayList<>();

        if (usuario <= 0){
            errores.add(new ErrorDto("usuario", ErrorType.REQUERIDO))
        }

        if (usuario  )

    }
}


