package org.example.model;

import org.example.service.CourseService;

import java.util.ArrayList;
import java.util.List;

//CourseBusiness = SUT - System (Method) Under Test
public class CourseBusiness {

  // CourseService is a dependency
  private CourseService courseService;

  public CourseBusiness(CourseService courseService) {
    this.courseService = courseService;
  }

  public List<String> retriveCoursesRelatedToSpring(String student) {
    var filteredCourses = new ArrayList<String>();
    var allCourses = courseService.retrieveCourses(student);
    for (String course : allCourses) {
      if (course.contains("Spring")) {
        filteredCourses.add(course);
      }
    }

    return filteredCourses;
  }
}
