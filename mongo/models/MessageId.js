import mongoose from 'mongoose';

const Schema = mongoose.Schema;

const scehmaMessageId = new Schema({

    chatId : {
        type: Number,
        required: true,
    },

    messageId :
        {
        type: Number,
        required: true,
        default: 0
        }

});

const MessageId = mongoose.model('MessageId', scehmaMessageId);
export { MessageId };