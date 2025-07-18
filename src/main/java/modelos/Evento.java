package modelos;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import jakarta.persistence.*;

@DiscriminatorColumn(name = "tipo_evento", discriminatorType = DiscriminatorType.STRING)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "evento")
public abstract class Evento {
    @Id
    @Column(name = "evento_id", columnDefinition = "UUID")
    private UUID idEvento = UUID.randomUUID();

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Date fechaInicio;

    @Column(nullable = false)
    private int duracion;

    @Column(length = 20, nullable = false)
    private EstadoEvento estado;

    @Column(nullable = false)
    private boolean requiereInscripcion = false;

    @Column(nullable = false)
    private boolean tieneCupo = false;

    @Column(nullable = false)
    private int cupoMaximo;

    @Column(nullable = false)
    private boolean baja = false;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participacion> participaciones = new ArrayList<>();

    protected Evento() {

    }
    public Evento(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo) {
        if(nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del evento no puede ser nulo o vacío.");
        }
        if(fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula.");
        }
        if(duracion <= 0) {
            throw new IllegalArgumentException("La duración del evento debe ser mayor a cero.");
        }
        
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estado = estado;
        this.requiereInscripcion = requiereInscripcion;
        this.tieneCupo = tieneCupo;
        this.cupoMaximo = cupoMaximo;
    }

    public UUID getIdEvento() {
        return idEvento;
    }
    public String getNombre() {
        return nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public int getVacantes() {
        if (!tieneCupo) return 0;
        return cupoMaximo - participaciones.size();
    }

    public void agregarParticipacion(Participacion participacion) {
        if (!participaciones.contains(participacion)) {
            participaciones.add(participacion);
            participacion.setEvento(this);
        }
    }

    public void removerParticipacion(Participacion participacion) {
        if (participaciones.remove(participacion)) {
            participacion.setEvento(null);
        }
    }


    public int getDuracion() {
        return duracion;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public boolean isRequiereInscripcion() {
        return requiereInscripcion;
    }

    public boolean isTieneCupo() {
        return tieneCupo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    public void setRequiereInscripcion(boolean requiereInscripcion) {
        this.requiereInscripcion = requiereInscripcion;
    }

    public void setTieneCupo(boolean tieneCupo) {
        this.tieneCupo = tieneCupo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public boolean isBaja() {
        return baja;
    }

    public void setBaja() {
        this.baja = true;
    }



    @Override
    public String toString() {
        return String.format("%s", nombre);
    }

}
