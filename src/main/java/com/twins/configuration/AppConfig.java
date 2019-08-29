package com.twins.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
//@EnableTransactionManagement
//@EnableAspectJAutoProxy
//@EnableJpaRepositories(basePackages = "com")
@ComponentScan("com")
public class AppConfig{
/*
	// Configuration Beans
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
    	Properties hibernateProperties = new Properties();
    	hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	hibernateProperties.put("hibernate.show_sql", "true");
    	// hibernateProperties.put("hibernate.show_sql", false);
    	// hibernateProperties.put("hibernate.generate_statistics", false);
    	// hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
    	// hibernateProperties.put("hibernate.use_sql_comments", false);

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setHibernateProperties(hibernateProperties);
		sessionFactory.setAnnotatedClasses(User.class,
										   UserPersonalDetails.class,
										   Project.class,
										   ProjectPhase.class,
										   ProjectTask.class,
										   Comment.class
										   );
		/*SessionFactory sessionFactory = new org.hibernate.cfg.Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(UserPersonalDetails.class)
				.buildSessionFactory();
				*/
/*		return sessionFactory;
	}

    @Bean(name="transactionManager")
    public PlatformTransactionManager txManager() {
        return new HibernateTransactionManager(sessionFactory().getObject());
    }

    @Bean
    public DataSource dataSource() {
    	ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
			dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
			dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/pro001?useSSL=false&amp");
	        dataSource.setUser("hbstudent");
	        dataSource.setPassword("hbstudent");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabase(Database.MYSQL);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com");
        factory.setDataSource(dataSource);
        return factory;
    }

    @Bean
    public LoggingAspect loggingAspect() {
    	return new LoggingAspect();
    }
 */
}
