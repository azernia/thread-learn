package com.rui.pattern.sync;

import com.rui.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * description: 保护性暂停扩展
 * <hr/>
 * date: 2022/10/31
 *
 * @author rui
 */
public class GuardedSuspensionExtension {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        Sleeper.sleep(1);
        MailBoxes.getIds().forEach(id -> new Postman(id, "内容：" + id).start());
    }
}

@Slf4j(topic = "rui.People")
class People extends Thread {
    @Override
    public void run() {
        GuardedObjV3 guardedObj = MailBoxes.createGuardedObj();
        log.debug("开始收信 id: {}", guardedObj.getId());
        Object mail = guardedObj.get(5000);
        log.debug("收到信 id: {}, 内容: {}", guardedObj.getId(), mail);
    }
}

@Slf4j(topic = "rui.Postman")
class Postman extends Thread {

    private int mailId;

    private String content;

    public Postman(int mailId, String content) {
        this.mailId = mailId;
        this.content = content;
    }

    @Override
    public void run() {
        GuardedObjV3 guardedObj = MailBoxes.getGuardedObj(mailId);
        log.debug("开始送信 id: {}, 内容: {}", guardedObj.getId(), content);
        guardedObj.complete(content);
        MailBoxes.removeGuardedObj(mailId);
    }
}

class MailBoxes {

    private static int id = 1;

    private static final Map<Integer, GuardedObjV3> boxes = new Hashtable<>();   // 保证线程安全

    private MailBoxes() {}

    /**
     * 生成唯一id
     *
     * @return int
     */
    private static synchronized int generateUniqueId() {
        return id++;
    }

    public static GuardedObjV3 createGuardedObj() {
        GuardedObjV3 guardedObjV3 = new GuardedObjV3(generateUniqueId());
        boxes.put(guardedObjV3.getId(), guardedObjV3);
        return guardedObjV3;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }

    public static GuardedObjV3 getGuardedObj(int id) {
        return boxes.get(id);
    }

    public static void removeGuardedObj(int id) {
        boxes.remove(id);
    }
}

class GuardedObjV3 {
    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 结果
     */
    private Object response;

    public GuardedObjV3(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    /**
     * 获取结果
     * @param timeout 等待最大时间
     * @return object
     */
    public Object get(long timeout) {
        synchronized (this) {
            // 开始时间
            long begin = System.currentTimeMillis();
            long passedTime = 0L;
            while (response == null) {
                long waitTime = timeout - passedTime;
                if (waitTime <= 0) {
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 求得经历时间
                passedTime = System.currentTimeMillis() - begin;
            }
            return this.response;
        }
    }

    /**
     * 产生结果
     */
    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }
}
