package org.example.Dao;

public class Dao_model extends DaoFather{
    public Dao_model(int choseDB, int choseTable) {
        super(choseDB, choseTable);
    }

    public void Method_ChangeDownState(int C_ID){
        System.out.println("执行更新操作 --->");
        String sql_str = "update  "+this.tableName+"  set C_DownState  = '是' where C_ID  = "+C_ID;
        MethodIUD(sql_str);

    }

}
