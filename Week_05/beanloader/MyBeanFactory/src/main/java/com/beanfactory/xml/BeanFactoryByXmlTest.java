package com.beanfactory.xml;

import com.beanfactory.clazz.ISchool;
import com.beanfactory.clazz.Klass;
import com.beanfactory.clazz.Student;

public class BeanFactoryByXmlTest {

    public static void main(String[] args) {

        BeanFactoryByXml context = new BeanFactoryByXml();
        context.intiIoc();

        Student studentA = (Student) context.getBean("studentA");
        System.out.println(studentA.toString());

        Student studentB = (Student) context.getBean("studentB");
        System.out.println(studentB.toString());

        // ISchool school = context.getBean(ISchool.class);
        // school.ding();
        // System.out.println(school.getClass());

        // klass实例
        // Klass klass = (Klass) context.getBean("klass");
        // for (Student student : klass.students) {
        //     System.out.println(student.toString());
        // }
        // klass.school.ding();
    }
}
