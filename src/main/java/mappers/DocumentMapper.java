package mappers;

import dto.DocumentDto;
import entity.Document;

public class DocumentMapper {

    public static DocumentDto entityToDto(Document document){
        DocumentDto documentDto = new DocumentDto();
        documentDto.setId_document(document.getId_document());
        documentDto.setName(document.getName());
        return documentDto;
    }

    public static Document dtoToEntity(DocumentDto documentDto){
        Document document = new Document();
        document.setId_document(documentDto.getId_document());
        document.setName(documentDto.getName());
        return document;
    }
}
