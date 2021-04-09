package queries;

public class DocumentQueries {
    public static String FIND_BY_ID_DOCUMENT = "FROM Document WHERE id_document = :id_document";
    public static String FIND_BY_NAME = "FROM Document WHERE name = :name";
    public static String GET_ALL_DOCUMENTS = "FROM Document";
}
