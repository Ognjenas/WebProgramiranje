package storage;

import beans.sportfacility.Comment;
import beans.sportfacility.CommentStatus;
import beans.sportfacility.SportFacility;
import beans.users.Customer;
import beans.users.Subscription;
import beans.users.User;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CommentStorage {
    private static CommentStorage instance = null;

    Gson gson = new GsonBuilder()
            .setExclusionStrategies(new CommentStorage.CommentExcluder())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static CommentStorage getInstance() {
        if (instance == null) {
            instance = new CommentStorage();
        }
        return instance;
    }

    private CommentStorage() {
    }

    public Comment getById(int id) {
        List<Comment> comments= getAll();
        for (Comment comment:comments) {
            if(comment.getId()==id) return comment;
        }
        return null;
    }

    private int getCount(){
        List<Comment> comments= getAll();
        return comments.size();
    }

    public Comment add(Comment comment) {
        List<Comment> comments = getAll();
        comment.setId(getCount()+1);
        try(FileWriter writer =new FileWriter("./storage/comments.json")){
            comments.add(comment);
            gson.toJson(comments, writer);
            return comment;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void edit(Comment commnet) {
        List<Comment> comments = getAll();
        for(Comment com:comments) {
            if(com.getId() == commnet.getId()) {
                com.setFacility(commnet.getFacility());
                com.setCustomer(commnet.getCustomer());
                com.setStatus(commnet.getStatus());
                com.setText(commnet.getText());
                com.setGrade(commnet.getGrade());
                break;
            }
        }
        save(comments);
    }


    public List<Comment> getAllSubmitted() {
        List<Comment> comments = new ArrayList<>();
        for (Comment comment : getAll()) {
            if(comment.getStatus()== CommentStatus.SUBMITTED) comments.add(comment);
        }
        return comments;
    }

    public List<Comment> getAllConfirmed(int id) {
        List<Comment> comments = new ArrayList<>();
        for (Comment comment : getAll()) {
            if(comment.getStatus()== CommentStatus.CONFIRMED && comment.getFacility().getId()==id) comments.add(comment);
        }
        return comments;
    }

    public List<Comment> getAllConfirmedAndRejectedAdmin() {
        List<Comment> comments = new ArrayList<>();
        for (Comment comment : getAll()) {
            if(comment.getStatus()== CommentStatus.CONFIRMED || comment.getStatus()== CommentStatus.REJECTED) comments.add(comment);
        }
        return comments;
    }

    public List<Comment> getAllConfirmedAndRejectedManager(int id) {
        List<Comment> comments = new ArrayList<>();
        for (Comment comment : getAll()) {
            if((comment.getStatus()== CommentStatus.CONFIRMED || comment.getStatus()== CommentStatus.REJECTED)
            && comment.getFacility().getId()==id) comments.add(comment);
        }
        return comments;
    }

    public double getAverageGradeForFacility(int id) {
        double averageGrade=0;
        double numberFacilities=0;

        for (Comment comment : getAll()) {
            if(comment.getFacility().getId()==id){
                averageGrade+=comment.getGrade();
                numberFacilities++;
            }
        }
        if(numberFacilities!=0) averageGrade=averageGrade/numberFacilities;
        return averageGrade;
    }


    private void save(List<Comment> comments) {
        try(FileWriter writer =new FileWriter("./storage/comments.json")){
            gson.toJson(comments, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Comment> getAll() {
        List<Comment> allComments = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/comments.json"));
            allComments = gson.fromJson(reader, new TypeToken<List<Comment>>() {}.getType());
            reader.close();
            return allComments;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class CommentExcluder implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            if (fieldAttributes.getDeclaringClass() == SportFacility.class && !fieldAttributes.getName().equals("id")) {
                return true;
            } else if (fieldAttributes.getDeclaringClass() == Customer.class && !fieldAttributes.getName().equals("id")) {
                return true;
            }else if (fieldAttributes.getDeclaringClass() == User.class && !fieldAttributes.getName().equals("id")) {
                return true;
            }else{
                return false;
            }
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }

}
