package ru.project.dictionary;

import org.simpleframework.xml.Element;

public class Word 
{
    String name = " ";
    @Element
    String translation = " ";
    @Element
    String transcription = " ";
    
    public String getName()
    {
        return name;
    }
    
    public String getTranslation()
    {
        return translation;
    }
    
    public String getTranscription()
    {
        return transcription;
    }
    
    public void setName(String aName)
    {
        name = aName;
    }
    
    public void setTranslation(String aTranslation)
    {
        translation = aTranslation;
    }
    
    public void setTranscription(String aTranscription)
    {
        transcription = aTranscription;
    }
    
    @Override
    public String toString()
    {
        return "[" + transcription + "] " + translation;
    }
}
