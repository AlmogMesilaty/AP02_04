<<<<<<< HEAD
import mongoose from 'mongoose';

const Schema = mongoose.Schema;

const chatId = new Schema({

    id : {
        type: Number,
        required: true,
        default: 0
    }

});

const ChatId = mongoose.model('ChatId', chatId);
=======
import mongoose from 'mongoose';

const Schema = mongoose.Schema;

const chatId = new Schema({

    id : {
        type: Number,
        required: true,
        default: 0
    }

});

const ChatId = mongoose.model('ChatId', chatId);
>>>>>>> origin/ChatPage_localDB
export { ChatId };