package my.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableAsync
@EnableScheduling
public class TaskConfig {


	// 以下的方法将以一个固定延迟时间5秒钟调用一次执行，
	// 这个周期是以上一个调用任务的完成时间为基准，在上一个任务完成之后，5s后再次执行
	@Scheduled(fixedDelay=5000)
	public void doSomething() {
		System.out.println("run doSomething1");
	}

	// 以下方法将以一个固定速率5s来调用一次执行，
	// 这个周期是以上一个任务开始时间为基准，从上一任务开始执行后5s再次调用
	@Scheduled(fixedRate=5000)
	public void doSomething2() {
		System.out.println("run doSomething2");
	}
	// 对于固定延迟和固定速率的任务，
	// 可以指定一个初始延迟表示该方法在第一被调用执行之前等待的毫秒数
	@Scheduled(initialDelay=1000, fixedRate=5000)
	public void doSomething3() {
		System.out.println("run doSomething3");
	}

	@Scheduled(cron="0 0 10 * * ?") // 每天上午10:15触发
	public void doSomething4() {
		System.out.println("run doSomething4");
	}

	/*
	1、spring的注解@Scheduled  需要写在实现方法上；
　　2、定时器的任务方法不能有返回值（如果有返回值，spring初始化的时候会告诉你有个错误、需要设定一个proxytargetclass的某个值为true），不能指向任何的参数；
　　3、如果该方法需要与应用程序上下文的其他对象进行交互，通常是通过依赖注入来实现；
　　4、实现类上要有组件的注解@Component。
	 */
}
