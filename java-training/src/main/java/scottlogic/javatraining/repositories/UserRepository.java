package scottlogic.javatraining.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import scottlogic.javatraining.models.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<UserAccount, UUID> {
    Optional<UserAccount> findById(UUID id);
}
