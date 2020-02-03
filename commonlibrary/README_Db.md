## GreenDao
 ` *注  每创建一个Bean类就需要创建一个BeanManager，用于管理数据库中的表。`  <br/>
       `BeanManager 需要继承 BaseDbBeanManager .`   
   ```   
//例子
public class UnitBeanManager extends BaseDbBeanManager<UnitBean,String> {
  public UnitBeanManager(AbstractDao dao) {
        super(dao);
    }
}
 
   ``` 

