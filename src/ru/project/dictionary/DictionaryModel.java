package ru.project.dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

@Root(name="dictionary")
public class DictionaryModel
{
    @ElementMap(entry="word", key="name", attribute=true, inline=true)
    public  HashMap<String, Object> dict = new HashMap<>();
    
    public Object getKey(String key)
    {
        return dict.get(key);
    }
    
    public void putKey(String key, Object word)
    {
        dict.put(key, word);
    }
    
    public boolean containKey(String key)
    {
        return dict.containsKey(key);
    }
    
    public Set<Map.Entry<String, Object>> showEntrySet()
    {
        Set<Map.Entry<String, Object>> set = dict.entrySet();
        return set;
    }
}
