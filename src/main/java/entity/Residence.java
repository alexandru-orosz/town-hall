package entity;

import javax.persistence.*;


@Entity
@Table(name = "residence")

public class Residence {

    @Id
    private String id_residence;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column
    private String name;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String zip_code;

    @Column
    private String street;

    @Column
    private String number;

    @Column
    private String apartment;

    @Column
    private boolean deleted;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId_residence() {
        return id_residence;
    }

    public void setId_residence(String id_residence) {
        this.id_residence = id_residence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
