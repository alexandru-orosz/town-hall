package queries;

public class UserQueries {
    public static String FIND_BY_USERNAME = "FROM User WHERE username = :username";
    public static String FIND_BY_ID_USER = "FROM User WHERE id_user = :id_user";
    public static String GET_ALL_EMAILS = "SELECT email FROM User ";
    public static String GET_ALL_USERS = "FROM User";
}
