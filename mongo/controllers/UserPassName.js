import { createUserPassName } from '../services/UserPassName.js';
import { UserPass } from '../models/UserPass.js';


const createUser = async(req, res) => { 

    const { username } = req.body;

    const user = await UserPass.findOne({ username: username })

    if (!user) {
        res.status(200).json(await createUserPassName (
            req.body.username,
            req.body.password,
            req.body.displayName, 
            req.body.profilePic
            ));

    } else {
      res.status(500).send(null);
    }

};

export { createUser };

