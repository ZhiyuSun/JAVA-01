package com.beanfactory.xml;

import com.beanfactory.clazz.Student;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactoryByXml {

    // bean容器
    public final static Map<String, Object> IOC_MAP = new HashMap<>();

    public void intiIoc() {
        try {
            InputStream inputStream = BeanFactoryByXml.class.getClassLoader().getResourceAsStream("xmlContext.xml");
            Document document = new SAXReader().read(inputStream);
            Element rootElement = document.getRootElement();

            List<Element> elements = rootElement.elements("bean");
            if (elements == null || elements.size() == 0) {
                throw new RuntimeException("无bean标签");
            }

            for (Element element : elements) {
                // <bean id="studentA" class="com.beanfactory.clazz.Student">
                String id = element.attributeValue("id");
                String clazz = element.attributeValue("class");
                // // 反射创建实例
                Class c = Class.forName(clazz);
                Object o = c.newInstance();
                Method[] methods = c.getMethods();
                HashMap<String, Method> methodMap = new HashMap<>();
                for (Method method : methods) {
                    methodMap.put(method.getName(), method);
                }

                HashMap<String, Field> fieldMap = new HashMap<>();
                Field[] fields = c.getDeclaredFields();
                for (Field field : fields) {
                    fieldMap.put(field.getName(), field);
                }
                // <property name="id" value="123"/>
                // <property name="name" value="AAA"/>
                List<Element> propertys = element.elements("property");
                if (propertys != null && propertys.size() > 0) {
                    for (Element property : propertys) {
                        String propertyName = property.attributeValue("name");
                        Object propertyValue = property.attributeValue("value");
                        String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                        Method method = methodMap.get(methodName);

                        if (fieldMap.get(propertyName).getGenericType().toString().equals("class java.lang.Integer") ||
                                fieldMap.get(propertyName).getGenericType().toString().equals("int")   ) {
                            method.invoke(o, Integer.valueOf((String) propertyValue));
                        }
                        if (fieldMap.get(propertyName).getGenericType().toString().equals("class java.lang.String")) {
                            method.invoke(o, (String) propertyValue);
                        }
                    }
                }
                IOC_MAP.put(id, o);
            }
        } catch (DocumentException | IllegalAccessException | ClassNotFoundException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String name) {
        return IOC_MAP.get(name);
    }

}
