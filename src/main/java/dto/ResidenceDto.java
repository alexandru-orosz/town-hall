package dto;

public class ResidenceDto {

    private String id_residence;

    private UserDto user;

    private String name;

    private String country;

    private String city;

    private String zip_code;

    private String street;

    private String number;

    private String apartment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResidenceDto)) return false;
        ResidenceDto that = (ResidenceDto) o;
        return getId_residence().equals(that.getId_residence());
    }


    public String getId_residence() {
        return id_residence;
    }

    public void setId_residence(String id_residence) {
        this.id_residence = id_residence;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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
}
