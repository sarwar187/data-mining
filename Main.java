/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datamining;

import apriori.Database;
import apriori.Joiner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author sarwar
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Joiner jn = new Joiner();
        //                                   destination                          source
        Database db = new Database("D:\\data mining\\book\\mushroom_pattern.txt",3d,"D:\\data mining\\book\\T10I4D100K.dat");
        HashMap<Integer,Integer> hm = db.init();
        HashMap<String,Integer> mp = new HashMap<String,Integer>();
        ArrayList<String> str = jn.joinLength2(hm);

        while(str.size()>0){
            mp=db.search(str);
            str = jn.join(mp,db);
        }
        db.close();
    }
}
