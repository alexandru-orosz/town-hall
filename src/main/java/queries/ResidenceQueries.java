package queries;

public class ResidenceQueries {
    public static String FIND_BY_ID_RESIDENCE = "FROM Residence WHERE id_residence = :id_residence";
    public static String GET_ALL_USER_RESIDENCES = "FROM Residence WHERE user = :user";
    public static String GET_USER_RESIDENCE_BY_NAME = "FROM Residence WHERE user = :user AND name = :name";
}
