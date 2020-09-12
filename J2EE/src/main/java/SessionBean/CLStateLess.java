package SessionBean;

import entity.ClassEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class CLStateLess {
    public CLStateLess() {
    }

    @PersistenceContext(name="default")
    EntityManager entityManager;

    public List<ClassEntity> getAllClass(){
        List<ClassEntity> cls = entityManager.createQuery("select l from  ClassEntity  l").getResultList();
        return  cls;
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean insertClass(ClassEntity cl){
        try{
            entityManager.persist(cl);
            entityManager.flush();
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    public boolean delete(String id){

        try{
            ClassEntity classEntity = entityManager.find(ClassEntity.class,id);
            entityManager.remove(classEntity);
            entityManager.flush();
            return true;
        }
        catch (Exception e){
            return false;
        }

    }


}
