package dataAccess;

import connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T>  {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * <p>
     *      Creates the String that is responsible of the search query
     * </p>
     * @param field field that is being searched for
     * @return String that contains the query for the search operation
     */
    private String createSelectQuery(String field){
        StringBuilder query = new StringBuilder();
        query.append("SELECT ").append(" * ").append(" FROM ").append(type.getSimpleName());
        query.append(" WHERE ").append(field).append(" =?");
        return query.toString();
    }


    /**
     * <p>
     *      Joins all 3 tables of the database and returns all of the rows that are in the table that results after the join
     * </p>
     * @return The result of the query that joins all 3 tables of the database
     */
    public List<T> findAllWithJoin(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT ProductOrder.orderId, Client.clientName, Client.clientSurname, Product.productName, ProductOrder.productQuantity, " +
                "(ProductOrder.productQuantity * Product.productPrice) as orderPrice " +
                "FROM ProductOrder " +
                "JOIN Client on Client.clientId = ProductOrder.clientId " +
                "JOIN Product on Product.productId = ProductOrder.productId";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;

    }

    /**
     * <p>
     *      Returns everything that is contained in a table of a database
     * </p>
     * @return List of all objects found in the table of the database
     */
    public List<T> findAll(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * <p>
     *      Receives a field and a value of the field and returns the object that results from executing a
     *      search query in the database
     * </p>
     * @param idField the field that will be searched for in the database
     * @param id the value of the field that will be searched
     * @return A generic object as a result of executing the search query
     */
    public T findById(String idField, int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(idField);
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> result = createObjects(resultSet);
            if(result.size()>0)
                return result.get(0);
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " +e.getMessage());
            e.printStackTrace();
        }finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * <p>
     *      Creates object from the result set that is given as a parameter
     * </p>
     * @param resultSet the result of an executed query
     * @return A list of objects
     */
    private List<T> createObjects(ResultSet resultSet){
        List<T> list = new ArrayList<T>();
        Constructor[] constructors = type.getDeclaredConstructors();
        Constructor constructor = null;
        for (int i=0;i<constructors.length;i++){
            constructor = constructors[i];
            if(constructor.getGenericParameterTypes().length == 0)
                break;
        }
        try{
            while(resultSet.next()){
                constructor.setAccessible(true);
                T instance = (T)constructor.newInstance();
                for(Field field:type.getDeclaredFields()){
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName,type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        }catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
        InvocationTargetException | SQLException |IntrospectionException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * <p>
     *      Creates the String that will be responsible for executing a insert operation
     * </p>
     * @param t the object that needs to be inserted
     * @return A String that contains the query responsible for inserting the object into the database
     */
    private String createInsertQuery(T t){
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(type.getSimpleName()).append(" (");
        String prefix="";
        for(Field field:type.getDeclaredFields()){
            query.append(prefix);
            prefix=",";
            query.append(field.getName());
        }
        query.append(") VALUES (");
        prefix="";
        try {
            for (Field field:type.getDeclaredFields()){
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                query.append(prefix);
                prefix=",";
                query.append("'").append(method.invoke(t)).append("'");
            }
        }catch (IntrospectionException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
        query.append(")");
        return query.toString();
    }

    /**
     * <p>
     *      Inserts the object given as a parameter into the database
     * </p>
     * @param t the object that needs to be inserted
     */
    public void insert(T t){
        Connection connection = null;
        Statement statement = null;
        String query = createInsertQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate(query);

        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Could not perform the insertion");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * <p>
     *      Creates the query that is responsible for updating the given object with the values that are passed on
     *      in the List of filters
     * </p>
     * @param t object that needs to be updated
     * @param filters values that will be changed in the object
     * @return A string that contains the query
     */
    private String createUpdateQuery(T t, List<String> filters){
        StringBuilder stringBuilder = new StringBuilder();
        String prefix = "";
        stringBuilder.append("UPDATE ").append(type.getSimpleName()).append(" SET ");
        for(String filter: filters){
            String[] parts = filter.split(":");
            stringBuilder.append(prefix).append(parts[0]).append("='").append(parts[1]).append("'");
            prefix=", ";
        }
        stringBuilder.append(" WHERE ");
        prefix ="";
        try {
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getReadMethod();
                stringBuilder.append(prefix);
                prefix=" AND ";
                stringBuilder.append(fieldName).append("='").append(method.invoke(t)).append("'");
            }
        }catch(IntrospectionException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * <p>
     *      Updates de object t with the given filters
     * </p>
     * @param t object that needs to be updated
     * @param filters fields that are updated on the given object
     */
    public void update(T t, List<String> filters){
        Connection connection = null;
        Statement statement = null;
        String query = createUpdateQuery(t,filters);
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate(query);
        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Could not perform the update");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

    /**
     * <p>
     *      Creates the query that is responsible for deleting the object given as a parameter
     * </p>
     * @param t the object that needs to be deleted
     * @return the query that is responsible for deleting the given object
     */
    private String createDeleteQuery(T t){
        StringBuilder stringBuilder = new StringBuilder();
        String prefix="";
        stringBuilder.append("DELETE FROM ").append(type.getSimpleName()).append(" WHERE ");
        try {
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getReadMethod();
                stringBuilder.append(prefix);
                prefix=" AND ";
                stringBuilder.append(fieldName).append("='").append(method.invoke(t)).append("'");
            }
        }catch(IntrospectionException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * <p>
     *      Deletes the object that is given as a parameter from the databes
     * </p>
     * @param t object that needs to be deleted
     */
    public void delete(T t){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
