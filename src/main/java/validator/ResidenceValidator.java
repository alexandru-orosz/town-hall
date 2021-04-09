package validator;

import entity.Residence;

public class ResidenceValidator {

    public static void validateInsertResidenceFlow(Residence residence){
        if(residence == null){
            throw new IllegalArgumentException("Residence is null");
        }
    }
}
