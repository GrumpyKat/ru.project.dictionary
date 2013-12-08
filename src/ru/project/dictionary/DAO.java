package ru.project.dictionary;

import java.io.File;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class DAO 
{
    static Serializer serializer = new Persister();
    static File resultEng = new File("dictionaryEng.xml");
    static File resultRus = new File("dictionaryRus.xml");
    
    public static void doSerialize(int type, Object dict) throws Exception
    {
        if (type == 0)
            serializer.write(dict, resultRus);
        else if (type == 1)
            serializer.write(dict, resultEng);
    }
}