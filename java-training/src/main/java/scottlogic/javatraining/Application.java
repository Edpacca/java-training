package scottlogic.javatraining;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import scottlogic.javatraining.repositories.OrderRepository;
import scottlogic.javatraining.repositories.TradeRepository;
import scottlogic.javatraining.repositories.UserRepository;
import scottlogic.javatraining.services.*;

@SpringBootApplication
@EnableMongoRepositories
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public OrderService orderService(
			@Autowired OrderRepository orderRepository,
			@Autowired TradeRepository tradeRepository) {
		return new OrderService(new Matcher(), new Trader(), orderRepository, tradeRepository);
	}

	@Bean
	public TradeService tradeService(@Autowired TradeRepository tradeRepository) {
		return new TradeService(tradeRepository);
	}

	@Bean
	public UserService userService(@Autowired UserRepository userRepository) {
		return new UserService(userRepository);
	}
}
