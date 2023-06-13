import mongoose from 'mongoose';

const Schema = mongoose.Schema;

const message = new Schema({

    id : {
        type: Number,
        required: true
    },
    created : {
        type: Date,
        default: Date.now

    },
    sender: {
        type: Schema.Types.ObjectId,
        ref: 'User'

    },
    content : {
        type: String,
        nullable: true,
        default: ""

    }
});

const Message = mongoose.model('Message', message);
export { Message };