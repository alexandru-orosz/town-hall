package validator;

import entity.Document;

public class DocumentValidator {

    public static void validateInsertDocumentFlow(Document document){
        if(document == null){
            throw new IllegalArgumentException("Document id null");
        }
    }
}
