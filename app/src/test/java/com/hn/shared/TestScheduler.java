package com.hn.shared;


import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by stevenpungdumri on 6/29/17.
 */

public class TestScheduler implements TestRule {
    private Scheduler immediate = new Scheduler() {
        @Override
        public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
            // this prevents StackOverflowErrors when scheduling with a delay
            return super.scheduleDirect(run, 0, unit);
        }

        @Override
        public Scheduler.Worker createWorker() {
            return new ExecutorScheduler.ExecutorWorker(new Executor() {
                @Override
                public void execute(@android.support.annotation.NonNull Runnable command) {
                    command.run();
                }
            });
        }
    };

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setInitIoSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(@NonNull Callable<Scheduler> scheduler) throws Exception {
                        return immediate;
                    }
                });
                RxJavaPlugins.setInitComputationSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(@NonNull Callable<Scheduler> scheduler) throws Exception {
                        return immediate;
                    }
                });
                RxJavaPlugins.setInitNewThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(@NonNull Callable<Scheduler> scheduler) throws Exception {
                        return immediate;
                    }
                });
                RxJavaPlugins.setInitSingleSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(@NonNull Callable<Scheduler> scheduler) throws Exception {
                        return immediate;
                    }
                });
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                    @Override
                    public Scheduler apply(@NonNull Callable<Scheduler> scheduler) throws Exception {
                        return immediate;
                    }
                });

                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}
