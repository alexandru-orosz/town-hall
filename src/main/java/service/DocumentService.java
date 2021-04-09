package service;


import dto.DocumentDto;
import entity.Document;
import exception.CustomExceptionMessages;
import exception.EntityNotExistsException;
import mappers.DocumentMapper;
import repository.DocumentRepo;
import utils.ApplicationUtils;
import validator.DocumentValidator;

import java.util.ArrayList;
import java.util.List;

public class DocumentService {

    private DocumentRepo documentRepo;

    public DocumentService(){
        this.documentRepo = new DocumentRepo();
    }

    public void insertDocument(DocumentDto documentDto){
        Document document = DocumentMapper.dtoToEntity(documentDto);
        DocumentValidator.validateInsertDocumentFlow(document);
        document.setId_document(ApplicationUtils.generateUUID());
        documentRepo.insertNewDocument(document);
    }

    public void deleteDocument(DocumentDto documentDto){
        Document document = documentRepo.findDocumentById(documentDto.getId_document());
        documentRepo.deleteDocument(document);
    }

    public void updateDocument(DocumentDto documentDto){
        Document document = DocumentMapper.dtoToEntity(documentDto);
        documentRepo.updateDocument(document);
    }

    public DocumentDto getDocumentById(String id_document){
        if(id_document == null || id_document.equals("")){
            throw new IllegalArgumentException(CustomExceptionMessages.INVALID_ID_MESSAGE);
        }

        Document document = documentRepo.findDocumentById(id_document);

        if(document == null){
            throw new EntityNotExistsException("No document having id " + id_document + " exists.");
        }

        return DocumentMapper.entityToDto(document);
    }

    public DocumentDto getDocumentByName(String name){
        if(name == null || name.equals("")){
            throw new IllegalArgumentException(CustomExceptionMessages.INVALID_NAME_MESSAGE);
        }

        Document document = documentRepo.findDocumentByName(name);

        if(document == null){
            throw new EntityNotExistsException("No document having name " + name + " exists.");
        }

        return DocumentMapper.entityToDto(document);
    }

    public List<DocumentDto> getAllDocuments(){
        List<Document> documents = documentRepo.getAllDocuments();
        List<DocumentDto> documentDtos = new ArrayList<>();

        for(Document document : documents){
            documentDtos.add(DocumentMapper.entityToDto(document));
        }

        return documentDtos;
    }
}
