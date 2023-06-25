import { Token } from '../models/token.js';
import { FcmToken } from '../models/FcmToken.js'
import jwt from "jsonwebtoken";

// POST - api/Tokens
// done
const CreateToken = async (username) => {

  // Generate the token.
  const temp = jwt.sign(username, "secret");
  const token = new Token(
    {
      token: temp
    })

  // Save the token to the database
  await token.save();
  return token;
}

// POST - api/Tokens/FCM
const CreateFcmToken = async(fcmToken ) => {
  const newFcmToken = new FcmToken({
    fcmToken : fcmToken
  })
  await newFcmToken.save();
  return newFcmToken;
}

export { CreateToken, CreateFcmToken };
