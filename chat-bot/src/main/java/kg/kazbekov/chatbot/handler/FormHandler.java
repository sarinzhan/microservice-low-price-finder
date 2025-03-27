package kg.kazbekov.chatbot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormHandler {
//    private final List<RegisterUserForm> forms;

//    @Autowired
//    @Lazy
//    private BotInitialization botInitialization;

//    private final UserService userService;

//    @PostConstruct
//    void postConstruct(){
//        if(botInitialization == null){
//            throw new RuntimeException("BotInitialization bean didn't injected");
//        }
//    }
//
//    public Object handle(TelegramRequest telegramRequest){
//        var optionalHandler = forms.stream()
//                .filter(form -> form.condition(telegramRequest))
//                .findFirst();
//
//        if(optionalHandler.isEmpty()) {
//            throw new RuntimeException("Form handler didn't found");
//        }
//
//        return optionalHandler.get()
//                    .handle(telegramRequest);
//    }
//
//    public boolean condition(TelegramRequest request){
//        return forms.stream()
//                .anyMatch(form -> form.condition(request));
//    }
//
//    public void initForm(TelegramRequest request){
//        CompletableFuture.runAsync(() -> {
//            var form = new RegisterUserForm(request, userService, forms);
//            forms.add(form);
//            var response = (SendMessage) form.handle(request);
//            try {
//                Thread.sleep(1000);
//                botInitialization.execute(response);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
}
