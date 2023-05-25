package com.chatappspring.chat;

import com.chatappspring.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChatController {

    private ChatRepository chatRepository;
    private UserService userService;
    @Autowired
    public ChatController(ChatRepository chatRepository, UserService userService){
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @GetMapping("chat-room")
    public String displayChatRoom(@CookieValue(value = "loggedInUserId") String userId, Model model){

        try {
            if (userId.isEmpty()) throw new RuntimeException("User session expired, please login to try again");
            model.addAttribute("user", this.userService.findUserById(Long.parseLong(userId)));
            model.addAttribute("chatList", this.chatRepository.findAll());
            return "chat-room";
        }catch (Exception e){
            return "redirect:login?status=CHAT_ROOM_ERROR&message=" + e.getMessage();
        }
    }

    @PostMapping("send-message")
    public String sendMessage(@CookieValue(value = "loggedInUserId", defaultValue = "") String userId, Chat message, ChatRequest chatRequest){

        try {
            if (userId.isEmpty()) throw new RuntimeException("User session expired, please login to try again");
            Chat chat = new Chat();
            chat.setMessage(chatRequest.getMessage());
            chat.setSender(this.userService.findUserById(Long.parseLong(userId)));
            System.out.println(userId + " : " + chatRequest);
            this.chatRepository.save(chat);
            return "redirect:chat-room";
        }catch (Exception e){
            return "redirect:login?status=CHAT_ROOM_ERROR&message=" + e.getMessage();
        }
    }
}
