package ru.project.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Dictionary 
{   
    public static HashMap<String, Object> dictRuEng = new HashMap<>();
    public static HashMap<String, Object> dictEngRu = new HashMap<>();
    
    public void getAllWordsToSee(HashMap<String, Object> srcHashMap)
    {
        Set<Map.Entry<String, Object>> set = srcHashMap.entrySet();
        for (Map.Entry<String, Object> me : set)
        {
            System.out.println(me.getKey() + ": " + me.getValue());
        }
    }
    
    public Object getWordFromDict(HashMap<String, Object> srcHashMap, String key)
    {
        return srcHashMap.get(key);
    }
    
    public static void putWordToDict(int dict, String key, Object word)
    {
        if (dict == 0)
        {
            dictRuEng.put(key, word);
        }
        if (dict == 1)
        {
            dictEngRu.put(key, word);
        }
    }
    
    public static boolean isInDict(int dict, String key)
    {
        if (dict == 0)
            return dictRuEng.containsKey(key);
        else if (dict == 1)
            return dictEngRu.containsKey(key);
        else 
        {
            System.out.println("I don't know how you came here");
            return false;
        }
    }
    
    public void sortKeys(HashMap<String, Object> srcHashMap)
    {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        Set<Map.Entry<String, Object>> set = srcHashMap.entrySet();
        for (Map.Entry<String, Object> mapEntry : set)
        {
            treeMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        System.out.println(treeMap);
    }
    
    public static void main(String[] args) throws IOException
    {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader inBuf = new BufferedReader(in);
        String buf;
        View newView = new View();
        
        while(true)
        {
            buf = inBuf.readLine();
            newView.keyWords(buf);
        }
    }
}
