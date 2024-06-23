package learn.juc;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的几种方式
 */
public class NewThread {

    //java 本身默认就是多线程的，我们可以打印看一下启动一个main方法一共启动了哪些线程
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //java 虚拟机线程管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //只获取线程的堆栈信息，不获取同步的monitors和synchronizer信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo: threadInfos) {
            /**
             1: main //主线程，程序入口
             2: Reference Handler //清除引用的线程
             3: Finalizer //调用对象finalizer方法的线程
             4: Signal Dispatcher //分发处理给JVM信号的线程 负责接收和处理来自操作系统的信号，例如用户中断（Ctrl+C）
             5: Attach Listener //内存dump 线程dump 类统计信息，获取系统属性等信息的线程
             13: Common-Cleaner //java 9 引入的清理线程
                【从Java 9开始引入了Cleaner机制作为java.lang.ref.PhantomReference和java.lang.ref.ReferenceQueue的替代方案，
                它负责执行与之关联的Cleaner任务。当一个对象变为不可达且被垃圾回收器标记为可清理时，与之关联的Cleaner动作将会被安排到
                "Common-Cleaner"线程或其他类似的清理线程上执行，从而释放或清理资源。】
             14: Monitor Ctrl-Break //监控 Ctrl-Break 中断信号的线程
             15: Notification Thread //监听与分发通知
             */
//            System.out.println(threadInfo.getThreadId() +": "+ threadInfo.getThreadName());
        }

        /**
         线程的创建与启动
         1. 继承Thread类，重写run方法，创建Thread对象，调用start方法启动线程
         2. 实现Runnable接口，重写run方法，创建Thread对象，调用start方法启动线程
         3. 使用Callable接口，创建FutureTask对象，创建Thread对象，调用start方法启动线程
         4. 使用线程池，创建线程池，创建Thread对象，调用start方法启动线程
         */
        //方式一：使用或者继承Thread类
        //创建线程对象
        Thread thread1 = new Thread() {
            public void run(){
                //线程执行体--执行任务
                System.out.println("使用Thread类-1");
            }
        };
        //启动线程
        thread1.start();

        //构造方法指定线程name，推荐
        Thread thread2 = new Thread("thread2") {
            @Override
            public void run(){
                //线程执行体--执行任务
                System.out.println("使用Thread类-2");
            }
        };
        thread2.start();

        //java8之后 使用lambda 表达式精简创建线程
        Thread thread = new Thread(() -> System.out.println("使用Thread类-3"), "方法1");
        thread.start();

        //方法2：实现Runnable接口--name可省略
        Runnable runnable1 = new Runnable() {
            @Override
            public void run(){
                //线程执行体--执行任务
                System.out.println("实现Runnable接口-1");
            }
        };
        //创建线程对象--推荐
        Thread thread3 = new Thread(runnable1, "方法2");
        thread3.start();

        //使用lambda 表达式精简创建线程
        Runnable runnable = () -> System.out.println("实现Runnable接口-2");
        Thread thread4 = new Thread(runnable, "方法2");
        thread4.start();

        //方式3：使用Callable接口
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println("使用Callable接口-1");
            return 1;
        });
        //启动线程
        new Thread(futureTask,"future1").start();
        System.out.println("执行结果是："+ futureTask.get());
    }


}
