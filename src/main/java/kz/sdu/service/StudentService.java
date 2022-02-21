package kz.sdu.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentService {

    public static boolean studentIDChecking(String studentID) {
        Pattern pattern = Pattern.compile("^[0-9]{9}$");
        Matcher matcher = pattern.matcher(studentID);
        return matcher.find();
    }
}
