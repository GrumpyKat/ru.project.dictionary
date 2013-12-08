package ru.project.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException, Exception
    {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader inBuf = new BufferedReader(in);
        String buf;
        View newView = new View();
        DAO.doDeserialize();
        
        while(true)
        {
            buf = inBuf.readLine();
            newView.keyWords(buf);
        }
    }
}
