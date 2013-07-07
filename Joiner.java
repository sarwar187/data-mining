/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package apriori;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 *
 * @author sarwar
 */
public class Joiner {

    public ArrayList<String> joinLength2(HashMap<Integer,Integer> mp){
        Map<Integer,Integer> hm = new TreeMap<Integer,Integer>(mp);
        mp.clear();
        Object[] ob = hm.keySet().toArray();
        String temp[] = new String[ob.length];
        for(int i=0;i<ob.length;i++)temp[i]= ob[i].toString();
        ArrayList<String> str = new ArrayList<String>();
        for(int i=0;i<temp.length;i++)for(int j=i+1;j<temp.length;j++)str.add(temp[i] + " " + temp[j]);
        return str;
     }

    public ArrayList<String> join(HashMap<String,Integer> hm,Database db){
        ArrayList<String> str = new ArrayList<String>();
        Object[] ob = hm.keySet().toArray();
        String temp[] = new String[ob.length];
        for(int i=0;i<ob.length;i++)temp[i]= ob[i].toString();
        for(int i=0;i<temp.length;i++)
            for(int j=0;j<temp.length;j++){
                String str1 = temp[i].substring(0,temp[i].lastIndexOf(' '));
                String str2 = temp[j].substring(0,temp[j].lastIndexOf(' '));
                Integer int1 = Integer.parseInt(temp[i].substring(temp[i].lastIndexOf(' ')+1,temp[i].length()));
                Integer int2 = Integer.parseInt(temp[j].substring(temp[j].lastIndexOf(' ')+1,temp[j].length()));
                if(str1.equals(str2)&&(int1<int2)){
                    String str3 = str1 + " " + temp[i].substring(temp[i].lastIndexOf(' ')+1,temp[i].length()) + " " + temp[j].substring(temp[j].lastIndexOf(' ')+1,temp[j].length());
                    ArrayList<String> tempStr = subset(str3);
                    int flag=0;
                    for(int k=0;k<tempStr.size();k++) if(hm.containsKey(tempStr.get(k))) if(hm.get(tempStr.get(k))<Math.ceil(db.getMin_sup()))flag=1;
                    if(flag==0)str.add(str3);
                }
            }
        return str;
    }
    public ArrayList<String> subset(String str){
        ArrayList<String> strl = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(str);
        while(st.hasMoreTokens())temp.add(st.nextToken());
        for(int i=0;i<temp.size();i++){
            String tempStr="";
            for(int j=0;j<temp.size();j++){
                if(j==i)continue;
                tempStr+=(temp.get(j)+ " ");
            }
            tempStr.trim();
            strl.add(tempStr);
        }
        return strl;
    }
}
