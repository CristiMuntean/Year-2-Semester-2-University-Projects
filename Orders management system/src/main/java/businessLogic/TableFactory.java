package businessLogic;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TableFactory<T> {
    /**
     * <p>
     *      This method creates a JTable from a list of objects that is passed as a parameter, by using the reflection technique
     * </p>
     * @param objects list of objects of a generic Class
     * @return a JTable object that contains the information form the given List
     */
    public JTable createTable(List<T> objects){
        if(objects.size()>0){
            String[] header = new String[objects.get(0).getClass().getDeclaredFields().length];
            int i=0;
            for (Field field: objects.get(0).getClass().getDeclaredFields()){
                String fieldName = field.getName();
                header[i]=fieldName;
                i++;
            }
            Object[][] data = new Object[objects.size()+1][i];
            int j=0;
            try {
                for(T object: objects){
                    int k=0;
                    for(Field field: object.getClass().getDeclaredFields()){
                        String fieldName = field.getName();
                        PropertyDescriptor propertyDescriptor = null;
                        propertyDescriptor = new PropertyDescriptor(fieldName,object.getClass());
                        Method method = propertyDescriptor.getReadMethod();
                        data[j][k]=method.invoke(object);
                        k++;
                    }
                    j++;
                }
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return new JTable(data,header){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }
        return null;
    }
}
