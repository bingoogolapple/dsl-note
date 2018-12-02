package cn.bingoogolapple.bytecode.forkjoinpool

import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

class ForkJoinPoolTest {

    ForkJoinPoolTest() {
//        testCounting()
//        testFibonacci()
//        testCyclicBarrier()
        testParallelStream()
    }

    // 有耗时操作时 parallelStream 更快
    private static void testParallelStream() {
        List<Integer> list = (0..<1000).asList()

        List<Integer> normalCopyList = new ArrayList<>()
        long startTime = System.currentTimeMillis()
        list.parallelStream().forEach(new Consumer<Integer>() {
            @Override
            void accept(Integer t) {
                Thread.sleep(3)
                normalCopyList.add(t)
            }
        })
        println "list.size = ${list.size()}，normalCopyList.size = ${normalCopyList.size()}，时间为 ${System.currentTimeMillis() - startTime}"

        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>()
        startTime = System.currentTimeMillis()
        list.parallelStream().forEach(new Consumer<Integer>() {
            @Override
            void accept(Integer t) {
                Thread.sleep(3)
                copyOnWriteArrayList.add(t)
            }
        })
        println "list.size = ${list.size()}，copyOnWriteArrayList.size = ${copyOnWriteArrayList.size()}，时间为 ${System.currentTimeMillis() - startTime}"

        AtomicInteger atomicInteger = new AtomicInteger()
        startTime = System.currentTimeMillis()
        list.parallelStream().forEach(new Consumer<Integer>() {
            @Override
            void accept(Integer t) {
                Thread.sleep(3)
                atomicInteger.incrementAndGet()
            }
        })
        println "atomicInteger 为 ${atomicInteger.get()}，时间为 ${System.currentTimeMillis() - startTime}"

        int count = 0
        startTime = System.currentTimeMillis()
        list.forEach(new Consumer<Integer>() {
            @Override
            void accept(Integer t) {
                Thread.sleep(3)
                count++
            }
        })
        println "count 为 ${count}，时间为 ${System.currentTimeMillis() - startTime}"
    }

    private static void testCyclicBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(9, {
            println "全都准备完成1"
        })
        List<Athlete> athleteList = new ArrayList<>()
        athleteList.add(new Athlete(cyclicBarrier, "博尔特"))
        athleteList.add(new Athlete(cyclicBarrier, "鲍威尔"))
        athleteList.add(new Athlete(cyclicBarrier, "盖伊"))
        athleteList.add(new Athlete(cyclicBarrier, "布雷克"))
        athleteList.add(new Athlete(cyclicBarrier, "加特林"))
        athleteList.add(new Athlete(cyclicBarrier, "苏炳添"))
        athleteList.add(new Athlete(cyclicBarrier, "路人甲"))
        athleteList.add(new Athlete(cyclicBarrier, "路人乙"))

        Executor executor = Executors.newFixedThreadPool(athleteList.size())
        for (Athlete athlete : athleteList) {
            Thread.sleep(200)
            executor.execute(athlete)
        }
        cyclicBarrier.await()
        println "全都准备完成2"
        executor.shutdown()
    }

    private static class Athlete implements Runnable {
        private CyclicBarrier mCyclicBarrier
        private String mName

        Athlete(CyclicBarrier cyclicBarrier, String name) {
            mCyclicBarrier = cyclicBarrier
            mName = name
        }

        @Override
        void run() {
            Thread.sleep(100)
            println "$mName 就位"
            mCyclicBarrier.await()
            println "$mName : ${new Random().nextDouble() + 9}"
            if (new Random().nextDouble() < 0.5) {
                throw new RuntimeException("sdfsdf $mName")

            }
        }
    }

    private static void testFibonacci() {
        long time = System.currentTimeMillis()
        Integer answer = ForkJoinPool.commonPool().invoke(new Fibonacci(5))
        println "结果为：${answer}，总耗时:${System.currentTimeMillis() - time}"
    }


    private static int computeCount = 0
    private static class Fibonacci extends RecursiveTask<Integer> {
        int n

        Fibonacci(int n) {
            this.n = n
        }

        @Override
        protected Integer compute() {
            computeCount++
            System.out.printf("${Thread.currentThread()} | n = ${n}")

            if (n <= 2)
                return 1
            Fibonacci f1 = new Fibonacci(n - 1)
            f1.fork()

            try {
                Thread.sleep(1000)
            } catch (InterruptedException e) {
                e.printStackTrace()
            }

            Fibonacci f2 = new Fibonacci(n - 2)
            f2.fork()
            try {
                Thread.sleep(1000)
            } catch (InterruptedException e) {
                e.printStackTrace()
            }
            System.out.printf("wati temp answer is :" + n + "\n")
            int answer = f1.join() + f2.join()
            System.out.printf("temp answer is :" + answer + ",  n is :" + n + "\n")
            return answer
        }
    }

    private static void testCounting() {
        String path = ForkJoinPoolTest.class.getResource("/").getFile()
        File file = new File(path).parentFile.parentFile.parentFile.parentFile.parentFile
        println "路径为：${file.absolutePath}"
        long time = System.currentTimeMillis()
        Integer count = ForkJoinPool.commonPool().invoke(new CountingTask(file))
        println "文件总数为：${count}，总耗时:${System.currentTimeMillis() - time}"
    }

    private static class CountingTask extends RecursiveTask<Integer> {
        private File dir

        CountingTask(File dir) {
            this.dir = dir
        }

        @Override
        protected Integer compute() {
            int count = 0
            File[] files = dir.listFiles()
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        // 对每个子目录都新建一个子任务。
                        CountingTask countingTask = new CountingTask(f)
                        countingTask.fork()
                        count += countingTask.join()
                    } else {
                        count++
                    }
                }
            }
            return count
        }
    }
}
