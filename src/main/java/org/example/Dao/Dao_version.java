package org.example.Dao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class Dao_version extends DaoFather {
    public Dao_version(int choseDB, int choseTable) {
        super(choseDB, choseTable);
    }

    public void Method_1_ChangeDownState(int C_ID) {
        String sql = "update   " + this.tableName + "  set DownState = '是'  where C_ID = " + C_ID;
        MethodIUD(sql);
    }

    public void Method_1_ChangeDownState_byGroupNumber(int GroupNumber) {
        String sql = "update   " + this.tableName + "  set DownState = '是'  where GroupNumber = " + GroupNumber;
        MethodIUD(sql);
    }

    public ArrayList<Object> MethodFind_HaveParam() {
        ArrayList<Object> results = new ArrayList<Object>();
        try {
            String query = "SELECT*FROM " + this.tableName + "  Where  C_haveParam = 'true' ";
            MethodCreateSomeObject();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                Class F = Class.forName(this.beanName);
                //Class F = obj.getClass();
                //实例化
                Object Find = F.newInstance();
                //获取列名
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                //获取列数
                int lieNumber = resultSetMetaData.getColumnCount();
//                System.out.println(lieNumber);
                for (int i = 0; i < lieNumber; i++) {
                    //通过序号获取列名
                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    //获取值
                    Object columnValue = resultSet.getObject(i + 1);
                    //根据列名获取属性.getDeclaredField,获取类中所有的声明字段
                    Field field = F.getDeclaredField(columnName);
                    //可以向私有属性中写值,将private变为public
                    field.setAccessible(true);
                    //写值
                    field.set(Find, columnValue);
                }
                results.add(Find);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return results;
    }

    public ArrayList<Object> MethodFind_HaveParam_FenZhu(int GroupNumber) {
        ArrayList<Object> results = new ArrayList<Object>();
        try {
            String query = "SELECT*FROM " + this.tableName + "  Where  C_haveParam = 'true' AND GroupNumber = " + GroupNumber;
            MethodCreateSomeObject();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                Class F = Class.forName(this.beanName);
                //Class F = obj.getClass();
                //实例化
                Object Find = F.newInstance();
                //获取列名
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                //获取列数
                int lieNumber = resultSetMetaData.getColumnCount();
//                System.out.println(lieNumber);
                for (int i = 0; i < lieNumber; i++) {
                    //通过序号获取列名
                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    //获取值
                    Object columnValue = resultSet.getObject(i + 1);
                    //根据列名获取属性.getDeclaredField,获取类中所有的声明字段
                    Field field = F.getDeclaredField(columnName);
                    //可以向私有属性中写值,将private变为public
                    field.setAccessible(true);
                    //写值
                    field.set(Find, columnValue);
                }
                results.add(Find);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return results;
    }


    //    对拥有配置信息的数据进行分组,10个一组
    public void Method_fenzu() {
        String sql = "WITH GroupedRows AS ( SELECT C_ID,(C_ID - 1)/10+1 AS GroupNumber FROM " + this.tableName + " where C_haveParam = 'true')  UPDATE " + this.tableName + "  SET GroupNumber = GroupedRows.GroupNumber FROM GroupedRows  WHERE " + this.tableName + ".C_ID = GroupedRows.C_ID;";

        MethodIUD(sql);
    }


}
