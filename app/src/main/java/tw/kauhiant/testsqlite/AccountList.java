package tw.kauhiant.testsqlite;

import java.util.ArrayList;

import tw.kauhiant.testsqlite.Account;

/**
 * Created by kauhia on 2018/5/29.
 */

public class AccountList {
    public ArrayList<Account> list;

    public AccountList(){
        list = new ArrayList<Account>();
    }

    public int[] sum(){
        int sumOfIncome = 0;
        int sumOfOutgo  = 0;

        for(int i=0; i<list.size(); ++i){
            if(list.get(i).isIncome)
                sumOfIncome += list.get(i).money;
            else
                sumOfOutgo  += list.get(i).money;
        }

        return new int[]{sumOfIncome,sumOfOutgo};
    }

}
