package mappers;

import dto.RequestDto;
import entity.Request;
import utils.ApplicationUtils;


public class RequestMapper {

    public static RequestDto entityToDto(Request request){
        RequestDto requestDto = new RequestDto();
        requestDto.setId_request(request.getId_request());
        requestDto.setDocument(DocumentMapper.entityToDto(request.getDocument()));
        requestDto.setResidence(ResidenceMapper.entityToDto(request.getResidence()));
        requestDto.setDate(ApplicationUtils.formattedDate(request.getDate()));
        requestDto.setStatus(request.getStatus());
        return requestDto;
    }


    public static Request dtoToEntity(RequestDto requestDto){
        Request request = new Request();
        request.setId_request(requestDto.getId_request());
        request.setDocument(DocumentMapper.dtoToEntity(requestDto.getDocument()));
        request.setResidence(ResidenceMapper.dtoToEntity(requestDto.getResidence()));
        request.setDate(ApplicationUtils.dateTime(requestDto.getDate()));
        request.setStatus(requestDto.getStatus());
        return request;
    }
}
