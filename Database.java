/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author sarwar
 */
public class Database {
    String outputFile, fileName;
    private int tCount = 0;
    private double min_sup;
    Writer output;

    public Database(String outputFile, Double min_sup, String fileName) throws IOException {
        this.outputFile = outputFile;
        this.min_sup = min_sup;
        output = new BufferedWriter(new FileWriter(outputFile));
        this.fileName = fileName;
    }

    public void close() throws IOException {
        output.close();
    }
    public int gettCount() {return tCount;}
    public void settCount(int tCount) {this.tCount = tCount;}
    public double getMin_sup() {return min_sup;}
    public void setMin_sup(double min_sup){this.min_sup = min_sup;}
    public HashMap<Integer, Integer> init() {
        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String transaction = scanner.nextLine();
                transaction = transaction.trim();
                StringTokenizer st = new StringTokenizer(transaction);
                settCount(gettCount() + 1);
                while (st.hasMoreTokens()) {
                    Integer item = Integer.parseInt(st.nextToken());
                    if (!hm.containsKey(item))hm.put(item, (Integer) 1);
                    else hm.put(item, hm.get(item) + 1);
                }
            }
            min_sup = tCount * (min_sup / 100);
            Integer[] unused = new Integer[hm.size()];
            int unusedIndex = 0;
            scanner.close();
            Iterator iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                Integer key = Integer.parseInt(iterator.next().toString());
                Integer value = hm.get(key);
                if (value >= Math.ceil(min_sup)) {
                    output.write(key + " : " + value + "\n");
                    //System.out.println(key + " : " + value);
                } else {
                    unused[unusedIndex++] = key;
                }
            }
            for (int i = 0; i < unusedIndex; i++)hm.remove(unused[i]);
            //close();
        } catch (Exception e) {
            System.out.println("Error in init() function " + e.getMessage());
        }
        return hm;
    }

    public HashMap<String, Integer> search(ArrayList<String> str) throws FileNotFoundException, IOException {
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String transaction = scanner.nextLine();
            transaction = transaction.trim();
            StringTokenizer st = new StringTokenizer(transaction);
            String strData[] = new String[500];
            int index = 0;
            while (st.hasMoreTokens())strData[index++] = st.nextToken();

            for (int i = 0; i < str.size(); i++) {
                String sample = str.get(i);
                StringTokenizer stTemp = new StringTokenizer(sample);
                String strCheck[] = new String[500];
                int indexCheck = 0, flag, flag1 = 0;
                while (stTemp.hasMoreTokens())strCheck[indexCheck++] = stTemp.nextToken();
                for (int m = 0; m < indexCheck; m++) {
                    flag = 0;
                    for (int n = 0; n < strData.length; n++)if (strCheck[m].equals(strData[n]))flag = 1;
                    if (flag == 0) {
                        flag1 = 1;
                        break;
                    }
                }
                if (flag1 == 0) {
                    if (!hm.containsKey(sample))hm.put(sample, 1);
                    else hm.put(sample, hm.get(sample) + 1);
                }
            }
        }
        HashMap<String, Integer> newHash = new HashMap<String, Integer>();
        for (int i = 0; i < str.size(); i++) {
            String string = str.get(i);
            if (hm.containsKey(string)) {
                Integer value = hm.get(string);
                if (value >= Math.ceil(min_sup)) {
                    if (!newHash.containsKey(string))newHash.put(string, 1);
                    else newHash.put(string, value + 1);
                    output.write(string + " : " + value + "\n");
                    //System.out.println(string + " : " + value);
                }
            }
        }
        return newHash;
    }
}
