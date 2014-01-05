package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

import javax.persistence.*;

import traveldream.dtos.HotelDTO;

@Entity
@Table(name="Hotel")
@NamedQueries({
	@NamedQuery(name=Hotel.FIND_ALL, query="SELECT h FROM Hotel h") })

public class Hotel implements Serializable {
        private static final long serialVersionUID = 1L;
        public static final String FIND_ALL = "Hotel.findAll";

        @Id
        @Column(name="ID")
        private int id;

        @Column(name="Costo giornaliero")
        private int costo_giornaliero;

        @Column(name="Immagine")
        private String immagine;

        @Column(name="Luogo")
        private String luogo;

        @Column(name="Nome")
        private String nome;

        @Column(name="Stelle")
        private int stelle;


        public Hotel() {
                super();
        }

        public Hotel(HotelDTO hoteldto)
        {
                 this.nome = hoteldto.getNome();
         this.luogo = hoteldto.getLuogo();
         this.costo_giornaliero = hoteldto.getCosto_giornaliero();
         this.immagine = hoteldto.getPathtoImage();
         this.stelle = hoteldto.getStelle();
        }

        public int getId() {
                return this.id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public int getCosto_giornaliero() {
                return this.costo_giornaliero;
        }

        public void setCosto_giornaliero(int costo_giornaliero) {
                this.costo_giornaliero = costo_giornaliero;
        }

        public String getImmagine() {
                return this.immagine;
        }

        public void setImmagine(String immagine) {
                this.immagine = immagine;
        }

        public String getLuogo() {
                return this.luogo;
        }

        public void setLuogo(String luogo) {
                this.luogo = luogo;
        }

        public String getNome() {
                return this.nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public int getStelle() {
                return this.stelle;
        }

        public void setStelle(int stelle) {
                this.stelle = stelle;
        }

}