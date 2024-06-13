package org.example.business;

import org.example.service.CourseService;

import java.util.ArrayList;
import java.util.List;

//CourseBusiness = SUT - System (Method) Under Test
public class CourseBusiness {

  // CourseService is a dependency
  private final CourseService courseService;

  public CourseBusiness(CourseService courseService) {
    this.courseService = courseService;
  }

  public List<String> retrieveCoursesRelatedToSpring(String student) {
    var filteredCourses = new ArrayList<String>();

    if("Foo Bar".equals(student)) {
      return filteredCourses;
    }

    var allCourses = courseService.retrieveCourses(student);
    for (String course : allCourses) {
      if (course.contains("Spring")) {
        filteredCourses.add(course);
      }
    }

    return filteredCourses;
  }

  public void deleteCoursesNotRelatedToSpring(String student) {

    var allCourses = courseService.retrieveCourses(student);

    for (String course : allCourses) {
      if (!course.toLowerCase().contains("spring")) {
        courseService.deleteCourse(course);
      }
    }
  }
}
