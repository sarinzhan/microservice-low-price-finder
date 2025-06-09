package kg.kazbekov.chatbot.service.internal;

import kg.kazbekov.chatbot.model.User;
import kg.kazbekov.chatbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, Long> {
    private final UserRepository userRepository;


    public User findByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId).orElse(null);
    }
}
