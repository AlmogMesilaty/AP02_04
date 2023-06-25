
import { UserPass } from '../models/UserPass.js';
import { FcmToken } from '../models/FcmToken.js';
import { CreateToken, CreateFcmToken } from '../services/Token.js';


//POST - /api/tokens
// done
const createToken = async (req, res) => {

    const { username, password } = req.body;

    const user = await UserPass.findOne({ username: username })

    if (user && user.password === password) {
      const token = await CreateToken(username);
      // Send the token
      res.status(200).json(token);

    } else {
      res.status(500).send(null);
    }
};

//POST - /api/tokens/FCM
const createFcmToken = async(req, res) => {
  
  const { fcmToken } = req.body;

  const existingFcmToken = await FcmToken.findOne({ fcmToken: fcmToken });

  if (existingFcmToken == null) {

    const newFcmToken = await CreateFcmToken(fcmToken);

    res.status(200).json(newFcmToken);

  } else {

      res.status(200).json(existingFcmToken);

  }

  

};


export { createToken, createFcmToken };