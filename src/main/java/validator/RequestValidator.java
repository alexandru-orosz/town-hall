package validator;

import entity.Request;

public class RequestValidator {
    public static void validateInsertRequestFlow(Request request){
        if(request == null){
            throw new IllegalArgumentException("Request is null");
        }
    }
}
