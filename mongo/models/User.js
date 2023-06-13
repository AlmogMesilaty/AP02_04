import mongoose from 'mongoose';

const Schema = mongoose.Schema;

const user = new Schema({

    username : {
        type: String,
        nullable: true,
        required: true
    },
    displayName : {
        type: String,
        nullable: true,
        required: true
    },
    profilePic : {
        type: String,
        nullable: true,
        required: true
    },
});

const User = mongoose.model('User', user);
export { User };