package com.aa183.putra;

// membuat object Film
public class Film {
    private Integer idfilm;
    private String title, descr, genre, director, actor, country, duration;
    private byte[] img;

    public Film(Integer idfilm, String title, String descr, String genre, String director, String actor, String country, String duration, byte[] img) {
        this.idfilm = idfilm;
        this.title = title;
        this.descr = descr;
        this.genre = genre;
        this.director = director;
        this.actor = actor;
        this.country = country;
        this.duration = duration;
        this.img = img;
    }

    public Integer getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(Integer idfilm) {
        this.idfilm = idfilm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
