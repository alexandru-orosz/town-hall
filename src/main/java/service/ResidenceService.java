package service;

import dto.ResidenceDto;
import dto.UserDto;
import entity.Residence;
import exception.CustomExceptionMessages;
import exception.EntityNotExistsException;
import mappers.ResidenceMapper;
import mappers.UserMapper;
import repository.ResidenceRepo;
import utils.ApplicationUtils;
import validator.ResidenceValidator;

import java.util.*;

public class ResidenceService {

    private final ResidenceRepo residenceRepo;

    public ResidenceService(){
        this.residenceRepo = new ResidenceRepo();
    }

    public void insertResidence(ResidenceDto residenceDto){
        Residence residence = ResidenceMapper.dtoToEntity(residenceDto);
        ResidenceValidator.validateInsertResidenceFlow(residence);
        residence.setId_residence(ApplicationUtils.generateUUID());
        residence.setDeleted(false);
        residenceRepo.insertNewResidence(residence);
    }

    public void deleteResidence(ResidenceDto residenceDto){
        Residence residence = residenceRepo.findResidenceById(residenceDto.getId_residence());
        residenceRepo.deleteResidence(residence);
    }

    public void hideResidence(ResidenceDto residenceDto){
        Residence residence = ResidenceMapper.dtoToEntity(residenceDto);
        residence.setDeleted(true);
        residenceRepo.updateResidence(residence);
    }

    public void updateResidence(ResidenceDto residenceDto){
        Residence residence = ResidenceMapper.dtoToEntity(residenceDto);
        residenceRepo.updateResidence(residence);
    }

    public ResidenceDto getResidenceById(String id_residence){
        if(id_residence == null || id_residence.equals("")){
            throw new IllegalArgumentException(CustomExceptionMessages.INVALID_ID_MESSAGE);
        }

        Residence residence = residenceRepo.findResidenceById(id_residence);

        if(residence == null){
            throw new EntityNotExistsException("No residence having id " + id_residence + " exists.");
        }

        return ResidenceMapper.entityToDto(residence);
    }

    public ResidenceDto getResidenceByNameAndUser(String name, UserDto userDto){
        if(name == null || name.equals("")){
            throw new IllegalArgumentException(CustomExceptionMessages.INVALID_NAME_MESSAGE);
        }

        Residence residence = residenceRepo.findResidenceByNameAndUser(name, UserMapper.dtoToEntity(userDto));

        if(residence == null){
            throw new EntityNotExistsException("No residence having name " + name + " exists.");
        }

        return ResidenceMapper.entityToDto(residence);
    }

    public List<ResidenceDto> getAllResidences(UserDto userDto){
        List<Residence> residences = residenceRepo.getAllResidences(UserMapper.dtoToEntity(userDto));
        List<ResidenceDto> residenceDtos = new ArrayList<>();

        for (Residence residence : residences) {
            if(!residence.getDeleted()) {
                residenceDtos.add(ResidenceMapper.entityToDto(residence));
            }
        }

        return residenceDtos;
    }
}
