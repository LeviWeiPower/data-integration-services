package com.test.creeper;

/*
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@Transactional
//@SpringApplicsationConfiguration(ApplicationRun.class)
@SpringBootTest(classes = ApplicationRun.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)*/
public class CreeperTest {
	/*
	@Autowired
	private CreeperBeanService creeperBeanService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@Test
	public void HobbyLable() throws InterruptedException {
//		System.out.println(creeperBeanService.hashCode());
		System.out.println(applicationContext.getBean(CreeperBeanService.class).hashCode());
		System.out.println(applicationContext.getBean(CreeperBeanService.class).hashCode());
				System.out.println("开始");
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					creeperBeanService.startCrawlingData("system", 1);
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
			}

		});
		thread.start();
		long id = thread.getId();
		
		Thread thread2 = ThreadUtils.getThreadById(id);
		System.out.println(thread2.getName());
		System.out.println(thread2.getState().toString());
		
		System.out.println(ThreadUtils.interruptThread(thread2));
		
		System.out.println(thread2.isInterrupted());
		System.out.println(ThreadUtils.getThreadById(id).getState().toString());
		
		System.out.println("111");
	}*/
	
}
