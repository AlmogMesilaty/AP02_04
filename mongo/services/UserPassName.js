import { UserPassName } from '../models/UserPassName.js';
import { UserPass } from '../models/UserPass.js';
import { User } from '../models/User.js';

const createUserPassName = async (username, password, displayName, profilePic) => {

    const userPassName = new UserPassName (
        { 
            username: username, 
            password: password,
            displayName: displayName,
            profilePic: profilePic
        })
    await userPassName.save();

    const user = new User (
        { 
            username: username, 
            displayName: displayName,
            profilePic: profilePic
        })
    await user.save();

    const userPass = new UserPass (
        { 
            username: username, 
            password: password
        })
    await userPass.save();
    return user;
}

export { createUserPassName };
