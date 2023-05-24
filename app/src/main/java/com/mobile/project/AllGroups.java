package com.mobile.project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobile.project.pojo.Group;
import com.mobile.project.pojo.Subject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

public class AllGroups {
    private static AllGroups instance = new AllGroups();
    public static AllGroups getInstance() {
        return instance;
    }
    Logger log = Logger.getLogger(AllGroups.class.getName());
    public List<Group> displayedGroupsList = new ArrayList<>();
    public Group chosenGroup;
    private List<Group> groups;

    private AllGroups() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                update("https://2a30-212-16-19-2.ngrok-free.app");
            }
        }).start();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private String getContent(String path) throws IOException {
        BufferedReader reader=null;
        InputStream stream = null;
        HttpsURLConnection connection = null;
        try {
            URL url=new URL(path);
            connection =(HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
            stream = connection.getInputStream();
            reader= new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf=new StringBuilder();
            String line;
            while ((line=reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            return(buf.toString());
        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    private String getHTML(String urlToRead){
        StringBuilder result = new StringBuilder();
        try{
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_16))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }

        } catch (IOException e) {
            log.warning("error in getHtml");
            e.printStackTrace();
        }

        return result.toString();
    }
    private void manySubjectToOne(List<Group> orig){
        for (int i = 0; i < orig.size(); i++) {
            List<Integer> toDelete = new ArrayList<>();
            for (int j = 0; j < orig.get(i).subjects.size(); j++) {
                for (int k = 0; k < orig.get(i).subjects.get(j).name.length(); k++) {
                    if(orig.get(i).subjects.get(j).name.charAt(k) == '\n'){
                        String[] splitted = orig.get(i).subjects.get(j).name.split("\n");
                        for(String split:splitted){
                            Subject newSubj = new Subject();
                            newSubj.name = split;
                            newSubj.time = orig.get(i).subjects.get(j).time;
                            newSubj.day_of_week = orig.get(i).subjects.get(j).day_of_week;
                            orig.get(i).subjects.add(newSubj);
                        }
                        toDelete.add(j);
                    }
                }
            }
            for(Integer index:toDelete){
                orig.get(i).subjects.remove((int)index);
            }
        }
    }
    private void subjectParse(List<Group> orig){
        for(Group group : orig){
            for(Subject subject : group.subjects){
                subject.name = subject.name.replaceAll(",","");
                String[] splitted = subject.name.split(" ");
                List<String> date = new ArrayList<>();
                for (int i = 0; i < splitted.length; i++) {
                    if(splitted[i].equals("(пр.)")|splitted[i].equals("(лк.)")){
                        subject.type = splitted[i].substring(1,3);
                        String newName = "";
                        for (int j = 0; j < i; j++) {
                            newName += splitted[j] + " ";
                        }
                        newName = newName.trim();
                        subject.name = newName;
                        String teacherName = splitted[splitted.length-5] +" "+ splitted[splitted.length-4] +splitted[splitted.length-3];
                        teacherName = teacherName.trim();
                        subject.teacher = teacherName;
                    }
                    if(splitted[i].contains(".")&splitted[i].length()==5&!splitted[i].equals("(пр.)")&!splitted[i].equals("(лк.)")&!splitted[i].equals("Проф.")){
                        date.add(splitted[i]);
                    }
                }
                subject.daysMonth = date;
                subject.room = splitted[splitted.length-1];
                String[] splittedTime = subject.time.split(" ");
                subject.timeStart = splittedTime[0];
                subject.timeEnd = splittedTime[splittedTime.length - 1];
            }
        }
    }
    public void update(String url){
        String jsonText = getHTML(url);
        System.out.println(jsonText);
        groups = new Gson().fromJson(jsonText,new TypeToken<List<Group>>(){}.getType());
        System.out.println(groups);
        for(Group group : groups){
            System.out.println(group.name);
        }
        manySubjectToOne(groups);
        subjectParse(groups);
    }
    public List<Group> getGroups(){
        return groups;
    }
}
