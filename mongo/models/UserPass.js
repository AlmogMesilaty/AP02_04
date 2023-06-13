import mongoose from 'mongoose';

const Schema = mongoose.Schema;

const userPass = new Schema({

    username : {
        type: String,
        nullable: true,
        required: true
    },
    password : {
        type: String,
        nullable: true,
        required: true
    },
});

const UserPass = mongoose.model('UserPass', userPass);

export { UserPass };