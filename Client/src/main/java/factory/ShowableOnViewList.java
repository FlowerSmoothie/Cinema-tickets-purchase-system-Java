package factory;

public interface ShowableOnViewList<T>
{

    String getName();
    void convertFromEntity(Object entity);
    Object convertToEntity();

}
