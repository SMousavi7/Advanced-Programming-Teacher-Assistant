import java.util.concurrent.CountDownLatch;

class part_of_bucketSort implements Runnable{
    int[] nums;
    CountDownLatch countDownLatch;
    int n;

    part_of_bucketSort(int[] nums, int n,CountDownLatch countDownLatch){
        this.nums = nums;
        this.n = n;
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        System.out.println("thread " + Thread.currentThread().getName() + " is working...");
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n - 1; j++)
            {
                if(nums[j] > nums[j + 1])
                {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
        countDownLatch.countDown();
    }
}


public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] nums = new int[500];
        int[] bucket1 = new int[500];
        int[] bucket2 = new int[500];
        int[] bucket3 = new int[500];
        int[] bucket4 = new int[500];
        int bucket1_len = 0;
        int bucket2_len = 0;
        int bucket3_len = 0;
        int bucket4_len = 0;
        CountDownLatch countDownLatch = new CountDownLatch(4);

        for(int i = 0; i < 500; i++)
        {
            nums[i] = (int) (Math.random() * 40);
        }

        for(int i = 0; i < 500; i++)
        {
            if(nums[i] < 10){
                bucket1[bucket1_len] = nums[i];
                bucket1_len++;
            }
            else if(nums[i] >= 10 && nums[i] < 20)
            {
                bucket2[bucket2_len] = nums[i];
                bucket2_len++;
            }
            else if(nums[i] >= 20 && nums[i] < 30)
            {
                bucket3[bucket3_len] = nums[i];
                bucket3_len++;
            }
            else
            {
                bucket4[bucket4_len] = nums[i];
                bucket4_len++;
            }
        }
        Thread t1 = new Thread(new part_of_bucketSort(bucket1, bucket1_len, countDownLatch), "first sorter");
        Thread t2 = new Thread(new part_of_bucketSort(bucket2, bucket2_len, countDownLatch), "second sorter");
        Thread t3 = new Thread(new part_of_bucketSort(bucket3, bucket3_len, countDownLatch), "third sorter");
        Thread t4 = new Thread(new part_of_bucketSort(bucket4, bucket4_len, countDownLatch), "forth sorter");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        countDownLatch.await();
        for(int i = 0; i < bucket1_len; i++)
        {
            System.out.println(bucket1[i]);
        }
        System.out.println("--------------------------------");
        for(int i = 0; i < bucket2_len; i++)
        {
            System.out.println(bucket2[i]);
        }
        System.out.println("--------------------------------");
        for(int i = 0; i < bucket3_len; i++)
        {
            System.out.println(bucket3[i]);
        }
        System.out.println("--------------------------------");
        for(int i = 0; i < bucket4_len; i++)
        {
            System.out.println(bucket4[i]);
        }
        System.out.println("--------------------------------");

    }
}
