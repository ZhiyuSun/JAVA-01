package queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description:
 * @Author: sunzhiyu
 * @CreateDate: 2021/4/11 22:16
 */
public class Consumer {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin",
                "admin","tcp://127.0.0.1:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue");
        MessageConsumer consumer = session.createConsumer(destination);
        while (true){
            TextMessage message = (TextMessage) consumer.receive();
            if (message==null){
                break;
            }
            System.out.println(message.getText());
        }
        if(connection!=null){
            connection.close();
        }
    }
}
