package kg.kazbekov.chatbot.repository;

import kg.kazbekov.chatbot.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChatId(Long chatId);
}
