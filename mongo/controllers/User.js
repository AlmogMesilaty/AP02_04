
import jwt from "jsonwebtoken";
import { User } from '../models/User.js';


// GET /api/Users/:username
// done
const GetUserDetails = async (req, res) => {

  // If the request has an authorization header
  if (req.headers.authorization) {
    // Extract the token from that header
    const tokenRaw = req.headers.authorization.split(" ")[1];
    const tokenJson =JSON.parse(tokenRaw);
    const token = tokenJson.token;

    try {
      // verify the token is valid
      const data = jwt.verify(token, "secret");
      // extract user name from decoded token
      const user_name = data;
      // finds user by username in the data base
      const user = await User.findOne({ username: user_name });
      // parse user to its attributes
      const { username, displayName, profilePic } = user;
      // reutrns response to client
      res.status(200).json({ username, displayName, profilePic });

   } catch (err) {
      return res.status(401).send("Invalid Token");
    }
  }
  else {
    return res.status(403).send("Token required");
  }
};


export { GetUserDetails };




