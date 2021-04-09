package service;

import dto.RequestDto;
import dto.ResidenceDto;
import dto.UserDto;
import entity.Request;
import exception.CustomExceptionMessages;
import exception.EntityNotExistsException;
import mappers.RequestMapper;
import mappers.UserMapper;
import repository.RequestRepo;
import utils.ApplicationUtils;
import validator.RequestValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestService {

    private final RequestRepo requestRepo;

    public RequestService() {
        this.requestRepo = new RequestRepo();
    }

    public void insertRequest(RequestDto requestDto) {
        Request request = RequestMapper.dtoToEntity(requestDto);
        RequestValidator.validateInsertRequestFlow(request);
        request.setId_request(ApplicationUtils.generateUUID());
        requestRepo.insertNewRequest(request);
    }

    public void deleteRequest(RequestDto requestDto) {
        Request request = requestRepo.findRequestById(requestDto.getId_request());
        requestRepo.deleteRequest(request);
    }

    public void updateRequest(RequestDto requestDto) {
        Request request = RequestMapper.dtoToEntity(requestDto);
        requestRepo.updateRequest(request);
    }

    public RequestDto getRequestById(String id_request) {
        if (id_request == null || id_request.equals("")) {
            throw new IllegalArgumentException(CustomExceptionMessages.INVALID_ID_MESSAGE);
        }

        Request request = requestRepo.findRequestById(id_request);

        if (request == null) {
            throw new EntityNotExistsException("No request having id " + id_request + " exists.");
        }

        return RequestMapper.entityToDto(request);
    }

    public List<RequestDto> getAllRequests(){
        List<Request> requests = requestRepo.getAllRequests();
        List<RequestDto> requestDtos = new ArrayList<>();

        for(Request request : requests){
            requestDtos.add(RequestMapper.entityToDto(request));
        }

        return requestDtos;
    }

    public List<RequestDto> getAllUserRequests(UserDto userDto){
        List<Request> requests = requestRepo.getAllRequests();
        List<RequestDto> requestDtos = new ArrayList<>();

        for(Request request : requests){
            if(UserMapper.entityToDto(request.getResidence().getUser()).equals(userDto)) {
                requestDtos.add(RequestMapper.entityToDto(request));
            }
        }

        return requestDtos;
    }

    public List<RequestDto> getPendingRequests(){
        List<Request> requests = requestRepo.getAllRequests();
        List<RequestDto> requestDtos = new ArrayList<>();

        for(Request request : requests){
            if(request.getStatus().equals("Pending")) {
                requestDtos.add(RequestMapper.entityToDto(request));
            }
        }

        return requestDtos;
    }

    public int getResidenceRequestsThisYear(ResidenceDto residenceDto){
        List<RequestDto> requests = getAllUserRequests(residenceDto.getUser());

        int numberOfRequests = 0;
        final int currentYear = LocalDateTime.now().getYear();

        for(RequestDto request : requests){
            if(request.getResidence().equals(residenceDto) && ApplicationUtils.dateTime(request.getDate()).getYear() == currentYear){
                numberOfRequests++;
            }
        }

        System.out.println(numberOfRequests);
        return numberOfRequests;
    }

    public boolean foundBySearch(RequestDto request, String searchText){
        String username = request.getUsername().toLowerCase();
        String document = request.getDocument_name().toLowerCase();
        String residence = request.getResidence_name().toLowerCase();
        String status = request.getStatus().toLowerCase();

        searchText = searchText.toLowerCase();

        return username.contains(searchText) || document.contains(searchText) || residence.contains(searchText) || status.contains(searchText);
    }
}
