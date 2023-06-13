import { Message } from '../models/Message.js'
import { MessageId } from '../models/MessageId.js'
import { Chat } from '../models/Chat.js'
//import { ChatId } from '../models/ChatId.js'


const createUserMessage = async ( chatId, sender, content) => {
    const id = await createMessageId(chatId);
    const message = new Message (
        { 
            id: id.messageId, 
            //created: created, created by default in models
            sender: sender,
            content: content
        })
    // Saves the message to the messages collection
    const currentChat = await Chat.findOne({ id: chatId });
    currentChat.messages.unshift(message);
    await currentChat.save();
    await message.save();
    return;
}

const createMessageId = async (chatId) => {
    var currentId = await MessageId.findOne({ chatId: chatId });
    if(currentId) {
        const newId = currentId.messageId + 1;
        await MessageId.findOneAndReplace({chatId: chatId}, {messageId: newId});
        return currentId;
    }
    currentId = new MessageId({ chatId: chatId, messageId: 0 })
    const newId = new MessageId({ chatId: chatId, messageId: 1 });
    await newId.save();
    return currentId;
}

export { createUserMessage };
