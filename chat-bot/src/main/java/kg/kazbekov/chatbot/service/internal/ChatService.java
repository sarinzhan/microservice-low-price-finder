package kg.kazbekov.chatbot.service.internal;

import kg.kazbekov.chatbot.model.Chat;
import kg.kazbekov.chatbot.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService extends BaseService<Chat, Long> {
    private final ChatRepository chatRepository;

    public Chat findByChatId(Long chatId) {
        return chatRepository.findByChatId(chatId).orElse(null);
    }

}
