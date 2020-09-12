package SessionBean;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

@LocalBean
@Stateful
public class TestStaful {
    public int DeleteNum =0 ;
    public int InsertNum =0;

    public TestStaful() {
    }
}
