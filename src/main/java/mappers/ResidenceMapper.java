package mappers;

import dto.ResidenceDto;
import entity.Residence;

public class ResidenceMapper {

    public static ResidenceDto entityToDto(Residence residence){
        ResidenceDto residenceDto = new ResidenceDto();
        residenceDto.setId_residence(residence.getId_residence());
        residenceDto.setUser(UserMapper.entityToDto(residence.getUser()));
        residenceDto.setName(residence.getName());
        residenceDto.setCountry(residence.getCountry());
        residenceDto.setCity(residence.getCity());
        residenceDto.setZip_code(residence.getZip_code());
        residenceDto.setStreet(residence.getStreet());
        residenceDto.setNumber(residence.getNumber());
        residenceDto.setApartment(residence.getApartment());
        return residenceDto;
    }

    public static Residence dtoToEntity(ResidenceDto residenceDto){
        Residence residence = new Residence();
        residence.setId_residence(residenceDto.getId_residence());
        residence.setUser(UserMapper.dtoToEntity(residenceDto.getUser()));
        residence.setName(residenceDto.getName());
        residence.setCountry(residenceDto.getCountry());
        residence.setCity(residenceDto.getCity());
        residence.setZip_code(residenceDto.getZip_code());
        residence.setStreet(residenceDto.getStreet());
        residence.setNumber(residenceDto.getNumber());
        residence.setApartment(residenceDto.getApartment());
        return residence;
    }
}
