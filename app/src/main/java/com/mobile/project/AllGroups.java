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
        String jsonText = "[{\"name\": \"РЛ-221\", \"row\": 5, \"column\": 2, \"subgroups\": [{\"name\": \"РЛ-221\", \"row\": 6, \"column\": 2, \"cell_size\": 2}, {\"name\": \"РЛ-222\", \"row\": 6, \"column\": 4, \"cell_size\": 2}], \"subgroup_names\": [\"РЛ-221\", \"РЛ-222\"], \"cell_size\": 4, \"subjects\": [{\"name\": \"Общеуниверситетские элективные модули\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Пн.\", \"subgroup_name\": null}, {\"name\": \"Лексикология (лк.) 14.02, 28.02, 14.03, 28.03 (4/9) Проф. И.В. Якушевич. Ауд. 3205\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Лексикология (лк.) 14.02, 28.02, 14.03, 28.03, 11.04 (5/9) Проф. И.В. Якушевич. Ауд. 3205\", \"time\": \"10.30 - 11.50\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"История русской литературы 18 века (пр.) 21.02, 07.03, 21.03, 04.04,  (4/9) Асс. Ю.Ю. Гребенщиков. Ауд. 3504\", \"time\": \"10.30 - 11.50\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Лексикология (пр.) 14.02, 28.02, 14.03, 28.03, 11.04 (5/9)  Проф. И.В. Якушевич. Ауд. 3215\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"История русской литературы 18 века (пр.) 21.02, 07.03, 21.03, 04.04, 18.04 (5/9) Асс. Ю.Ю. Гребенщиков. Ауд. 3504\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Лексикология (пр.) 14.02, 28.02, 14.03, 28.03 (4/9)  Проф. И.В. Якушевич. Ауд. 3215\", \"time\": \"14.10 - 15.30\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Анализ и интерпретация литературного произведения (лк.) 15.02, 01.03, 15.03, 29.03, 12.04, 26.04, 10.05, 24.05, 07.06 (9/9) Проф. С.А. Васильев. Ауд. 3205 \\nАнализ и интерпретация литературного произведения (пр.) 21.06 (1/9) для группы РЛ-222 Проф. С.А. Васильев. Ауд. 3205\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Ср.\", \"subgroup_name\": null}]}, {\"name\": \"РЛ-222\", \"row\": 5, \"column\": 6, \"subgroups\": [{\"name\": \"РЛ-223\", \"row\": 6, \"column\": 6, \"cell_size\": 2}, {\"name\": \"РЛ-224\", \"row\": 6, \"column\": 8, \"cell_size\": 2}], \"subgroup_names\": [\"РЛ-223\", \"РЛ-224\"], \"cell_size\": 4, \"subjects\": [{\"name\": \"Общеуниверситетские элективные модули\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Пн.\", \"subgroup_name\": null}, {\"name\": \"Лексикология (лк.) 14.02, 28.02, 14.03, 28.03 (4/9) Проф. И.В. Якушевич. Ауд. 3205\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Лексикология (лк.) 14.02, 28.02, 14.03, 28.03, 11.04 (5/9) Проф. И.В. Якушевич. Ауд. 3205\", \"time\": \"10.30 - 11.50\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Анализ и интерпретация литературного произведения (лк.) 15.02, 01.03, 15.03, 29.03, 12.04, 26.04, 10.05, 24.05, 07.06 (9/9) Проф. С.А. Васильев. Ауд. 3205 \\nАнализ и интерпретация литературного произведения (пр.) 21.06 (1/9) для группы РЛ-222 Проф. С.А. Васильев. Ауд. 3205\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Ср.\", \"subgroup_name\": null}, {\"name\": \"Лексикология (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05 (7/9) Доц. Е.Н. Абрашина. Ауд. 3307\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Лексикология (пр.) 25.04,23.05 (2/9) Доц. Е.Н. Абрашина. Ауд. 3307\", \"time\": \"14.10 - 15.30\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Английский язык (пр.) 21.02, 07.03, 21.03, 04.04, 18.04, 02.05, 16.05 (7/15) Ст.преп. А.В. Останина. Ауд. 3506\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-224\"}, {\"name\": \"Английский язык (пр.) 21.02, 07.03, 21.03, 04.04, 18.04, 02.05, 16.05, 30.05 (8/15) Ст.преп. А.В. Останина. Ауд. 3506\", \"time\": \"10.30 - 11.50\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-224\"}, {\"name\": \"Латинский язык (пр.) 07.03, 21.03, 04.04, 18.04, 02.05, 16.05, 30.05, 13.06 (9/36) Доц. А.А. Качанова. Ауд. 3406\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-224\"}, {\"name\": \"Латинский язык (пр.) 07.03, 21.03, 04.04, 18.04, 02.05, 16.05, 30.05, 13.06 (9/36) Доц. А.А. Качанова. Ауд. 3406\", \"time\": \"14.10 - 15.30\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-224\"}]}, {\"name\": \"РЛ-223\", \"row\": 5, \"column\": 10, \"subgroups\": [{\"name\": \"РЛ-225\", \"row\": 6, \"column\": 10, \"cell_size\": 2}, {\"name\": \"РЛ-226\", \"row\": 6, \"column\": 12, \"cell_size\": 2}], \"subgroup_names\": [\"РЛ-225\", \"РЛ-226\"], \"cell_size\": 4, \"subjects\": [{\"name\": \"Общеуниверситетские элективные модули\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Пн.\", \"subgroup_name\": null}, {\"name\": \"Лексикология (лк.) 14.02, 28.02, 14.03, 28.03 (4/9) Проф. И.В. Якушевич. Ауд. 3205\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Лексикология (лк.) 14.02, 28.02, 14.03, 28.03, 11.04 (5/9) Проф. И.В. Якушевич. Ауд. 3205\", \"time\": \"10.30 - 11.50\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Анализ и интерпретация литературного произведения (лк.) 15.02, 01.03, 15.03, 29.03, 12.04, 26.04, 10.05, 24.05, 07.06 (9/9) Проф. С.А. Васильев. Ауд. 3205 \\nАнализ и интерпретация литературного произведения (пр.) 21.06 (1/9) для группы РЛ-222 Проф. С.А. Васильев. Ауд. 3205\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Ср.\", \"subgroup_name\": null}, {\"name\": \"Лексикология (пр.) 07.03, 21.03, 04.04, 18.04 (4/9)  Проф. И.В. Якушевич. Ауд. 3215\", \"time\": \"10.30 - 11.50\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Латинский язык (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05, 06.06, 20.06 (9/36) Доц. М.В. Захарова. Ауд. 3406\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-225\"}, {\"name\": \"Лексикология (пр.) 07.03, 21.03, 04.04, 18.04, 02.05 (5/9)  Проф. И.В. Якушевич. Ауд. 3215  \\nФ Полевые исследования фольклора (пр.) 21.02 (1/9) Проф. И.Н. Райкова Ауд. 3215\\nИстория русской литературы 18 века (пр.) 02.05, 16.05 (2/9) Асс. Ю.Ю. Гребенщиков. Ауд. 3504\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Латинский язык (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05, 06.06, 20.06 (9/36) Доц. М.В. Захарова. Ауд. 3406\", \"time\": \"14.10 - 15.30\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-225\"}, {\"name\": \"История русской литературы 18 века (пр.) 21.02, 07.03, 21.03, 04.04, 18.04, 02.05, 16.05 (7/9) Асс. Ю.Ю. Гребенщиков. Ауд. 3504\", \"time\": \"14.10 - 15.30\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Латинский язык (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05, 06.06, 20.06 (9/36) Ст.преп. О.В. Кузьмина. Ауд. 3504\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-226\"}, {\"name\": \"Латинский язык (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05, 06.06, 20.06 (9/36) Ст.преп. О.В. Кузьмина. Ауд. 3504\", \"time\": \"14.10 - 15.30\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-226\"}]}, {\"name\": \"РЛ-224\", \"row\": 5, \"column\": 14, \"subgroups\": [{\"name\": \"РЛ-227\", \"row\": 6, \"column\": 14, \"cell_size\": 2}, {\"name\": \"РЛ-228\", \"row\": 6, \"column\": 16, \"cell_size\": 2}, {\"name\": \"РЛ-229\", \"row\": 6, \"column\": 18, \"cell_size\": 2}], \"subgroup_names\": [\"РЛ-227\", \"РЛ-228\", \"РЛ-229\"], \"cell_size\": 6, \"subjects\": [{\"name\": \"Общеуниверситетские элективные модули\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Пн.\", \"subgroup_name\": null}, {\"name\": \"Лексикология (лк.) 14.02, 28.02, 14.03, 28.03 (4/9) Проф. И.В. Якушевич. Ауд. 3205\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Лексикология (лк.) 14.02, 28.02, 14.03, 28.03, 11.04 (5/9) Проф. И.В. Якушевич. Ауд. 3205\", \"time\": \"10.30 - 11.50\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Анализ и интерпретация литературного произведения (лк.) 15.02, 01.03, 15.03, 29.03, 12.04, 26.04, 10.05, 24.05, 07.06 (9/9) Проф. С.А. Васильев. Ауд. 3205 \\nАнализ и интерпретация литературного произведения (пр.) 21.06 (1/9) для группы РЛ-222 Проф. С.А. Васильев. Ауд. 3205\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Ср.\", \"subgroup_name\": null}, {\"name\": \"Английский язык (пр.) 21.02, 07.03, 21.03, 04.04, 18.04, 02.05, 16.05 (7/15) Ст.преп. Н.М. Носов. Ауд. 3405\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-227\"}, {\"name\": \"Английский язык (пр.) 21.02, 07.03, 21.03, 04.04, 18.04, 02.05, 16.05, 30.05 (8/15) Ст.преп. Н.М. Носов. Ауд. 3405\", \"time\": \"10.30 - 11.50\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-227\"}, {\"name\": \"Латинский язык (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05, 06.06, 20.06 (9/36) Доц. А.А. Качанова. Ауд. 3503\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-227\"}, {\"name\": \"Лексикология (пр.) 21.02, ауд. 3308, 07.03, 21.03, 04.04, 18.04, 02.05, 16.05, 30.05 (8/9) Доц. Е.Н. Абрашина. Ауд. 3303\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Латинский язык (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05, 06.06, 20.06 (9/36) Доц. А.А. Качанова. Ауд. 3503\", \"time\": \"14.10 - 15.30\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-227\"}, {\"name\": \"Лексикология (пр.) 30.05 (1/9) Доц. Е.Н. Абрашина. Ауд. 3303\", \"time\": \"14.10 - 15.30\", \"day_of_week\": \"Вт. \", \"subgroup_name\": null}, {\"name\": \"Латинский язык (пр.) 21.02, 07.03, 21.03, 04.04, 18.04, 02.05, 16.05, 30.05, 13.06 (9/36) Доц. М.В. Захарова. Ауд. 3406\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-228\"}, {\"name\": \"Латинский язык (пр.) 21.02, 07.03, 21.03, 04.04, 18.04, 02.05, 16.05, 30.05, 13.06 (9/36) Доц. М.В. Захарова. Ауд. 3406\", \"time\": \"10.30 - 11.50\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-228\"}, {\"name\": \"Английский язык (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05, 06.06 (8/15) Асс.  П.В. Ракова. Ауд. 3603\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-228\"}, {\"name\": \"Английский язык (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05 (7/15) Асс. П.В. Ракова. Ауд. 3603\", \"time\": \"14.10 - 15.30\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-228\"}, {\"name\": \"Латинский язык (пр.) 21.02, 07.03, 21.03, 04.04, 18.04, 02.05, 16.05, 30.05, 13.06 (9/36) Ст.преп. О.В. Кузьмина. Ауд. 3503\", \"time\": \"09.00 - 10.20\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-229\"}, {\"name\": \"Латинский язык (пр.) 21.02, 07.03, 21.03, 04.04, 18.04, 02.05, 16.05, 30.05, 13.06 (9/36) Ст.преп. О.В. Кузьмина. Ауд. 3503\", \"time\": \"10.30 - 11.50\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-229\"}, {\"name\": \"Английский язык (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05, 06.06 (8/15) Асс. А.Ю. Стрелкова. Ауд. 3506\", \"time\": \"12.40 - 14.00\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-229\"}, {\"name\": \"Английский язык (пр.) 14.02, 28.02, 14.03, 28.03, 11.04, 25.04, 23.05 (7/15) Асс. А.Ю. Стрелкова. Ауд. 3506\", \"time\": \"14.10 - 15.30\", \"day_of_week\": \"Вт. \", \"subgroup_name\": \"РЛ-229\"}]}]";
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
