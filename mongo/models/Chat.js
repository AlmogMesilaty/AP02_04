import mongoose from 'mongoose';

const Schema = mongoose.Schema;

const chat = new Schema({

    id : {
        type: Number,
        required: true
    },
    users : [ 
        {
        type: Schema.Types.ObjectId,
        ref: 'User' 
        }
    ],
    messages: [
        {
        type: Schema.Types.ObjectId,
        ref: 'Message',
        default: "Welcome!"
        }
    ]
});

const Chat = mongoose.model('Chat', chat);
export { Chat };