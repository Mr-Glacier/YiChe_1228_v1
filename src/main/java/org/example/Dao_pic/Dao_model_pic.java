package org.example.Dao_pic;

import org.example.Dao.DaoFather;

public class Dao_model_pic extends DaoFather {
    public Dao_model_pic(int choseDB, int choseTable) {
        super(choseDB, choseTable);
    }

    public void Method_ChangeState(int C_ID){
        String sql = "update "+this.tableName+" set DownState = '是' where C_ID = "+C_ID;
        MethodIUD(sql);
    }
}
