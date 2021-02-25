package com.gang.study.thread.aqs.demo.logic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Classname Sample2Lock
 * @Description TODO
 * @Date 2021/2/25 22:13
 * @Created by zengzg
 */
public class Sample2Lock implements Lock {

    private Sync sync = new Sync();

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }


    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg){
            //如果第一个线程进来，拿到锁，返回TRUE

            //如果第二个线程进来，返回FALSE，拿不到锁

            int state = getState();
            if(state == 0){
                if(compareAndSetState(0,arg)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }

            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            //锁的获取和释放需要11对应，那么调用这个方法的线程，一定是当前线程让。
            if(Thread.currentThread() != getExclusiveOwnerThread()){
                throw new RuntimeException();
            }
            int state = getState() - arg;
            setState(state);
            if(state == 0){
                setExclusiveOwnerThread(null);
                return true;
            }
            return false;
        }

        // 判断当前独占锁是否被持有
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // 提供条件队列功能
        Condition newCondition() {
            return new ConditionObject();
        }
    }
}
