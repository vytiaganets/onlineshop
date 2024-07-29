package com.example.onlineshopproject.bot;

import com.example.onlineshopproject.entity.*;
import com.example.onlineshopproject.repository.*;
import com.example.onlineshopproject.service.RAGAssistant;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final String botName;
    @Autowired
    private RAGAssistant ragAssistant;
    @Autowired
    private TelegramUserRepository telegramUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    static final String YES_BUTTON = "YES_BUTTON";
    static final String NO_BUTTON = "NO_BUTTON";
    static final String ERROR_TEXT = "Error occurred: ";
    private static final String HELP_TEXT = "This bot is created demonstrate online shop capabilities.\n\n" +
            "You can execute commands from the main menu on the left or by typing a command: \n\n" +
            "Type /start to see a welcome message \n\n" +
            "Type /users to see users \n\n" +
            "Type /categories to see categories \n\n" +
            "Type /products to see products \n\n" +
            "Type /orders to see orders \n\n" +
            "Type /help to see this message again";

    public TelegramBot(String botName, String botToken) {
        super(botToken);
        this.botName = botName;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
//        listOfCommands.add(new BotCommand("/register", "register a new user"));
        listOfCommands.add(new BotCommand("/users", "get users"));
        listOfCommands.add(new BotCommand("/categories", "get categories"));
        listOfCommands.add(new BotCommand("/products", "get products"));
        listOfCommands.add(new BotCommand("/orders", "get orders"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bots' command list" + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
           long chatId = message.getChatId();
            log.info("Message received: {}", message.getChatId());
            String messageText = message.getText();
            log.info(messageText);
            if (messageText.contains("/send")) {
                var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                var users = telegramUserRepository.findAll();
                for (TelegramUser user : users) {
                    prepareAndSendMessage(user.getChatId(), textToSend);
                }
            } else {
            switch (messageText) {
                case "/start":
                    try {
                        registerUser(update.getMessage());
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
//                case "/register":
//                    register(chatId);
//                    break;
                case "/users":
                    sendUsers();
                    break;
                case "/categories":
                    sendCategories();
                    break;
                case "/products":
                    sendProducts();
                    break;
                case "/orders":
                    sendOrders(chatId);
                    break;
                case "/help":
                    prepareAndSendMessage(chatId, HELP_TEXT);
                    break;
                default:
                    var response = ragAssistant.chat(chatId, messageText);
                    try {
                        execute(new SendMessage(Long.toString(chatId), response));
                    } catch (TelegramApiException e) {
                        log.error("Exception during processing Telegram API: {}", e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals(YES_BUTTON)) {
                String text = "You pressed YES button";
                executeEditMessageText(text, chatId, messageId);
            } else if (callbackData.equals(NO_BUTTON)) {
                String text = "You pressed NO button";
                executeEditMessageText(text, chatId, messageId);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }
//    private void register(long chatId) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText("Do you really want to register?");
//
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
//        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
//        var yesButton = new InlineKeyboardButton();
//        yesButton.setText("Yes");
//        yesButton.setCallbackData(YES_BUTTON);
//        var noButton = new InlineKeyboardButton();
//        noButton.setText("No");
//        noButton.setCallbackData(NO_BUTTON);
//        rowInLine.add(yesButton);
//        rowInLine.add(noButton);
//        rowsInLine.add(rowInLine);
//        markupInline.setKeyboard(rowsInLine);
//        message.setReplyMarkup(markupInline);
//        executeMessage(message);
//
//    }
    private void registerUser(Message msg) {
        if (telegramUserRepository.findById(msg.getChatId()).isEmpty()) {
            var chatId = msg.getChatId();
            var chat = msg.getChat();
            TelegramUser user = new TelegramUser();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            telegramUserRepository.save(user);
            log.info("user saved" + user);
        }
    }
    private void startCommandReceived(long chatId, String name) throws TelegramApiException {
        String answer = EmojiParser.parseToUnicode("Hi, " + name + ", nice to meet enjoy!" + " :blush:");
        //https://emojipedia.org
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/users");
        row.add("/categories");


        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add("/products");
        row.add("/orders");

        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
        executeMessage(message);
    }
    private void executeEditMessageText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
            throw new RuntimeException(e);
        }

    }
    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void prepareAndSendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }

    private void sendUsers() {
        var usersEntity = userRepository.findAll();
        var users = telegramUserRepository.findAll();
        for (UserEntity userEntity : usersEntity) {
            for (TelegramUser user : users) {
                prepareAndSendMessage(user.getChatId(),
                        userEntity.getUserId() +
                                ". " + userEntity.getName() +
                                ", " + userEntity.getEmail() +
                                ", " + userEntity.getPhoneNumber() +
                                ", " + userEntity.getRole());
            }
        }
    }

    private void sendCategories() {
        var categories = categoryRepository.findAll();
        var users = telegramUserRepository.findAll();
        for (CategoryEntity category : categories) {
            for (TelegramUser user : users) {
                prepareAndSendMessage(user.getChatId(),
                        category.getCategoryId() +
                                ". " + category.getName());
            }
        }
    }

    private void sendProducts() {
        var products = productRepository.findAll();
        var users = telegramUserRepository.findAll();
        for (ProductEntity product : products) {
            for (TelegramUser user : users) {
                prepareAndSendMessage(user.getChatId(),
                        product.getProductId() +
                                ". " + product.getName() +
                                ", " + product.getDescription() +
                                ", " + product.getPrice() + "$" +
                                ", discount " + product.getDiscountPrice() + "$");
            }
        }
    }
    private void sendOrders(long chatId) {
        var orders = orderRepository.findAll();
        var users = telegramUserRepository.findAll();
        for (OrderEntity order : orders) {
            for (TelegramUser user : users) {
                prepareAndSendMessage(user.getChatId(),
                        order.getOrderId() +
                                ". delivery address: " + order.getDeliveryAddress() +
                                ", delivery method: " + order.getDeliveryMethod() +
                                ", contact phone: " + order.getContactPhone() +
                                ", status: " + order.getStatus() +
                                ", created at: " + order.getCreatedAt() +
                                ", updated at: " + order.getUpdatedAt());
            }
        }
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Do you really want to order?");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();
        yesButton.setText("Yes");
        yesButton.setCallbackData(YES_BUTTON);
        var noButton = new InlineKeyboardButton();
        noButton.setText("No");
        noButton.setCallbackData(NO_BUTTON);
        rowInLine.add(yesButton);
        rowInLine.add(noButton);
        rowsInLine.add(rowInLine);
        markupInline.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInline);
        executeMessage(message);
    }

}
