import mongoose from 'mongoose';

const Schema = mongoose.Schema;

const token = new Schema({

    token : {
        type: String,
        nullable: true,
        required: true
    }
});

const Token = mongoose.model('Token', token);
export { Token };