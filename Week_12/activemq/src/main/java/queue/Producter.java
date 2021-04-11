package queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description:
 * @Author: sunzhiyu
 * @CreateDate: 2021/4/11 22:16
 */
public class Producter {
    public static void main(String[] args) throws JMSException {
        /*
         * 实现步骤
         * 1.建立ConnectionFactory工厂对象，需要填入用户名、密码、连接地址（一般使用默认，如果没有修改的话）
         * 2.通过ConnectionFactory对象创建一个Connection连接，并且调用Connection的start方法开启连接，Connection方法默认是关闭的
         * 3.通过Connection对象创建Session会话（上下文环境对象），用于接收消息，参数1是是否启用事物，参数2是签收模式，一般设置为自动签收
         * 4.通过Session对象创建Destination对象，指的是一个客户端用来制定生产消息目标和消费消息来源的对象。在PTP的模式中，Destination被称作队列，在Pub/Sub模式中，Destination被称作主题（Topic）
         * 5.通过Session对象创建消息的发送和接收对象（生产者和消费者）
         * 6.通过MessageProducer的setDeliverMode方法为其设置持久化或者非持久化特性
         * 7.使用JMS规范的TextMessage形式创建数据（通过Session对象），并用MessageProducer的send方法发送数据。客户端同理。记得关闭
         */
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin",
                "admin","tcp://127.0.0.1:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue");
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        for (int i=0;i<=5;i++) {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("我是第"+i+"消息");
            producer.send(textMessage);
        }
        if(connection!=null){
            connection.close();
        }
    }
}
