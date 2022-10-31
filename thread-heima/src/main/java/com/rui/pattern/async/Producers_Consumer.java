package com.rui.pattern.async;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * description: 异步模式之生产者消费者
 * <hr/>
 * date: 2022/10/31
 *
 * @author rui
 */
@Slf4j(topic = "rui.Producers_Consumer")
public class Producers_Consumer {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                queue.put(new Message(id, "value:" + id));
            }, "生产者" + i).start();
        }
        new Thread(() -> {
            while (true) {
                Sleeper.sleep(1);
                queue.take();
            }
        }, "消费者").start();
    }
}

/**
 * 消息队列 线程间通信
 * RabbitMQ 进程间通信
 */
@Slf4j(topic = "rui.MessageQueue")
class MessageQueue {

    /**
     * 队列结合
     */
    private final LinkedList<Message> list = new LinkedList<>();

    /**
     * 队列容量
     */
    private final int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取消息
     */
    public Message take() {
        // 检查队列是否为空
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    log.debug("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 从队列头部获取
            Message message = list.removeFirst();
            log.debug("已消费消息：{}", message);
            list.notifyAll();
            return message;
        }
    }

    /**
     * 存入消息
     */
    public void put(Message message) {
        synchronized (list) {
            // 检查队列是否已满
            while (list.size() == capacity) {
                try {
                    log.debug("队列已满，生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(message);
            log.debug("已生产消息：{}", message);
            list.notifyAll();
        }
    }

}

@Slf4j(topic = "rui.Message")
final class Message {
    private final Integer id;

    private final Object object;

    public Message(Integer id, Object object) {
        this.id = id;
        this.object = object;
    }

    public Integer getId() {
        return id;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", object=" + object +
                '}';
    }
}
