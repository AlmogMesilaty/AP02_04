import mongoose from 'mongoose';

const Schema = mongoose.Schema;

const userPassName = new Schema({

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
    displayName : {
        type: String,
        nullable: true,
        required: true
    },
    profilePic : {
        type: String,
        nullable: true,
        required: false
    }

});

const UserPassName = mongoose.model('UserPassName', userPassName);

export { UserPassName };