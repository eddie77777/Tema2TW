package com.example.tema2_servlet.utils;

import com.example.tema2_servlet.database.model.CourseInfo;

import java.util.Comparator;
import java.util.List;

public final class SortUtils {
    public static List<CourseInfo> sortByName(List<CourseInfo> students){
        students.sort(Comparator.comparing(a -> a.getStudent().getName()));
        return students;
    }



}
