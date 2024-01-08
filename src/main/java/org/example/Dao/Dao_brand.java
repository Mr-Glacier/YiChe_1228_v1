package org.example.Dao;

public class Dao_brand extends DaoFather{
    public Dao_brand(int choseDB, int choseTable) {
        super(choseDB, choseTable);
    }


    public void Method_1_ChangeDownState(int C_ID,String time){

        String sql_str = "update "+this.tableName+"  set C_DownState  = '是' where C_ID  = "+C_ID;
        MethodIUD(sql_str);
        String sql_str2 = "update "+this.tableName+"  set C_DownTime = '"+time+"'  where C_ID  = "+C_ID;
        MethodIUD(sql_str2);

    }

}
