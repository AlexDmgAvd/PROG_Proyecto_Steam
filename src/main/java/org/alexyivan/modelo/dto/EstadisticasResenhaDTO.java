package org.alexyivan.modelo.dto;

public class EstadisticasResenhaDTO {

    private long id;
    private long idJuego;
    private long totalResenhas;
    private float porcentajePositivas;
    private float getPorcentajeNegativas;
    private float promedioHoras;
    private String tendenciaReciente;

    public EstadisticasResenhaDTO(long id, long idJuego, long totalResenhas,
                                  float porcentajePositivas, float getPorcentajeNegativas,
                                  float promedioHoras, String tendenciaReciente) {
        this.id = id;
        this.idJuego = idJuego;
        this.totalResenhas = totalResenhas;
        this.porcentajePositivas = porcentajePositivas;
        this.getPorcentajeNegativas = getPorcentajeNegativas;
        this.promedioHoras = promedioHoras;
        this.tendenciaReciente = tendenciaReciente;
    }


}
