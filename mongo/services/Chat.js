import {Chat} from '../models/Chat.js'
import {ChatId} from '../models/ChatId.js'

const createUserChat = async (users, messages) => {
    const id = await createChatId();
    const chat = new Chat (
        {  
            id: id,
            users: users,
            messages: messages
        })
    await chat.save();
    return chat;
}

const createChatId = async () => {
    var currentId = await ChatId.findOne();
    if(currentId) {
        const newId = currentId.id + 1;
        await ChatId.findOneAndReplace({id:currentId.id}, {id:newId});
        return currentId.id;
    }
    var currentId = 0;
    const newId = currentId + 1;
    const id = new ChatId({ id: newId });
    await id.save();
    return currentId;
}

export { createUserChat };
